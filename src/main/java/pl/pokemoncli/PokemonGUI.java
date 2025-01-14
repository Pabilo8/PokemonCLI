package pl.pokemoncli;

import pl.PokemonCommon;
import pl.pokemoncli.cli.DoubleBufferedTerminal;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.gui.*;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

import java.util.Random;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class PokemonGUI extends PokemonCommon
{
	private final MainMenuScreen mainMenuDisplay;
	private final DialogueScreen dialogueDisplay;
	private final FightScreen fightDisplay;
	private final FightPanelScreen fightPanelDisplay;
	private final GamePanelScreen gamePanelDisplay;
	private final GameScreen gameDisplay;


	public PokemonGUI()
	{
		this.mainMenuDisplay = new MainMenuScreen();
		this.gamePanelDisplay = new GamePanelScreen();
		this.gameDisplay = new GameScreen();
		this.fightDisplay = new FightScreen();
		this.fightPanelDisplay = new FightPanelScreen();
		this.dialogueDisplay = new DialogueScreen();
	}

	public static void main(String[] args) throws InterruptedException
	{
		PokemonGUI instance = new PokemonGUI();

		//Load level
		instance.loadGame();

		//Load Graphics
		instance.loadGraphics();

		//Display the Game
//		instance.displayMenu();
//		instance.displayGame();
	}

	@Override
	protected void loadGraphics()
	{

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
