package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.pokemon.Pokemon;

import java.util.ArrayList;

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
	private int usablePokemons;
	private boolean fightable;

	public Character(String name, int x, int y, int maxPokemons)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.maxPokemons = maxPokemons;
		this.pokemons = new ArrayList<>();
		this.usablePokemons = 0;
		this.fightable = false;
	}

	protected Character(String name, int x, int y, int maxPokemons, boolean fightable)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.maxPokemons = maxPokemons;
		this.pokemons = new ArrayList<>();
		this.usablePokemons = 0;
		this.fightable = fightable;
	}

	public void reduceUsablePokemons(int amount) {
		if (usablePokemons > 0)
			usablePokemons--;
	}

	public void increaseUsablePokemons(int amount) {
		if (usablePokemons < maxPokemons)
			usablePokemons++;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void addPokemon(Pokemon newPokemon)
	{
		if(pokemons.size() < maxPokemons) {
			pokemons.add(newPokemon);
			if (newPokemon.getCurrentHp()>0) {
				usablePokemons++;
			}
		}
	}

	public Character withPokemon(Pokemon newPokemon)
	{
		addPokemon(newPokemon);
		return this;
	}

	public Pokemon getPokemon(int id)
	{
		return pokemons.get(id);
	}

	public abstract Tile getCurrentSprite();

}
