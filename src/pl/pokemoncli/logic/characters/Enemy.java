package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.Tile;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Enemy extends Character
{
	public Enemy(String name, int y, int health, int maxHealth, int x)
	{
		super(name, x, y, health, maxHealth);
	}

	@Override
	public Tile getCurrentSprite()
	{
		return Tile.PLAYER_VERTICAL;
	}
}
