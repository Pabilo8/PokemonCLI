package pl.pokemoncli.gui.display;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 19.01.2025
 */
public interface IPokemonGui
{
	JPanel getMainPanel();

	default void onInit()
	{

	}

	default void loadGraphics()
	{

	}
}
