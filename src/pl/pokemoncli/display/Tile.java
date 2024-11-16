package pl.pokemoncli.display;

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
	final Color foreground;
	final Color background;
	final String[] graphics;

	Tile(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreground = foreground;
		this.background = background;
	}

	public void draw(int offsetX, int offsetY, DoubleBufferedTerminal terminal)
	{
		//TODO: 16.11.2024 batch drawing using System.arraycopy
		for(int y = 0; y < TILE_SIZE_Y; y++)
			for(int x = 0; x < TILE_SIZE_X; x++)
				terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background);
	}

	public void drawWithBackground(Tile background, int offsetX, int offsetY, DoubleBufferedTerminal terminal)
	{
		for(int y = 0; y < TILE_SIZE_Y; y++)
			for(int x = 0; x < TILE_SIZE_X; x++)
				if(graphics[y].charAt(x)!=' ')
					terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background==null?this.background: background.background);
	}
}
