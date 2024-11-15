package pl.pokemoncli.display;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class DisplayUtils
{
	public static void drawString(Terminal terminal, String displayed, int x, int y)
	{
		terminal.moveCursor(x, y);
		displayed.chars().forEach(c -> terminal.putCharacter((char)c));
	}
}