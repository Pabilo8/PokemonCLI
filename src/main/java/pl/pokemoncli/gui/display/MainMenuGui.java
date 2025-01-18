package pl.pokemoncli.gui.display;

import com.esotericsoftware.minlog.Log;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Author: Pabilo8
 * Since: 14.01.2025
 */
public class MainMenuGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton settingsButton;
	private JButton exitButton;
	private JPanel imagePanel;

	public MainMenuGui()
	{
		newGameButton.addActionListener(this::startNewGame);
		loadGameButton.addActionListener(this::loadGame);
		settingsButton.addActionListener(this::openSettings);
		exitButton.addActionListener(this::exitGame);
	}

	private void startNewGame(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Starting new game...");
		pok.changeGui(pok.getGameDisplay().getMainPanel());
	}

	private void loadGame(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Loading game...");
		pok.changeGui(pok.getGameDisplay().getMainPanel());
	}

	private void openSettings(ActionEvent e)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		Log.info("Opening settings...");
		pok.changeGui(pok.getGameDisplay().getMainPanel());
	}

	private void exitGame(ActionEvent e)
	{
		Log.info("Exiting game...");
		System.exit(0);
	}

	private void createUIComponents()
	{
		// TODO: place custom component creation code here
	}
}