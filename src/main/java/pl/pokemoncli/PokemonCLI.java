package pl.pokemoncli;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.display.*;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.*;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.logic.dialogue.DialogueNode;
import pl.pokemoncli.logic.dialogue.DialogueResponse;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;
import pl.pokemoncli.logic.combat.move.MoveType;
import pl.pokemoncli.sound.AudioSystem;
import pl.pokemoncli.sound.AudioSystem.Track;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class PokemonCLI
{
	private final DoubleBufferedTerminal terminal;

	private final DialogueDisplay dialogueDisplay;
	private final FightDisplay fightDisplay;
	private final FightPanelDisplay fightPanelDisplay;
	private final PanelDisplay panelDisplay;
	private final GameDisplay gameDisplay;

	private final AudioSystem audioSystem;

	public static int GAME_X, GAME_Y;
	private Level level;
	private Player player;
	private Dialogue dialogue;
	private Fight fight;

	int tickTimer = 0;

	public PokemonCLI(DoubleBufferedTerminal terminal)
	{
		this.terminal = terminal;
		this.panelDisplay = new PanelDisplay(terminal);
		this.gameDisplay = new GameDisplay(terminal);
		this.fightDisplay = new FightDisplay(terminal);
		this.fightPanelDisplay = new FightPanelDisplay(terminal);
		this.dialogueDisplay = new DialogueDisplay(terminal);
		this.audioSystem = new AudioSystem();
	}

	public static void main(String[] args) throws InterruptedException
	{
		DoubleBufferedTerminal dbTerminal = new DoubleBufferedTerminal();
		PokemonCLI instance = new PokemonCLI(dbTerminal);
		instance.loadGame();
		instance.displayMenu();
		instance.displayGame();
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

		//TODO: 19.11.2024 game end condition / return to main menu
		while(true)
		{
			//ORDER: DIALOGUE, FIGHT, WORLD
			panelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
			if(dialogue!=null)
				dialogueDisplay.drawDialogue(dialogue, GAME_X, GAME_Y);
			else if(fight!=null)
			{
				fightDisplay.drawFightScreen(fight, GAME_X, GAME_Y);
				fightPanelDisplay.drawMenuPanel(fight, GAME_X, GAME_Y);
			}
			else
				gameDisplay.drawWholeMap(player, level, GAME_X, GAME_Y, tickTimer);
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
		ActionResult result;
		if(dialogue!=null)
			result = dialogueDisplay.handleKeyInput(level, player, dialogue, fight, key);
		else if(fight!=null)
			result = fightDisplay.handleKeyInput(level, player, null, fight, key);
		else
			result = gameDisplay.handleKeyInput(level, player, null, null, key);

		if(result==null)
			return false;
		final GameObject contacted = result.getContactedGameObject();
		return switch(result.getResult())
		{
			case MOVE -> true;
			case MET_OBSTACLE -> false;
			case FIGHT ->
			{
				//start fight
				fight = new Fight(player, ((Enemy)contacted));
				yield true;
			}
			case END_OF_BATTLE ->
			{
				fight = null;
				yield true;
			}
			case CHANGE_LEVEL ->
			{
				Door door = (Door)contacted;
				this.level.removeCharacter(player);
				this.level = door.getLevel();
				this.level.addCharacter(player);
				for(int i = 0; i < 2; i++)
					this.player.setPosition(door.getDestX(), door.getDestY());
				yield true;
			}
			case DIALOG ->
			{
				if(!(contacted instanceof NPC))
					yield false;
				if(((NPC)contacted).getDialogue()==null)
					yield false;
				this.dialogue = new Dialogue(((NPC)contacted).getDialogue(),
						contacted);
				yield true;
			}
			case DIALOG_PROGRESS ->
			{
				if(dialogue.getCurrentNode()==null)
					this.dialogue = null;
				yield true;
			}
			case WILD_POKEMON ->
				//display message that wild pokemon appeared, start fight
					false;
			case COLLECT_ITEM ->
				//display message that item was acquired, add item to player's inventory
					false;
			case null -> false;

		};
	}

	private void loadGame()
	{
		level = new Level(32, 32, Terrain.GRASS);
		level.addCharacter(player = new Player("Ash", 5, 5, 6));
		fight = null;

		//--- Adding pokemons to player ---//
		player.addPokemon(new Pokemon(PokemonSpecies.EEVEE, 5)
				.withMoves(MoveType.TACKLE, MoveType.GROWL, MoveType.TAIL_WHIP, MoveType.COVET));
		player.addPokemon(new Pokemon(PokemonSpecies.BULBASAUR, 5)
				.withMoves(MoveType.TACKLE, MoveType.GROWL, MoveType.TAIL_WHIP, MoveType.COVET));

		//--- House Interior 1 ---//
		Level houseInside = new Level(7, 7, Terrain.VOID);
		houseInside.paintTerrain(0, 0, 10, 5, Terrain.FLOOR);
		houseInside.addDoor(3, 6, level, 8, 4);
		houseInside.setTerrain(3, 6, Terrain.DOOR);
		houseInside.addCharacter(new NPC("Pies", 3, 3)
				.withDialogue(new DialogueNode("Wrrrrr",
						new DialogueResponse("Onie", null),
						new DialogueResponse("Otak", new DialogueNode("(starts doing dog stuff)",
								new DialogueResponse("Creature of Dog, My gratitude upon thee for thy tricks, but the crimes-", null)
						))
				))
		);
		houseInside.addCharacter(new NPC("Psi Syn", 5, 5));
		houseInside.addCharacter(new NPC("Czlowiek", 1, 1));

		//--- House Interior 2 ---//
		Level houseInside2 = new Level(17, 7, Terrain.VOID);
		houseInside2.paintTerrain(0, 0, 4, 5, Terrain.FLOOR);
		houseInside2.paintTerrain(6, 0, 10, 5, Terrain.FLOOR);
		houseInside2.paintTerrain(12, 0, 17, 5, Terrain.FLOOR);
		houseInside2.setTerrain(2, 6, Terrain.DOOR);
		houseInside2.setTerrain(8, 6, Terrain.DOOR);
		houseInside2.setTerrain(14, 6, Terrain.DOOR);
		houseInside2.addDoor(2, 6, level, 12, 4);
		houseInside2.addDoor(8, 6, level, 15, 4);
		houseInside2.addDoor(14, 6, level, 18, 4);

		//--- Outside ---//
		level.placeHouse(0, 2, 2, 5, 2, 4);
		level.placeHouse(7, 3, 1, 3, 1, 4, houseInside, 3, 5);
		level.placeHouse(11, 3, 1, 3, 1, 4, houseInside2, 2, 5);
		level.placeHouse(14, 3, 1, 3, 1, 4, houseInside2, 8, 5);
		level.placeHouse(17, 3, 1, 3, 1, 4, houseInside2, 14, 5);

		level.addCharacter(new Enemy("Psi Syn", 10, 5, 1)
				.withPokemon(new Pokemon(PokemonSpecies.EEVEE, 1).withMoves(MoveType.TACKLE, MoveType.GROWL))
		);
		level.addCharacter(new Enemy("Czlowiek", 3, 14, 1)
				.withPokemon(new Pokemon(PokemonSpecies.EEVEE, 1).withMoves(MoveType.TACKLE, MoveType.GROWL))
		);

		level.paintTerrain(0, 6, 31, 15, Terrain.BEACH, Terrain.BEACH2);
		level.paintTerrain(5, 0, 6, 7, Terrain.ROAD);

		level.paintTerrain(2, 3, 2, 4, Terrain.ROAD);
		level.paintTerrain(2, 4, 23, 4, Terrain.ROAD);
		level.paintTerrain(10, 0, 10, 4, Terrain.ROAD);
		level.paintTerrain(23, 0, 23, 4, Terrain.ROAD);
		level.paintTerrain(24, 2, 31, 2, Terrain.ROAD);
		level.paintTerrain(5, 14, 6, 31, Terrain.ROAD);

		level.paintTerrain(21, 0, 21, 2, Terrain.BUSH1, Terrain.BUSH2);
		level.paintTerrain(24, 0, 31, 0, Terrain.TREE_TRUNK);
		level.paintTerrain(24, 3, 31, 3, Terrain.TREE_LEAVES);
		level.paintTerrain(24, 4, 31, 4, Terrain.TREE_TRUNK);

		level.setTerrain(0, 6, Terrain.BLOCKED);
		level.setTerrain(2, 7, Terrain.BLOCKED);
		level.setTerrain(8, 6, Terrain.BLOCKED);
		level.setTerrain(9, 8, Terrain.BLOCKED);
		level.setTerrain(14, 7, Terrain.BLOCKED);
		level.setTerrain(19, 7, Terrain.BLOCKED);
		level.setTerrain(25, 6, Terrain.BLOCKED);
		level.setTerrain(30, 6, Terrain.BLOCKED);
		level.setTerrain(1, 14, Terrain.BLOCKED);
		level.setTerrain(2, 17, Terrain.BLOCKED);
		level.setTerrain(8, 15, Terrain.BLOCKED);
		level.setTerrain(8, 15, Terrain.BLOCKED);
		level.setTerrain(13, 14, Terrain.BLOCKED);

		level.paintTerrain(0, 19, 4, 31, Terrain.BUSH1, Terrain.BUSH2, Terrain.GRASS, Terrain.GRASS, Terrain.GRASS);

		level.paintTerrain(0, 9, 20, 12, Terrain.WATER_FLOWING);
		level.paintTerrain(20, 8, 31, 11, Terrain.WATER_FLOWING);
		level.paintTerrain(5, 8, 5, 13, Terrain.BRIDGE1);
		level.paintTerrain(6, 8, 6, 13, Terrain.BRIDGE2);


		level.placeHouse(11, 26, 1, 5, 2, 4);
		level.addCharacter(new Enemy("Pies1", 9, 17, 3)
				.withPokemon(new Pokemon(PokemonSpecies.EEVEE, 1).withMoves(MoveType.TACKLE, MoveType.GROWL))
				.withPokemon(new Pokemon(PokemonSpecies.BULBASAUR, 2).withMoves(MoveType.TACKLE, MoveType.GROWL))
		);
		level.addCharacter(new Enemy("Pies2", 9, 21, 3)
				.withPokemon(new Pokemon(PokemonSpecies.EEVEE, 1).withMoves(MoveType.TACKLE, MoveType.GROWL))
				.withPokemon(new Pokemon(PokemonSpecies.BULBASAUR, 2).withMoves(MoveType.TACKLE, MoveType.GROWL))
		);

		level.paintTerrain(7, 18, 12, 18, Terrain.ROAD);
		level.paintTerrain(12, 19, 17, 19, Terrain.ROAD);
		level.paintTerrain(17, 20, 17, 31, Terrain.ROAD);
		level.paintTerrain(7, 28, 16, 28, Terrain.ROAD);
		level.paintTerrain(18, 24, 31, 24, Terrain.ROAD);

		level.paintTerrain(8, 30, 15, 30, Terrain.TREE_LEAVES);
		level.paintTerrain(8, 31, 15, 31, Terrain.TREE_TRUNK);
		level.paintTerrain(19, 25, 24, 31, Terrain.TREE_LEAVES_SOLID);
		level.paintTerrain(19, 19, 24, 23, Terrain.TREE_LEAVES_SOLID);
		level.paintTerrain(19, 16, 24, 18, Terrain.TREE_LEAVES_SOLID, Terrain.GRASS, Terrain.GRASS, Terrain.GRASS);

	}
}