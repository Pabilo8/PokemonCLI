package pl.pokemoncli;

import com.esotericsoftware.minlog.Log;
import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.AccessLevel;
import lombok.Getter;
import pl.PokemonCommon;
import pl.pokemoncli.gui.PokeLogger;
import pl.pokemoncli.gui.display.*;

import javax.swing.*;
import java.awt.*;

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
	private final IPokemonGui[] allGuis;

	public PokemonGUI()
	{
		this.mainMenuDisplay = new MainMenuGui();
		this.settingsDisplay = new SettingsGui();
		this.loadGameDisplay = new LoadGameGui();
		this.gameDisplay = new GameGui();
		this.fightDisplay = new FightGui();
		this.dialogueDisplay = new DialogueGui();
		this.allGuis = new IPokemonGui[]{mainMenuDisplay, settingsDisplay, loadGameDisplay, dialogueDisplay, fightDisplay, gameDisplay};

		this.window = new JFrame("Pokemon (GUI Edition)");
	}

	public static void main(String[] args)
	{
		Log.setLogger(new PokeLogger());
		//Set FlatLaf look and feel
		if(!FlatDarculaLaf.setup())
			Log.error("PokemonGUI", "Failed to set FlatLaf look and feel");
		UIManager.put("defaultFont", new Font("JetBrains Mono", Font.PLAIN, 18));

		instance = new PokemonGUI();

		//Load level
		instance.loadGame();
		//Load Graphics
		instance.loadGraphics();
		//Display the Game
		instance.displayMenu();
	}

	private void displayMenu()
	{
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		changeGui(mainMenuDisplay);
		window.setVisible(true);
	}

	@Override
	protected void saveGame()
	{
		//TODO: 19.01.2025 game saving
	}

	@Override
	protected void loadGraphics()
	{
		mainMenuDisplay.loadGraphics();
	}

	public void changeGui(IPokemonGui gui)
	{
		SwingUtilities.invokeLater(() -> {
			window.setContentPane(gui.getMainPanel());
			window.revalidate();
			window.repaint();
			gui.onInit();
		});
	}
}
