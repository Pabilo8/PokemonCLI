package pl.pokemoncli.display;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public abstract class BaseDisplay
{
	protected final DoubleBufferedTerminal terminal;

	public BaseDisplay(DoubleBufferedTerminal terminal)
	{
		this.terminal = terminal;
	}

	public void drawString(String displayed, int x, int y)
	{
		for(int i = 0; i < displayed.length(); i++)
			terminal.draw(x+i, y, displayed.charAt(i));
	}

	public void drawStringColor(String displayed, int x, int y, Color foreground, Color background)
	{
		for(int i = 0; i < displayed.length(); i++)
			terminal.drawColor(x+i, y, displayed.charAt(i), foreground, background);
	}

	public void drawStringWrappedColor(String displayed, int x, int y, int width, Color foreground, Color background)
	{
		int currentX = x;
		int currentY = y;
		for(int i = 0; i < displayed.length(); i++)
		{
			if(currentX >= x+width)
			{
				currentX = x;
				currentY++;
			}
			terminal.drawColor(currentX++, currentY, displayed.charAt(i), foreground, background);
		}
	}
}
