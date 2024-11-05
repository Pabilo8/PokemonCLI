package pl.pokemoncli.display;

import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 05.11.2024
 */
public enum Tile
{
	GRASS(new Color(0x4C7C4F), new Color(0x68916C), new String[]{
			". ;.; ",
			",. ,..",
			";'.. ."
	}),
	BLOCKED(new Color(0x000000), new Color(0x8A8A8A), new String[]{
			"░░░░░▒",
			"░▒▒▒▒▓",
			"▒▓▓▓▓▓"
	}),
	PLAYER_VERTICAL(new Color(0xAF6464), new Color(0xB5A5A5), new String[]{
			"  []  ",
			" /██\\ ",
			"  ▟▙  "
	}),
	PLAYER_LEFT(new Color(0xAF6464), new Color(0xB5A5A5), new String[]{
			"  []  ",
			" [/█  ",
			"  ▟▐  "
	}),
	PLAYER_RIGHT(new Color(0xAF6464), new Color(0xB5A5A5), new String[]{
			"  []  ",
			"  █/] ",
			"  ▌▙  "
	}),
	;

	public static final int TILE_SIZE_X = 6;
	public static final int TILE_SIZE_Y = 3;
	final int foreR, foreG, foreB;
	final int backR, backG, backB;
	final String[] graphics;

	Tile(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreR = foreground.getRed();
		this.foreG = foreground.getGreen();
		this.foreB = foreground.getBlue();
		if(background!=null)
		{
			this.backR = background.getRed();
			this.backG = background.getGreen();
			this.backB = background.getBlue();
		}
		else
			backR = backG = backB = -1;
	}

	public void draw(int offsetX, int offsetY, Terminal terminal)
	{
		terminal.applyBackgroundColor(backR, backG, backB);
		terminal.applyForegroundColor(foreR, foreG, foreB);
		for(int y = 0; y < TILE_SIZE_Y; y++)
		{
			terminal.moveCursor(offsetX, offsetY+y);
			for(int x = 0; x < TILE_SIZE_X; x++)
				terminal.putCharacter(graphics[y].charAt(x));
		}
	}

	public void drawWithBackground(Tile background, int offsetX, int offsetY, Terminal terminal)
	{
		terminal.applyBackgroundColor(background.backR, background.backG, background.backB);
		terminal.applyForegroundColor(foreR, foreG, foreB);
		for(int y = 0; y < TILE_SIZE_Y; y++)
		{
			terminal.moveCursor(offsetX, offsetY+y);
			for(int x = 0; x < TILE_SIZE_X; x++)
			{
				char c = graphics[y].charAt(x);
				if(c==' ')
					terminal.moveCursor(offsetX+x+1, offsetY+y);
				else
					terminal.putCharacter(c);
			}
		}
	}
}
