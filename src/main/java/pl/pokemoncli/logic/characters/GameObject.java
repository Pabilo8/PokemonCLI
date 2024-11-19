package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.graphics.TileGraphics;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
@Getter
public abstract class GameObject implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	protected String name;
	protected int x, y;

	protected GameObject(String name, int x, int y)
	{
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public abstract TileGraphics getCurrentSprite();

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
