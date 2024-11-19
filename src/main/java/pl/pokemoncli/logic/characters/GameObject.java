package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.Tile;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public abstract class GameObject
{
	protected String name;
	protected int x, y;

	protected GameObject(String name, int x, int y)
	{
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public abstract Tile getCurrentSprite();

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
