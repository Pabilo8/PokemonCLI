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

	FLOOR(new Color(0x7C674C), new Color(0x918368), new String[]{
			"------",
			"======",
			"------"
	}),

	//--- Water ---//
	WATER_STILL1(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"░▒░░░░",
			"░░░░░▒",
			"░░░▒░░"
	}),
	WATER_STILL2(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"░▒░░░▒",
			"░░░░░░",
			"░░▒░░░"
	}),

	WATER_FLOWING1(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"░▒▓▒░~",
			"░~░▒▓▒",
			"░▒▓▒░~"
	}),
	WATER_FLOWING2(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"░~░▒▓▒",
			"▒░~░▒▓",
			"░~░▒▓▒"
	}),
	WATER_FLOWING3(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"▒░~░▒▓",
			"▓▒░~░▒",
			"▒░~░▒▓"
	}),
	WATER_FLOWING4(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"▓▒░~░▒",
			"▒▓▒░~░",
			"▓▒░~░▒"
	}),
	WATER_FLOWING5(new Color(0x4C787C), new Color(0x68918B), new String[]{
			"▒▓▒░~░",
			"░▒▓▒░~",
			"▒▓▒░~░"
	}),

	//--- Beach ---//
	BRIDGE1(new Color(0x716658), new Color(0x3A3430), new String[]{
			"▒░    ",
			"▒░    ",
			"▒░    "
	}),
	BRIDGE2(new Color(0x716658), new Color(0x3A3430), new String[]{
			"    ░▒",
			"    ░▒",
			"    ░▒"
	}),

	BEACH(new Color(0xCCCCAA), new Color(0x8B8B4C), new String[]{
			"░░░░░░",
			"░░░░░░",
			"░░░░░░"
	}),
	ROAD(new Color(0x56594F), new Color(0x717167), new String[]{
			"░▓▓░  ",
			"░░ ░░░",
			"░   ▓░"
	}),

	//--- House ---//
	DOOR(new Color(0x352F25), new Color(0x6A5E52), new String[]{
			"▒▒▒▒▒▒",
			"▒   ▓▒",
			"▒▒▒▒▒▒"
	}),
	DOOR_OPENABLE(new Color(0xB1B0AD), new Color(0x777777), new String[]{
			"      ",
			"    ▒ ",
			"      "
	}),
	HOUSE_WALL(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"      ",
			"      ",
			"      "
	}),
	HOUSE_WALL_BOTTOM(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"      ",
			"      ",
			"▒▒▒▒▒▒"
	}),
	HOUSE_WALL_LEFT_BOTTOM(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"▒     ",
			"▒     ",
			"▒▒▒▒▒▒"
	}),
	HOUSE_WALL_RIGHT_BOTTOM(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"     ▒",
			"     ▒",
			"▒▒▒▒▒▒"
	}),
	HOUSE_WALL_LEFT(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"▒     ",
			"▒     ",
			"▒     "
	}),
	HOUSE_WALL_RIGHT(new Color(0x525151), new Color(0xB1B0AD), new String[]{
			"     ▒",
			"     ▒",
			"     ▒"
	}),
	HOUSE_WALL_LEFT_ROOF(new Color(0x709A9A), new Color(0xB1B0AD), new String[]{
			"██▓▓██",
			"▓▓██  ",
			"██    "
	}),
	HOUSE_WALL_RIGHT_ROOF(new Color(0x709A9A), new Color(0xB1B0AD), new String[]{
			"██▓▓██",
			"  ██▓▓",
			"    ██"
	}),
	HOUSE_WALL_MIDDLE_ROOF(new Color(0x709A9A), new Color(0xB1B0AD), new String[]{
			"██▓▓██",
			"▓▓██▓▓",
			"██  ██"
	}),
	HOUSE_ROOF_TOP_RIGHT(new Color(0x709A9A), new Color(0x4C6A6A), new String[]{
			"  ▓▓██",
			"██  ▓▓",
			"▓▓██  "
	}),
	HOUSE_ROOF_TOP_LEFT(new Color(0x709A9A), new Color(0x4C6A6A), new String[]{
			"██▓▓  ",
			"▓▓  ██",
			"  ██▓▓"
	}),
	HOUSE_ROOF_TOP_MIDDLE(new Color(0x709A9A), new Color(0x4C6A6A), new String[]{
			"██▓▓██",
			"▓▓  ▓▓",
			"  ██  "
	}),


	VOID(new Color(0x000000), new Color(0x222222), new String[]{
			"░░░░░░",
			"░░░░░░",
			"░░░░░░"
	}),
	BLOCKED(new Color(0x000000), new Color(0x8A8A8A), new String[]{
			"░░░░░▒",
			"░▒▒▒▒▓",
			"▒▓▓▓▓▓"
	}),


	//--- Characters ---//
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

	public void drawTrnsparent(int offsetX, int offsetY, DoubleBufferedTerminal terminal)
	{
		for(int y = 0; y < TILE_SIZE_Y; y++)
			for(int x = 0; x < TILE_SIZE_X; x++)
				if(graphics[y].charAt(x)!=' ')
					terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, this.background);
	}
}
