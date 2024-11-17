package pl.pokemoncli;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.display.*;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;
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
	private final PanelDisplay panelDisplay;
	private final GameDisplay gameDisplay;
	private final AudioSystem audioSystem;

	static int GAME_X, GAME_Y;
	private Level level;
	private Player player;
	private Fight fight;

	int tickTimer = 0;

	public PokemonCLI(DoubleBufferedTerminal terminal, PanelDisplay panelDisplay, GameDisplay gameDisplay, FightDisplay fightDisplay)
	{
		this.terminal = terminal;
		this.panelDisplay = panelDisplay;
		this.gameDisplay = gameDisplay;
		this.fightDisplay = fightDisplay;
		this.audioSystem = new AudioSystem();
	}

	public static void main(String[] args) throws InterruptedException
	{
		DoubleBufferedTerminal dbTerminal = new DoubleBufferedTerminal();
		INSTANCE = new PokemonCLI(dbTerminal, new PanelDisplay(dbTerminal), new GameDisplay(dbTerminal), new FightDisplay(dbTerminal));
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
				fightDisplay.drawFightScreen(fight);
			else
				gameDisplay.drawWholeMap(player, level, GAME_X, GAME_Y, tickTimer);
			panelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
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
		//TODO: 16.11.2024 fight screen controls
		//TODO: 16.11.2024 current screen enum (?)

		ActionResult result = switch(key.getCharacter())
		{
			case 'w' -> level.moveCharacterBy(player, 0, -1);
			case 'a' -> level.moveCharacterBy(player, -1, 0);
			case 's' -> level.moveCharacterBy(player, 0, 1);
			case 'd' -> level.moveCharacterBy(player, 1, 0);
			default -> null;
		};

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
		player = new Player("Ash", 5, 5, 100, 100);
		fight = null;
		level.addCharacter(player);

		level.paintTerrain(0, 9, 20, 12, Terrain.WATER_FLOWING);
		level.paintTerrain(20, 8, 31, 11, Terrain.WATER_FLOWING);
		level.paintTerrain(5, 8, 5, 13, Terrain.BRIDGE1);
		level.paintTerrain(6, 8, 6, 13, Terrain.BRIDGE2);

		level.setTerrain(2, 2, Terrain.BLOCKED);
		level.setTerrain(3, 2, Terrain.BLOCKED);
		level.setTerrain(4, 2, Terrain.BLOCKED);
		level.setTerrain(4, 3, Terrain.BLOCKED);

		level.addCharacter(new Enemy("Psi Syn", 10, 5, 10, 10));
		level.addCharacter(new Enemy("Czlowiek", 3, 12, 10, 10));
	}
}