package pl.pokemoncli.logic.characters;

import pl.pokemoncli.display.graphics.TileGraphics;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Player extends FightableCharacter
{
	TileGraphics currentSprite = TileGraphics.PLAYER_VERTICAL;

	public Player(String name, int y, int x, int maxPokemons)
	{
		super(name, x, y, maxPokemons);
	}

	@Override
	public void setPosition(int x, int y)
	{
		int cX = this.x, cY = this.y;
		super.setPosition(x, y);

		//Change sprite based on movement
		if(x > cX)
			currentSprite = TileGraphics.PLAYER_RIGHT;
		else if(x < cX)
			currentSprite = TileGraphics.PLAYER_LEFT;
		else
			currentSprite = TileGraphics.PLAYER_VERTICAL;
	}

	@Override
	public TileGraphics getCurrentSprite()
	{
		return currentSprite;
	}
}
