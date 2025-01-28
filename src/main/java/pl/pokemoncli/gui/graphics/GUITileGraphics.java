package pl.pokemoncli.gui.graphics;

import pl.pokemoncli.logic.AbstractTileGraphics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Pabilo8
 * @since 05.11.2024
 */
public enum GUITileGraphics implements AbstractTileGraphics<Graphics>
{
	GRASS("/gui/tiles/grass.png", 6, null),
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
	BEACH2("/gui/tiles/beach2.png", 3, null),
	ROAD("/gui/tiles/road.png", null, true),

	//--- Forest ---//
	BUSH_GENERIC("/gui/tiles/bush1.png"),
	BUSH1("/gui/tiles/bush_fore.png", 0, BUSH_GENERIC),
	BUSH2("/gui/tiles/empty.png", 0, GRASS),
	TREE_TRUNK("/gui/tiles/tree_trunk.png"),
	TREE_LEAVES("/gui/tiles/tree_leaves.png", GRASS, false),
	TREE_LEAVES_BIG("/gui/tiles/tree_leaves_big.png", GRASS, false),

	//--- House ---//
	DOOR_BACK("/gui/tiles/door.png", 0, FLOOR),
	DOOR("/gui/tiles/door_fore.png", 0, DOOR_BACK),
	DOOR_INSIDE("/gui/tiles/door_inside.png", 0, FLOOR),
	DOOR_OPENABLE("/gui/tiles/empty.png"),
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
	HOUSE_ROOF_TOP_MIDDLE("/gui/tiles/house_roof_top_middle.png", null, true),

	VOID("/gui/tiles/void.png"),
	ROCK("/gui/tiles/rock.png"),
	ROCK_SAND("/gui/tiles/rock_sand.png"),

	//--- Characters ---//
	PLAYER_BACK1("/gui/sprites/player/player_back1.png"),
	PLAYER_BACK2("/gui/sprites/player/player_back2.png"),
	PLAYER_BACK3("/gui/sprites/player/player_back3.png"),
	PLAYER_BACK4("/gui/sprites/player/player_back4.png"),

	PLAYER_FRONT1("/gui/sprites/player/player_front1.png"),
	PLAYER_FRONT2("/gui/sprites/player/player_front2.png"),
	PLAYER_FRONT3("/gui/sprites/player/player_front3.png"),
	PLAYER_FRONT4("/gui/sprites/player/player_front4.png"),

	PLAYER_RIGHT1("/gui/sprites/player/player_right1.png"),
	PLAYER_RIGHT2("/gui/sprites/player/player_right2.png"),
	PLAYER_RIGHT3("/gui/sprites/player/player_right3.png"),
	PLAYER_RIGHT4("/gui/sprites/player/player_right4.png"),

	PLAYER_LEFT1("/gui/sprites/player/player_left1.png"),
	PLAYER_LEFT2("/gui/sprites/player/player_left2.png"),
	PLAYER_LEFT3("/gui/sprites/player/player_left3.png"),
	PLAYER_LEFT4("/gui/sprites/player/player_left4.png"),

	ENEMY0("/gui/sprites/enemy0.png"),
	ENEMY1("/gui/sprites/enemy1.png"),
	ENEMY2("/gui/sprites/enemy2.png"),
	ENEMY3("/gui/sprites/enemy3.png"),
	NPC0("/gui/sprites/npc0.png"),
	NPC1("/gui/sprites/npc1.png"),
	NPC2("/gui/sprites/npc2.png"),
	NPC_PIES("/gui/sprites/npc_pies.png");

	public static final int DISPLAYED_SIZE = 64;

	private final String filePath;
	private final GUITileGraphics foreground;
	private int additionalImagesCount;
	private final List<Image> additionalImages;
	private Image image;
	boolean averageYOffset = false;
	private int xSize, ySize, xOffset, yOffset;

	GUITileGraphics(String filePath)
	{
		this(filePath, null, false);
	}

	GUITileGraphics(String filePath, @Nullable GUITileGraphics foreground, boolean averageYOffset)
	{
		this(filePath, 0, foreground);
		this.averageYOffset = averageYOffset;
	}

	GUITileGraphics(String filePath, int additionalImages, @Nullable GUITileGraphics foreground)
	{
		this.filePath = filePath;
		this.foreground = foreground;
		this.additionalImagesCount = additionalImages;
		this.additionalImages = additionalImages==0?Collections.emptyList(): new ArrayList<>();
	}

	@Nonnull
	@Override
	public void loadGraphics()
	{
		ImageLoader.getInstance().loadImage(name(), filePath);
		this.image = ImageLoader.getInstance().getImage(name());
		if(additionalImagesCount > 0)
		{
			this.additionalImages.add(this.image);
			for(int i = 1; i <= additionalImagesCount; i++)
			{
				ImageLoader.getInstance().loadImage(name()+i, filePath.replace(".", "_"+i+"."));
				this.additionalImages.add(ImageLoader.getInstance().getImage(name()+i));
			}
		}

		this.xSize = (int)((image.getWidth(null)/16f)*DISPLAYED_SIZE);
		this.ySize = (int)((image.getHeight(null)/16f)*DISPLAYED_SIZE);
		this.xOffset = (DISPLAYED_SIZE-xSize)/2;
		this.yOffset = averageYOffset?((DISPLAYED_SIZE-ySize)/2): -(ySize-DISPLAYED_SIZE);
	}

	private void drawSelf(int offsetX, int offsetY, Graphics graphics, int seed, float animation, boolean[] surroundings)
	{
		if(!additionalImages.isEmpty())
			graphics.drawImage(this.additionalImages.get(seed%additionalImages.size()), offsetX, offsetY, xSize, ySize, null);
		else
			graphics.drawImage(this.image, offsetX+this.xOffset, offsetY+this.yOffset, xSize, ySize, null);
	}

	@Override
	public void drawBackground(int offsetX, int offsetY, Graphics graphics, int seed, float animation, boolean[] surroundings)
	{
		(this.foreground!=null?this.foreground: this)
				.drawSelf(offsetX, offsetY, graphics, seed, animation, surroundings);
	}


	@Override
	public void drawBackground(int offsetX, int offsetY, Graphics graphics)
	{
		graphics.drawImage(this.image, offsetX+xOffset, offsetY+yOffset, xSize, ySize, null);
	}

	@Override
	public boolean hasForeground()
	{
		return foreground!=null;
	}

	@Override
	public void drawForeground(int offsetX, int offsetY, Graphics graphics, int seed, float animation, boolean[] surroundings)
	{
		drawSelf(offsetX, offsetY, graphics, seed, animation, surroundings);
	}
}
