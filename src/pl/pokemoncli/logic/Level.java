package pl.pokemoncli.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public class Level
{
	private int width, height;
	private Terrain[][] map;
	private List<Character> characters;
	private Player player;

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

	public boolean moveCharacterBy(Character character, int dx, int dy)
	{
		int newX = character.getX()+dx;
		int newY = character.getY()+dy;

		// Check if the new position is within bounds
		if(newX < 0||newY < 0||newX >= map.length||newY >= map[0].length)
			return false;

		// Check if the tile is passable
		if(!map[newX][newY].isPassable())
			return false;

		// Check if there is another character at the new position
		for(Character other : characters)
			if(other.getX()==newX&&other.getY()==newY)
				return false;

		// Move the character
		character.setPosition(newX, newY);
		return true;
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
}
