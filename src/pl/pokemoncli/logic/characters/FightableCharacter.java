package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;

import java.util.ArrayList;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
@Getter
public abstract class FightableCharacter extends GameObject
{
	private final ArrayList<Pokemon> pokemons;
	private final int maxPokemons;
	private int usablePokemons;

	public FightableCharacter(String name, int x, int y, int maxPokemons)
	{
		super(name, x, y);
		this.maxPokemons = maxPokemons;
		this.pokemons = new ArrayList<>();
		this.usablePokemons = 0;
	}

	public void reduceUsablePokemons(int amount)
	{
		if(usablePokemons > 0)
			usablePokemons--;
	}

	public void increaseUsablePokemons(int amount)
	{
		if(usablePokemons < maxPokemons)
			usablePokemons++;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void addPokemon(Pokemon newPokemon)
	{
		if(pokemons.size() < maxPokemons)
		{
			pokemons.add(newPokemon);
			if(newPokemon.getCurrentHp() > 0)
				usablePokemons++;
		}
	}

	public FightableCharacter withPokemon(Pokemon newPokemon)
	{
		addPokemon(newPokemon);
		return this;
	}

	public Pokemon getPokemon(int id)
	{
		return pokemons.get(id);
	}
}
