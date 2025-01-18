package pl.pokemoncli;

import com.esotericsoftware.minlog.Log;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import lombok.AccessLevel;
import lombok.Getter;
import pl.PokemonCommon;
import pl.pokemoncli.gui.display.*;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
@Getter
public class PokemonGUI extends PokemonCommon
{
	private final JFrame window;
	@Getter(AccessLevel.PUBLIC)
	private static PokemonGUI instance;

	//GUI
	private final MainMenuGui mainMenuDisplay;
	private final SettingsGui settingsDisplay;
	private final LoadGameGui loadGameDisplay;
	private final DialogueGui dialogueDisplay;
	private final FightGui fightDisplay;
	private final GameGui gameDisplay;

	//Panels
	private final FightPanelGui fightPanelDisplay;
	private final GamePanelGui gamePanelDisplay;

	public PokemonGUI()
	{
		this.mainMenuDisplay = new MainMenuGui();
		this.settingsDisplay = new SettingsGui();
		this.loadGameDisplay = new LoadGameGui();
		this.gameDisplay = new GameGui();
		this.fightDisplay = new FightGui();
		this.dialogueDisplay = new DialogueGui();

		this.gamePanelDisplay = new GamePanelGui();
		this.fightPanelDisplay = new FightPanelGui();

		this.window = new JFrame("Pokemon (GUI Edition)");
	}

	public static void main(String[] args)
	{
		//Set FlatLaf look and feel
		if(!FlatDarculaLaf.setup())
			Log.error("PokemonGUI", "Failed to set FlatLaf look and feel");
		instance = new PokemonGUI();

		//Load level
		instance.loadGame();
		//Load Graphics
		instance.loadGraphics();
		//Display the Game
		instance.displayMenu();
	}

	private void displayGame()
	{

	}

	private void displayMenu()
	{
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		changeGui(mainMenuDisplay.getMainPanel());
		window.setVisible(true);

	}

	@Override
	protected void loadGraphics()
	{

	}

	public void changeGui(JPanel gui)
	{
		window.setContentPane(gui);
		window.repaint();
	}
}
