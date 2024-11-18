package pl.pokemoncli;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import pl.pokemoncli.display.*;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Door;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.NPC;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.pokemon.MoveList;
import pl.pokemoncli.logic.pokemon.Pokedex;
import pl.pokemoncli.logic.pokemon.Pokemon;
import pl.pokemoncli.sound.AudioSystem;
import pl.pokemoncli.sound.AudioSystem.Track;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class PokemonCLI
{
	private static PokemonCLI INSTANCE;
	private final DoubleBufferedTerminal terminal;
	private final FightDisplay fightDisplay;
	private final FightMenuDisplay fightMenuDisplay;
	private final PanelDisplay panelDisplay;
	private final GameDisplay gameDisplay;
	private final AudioSystem audioSystem;

	static int GAME_X, GAME_Y;
	private Level level;
	private Player player;
	private Fight fight;

	public Pokedex pokedex;
	public MoveList moveList;

	int tickTimer = 0;

	public PokemonCLI(DoubleBufferedTerminal terminal, PanelDisplay panelDisplay, GameDisplay gameDisplay, FightDisplay fightDisplay, FightMenuDisplay fightMenuDisplay)
	{
		this.terminal = terminal;
		this.panelDisplay = panelDisplay;
		this.gameDisplay = gameDisplay;
		this.fightDisplay = fightDisplay;
		this.fightMenuDisplay = fightMenuDisplay;
		this.audioSystem = new AudioSystem();
		this.pokedex = new Pokedex();
		this.moveList = new MoveList();
	}

	public static void main(String[] args) throws InterruptedException
	{
		DoubleBufferedTerminal dbTerminal = new DoubleBufferedTerminal();
		INSTANCE = new PokemonCLI(dbTerminal, new PanelDisplay(dbTerminal), new GameDisplay(dbTerminal), new FightDisplay(dbTerminal), new FightMenuDisplay(dbTerminal));
		INSTANCE.loadGame();
		INSTANCE.displayMenu();
		INSTANCE.displayGame();
	}

	private void displayMenu()
	{
		//TODO: 16.11.2024 main menu
	}

	private void displayGame() throws InterruptedException
	{
		terminal.init();
		GAME_Y = Math.min(terminal.getWidth(), terminal.getHeight());
		GAME_X = (int)(GAME_Y*(Tile.TILE_SIZE_X/(float)Tile.TILE_SIZE_Y));

		while(true)
		{
			if(fight!=null)
			{
				fightDisplay.drawFightScreen(fight, GAME_X, GAME_Y);
				panelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
				fightMenuDisplay.drawMenuPanel(fight, GAME_X, GAME_Y);
			}
			else
			{
				gameDisplay.drawWholeMap(player, level, GAME_X, GAME_Y, tickTimer);
				panelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
			}
			terminal.flush();

			handleMusic();

			//Clean get top key and clear queue to prevent lag
			boolean handled = false;
			Key key;
			while((key = terminal.readInput())!=null)
				if(handled||handleKeyInput(key))
					handled = true;

			//A mirmir
			Thread.sleep(100);
			tickTimer = (tickTimer+1)%20;
		}

//		terminal.end();
	}

	private void handleMusic()
	{
		if(this.fight!=null)
			audioSystem.play(Track.FIGHT);
		else
			audioSystem.play(AudioSystem.Track.GAME);
	}

	private boolean handleKeyInput(Key key)
	{
		//TODO: 16.11.2024 current screen enum (?)
		ActionResult result;
		if(fight!=null)
		{
			if(key.getKind().equals(Kind.Escape))
				result = fight.goBack(true, fight.getButton());
			else result = switch(key.getCharacter()) {
				case 'w' -> fight.moveButton(0, 1);
				case 'a' -> fight.moveButton(-1, 0);
				case 's' -> fight.moveButton(0, -1);
				case 'd' -> fight.moveButton(1, 0);
				case ' ' -> {
					if(fight.getButton()==Fight.Button.RUN&&fight.isMainMenu())
					{
						fight = null;
						yield new Level.ActionResult(Level.ResultType.MOVE);
					}
					else
					{
						yield fight.selectButton();
					}
				}
				default -> null;
			};
		}
		else
		{
			result = switch(key.getCharacter())
			{
				case 'w' -> level.moveCharacterBy(player, 0, -1);
				case 'a' -> level.moveCharacterBy(player, -1, 0);
				case 's' -> level.moveCharacterBy(player, 0, 1);
				case 'd' -> level.moveCharacterBy(player, 1, 0);
				default -> null;
			};
		}

		if(result==null)
			return false;

		return switch(result.getResult())
		{
			case MOVE -> true;
			case MET_OBSTACLE -> false;
			case FIGHT ->
			{
				//TODO: 16.11.2024 other characters that have dialogues
				//start fight
				fight = new Fight(player, ((Enemy)result.getContactedCharacter()));
				yield true;
			}
			case CHANGE_LEVEL ->
			{
				Door door = (Door)result.getContactedCharacter();
				this.level.removeCharacter(player);
				this.level = door.getLevel();
				this.level.addCharacter(player);
				for(int i = 0; i < 2; i++)
					this.player.setPosition(door.getDestX(), door.getDestY());
				yield true;
			}
			case DIALOG -> false;
			case WILD_POKEMON ->
			{
				//display message that wild pokemon appeared, start fight
				yield false;
			}
			case COLLECT_ITEM ->
			{
				//display message that item was acquired, add item to player's inventory
				yield false;
			}
			case null -> false;

		};
	}

	private void loadGame()
	{

		level = new Level(32, 32);
		level.addCharacter(player = new Player("Ash", 5, 5, 6));
		fight = null;

		Level houseInside = new Level(7, 7);
		houseInside.paintTerrain(0, 0, 10, 10, Terrain.FLOOR);
		houseInside.addDoor(3, 6, level, 8, 4);
		houseInside.addCharacter(new NPC("Pies", 3, 3));
		houseInside.addCharacter(new NPC("Psi Syn", 5, 5));
		houseInside.addCharacter(new NPC("Czlowiek", 1, 1));


		level.placeHouse(0, 2, 2, 5, 2, 4);
		level.placeHouse(7, 3, 1, 3, 1, 4, houseInside, 3, 5);
		level.placeHouse(11, 3, 1, 3, 1, 4);
		level.placeHouse(14, 3, 1, 3, 1, 4);
		level.placeHouse(17, 3, 1, 3, 1, 4);

		level.paintTerrain(0, 6, 31, 15, Terrain.BEACH);
		level.paintTerrain(5, 0, 6, 7, Terrain.ROAD);
		for(int i = 0; i < player.getMaxPokemons(); i++)
		{
			player.addPokemon(new Pokemon(pokedex.getPokemon(133), 5));
			player.getPokemon(i).getAttacks().add(moveList.getMove(1));
			player.getPokemon(i).getAttacks().add(moveList.getMove(2));
			player.getPokemon(i).getAttacks().add(moveList.getMove(3));
			player.getPokemon(i).getAttacks().add(moveList.getMove(4));
		}

		level.paintTerrain(0, 9, 20, 12, Terrain.WATER_FLOWING);
		level.paintTerrain(20, 8, 31, 11, Terrain.WATER_FLOWING);
		level.paintTerrain(5, 8, 5, 13, Terrain.BRIDGE1);
		level.paintTerrain(6, 8, 6, 13, Terrain.BRIDGE2);

//		level.setTerrain(2, 2, Terrain.BLOCKED);
//		level.setTerrain(3, 2, Terrain.BLOCKED);
//		level.setTerrain(4, 2, Terrain.BLOCKED);
//		level.setTerrain(4, 3, Terrain.BLOCKED);

		Enemy e1, e2;
		level.addCharacter(e1 = new Enemy("Psi Syn", 10, 5, 1));
		e1.addPokemon(new Pokemon(pokedex.getPokemon(1), 5));
		level.addCharacter(e2 = new Enemy("Czlowiek", 3, 12, 1));
		e2.addPokemon(new Pokemon(pokedex.getPokemon(1), 5));
	}
}