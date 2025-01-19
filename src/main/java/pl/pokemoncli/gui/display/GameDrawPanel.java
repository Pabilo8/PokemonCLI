package pl.pokemoncli.gui.display;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 19.01.2025
 */
public class GameDrawPanel extends JPanel
{
	public GameDrawPanel()
	{

	}

	@Override
	public void paintComponents(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 100, 100);
	}
}
