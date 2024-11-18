package pl.pokemoncli.logic.pokemon;

import pl.pokemoncli.display.PokemonSprite;

import java.util.ArrayList;

public class Pokedex {
    private final ArrayList<Pokemon> Dex;
    private final int MAX_POKEMON_NUMBER = 133;
    private final Pokemon Pokemon_Null;
    private final MoveList moves;

    public Pokedex() {
        Dex = new ArrayList<>();
        moves = new MoveList();
        Pokemon_Null = new Pokemon(0,"POKEMON_NULL",PokemonType.NONE,PokemonType.NONE ,0,0,0,0,0,0, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
        AssignPokemons();
    }
    private void AssignPokemons() {
        for(int id = 0; id <= MAX_POKEMON_NUMBER; id++) {
            Dex.add(PokemonById(id));
        }
    }
    private Pokemon PokemonById(int id) {
        return switch (id)
        {
            case 1 -> new Pokemon(1,"Bulbasaur",PokemonType.GRASS,PokemonType.POISON ,45,49,49,65,65,45, PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL, new ArrayList<>());
            case 133 -> new Pokemon(133,"Eevee",PokemonType.NORMAL, PokemonType.NONE,55,55,50,45,65,55, PokemonSprite.EEVEE_BACK, PokemonSprite.EEVEE_BACK, new ArrayList<>());
            default -> Pokemon_Null;
        };
    }
    public Pokemon getPokemon(int id) {
        return Dex.get(id);
    }
}
