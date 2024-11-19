package pl.pokemoncli.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.display.graphics.TileGraphics;
import pl.pokemoncli.logic.characters.*;
import pl.pokemoncli.logic.combat.item.ItemType;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public class Level
{
	private final Random random = new Random();
	private final int width, height;
	private final Terrain[][] map;
	private final List<GameObject> gameObjects;
	private final List<PokemonSpecies> pokemonSpawnList;
	private final List<WildPokemon>	wildPokemons;

	public Level(int width, int height, Terrain defaultTile)
	{
		this.width = width;
		this.height = height;
		this.map = new Terrain[width][height];
		this.gameObjects = new ArrayList<>();
		this.pokemonSpawnList = new ArrayList<>();
		this.wildPokemons = new ArrayList<>();

		// Initialize the map with default tiles
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				map[x][y] = defaultTile;

		// Initialize the pokemon spawn list
		pokemonSpawnList.add(PokemonSpecies.CATERPIE);
		pokemonSpawnList.add(PokemonSpecies.METAPOD);
		pokemonSpawnList.add(PokemonSpecies.WEEDLE);
		pokemonSpawnList.add(PokemonSpecies.KAKUNA);
		pokemonSpawnList.add(PokemonSpecies.PIDGEY);
		pokemonSpawnList.add(PokemonSpecies.RATTATA);
	}

	public Terrain getTerrain(int x, int y)
	{
		if(x < 0||y < 0||x >= width||y >= height)
			return Terrain.VOID;
		return map[x][y];
	}

	public void setTerrain(int x, int y, Terrain terrain)
	{
		if(x < 0||y < 0||x >= width||y >= height)
			return;

		map[x][y] = terrain;
	}

	public void addCharacter(GameObject gameObject)
	{
		gameObjects.add(gameObject);
	}

	public void removeCharacter(GameObject gameObject)
	{
		gameObjects.remove(gameObject);
	}

	public GameObject findCharacterAt(GameObject filter, int x, int y)
	{
		for(GameObject other : gameObjects)
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
			for(int y = 0; y < visibleHeight; y++)
			{
				int mapX = startX+x;
				int mapY = startY+y;
				if(mapX < width&&mapY < height)
					visibleMap[x][y] = map[mapX][mapY];
				else
					visibleMap[x][y] = Terrain.VOID; // Default terrain for out-of-bounds
			}
		return visibleMap;
	}

	public ActionResult moveCharacterBy(FightableCharacter gameObject, int dx, int dy)
	{
		int newX = gameObject.getX()+dx;
		int newY = gameObject.getY()+dy;

		// Check if the new position is within bounds
		if(newX < 0||newY < 0||newX >= map.length||newY >= map[0].length)
			return new ActionResult(ResultType.MET_OBSTACLE);

		// Check if the tile is passable
		if(!map[newX][newY].isPassable())
			return new ActionResult(ResultType.MET_OBSTACLE);

		// Check if there is another character at the new position
		GameObject c;
		if((c = findCharacterAt(gameObject, newX, newY))!=null)
			return switch(c)
			{
				case WildPokemon wildPokemon -> new ActionResult(ResultType.WILD_POKEMON, wildPokemon);
				case Enemy enemy -> new ActionResult(ResultType.FIGHT, enemy);
				case Door door -> new ActionResult(ResultType.CHANGE_LEVEL, door);
				case NPC npc -> new ActionResult(ResultType.DIALOG, npc);
				default -> new ActionResult(ResultType.MET_OBSTACLE);
			};

		// Move the character
		gameObject.setPosition(newX, newY);
		return new ActionResult(ResultType.MOVE);
	}

	public void paintTerrain(int x, int y, int xx, int yy, Terrain... terrains)
	{
		for(int i = x; i <= xx; i++)
			for(int j = y; j <= yy; j++)
				setTerrain(i, j, terrains.length==1?terrains[0]: terrains[random.nextInt(terrains.length)]);
	}

	public void addDoor(int x, int y, Level interior, int destX, int destY)
	{
		Door door = new Door(x, y, interior, destX, destY);
		this.addCharacter(door);
	}

	public void placeHouse(int x, int y, int doorOffset, int width, int wallHeight, int roofLength)
	{
		placeHouse(x, y, doorOffset, width, wallHeight, roofLength, null, 0, 0);
	}

	public void placeHouse(int x, int y, int doorOffset, int width, int wallHeight, int roofLength,
						   Level interior, int destX, int destY)
	{
		wallHeight -= 1;
		width -= 1;

		// Draw walls
		this.paintTerrain(x, y-wallHeight, x+width, y, Terrain.HOUSE_WALL);
		this.paintTerrain(x, y, x+width, y, Terrain.HOUSE_WALL_BOTTOM);
		this.setTerrain(x, y, Terrain.HOUSE_WALL_LEFT_BOTTOM);
		this.setTerrain(x+width, y, Terrain.HOUSE_WALL_RIGHT_BOTTOM);

		// Draw door
		this.paintTerrain(x, y-wallHeight, x, y-1, Terrain.HOUSE_WALL_LEFT);
		this.paintTerrain(x+width, y-wallHeight, x+width, y-1, Terrain.HOUSE_WALL_RIGHT);

		//Draw long roof
		this.paintTerrain(x, y-wallHeight-roofLength-1, x+width/2, y-wallHeight-1, Terrain.HOUSE_WALL_ROOF_LEFT);
		this.paintTerrain(x+width/2, y-wallHeight-roofLength-1, x+width, y-wallHeight-1, Terrain.HOUSE_WALL_ROOF_RIGHT);
		if(width%2==0)
			this.paintTerrain(x+width/2, y-wallHeight-roofLength-1, x+width/2, y-wallHeight-1, Terrain.HOUSE_WALL_ROOF_MIDDLE);

		// Draw roof
		for(int i = 0; i < width/2; i++)
		{
			int startX = x+i;
			int endX = x+width-i;
			this.paintTerrain(startX, y-wallHeight-i-1, endX, y-wallHeight-i-1, Terrain.HOUSE_WALL);
			this.setTerrain(startX, y-wallHeight-i-1, Terrain.HOUSE_WALL_LEFT_ROOF);
			this.setTerrain(endX, y-wallHeight-i-1, Terrain.HOUSE_WALL_RIGHT_ROOF);
		}
		if(width%2==0)
			this.setTerrain(x+width/2, y-(wallHeight+width/2)-1, Terrain.HOUSE_WALL_MIDDLE_ROOF);


		this.setTerrain(x+doorOffset, y, Terrain.DOOR);
		if(interior!=null)
			addDoor(x+doorOffset, y, interior, destX, destY);
	}

	@Getter
	public enum Terrain
	{
		GRASS(true, TileGraphics.GRASS),
		BEACH(true, TileGraphics.BEACH),
		BEACH2(true, TileGraphics.BEACH2),
		ROAD(true, TileGraphics.ROAD),
		FLOOR(true, TileGraphics.FLOOR),
		BLOCKED(false, TileGraphics.BLOCKED),
		VOID(false, TileGraphics.VOID),

		BUSH1(true, TileGraphics.BUSH1),
		BUSH2(true, TileGraphics.BUSH2),
		TREE_LEAVES(true, TileGraphics.TREE_LEAVES),
		TREE_TRUNK(false, TileGraphics.TREE_TRUNK),
		TREE_LEAVES_SOLID(false, TileGraphics.TREE_LEAVES),

		WATER_STILL(false, TileGraphics.WATER_STILL1, TileGraphics.WATER_STILL1, TileGraphics.WATER_STILL2, TileGraphics.WATER_STILL2),
		WATER_FLOWING(false, TileGraphics.WATER_FLOWING1, TileGraphics.WATER_FLOWING2, TileGraphics.WATER_FLOWING3, TileGraphics.WATER_FLOWING4, TileGraphics.WATER_FLOWING5),
		BRIDGE1(true, TileGraphics.BRIDGE1),
		BRIDGE2(true, TileGraphics.BRIDGE2),

		DOOR(true, TileGraphics.DOOR),
		HOUSE_WALL_LEFT(false, TileGraphics.HOUSE_WALL_LEFT),
		HOUSE_WALL_RIGHT(false, TileGraphics.HOUSE_WALL_RIGHT),
		HOUSE_WALL_LEFT_BOTTOM(false, TileGraphics.HOUSE_WALL_LEFT_BOTTOM),
		HOUSE_WALL_RIGHT_BOTTOM(false, TileGraphics.HOUSE_WALL_RIGHT_BOTTOM),

		HOUSE_WALL_LEFT_ROOF(false, TileGraphics.HOUSE_WALL_LEFT_ROOF),
		HOUSE_WALL_RIGHT_ROOF(false, TileGraphics.HOUSE_WALL_RIGHT_ROOF),
		HOUSE_WALL_MIDDLE_ROOF(false, TileGraphics.HOUSE_WALL_MIDDLE_ROOF),
		HOUSE_WALL_ROOF_LEFT(false, TileGraphics.HOUSE_ROOF_TOP_LEFT),
		HOUSE_WALL_ROOF_RIGHT(false, TileGraphics.HOUSE_ROOF_TOP_RIGHT),
		HOUSE_WALL_ROOF_MIDDLE(false, TileGraphics.HOUSE_ROOF_TOP_MIDDLE),

		HOUSE_WALL(false, TileGraphics.HOUSE_WALL),
		HOUSE_WALL_BOTTOM(false, TileGraphics.HOUSE_WALL_BOTTOM),
		;

		final boolean passable;
		final TileGraphics[] tileGraphics;

		Terrain(boolean passable, TileGraphics... tileGraphics)
		{
			this.passable = passable;
			this.tileGraphics = tileGraphics;
		}

		public TileGraphics getTile(int time)
		{
			return tileGraphics[time%tileGraphics.length];
		}
	}

	@Getter
	@AllArgsConstructor
	public static class ActionResult
	{
		private final ResultType result;
		private final GameObject contactedGameObject;
		private final ItemType collectedItem;

		public ActionResult(ResultType result)
		{
			this(result, null, null);
		}

		public ActionResult(ResultType result, GameObject contactedGameObject)
		{
			this(result, contactedGameObject, null);
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
		CHANGE_LEVEL,
		DIALOG,
		WILD_POKEMON,
		COLLECT_ITEM,
		DIALOG_PROGRESS,
		END_OF_BATTLE
	}
}
