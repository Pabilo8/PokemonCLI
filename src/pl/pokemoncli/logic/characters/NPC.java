package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.Tile;

/**
 * @author Pabilo8
 * @since 17.11.2024
 */
public class NPC extends Character
{
	public NPC(String name, int x, int y)
	{
		super(name, x, y, 100, 100);
	}

	@Override
	public Tile getCurrentSprite()
	{
		return Tile.PLAYER_VERTICAL;
	}
}
