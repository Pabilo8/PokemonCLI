package pl.pokemoncli.gui.display;

import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Pabilo8
 * @since 18.01.2025
 */
public class SettingsGui implements IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JButton saveSettingsButton;
	private JButton backToMenuButton;

	public SettingsGui()
	{
		this.saveSettingsButton.addActionListener(this::saveSettings);
		this.saveSettingsButton.setDefaultCapable(true);
		this.backToMenuButton.addActionListener(this::backToMenu);
	}

	private void backToMenu(ActionEvent actionEvent)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		pok.changeGui(pok.getMainMenuDisplay());
	}

	private void saveSettings(ActionEvent actionEvent)
	{

	}
}
