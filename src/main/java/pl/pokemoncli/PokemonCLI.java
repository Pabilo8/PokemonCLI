package pl.pokemoncli;

import com.googlecode.lanterna.input.Key;
import pl.PokemonCommon;
import pl.pokemoncli.cli.DoubleBufferedTerminal;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.cli.MainMenuDisplay;
import pl.pokemoncli.cli.graphics.PokemonGraphics;
import pl.pokemoncli.cli.graphics.TileGraphics;
import pl.pokemoncli.cli.main_display.DialogueDisplay;
import pl.pokemoncli.cli.main_display.FightDisplay;
import pl.pokemoncli.cli.main_display.GameDisplay;
import pl.pokemoncli.cli.side_display.FightPanelDisplay;
import pl.pokemoncli.cli.side_display.GamePanelDisplay;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.SaveStateUtils;
import pl.pokemoncli.sound.AudioSystem.Track;

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

	protected void loadGraphics()
	{
		for(TileGraphics value : TileGraphics.values())
			value.loadGraphics();
		for(PokemonGraphics value : PokemonGraphics.values())
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
							level.removeCharacter(player);
							player = SaveStateUtils.loadGame(player);
							level.addCharacter(player);
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
		GAME_X = (int)(GAME_Y*(TileGraphics.TILE_SIZE_X/(float)TileGraphics.TILE_SIZE_Y));

		//TODO: 19.11.2024 game end condition / return to main menu
		while(!exitGame)
		{
			//ORDER: DIALOGUE, FIGHT, WORLD
			gamePanelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
			if(fight!=null)
			{
				fightDisplay.drawFightScreen(fight, GAME_X, GAME_Y);
				fightPanelDisplay.drawMenuPanel(fight, GAME_X, GAME_Y);
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