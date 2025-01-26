package pl.pokemoncli.logic;

/**
 * @author Pabilo8
 * @since 20.01.2025
 */
public interface AbstractTileGraphics<GRAPHICS>
{
	void loadGraphics();

	void draw(int x, int y, GRAPHICS graphics);

	default void drawTransparent(int x, int y, GRAPHICS graphics)
	{
		draw(x, y, graphics);
	}
}
