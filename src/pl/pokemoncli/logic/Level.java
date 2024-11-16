package pl.pokemoncli.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.equipment.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public class Level
{
	private final int width, height;
	private final Terrain[][] map;
	private final List<Character> characters;

	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.map = new Terrain[width][height];
		this.characters = new ArrayList<>();

		// Initialize the map with default tiles
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				map[x][y] = Terrain.GRASS;
	}

	public Terrain getTerrain(int x, int y)
	{
		return map[x][y];
	}

	public void setTerrain(int x, int y, Terrain terrain)
	{
		map[x][y] = terrain;
	}

	public void addCharacter(Character character)
	{
		characters.add(character);
	}

	public void removeCharacter(Character character)
	{
		characters.remove(character);
	}

	public Character findCharacterAt(Character filter, int x, int y)
	{
		for(Character other : characters)
			if(other!=filter&&other.getX()==x&&other.getY()==y)
				return other;
		return null;
	}

	public Terrain[][] getVisibleMap(int playerX, int playerY, int visibleWidth, int visibleHeight)
	{
		Terrain[][] visibleMap = new Terrain[visibleWidth][visibleHeight];
		int startX = Math.max(0, playerX-visibleWidth/2);
		int startY = Math.max(0, playerY-visibleHeight/2);

		for(int x = 0; x < visibleWidth; x++)
		{
			for(int y = 0; y < visibleHeight; y++)
			{
				int mapX = startX+x;
				int mapY = startY+y;
				if(mapX < width&&mapY < height)
				{
					visibleMap[x][y] = map[mapX][mapY];
				}
				else
				{
					visibleMap[x][y] = Terrain.BLOCKED; // Default terrain for out-of-bounds
				}
			}
		}
		return visibleMap;
	}

	public ActionResult moveCharacterBy(Character character, int dx, int dy)
	{
		int newX = character.getX()+dx;
		int newY = character.getY()+dy;

		// Check if the new position is within bounds
		if(newX < 0||newY < 0||newX >= map.length||newY >= map[0].length)
			return new ActionResult(ResultType.MET_OBSTACLE);

		// Check if the tile is passable
		if(!map[newX][newY].isPassable())
			return new ActionResult(ResultType.MET_OBSTACLE);

		// Check if there is another character at the new position
		Character c;
		if((c = findCharacterAt(character, newX, newY))!=null)
			return new ActionResult(ResultType.FIGHT, c);

		// Move the character
		character.setPosition(newX, newY);
		return new ActionResult(ResultType.MOVE);
	}

	@Getter
	@AllArgsConstructor
	public enum Terrain
	{
		GRASS(true, Tile.GRASS),
		ROAD(true, Tile.GRASS),
		BLOCKED(false, Tile.BLOCKED),
		WATER(false, Tile.GRASS);

		final boolean passable;
		final Tile tile;
	}

	@Getter
	@AllArgsConstructor
	public static class ActionResult
	{
		private final ResultType result;
		private final Character contactedCharacter;
		private final ItemType collectedItem;

		public ActionResult(ResultType result)
		{
			this(result, null, null);
		}

		public ActionResult(ResultType result, Character contactedCharacter)
		{
			this(result, contactedCharacter, null);
		}

		public ActionResult(ResultType result, ItemType collectedItem)
		{
			this(result, null, collectedItem);
		}
	}

	public enum ResultType
	{
		MOVE,
		MET_OBSTACLE,
		FIGHT,
		WILD_POKEMON,
		COLLECT_ITEM
	}
}
