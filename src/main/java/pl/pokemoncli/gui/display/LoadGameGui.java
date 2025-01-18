package pl.pokemoncli.gui.display;

import lombok.AccessLevel;
import lombok.Getter;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 17.01.2025
 */
public class LoadGameGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JButton backToMenuButton;
	private JButton loadGameButton;
}
