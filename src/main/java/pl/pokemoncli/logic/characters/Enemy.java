package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.Tile;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Enemy extends FightableCharacter
{
	public Enemy(String name, int x, int y, int maxPokemons)
	{
		super(name, x, y, maxPokemons);
	}

	@Override
	public Tile getCurrentSprite()
	{
		return Tile.PLAYER_VERTICAL;
	}
}