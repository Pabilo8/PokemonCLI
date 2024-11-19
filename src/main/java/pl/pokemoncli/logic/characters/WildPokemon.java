package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;

public class WildPokemon extends Enemy
{
    public WildPokemon(Pokemon wildPokemon, int x, int y)
    {
        super(wildPokemon.getName(), x, y, 1);
        getPokemons().add(wildPokemon);
    }

    @Override
    public Tile getCurrentSprite()
    {
        return Tile.BUSH1;
    }
}