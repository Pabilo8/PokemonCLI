package pl.pokemoncli.logic.pokemon;

import pl.pokemoncli.display.PokemonSprite;

import java.util.ArrayList;

public class Pokedex
{
	private final ArrayList<Pokemon> Dex;
	private final int MAX_POKEMON_NUMBER = 133;
	private final Pokemon Pokemon_Null;
	private final MoveList moves;

	public Pokedex()
	{
		Dex = new ArrayList<>();
		moves = new MoveList();
		Pokemon_Null = new Pokemon(0, "POKEMON_NULL", PokemonType.NONE, PokemonType.NONE, 0, 0, 0, 0, 0, 0, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
		AssignPokemons();
	}

	private void AssignPokemons()
	{
		for(int id = 0; id <= MAX_POKEMON_NUMBER; id++)
			Dex.add(PokemonById(id));
	}

	private Pokemon PokemonById(int id)
	{
		return switch(id)
		{
			case 1 ->
					new Pokemon(id, "Bulbasaur", PokemonType.GRASS, PokemonType.POISON, 45, 49, 49, 65, 65, 45, PokemonSprite.BULBASAUR_FRONT, PokemonSprite.BULBASAUR_BACK, new ArrayList<>());
			case 4 ->
					new Pokemon(id, "Charmander", PokemonType.FIRE, PokemonType.NONE, 39, 52, 43, 60, 50, 65, PokemonSprite.CHARMANDER_FRONT, PokemonSprite.CHARMANDER_BACK, new ArrayList<>());
			case 7 ->
					new Pokemon(id, "Squirltle", PokemonType.WATER, PokemonType.NONE, 44, 48, 65, 50, 64, 43, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 10 ->
					new Pokemon(id, "Caterpie", PokemonType.BUG, PokemonType.NONE, 45, 30, 35, 20, 20, 45, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 11 ->
					new Pokemon(id, "Metapod", PokemonType.BUG, PokemonType.NONE, 50, 20, 55, 25, 25, 30, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 12 ->
					new Pokemon(id, "Butterfree", PokemonType.BUG, PokemonType.FLYING, 60, 45, 50, 90, 80, 70, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 13 ->
					new Pokemon(id, "Weedle", PokemonType.BUG, PokemonType.POISON, 40, 35, 30, 20, 20, 50, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 14 ->
					new Pokemon(id, "Kakuna", PokemonType.BUG, PokemonType.POISON, 45, 25, 50, 25, 25, 35, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 15 ->
					new Pokemon(id, "Beedrill", PokemonType.BUG, PokemonType.POISON, 65, 90, 40, 45, 80, 75, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 16 ->
					new Pokemon(id, "Pidgey", PokemonType.NORMAL, PokemonType.NONE, 40, 45, 40, 35, 35, 56, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 17 ->
					new Pokemon(id, "Rattata", PokemonType.NORMAL, PokemonType.FLYING, 30, 56, 35, 25, 35, 72, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
			case 133 ->
					new Pokemon(id, "Eevee", PokemonType.NORMAL, PokemonType.NONE, 55, 55, 50, 45, 65, 55, PokemonSprite.EEVEE_FRONT, PokemonSprite.EEVEE_BACK, new ArrayList<>());
			default -> Pokemon_Null;
		};
	}

	public Pokemon getPokemon(int id)
	{
		return Dex.get(id);
	}
}
