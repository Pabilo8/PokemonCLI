package pl.pokemoncli.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Door;
import pl.pokemoncli.logic.characters.GameObject;
import pl.pokemoncli.logic.characters.Player;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
class LevelTest
{
	Level level;
	GameObject character;

	@BeforeEach
	void setUp()
	{
		level = new Level(5, 10, Terrain.GRASS);
		character = new Player("Test", 1, 1, 1);
		level.addCharacter(character);
	}

	@Test
	void createLevelTest()
	{
		assertNotNull(level);
		assertEquals(level.getWidth(), 5);
		assertEquals(level.getHeight(), 10);

		int width = level.getWidth();
		int height = level.getHeight();

		for(int x = -1; x < 11; x++)
			for(int y = -1; y < 11; y++)
			{
				if(x < 0||x >= width||y < 0||y >= height)
					assertEquals(Terrain.VOID, level.getTerrain(x, y));
				else
					assertEquals(Terrain.GRASS, level.getTerrain(x, y));
			}
	}

	@Test
	void containsCharacterTest()
	{
		assertNotNull(level.findCharacterAt(null, character.getX(), character.getY()), "Character was not added to the level");

		level.removeCharacter(character);
		assertNull(level.findCharacterAt(null, character.getX(), character.getY()), "Character was not removed from the level");
	}

	@Test
	void setTerrainTest()
	{
		Level level = new Level(5, 10, Terrain.GRASS);
		level.setTerrain(1, 1, Terrain.FLOOR);
		assertEquals(level.getTerrain(1, 1), Terrain.FLOOR);
	}

	@Test
	void paintTerrainTest()
	{
		Level level = new Level(5, 10, Terrain.GRASS);
		level.paintTerrain(1, 1, 2, 2, Terrain.WATER_FLOWING);
		for(int x = 1; x < 3; x++)
			for(int y = 1; y < 3; y++)
				assertEquals(level.getTerrain(x, y), Terrain.WATER_FLOWING);
	}

	@Test
	void testPlaceHouseWithDoor()
	{
		Level interior = new Level(5, 5, Terrain.FLOOR);
		level.placeHouse(2, 2, 1, 3, 3, 2, interior, 1, 1);

		GameObject door = level.findCharacterAt(null, 3, 2);
		assertNotNull(door, "Door should be placed when interior is provided");
		assertInstanceOf(Door.class, door, "Placed object should be an instance of Door");
		assertEquals(interior, ((Door)door).getLevel(), "Door should lead to the provided interior level");
	}

	@Test
	void testPlaceHouseWithoutDoor()
	{
		level.placeHouse(2, 2, 1, 3, 3, 2, null, 0, 0);

		GameObject door = level.findCharacterAt(null, 2, 2);
		assertNull(door, "Door should not be placed when interior is null");
	}

	@Test
	void testAddDoor()
	{
		Level interior = new Level(5, 5, Terrain.FLOOR);
		level.addDoor(2, 2, interior, 3, 3);

		GameObject door = level.findCharacterAt(null, 2, 2);

		assertNotNull(door);
		assertInstanceOf(Door.class, door);
		assertEquals(interior, ((Door)door).getLevel());

		assertEquals(3, ((Door)door).getDestX());
		assertEquals(3, ((Door)door).getDestY());
	}
}