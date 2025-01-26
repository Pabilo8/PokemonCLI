package pl.pokemoncli.gui.graphics;

import pl.pokemoncli.logic.AbstractTileGraphics;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 05.11.2024
 */
public enum GUITileGraphics implements AbstractTileGraphics<Graphics>
{
	GRASS("/gui/tiles/grass.png"),
	FLOOR("/gui/tiles/floor.png"),

	//--- Water ---//
	WATER_STILL1("/gui/tiles/water_still1.png"),
	WATER_STILL2("/gui/tiles/water_still2.png"),

	WATER_FLOWING1("/gui/tiles/water_flowing1.png"),
	WATER_FLOWING2("/gui/tiles/water_flowing2.png"),
	WATER_FLOWING3("/gui/tiles/water_flowing3.png"),
	WATER_FLOWING4("/gui/tiles/water_flowing4.png"),
	WATER_FLOWING5("/gui/tiles/water_flowing5.png"),

	//--- Beach ---//
	BRIDGE1("/gui/tiles/bridge1.png"),
	BRIDGE2("/gui/tiles/bridge2.png"),

	BEACH("/gui/tiles/beach.png"),
	BEACH2("/gui/tiles/beach2.png"),
	ROAD("/gui/tiles/road.png"),

	//--- Forest ---//
	BUSH1("/gui/tiles/bush1.png"),
	BUSH2("/gui/tiles/bush2.png"),
	TREE_TRUNK("/gui/tiles/tree_trunk.png"),
	TREE_LEAVES("/gui/tiles/tree_leaves.png"),

	//--- House ---//
	DOOR("/gui/tiles/door.png"),
	DOOR_OPENABLE("/gui/tiles/door_openable.png"),
	HOUSE_WALL("/gui/tiles/house_wall.png"),
	HOUSE_WALL_BOTTOM("/gui/tiles/house_wall_bottom.png"),
	HOUSE_WALL_LEFT_BOTTOM("/gui/tiles/house_wall_left_bottom.png"),
	HOUSE_WALL_RIGHT_BOTTOM("/gui/tiles/house_wall_right_bottom.png"),
	HOUSE_WALL_LEFT("/gui/tiles/house_wall_left.png"),
	HOUSE_WALL_RIGHT("/gui/tiles/house_wall_right.png"),
	HOUSE_WALL_LEFT_ROOF("/gui/tiles/house_wall_left_roof.png"),
	HOUSE_WALL_RIGHT_ROOF("/gui/tiles/house_wall_right_roof.png"),
	HOUSE_WALL_MIDDLE_ROOF("/gui/tiles/house_wall_middle_roof.png"),
	HOUSE_ROOF_TOP_RIGHT("/gui/tiles/house_roof_top_right.png"),
	HOUSE_ROOF_TOP_LEFT("/gui/tiles/house_roof_top_left.png"),
	HOUSE_ROOF_TOP_MIDDLE("/gui/tiles/house_roof_top_middle.png"),

	VOID("/gui/tiles/void.png"),
	BLOCKED("/gui/tiles/blocked.png"),

	//--- Characters ---//
	PLAYER_VERTICAL("/gui/sprites/player_vertical.png"),
	PLAYER_LEFT("/gui/sprites/player_left.png"),
	PLAYER_RIGHT("/gui/sprites/player_right.png"),

	ENEMY_VERTICAL("/gui/sprites/player_vertical.png"),
	NPC_VERTICAL("/gui/sprites/player_vertical.png");

	private String filePath;
	private Image image;

	GUITileGraphics(String filePath)
	{
		this.filePath = filePath;
	}

	@Nonnull
	@Override
	public void loadGraphics()
	{
		ImageLoader.getInstance().loadImage(name(), filePath);
		this.image = ImageLoader.getInstance().getImage(name());
	}

	@Override
	public void draw(int offsetX, int offsetY, Graphics graphics)
	{
		graphics.drawImage(this.image, offsetX, offsetY, 64, 64, null);
	}
}
