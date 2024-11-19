package pl.pokemoncli.logic.combat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pl.pokemoncli.logic.combat.move.Move;
import pl.pokemoncli.logic.combat.move.MoveType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class MoveTest
{
	private Move move;

	void setUp(MoveType moveType)
	{
		move = new Move(moveType);
	}

	@ParameterizedTest
	@EnumSource(MoveType.class)
	void testReduceCurrentPp(MoveType moveType)
	{
		setUp(moveType);
		move.reduceCurrentPp(5);
		assertEquals(moveType.getPp()-5, move.getCurrentPp(), "PP should be reduced by 5");
	}

	@ParameterizedTest
	@EnumSource(MoveType.class)
	void testIncreaseCurrentPp(MoveType moveType)
	{
		setUp(moveType);
		move.reduceCurrentPp(1000);
		move.increaseCurrentPp(1000);
		assertEquals(Math.min(moveType.getPp()+5, moveType.getPp()), move.getCurrentPp(), "PP should be increased back to max level");
	}

	@ParameterizedTest
	@EnumSource(MoveType.class)
	void testGetName(MoveType moveType)
	{
		setUp(moveType);
		assertEquals(moveType.name().toLowerCase().replace('_', ' '), move.getName(), "Move name should be lowercase");
	}
}
