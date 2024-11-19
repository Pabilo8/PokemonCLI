package pl.pokemoncli.logic.combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pl.pokemoncli.logic.combat.move.Move;
import pl.pokemoncli.logic.combat.move.MoveType;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class PokemonTest
{
	private Pokemon pokemon;

	@BeforeEach
	void setUp()
	{
		pokemon = new Pokemon(PokemonSpecies.BULBASAUR, 5);
	}

	@Test
	void testReduceCurrentHp()
	{
		pokemon.reduceCurrentHp(10);
		assertEquals(35, pokemon.getCurrentHp(), "HP should be reduced by 10");
	}

	@Test
	void testIncreaseCurrentHp()
	{
		pokemon.reduceCurrentHp(10);
		pokemon.increaseCurrentHp(5);
		assertEquals(40, pokemon.getCurrentHp(), "HP should be increased by 5");
	}

	@ParameterizedTest
	@EnumSource(MoveType.class)
	void testAddAttack(MoveType moveType)
	{
		Move move = new Move(moveType);
		pokemon.addAttack(move);
		assertTrue(pokemon.getMoves().contains(move), "Move should be added to the Pokemon's moves");
	}

	@ParameterizedTest
	@EnumSource(MoveType.class)
	void testReplaceAttack(MoveType moveType)
	{
		Move move1 = new Move(MoveType.TACKLE);
		Move move2 = new Move(moveType);
		pokemon.addAttack(move1);
		pokemon.replaceAttack(move2, 0);
		assertEquals(move2, pokemon.getMoves().get(0), "Move should be replaced in the Pokemon's moves");
	}

	@Test
	void testLevelUp()
	{
		pokemon.levelUp();
		assertEquals(6, pokemon.getLevel(), "Level should be increased by 1");
		assertEquals(47, pokemon.getHp(), "HP should be increased by 2");
		assertEquals(50, pokemon.getAttack(), "Attack should be increased by 1");
		assertEquals(50, pokemon.getDefence(), "Defence should be increased by 1");
		assertEquals(66, pokemon.getSpAttack(), "Special Attack should be increased by 1");
		assertEquals(66, pokemon.getSpDefence(), "Special Defence should be increased by 1");
		assertEquals(46, pokemon.getSpeed(), "Speed should be increased by 1");
		assertEquals(47, pokemon.getCurrentHp(), "Current HP should be set to new HP value");
	}
}
