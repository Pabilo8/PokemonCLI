package pl.pokemoncli.gui.display;

import pl.pokemoncli.PokemonGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Pabilo8
 * @since 18.01.2025
 */
public class SettingsGui
{
	private JPanel mainPanel;
	private JButton saveSettingsButton;
	private JButton backToMenuButton;

	public SettingsGui()
	{
		this.saveSettingsButton.addActionListener(this::saveSettings);
		this.backToMenuButton.addActionListener(this::backToMenu);
	}

	private void backToMenu(ActionEvent actionEvent)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		pok.changeGui(pok.getMainMenuDisplay().getMainPanel());
	}

	private void saveSettings(ActionEvent actionEvent)
	{

	}
}
