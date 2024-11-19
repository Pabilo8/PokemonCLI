package pl.pokemoncli.logic.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Door;
import pl.pokemoncli.logic.characters.FightableCharacter;
import pl.pokemoncli.logic.characters.NPC;
import pl.pokemoncli.logic.characters.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class MovableCharacterTest
{
	Level level;
	FightableCharacter character;

	@BeforeEach
	void setUp()
	{
		level = new Level(5, 10, Terrain.GRASS);
		character = new Player("Test", 0, 0, 1);
		level.addCharacter(character);
	}

	@Test
	void testSetPosition()
	{
		character.setPosition(1, 1);
		assertEquals(1, character.getX());
		assertEquals(1, character.getY());
	}

	@Test
	void testMoveCharacter()
	{
		ActionResult result = level.moveCharacterBy(character, 1, 0);

		assertEquals(ResultType.MOVE, result.getResult());
		assertEquals(1, character.getX());
		assertEquals(0, character.getY());
	}

	@Test
	void testMoveCharacterToObstacle()
	{
		character.setPosition(0, 0);
		level.setTerrain(1, 0, Level.Terrain.BLOCKED);
		ActionResult result = level.moveCharacterBy(character, 1, 0);

		assertEquals(ResultType.MET_OBSTACLE, result.getResult());
		assertEquals(0, character.getX());
		assertEquals(0, character.getY());
	}

	@Test
	void testMoveCharacterDoor()
	{
		Level interior = new Level(5, 5, Terrain.FLOOR);
		level.addDoor(1, 0, interior, 0, 0);

		ActionResult result = level.moveCharacterBy(character, 1, 0);

		assertEquals(ResultType.CHANGE_LEVEL, result.getResult());
		assertInstanceOf(Door.class, result.getContactedGameObject());

		assertEquals(interior, ((Door)result.getContactedGameObject()).getLevel());
	}

	@Test
	void testMoveCharacterToNPC()
	{
		NPC npc = new NPC("NPC", 1, 0);

		level.addCharacter(character);
		level.addCharacter(npc);
		ActionResult result = level.moveCharacterBy(character, 1, 0);

		assertEquals(ResultType.DIALOG, result.getResult());
		assertEquals(npc, result.getContactedGameObject());
	}
}
