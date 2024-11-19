package pl.pokemoncli.logic.pokemon;

import lombok.Getter;

@Getter
public class Move
{
	private final String name;
	private final PokemonType type;
	private final MoveCategory category;
	private final int power;
	private final int accuracy;
	private final int pp;
	private int currentPp;

	public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp)
	{
		this.name = name;
		this.type = type;
		this.category = category;
		this.power = power;
		this.accuracy = accuracy;
		this.pp = pp;
		this.currentPp = pp;
	}

	public Move(Move move)
	{
		this.name = move.name;
		this.type = move.type;
		this.category = move.category;
		this.power = move.power;
		this.accuracy = move.accuracy;
		this.pp = move.pp;
		this.currentPp = move.pp;
	}

	public void reduceCurrentPp(int amount)
	{
		currentPp -= amount;
	}

	public void increaseCurrentPp(int amount)
	{
		currentPp += amount;
	}

	public enum MoveCategory
	{
		PHISICAL,
		SPECIAL,
		STATUS
	}
}
