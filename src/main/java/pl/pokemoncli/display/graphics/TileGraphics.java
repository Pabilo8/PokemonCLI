package pl.pokemoncli.display.graphics;

import pl.pokemoncli.display.DoubleBufferedTerminal;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 05.11.2024
 */
public enum TileGraphics
{
	GRASS("tiles/grass.txt", new Color(0x4C7C4F), new Color(0x68916C)),
	FLOOR("tiles/floor.txt", new Color(0x7C674C), new Color(0x918368)),


	//--- Water ---//
	WATER_STILL1("tiles/water_still1.txt", new Color(0x4C787C), new Color(0x68918B)),
	WATER_STILL2("tiles/water_still2.txt", new Color(0x4C787C), new Color(0x68918B)),

	WATER_FLOWING1("tiles/water_flowing1.txt", new Color(0x4C787C), new Color(0x68918B)),
	WATER_FLOWING2("tiles/water_flowing2.txt", new Color(0x4C787C), new Color(0x68918B)),
	WATER_FLOWING3("tiles/water_flowing3.txt", new Color(0x4C787C), new Color(0x68918B)),
	WATER_FLOWING4("tiles/water_flowing4.txt", new Color(0x4C787C), new Color(0x68918B)),
	WATER_FLOWING5("tiles/water_flowing5.txt", new Color(0x4C787C), new Color(0x68918B)),

	//--- Beach ---//
	BRIDGE1("tiles/bridge1.txt", new Color(0x716658), new Color(0x3A3430)),
	BRIDGE2("tiles/bridge2.txt", new Color(0x716658), new Color(0x3A3430)),

	BEACH("tiles/beach.txt", new Color(0xCCCCAA), new Color(0x8B8B4C)),
	BEACH2("tiles/beach2.txt", new Color(0xBDBD9E), new Color(0x8B8B4C)),
	ROAD("tiles/road.txt", new Color(0x65675A), new Color(0x717167)),

	//--- Forest ---//
	BUSH1("tiles/bush1.txt", new Color(0x3A5736), new Color(0x68916C)),
	BUSH2("tiles/bush2.txt", new Color(0x3A5736), new Color(0x68916C)),
	TREE_TRUNK("tiles/tree_trunk.txt", new Color(0x4E4138), new Color(0x68916C)),
	TREE_LEAVES("tiles/tree_leaves.txt", new Color(0x3A5736), new Color(0x68916C)),

	//--- House ---//
	DOOR("tiles/door.txt", new Color(0x352F25), new Color(0x6A5E52)),
	DOOR_OPENABLE("tiles/door_openable.txt", new Color(0xB1B0AD), new Color(0x777777)),
	HOUSE_WALL("tiles/house_wall.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_BOTTOM("tiles/house_wall_bottom.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_LEFT_BOTTOM("tiles/house_wall_left_bottom.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_RIGHT_BOTTOM("tiles/house_wall_right_bottom.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_LEFT("tiles/house_wall_left.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_RIGHT("tiles/house_wall_right.txt", new Color(0x525151), new Color(0xB1B0AD)),
	HOUSE_WALL_LEFT_ROOF("tiles/house_wall_left_roof.txt", new Color(0x709A9A), new Color(0xB1B0AD)),
	HOUSE_WALL_RIGHT_ROOF("tiles/house_wall_right_roof.txt", new Color(0x709A9A), new Color(0xB1B0AD)),
	HOUSE_WALL_MIDDLE_ROOF("tiles/house_wall_middle_roof.txt", new Color(0x709A9A), new Color(0xB1B0AD)),
	HOUSE_ROOF_TOP_RIGHT("tiles/house_roof_top_right.txt", new Color(0x709A9A), new Color(0x4C6A6A)),
	HOUSE_ROOF_TOP_LEFT("tiles/house_roof_top_left.txt", new Color(0x709A9A), new Color(0x4C6A6A)),
	HOUSE_ROOF_TOP_MIDDLE("tiles/house_roof_top_middle.txt", new Color(0x709A9A), new Color(0x4C6A6A)),

	VOID("tiles/void.txt", new Color(0x000000), new Color(0x222222)),
	BLOCKED("tiles/blocked.txt", new Color(0x000000), new Color(0x8A8A8A)),

	//--- Characters ---//
	PLAYER_VERTICAL("tiles/player_vertical.txt", new Color(0xAF6464), new Color(0xB5A5A5)),
	PLAYER_LEFT("tiles/player_left.txt", new Color(0xAF6464), new Color(0xB5A5A5)),
	PLAYER_RIGHT("tiles/player_right.txt", new Color(0xAF6464), new Color(0xB5A5A5));

	public static final int TILE_SIZE_X = 6;
	public static final int TILE_SIZE_Y = 3;
	final Color foreground;
	final Color background;
	private String filePath;
	String[] graphics;

	TileGraphics(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreground = foreground;
		this.background = background;
		this.filePath = null;
	}

	TileGraphics(String filePath, Color foreground, Color background)
	{
		this(foreground, background, null);
		this.filePath = filePath;
	}

	@Nonnull
	public void loadGraphics()
	{
		if(filePath!=null)
			this.graphics = AsciiArtLoader.loadAsciiArt(filePath, TILE_SIZE_X, TILE_SIZE_Y, AsciiArtLoader.FALLBACK_TILE_GRAPHICS);
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
