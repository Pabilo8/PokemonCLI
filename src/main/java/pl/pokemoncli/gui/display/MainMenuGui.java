package pl.pokemoncli.gui.display;

import com.esotericsoftware.minlog.Log;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.gui.graphics.ImageLoader;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Author: Pabilo8
 * Since: 14.01.2025
 */
public class MainMenuGui implements IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton settingsButton;
	private JButton exitButton;
	private JPanel imagePanel;
	private JPanel centerPanel;

	public MainMenuGui()
	{
		newGameButton.addActionListener(this::startNewGame);
		newGameButton.setDefaultCapable(true);
		loadGameButton.addActionListener(this::loadGame);
		settingsButton.addActionListener(this::openSettings);
		exitButton.addActionListener(this::exitGame);
	}

	private void startNewGame(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Starting new game...");
		pok.changeGui(pok.getGameDisplay());
	}

	private void loadGame(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Loading game...");
		pok.changeGui(pok.getLoadGameDisplay());
	}

	private void openSettings(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Opening settings...");
		pok.changeGui(pok.getSettingsDisplay());
	}

	private void exitGame(ActionEvent e)
	{
		Log.info("Exiting game...");
		System.exit(0);
	}

	private void createUIComponents()
	{
		ImageLoader loader = ImageLoader.getInstance();
		loader.loadImage("background", "/gui/mainmenu/background.png");
		loader.loadImage("logo", "/gui/mainmenu/logo.png");

		mainPanel = new ImagePanel("background");
		imagePanel = new ImagePanel("logo");
	}

	@Override
	public void onInit()
	{
		PokemonGUI.getInstance().getAudioSystem().play(Track.MAIN_MENU);
		centerPanel.setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	public void loadGraphics()
	{

	}
}