package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public abstract class Character
{
	protected String name;
	protected int x, y;
	private ArrayList<Pokemon> pokemons;
	private int maxPokemons;

	public Character(String name, int x, int y, int maxPokemons)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.maxPokemons = maxPokemons;
		this.pokemons = new ArrayList<>();
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public void addPokemon(Pokemon newPokemon) {
		if (pokemons.size()<maxPokemons)
			pokemons.add(newPokemon);
	}

	public Pokemon getPokemon(int id) {
		return pokemons.get(id);
	}

	public abstract Tile getCurrentSprite();

}
