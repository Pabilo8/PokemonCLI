package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.pokemon.AbstractPokemon;

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
	protected int health, maxHealth;
	protected final List<AbstractPokemon> pokemons;

	public Character(String name, int x, int y, int health, int maxHealth)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.health = health;
		this.maxHealth = maxHealth;
		this.pokemons = new ArrayList<>();
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public abstract Tile getCurrentSprite();

}
