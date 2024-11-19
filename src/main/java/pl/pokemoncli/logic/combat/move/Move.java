package pl.pokemoncli.logic.combat.move;

import lombok.Getter;
import pl.pokemoncli.logic.combat.pokemon.PokemonType;

@Getter
public class Move
{
	private final MoveType type;
	private int currentPp;

	public Move(MoveType type)
	{
		this.type = type;
		this.currentPp = type.getPp();
	}

	public void reduceCurrentPp(int amount)
	{
		currentPp = Math.max(currentPp-amount, 0);
	}

	public void increaseCurrentPp(int amount)
	{
		currentPp = Math.min(currentPp+amount, type.getPp());
	}

	public String getName()
	{
		return type.name();
	}

	public PokemonType getType()
	{
		return type.getType();
	}

	public MoveCategory getCategory()
	{
		return type.getCategory();
	}

	public int getPower()
	{
		return type.getPower();
	}

	public int getAccuracy()
	{
		return type.getAccuracy();
	}

	public int getPp()
	{
		return type.getPp();
	}

}
