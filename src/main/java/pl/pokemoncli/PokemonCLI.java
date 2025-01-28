package pl.pokemoncli;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.cli.DoubleBufferedTerminal;
import pl.pokemoncli.logic.KeyHandlingDisplay;
import pl.pokemoncli.cli.MainMenuDisplay;
import pl.pokemoncli.cli.graphics.CLITileGraphics;
import pl.pokemoncli.cli.graphics.CLIPokemonGraphics;
import pl.pokemoncli.cli.main_display.DialogueDisplay;
import pl.pokemoncli.cli.main_display.FightDisplay;
import pl.pokemoncli.cli.main_display.GameDisplay;
import pl.pokemoncli.cli.side_display.FightPanelDisplay;
import pl.pokemoncli.cli.side_display.GamePanelDisplay;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.PokemonCommon;
import pl.pokemoncli.logic.SaveStateUtils;
import pl.pokemoncli.logic.SaveStateUtils.SaveObject;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.SpriteHandler.SimpleSpriteHandler;
import pl.pokemoncli.logic.characters.*;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;
import pl.pokemoncli.sound.AudioSystem.Track;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class PokemonCLI extends PokemonCommon
{
	private final DoubleBufferedTerminal terminal;

	private final MainMenuDisplay mainMenuDisplay;
	private final DialogueDisplay dialogueDisplay;
	private final FightDisplay fightDisplay;
	private final FightPanelDisplay fightPanelDisplay;
	private final GamePanelDisplay gamePanelDisplay;
	private final GameDisplay gameDisplay;

	public static int GAME_X, GAME_Y;

	public PokemonCLI(DoubleBufferedTerminal terminal)
	{
		this.terminal = terminal;
		this.mainMenuDisplay = new MainMenuDisplay(terminal);
		this.gamePanelDisplay = new GamePanelDisplay(terminal);
		this.gameDisplay = new GameDisplay(terminal);
		this.fightDisplay = new FightDisplay(terminal);
		this.fightPanelDisplay = new FightPanelDisplay(terminal);
		this.dialogueDisplay = new DialogueDisplay(terminal);
	}

	public static void main(String[] args) throws InterruptedException
	{
		DoubleBufferedTerminal dbTerminal = new DoubleBufferedTerminal();
		PokemonCLI instance = new PokemonCLI(dbTerminal);

		//Load level
		instance.loadGame();

		//Load Graphics
		instance.loadGraphics();

		//Display the Game
		instance.displayMenu();
		dbTerminal.flush();
		instance.displayGame();
	}

	@Override
	protected void saveGame()
	{
		SaveStateUtils.saveGame(new SaveObject(player, new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB),
						"Pokemon CLI Save"),
				new File("saves/player.pok"));
	}

	protected void loadGraphics()
	{
		//Set tile graphics
		Terrain.GRASS.setTileGraphics(CLITileGraphics.GRASS);
		Terrain.BEACH.setTileGraphics(CLITileGraphics.BEACH);
		Terrain.BEACH2.setTileGraphics(CLITileGraphics.BEACH2);
		Terrain.ROAD.setTileGraphics(CLITileGraphics.ROAD);
		Terrain.FLOOR.setTileGraphics(CLITileGraphics.FLOOR);
		Terrain.ROCK.setTileGraphics(CLITileGraphics.ROCK);
		Terrain.ROCK_SAND.setTileGraphics(CLITileGraphics.ROCK);
		Terrain.VOID.setTileGraphics(CLITileGraphics.VOID);
		Terrain.BUSH1.setTileGraphics(CLITileGraphics.BUSH1);
		Terrain.BUSH2.setTileGraphics(CLITileGraphics.BUSH2);
		Terrain.TREE_LEAVES.setTileGraphics(CLITileGraphics.TREE_LEAVES);
		Terrain.TREE_TRUNK.setTileGraphics(CLITileGraphics.TREE_TRUNK);
		Terrain.TREE_TRUNK_SOLID.setTileGraphics(CLITileGraphics.TREE_TRUNK);
		Terrain.TREE_LEAVES_SOLID.setTileGraphics(CLITileGraphics.TREE_LEAVES);
		Terrain.WATER_STILL.setTileGraphics(CLITileGraphics.WATER_STILL1,
				CLITileGraphics.WATER_STILL1, CLITileGraphics.WATER_STILL2, CLITileGraphics.WATER_STILL2);
		Terrain.WATER_FLOWING.setTileGraphics(CLITileGraphics.WATER_FLOWING1,
				CLITileGraphics.WATER_FLOWING2, CLITileGraphics.WATER_FLOWING3, CLITileGraphics.WATER_FLOWING4, CLITileGraphics.WATER_FLOWING5);
		Terrain.BRIDGE1.setTileGraphics(CLITileGraphics.BRIDGE1);
		Terrain.BRIDGE2.setTileGraphics(CLITileGraphics.BRIDGE2);
		Terrain.DOOR.setTileGraphics(CLITileGraphics.DOOR);
		Terrain.DOOR_INSIDE.setTileGraphics(CLITileGraphics.DOOR);
		Terrain.HOUSE_WALL_LEFT.setTileGraphics(CLITileGraphics.HOUSE_WALL_LEFT);
		Terrain.HOUSE_WALL_RIGHT.setTileGraphics(CLITileGraphics.HOUSE_WALL_RIGHT);
		Terrain.HOUSE_WALL_LEFT_BOTTOM.setTileGraphics(CLITileGraphics.HOUSE_WALL_LEFT_BOTTOM);
		Terrain.HOUSE_WALL_RIGHT_BOTTOM.setTileGraphics(CLITileGraphics.HOUSE_WALL_RIGHT_BOTTOM);
		Terrain.HOUSE_WALL_LEFT_ROOF.setTileGraphics(CLITileGraphics.HOUSE_WALL_LEFT_ROOF);
		Terrain.HOUSE_WALL_RIGHT_ROOF.setTileGraphics(CLITileGraphics.HOUSE_WALL_RIGHT_ROOF);
		Terrain.HOUSE_WALL_MIDDLE_ROOF.setTileGraphics(CLITileGraphics.HOUSE_WALL_MIDDLE_ROOF);
		Terrain.HOUSE_WALL_ROOF_LEFT.setTileGraphics(CLITileGraphics.HOUSE_ROOF_TOP_LEFT);
		Terrain.HOUSE_WALL_ROOF_RIGHT.setTileGraphics(CLITileGraphics.HOUSE_ROOF_TOP_RIGHT);
		Terrain.HOUSE_WALL_ROOF_MIDDLE.setTileGraphics(CLITileGraphics.HOUSE_ROOF_TOP_MIDDLE);
		Terrain.HOUSE_WALL.setTileGraphics(CLITileGraphics.HOUSE_WALL);
		Terrain.HOUSE_WALL_BOTTOM.setTileGraphics(CLITileGraphics.HOUSE_WALL_BOTTOM);

		//Set pokemon graphics
		PokemonSpecies.MISSINGNO.setGraphics(CLIPokemonGraphics.POKEMON_NULL, CLIPokemonGraphics.POKEMON_NULL);
		PokemonSpecies.BULBASAUR.setGraphics(CLIPokemonGraphics.BULBASAUR_FRONT, CLIPokemonGraphics.BULBASAUR_BACK);
		PokemonSpecies.CHARMANDER.setGraphics(CLIPokemonGraphics.CHARMANDER_FRONT, CLIPokemonGraphics.CHARMANDER_BACK);
		PokemonSpecies.SQUIRTLE.setGraphics(CLIPokemonGraphics.SQUIRTLE_FRONT, CLIPokemonGraphics.SQUIRTLE_BACK);
		PokemonSpecies.CATERPIE.setGraphics(CLIPokemonGraphics.CATERPIE_FRONT, CLIPokemonGraphics.CATERPIE_BACK);
		PokemonSpecies.METAPOD.setGraphics(CLIPokemonGraphics.METAPOD_FRONT, CLIPokemonGraphics.METAPOD_BACK);
		PokemonSpecies.BUTTERFREE.setGraphics(CLIPokemonGraphics.BUTTERFREE_FRONT, CLIPokemonGraphics.BUTTERFREE_BACK);
		PokemonSpecies.WEEDLE.setGraphics(CLIPokemonGraphics.WEEDLE_FRONT, CLIPokemonGraphics.WEEDLE_BACK);
		PokemonSpecies.KAKUNA.setGraphics(CLIPokemonGraphics.KAKUNA_FRONT, CLIPokemonGraphics.KAKUNA_BACK);
		PokemonSpecies.BEEDRILL.setGraphics(CLIPokemonGraphics.BEEDRILL_FRONT, CLIPokemonGraphics.BEEDRILL_BACK);
		PokemonSpecies.PIDGEY.setGraphics(CLIPokemonGraphics.PIDGEY_FRONT, CLIPokemonGraphics.PIDGEY_BACK);
		PokemonSpecies.RATTATA.setGraphics(CLIPokemonGraphics.RATTATA_FRONT, CLIPokemonGraphics.RATTATA_BACK);
		PokemonSpecies.EEVEE.setGraphics(CLIPokemonGraphics.EEVEE_FRONT, CLIPokemonGraphics.EEVEE_BACK);

		//Set sprite handlers
		SpriteHandler.registerHandler(Player.class, new SpriteHandler<>()
		{
			@Override
			protected CLITileGraphics getSprite(Player gameObject, float progress)
			{
				return switch(gameObject.getDirection())
				{
					case 0 -> CLITileGraphics.PLAYER_LEFT;
					case 1 -> CLITileGraphics.PLAYER_RIGHT;
					default -> CLITileGraphics.PLAYER_VERTICAL;
				};
			}
		});
		SpriteHandler.registerHandler(WildPokemon.class, new SimpleSpriteHandler<>(CLITileGraphics.BUSH1));
		SpriteHandler.registerHandler(Enemy.class, new SimpleSpriteHandler<>(CLITileGraphics.ENEMY_VERTICAL));
		SpriteHandler.registerHandler(NPC.class, new SimpleSpriteHandler<>(CLITileGraphics.NPC_VERTICAL));
		SpriteHandler.registerHandler(Door.class, new SimpleSpriteHandler<>(CLITileGraphics.DOOR_OPENABLE));

		//Load tile graphics
		for(CLITileGraphics value : CLITileGraphics.values())
			value.loadGraphics();
		for(CLIPokemonGraphics value : CLIPokemonGraphics.values())
			value.loadGraphics();
	}

	private void displayMenu()
	{
		boolean continueLoop = true;
		terminal.init();

		while(continueLoop)
		{
			mainMenuDisplay.drawMainMenu();
			terminal.flush();
			audioSystem.play(Track.MAIN_MENU);
			Key key;
			while((key = terminal.readInput())!=null)
			{
				ActionResult result = mainMenuDisplay.handleKeyInput(level, player, dialogue, fight, key);
				if(result!=null)
				{
					switch(result.getResult())
					{
						case NEW_GAME -> continueLoop = false;
						case LOAD_GAME ->
						{
							loadSaveFile(SaveStateUtils.loadGame(new File("saves/player.pok")));
							continueLoop = false;
						}
						case EXIT_GAME ->
						{
							terminal.end();
							System.exit(0);
						}
						default -> {}
					}
					break;
				}
			}
		}

	}

	private void displayGame() throws InterruptedException
	{
		GAME_Y = Math.min(terminal.getWidth(), terminal.getHeight());
		GAME_X = (int)(GAME_Y*(CLITileGraphics.TILE_SIZE_X/(float)CLITileGraphics.TILE_SIZE_Y));

		//TODO: 19.11.2024 game end condition / return to main menu
		while(!exitGame)
		{
			//ORDER: DIALOGUE, FIGHT, WORLD
			gamePanelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
			if(fight!=null)
			{
				fightDisplay.drawFightScreen(fight, GAME_X, GAME_Y);
				fightPanelDisplay.drawMenuPanel(fight, fightDisplay, GAME_X, GAME_Y);
			}
			else
				gameDisplay.drawWholeMap(player, level, GAME_X, GAME_Y, tickTimer);


			if(dialogue!=null)
				dialogueDisplay.drawDialogue(dialogue, GAME_X, GAME_Y);

			terminal.flush();

			handleMusic();

			//Clean get top key and clear queue to prevent lag
			boolean handled = false;
			Key key;
			while((key = terminal.readInput())!=null)
				if(handled||handleKeyInput(key))
					handled = true;

			//A mimir
			Thread.sleep(100);
			tickTimer = (tickTimer+1)%20;
		}

		terminal.end();
	}

	@Override
	protected KeyHandlingDisplay getDialogueDisplay()
	{
		return dialogueDisplay;
	}

	@Override
	protected KeyHandlingDisplay getFightDisplay()
	{
		return fightDisplay;
	}

	@Override
	protected KeyHandlingDisplay getGameDisplay()
	{
		return gameDisplay;
	}
}