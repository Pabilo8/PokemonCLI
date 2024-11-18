package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.equipment.ItemType;
import pl.pokemoncli.logic.pokemon.Pokemon;

import java.util.ArrayList;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Player extends Character
{
	protected final ItemType[] equipment = new ItemType[3];

	Tile currentSprite = Tile.PLAYER_VERTICAL;

	public Player(String name, int y, int x, int maxPokemons)
	{
		super(name, x, y, maxPokemons, false);
	}

	@Override
	public void setPosition(int x, int y)
	{
		int cX = this.x, cY = this.y;
		super.setPosition(x, y);

		//Change sprite based on movement
		if(x > cX)
			currentSprite = Tile.PLAYER_RIGHT;
		else if(x < cX)
			currentSprite = Tile.PLAYER_LEFT;
		else
			currentSprite = Tile.PLAYER_VERTICAL;
	}

	@Override
	public Tile getCurrentSprite()
	{
		return currentSprite;
	}
}
