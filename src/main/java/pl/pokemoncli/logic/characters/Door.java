package pl.pokemoncli.logic.characters;

import lombok.Getter;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.Level;

/**
 * @author Pabilo8
 * @since 17.11.2024
 */
@Getter
public class Door extends GameObject
{
	private final Level level;
	private final int destX, destY;

	public Door(int x, int y, Level level, int destX, int destY)
	{
		super("door", x, y);
		this.level = level;
		this.destX = destX;
		this.destY = destY;
	}

	@Override
	public Tile getCurrentSprite()
	{
		return Tile.DOOR_OPENABLE;
	}
}
