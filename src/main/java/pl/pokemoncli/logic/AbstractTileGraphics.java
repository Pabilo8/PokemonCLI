package pl.pokemoncli.logic;

/**
 * @author Pabilo8
 * @since 20.01.2025
 */
public interface AbstractTileGraphics<GRAPHICS>
{
	/**
	 * Called upon loading the game, load the graphics here (regardless if text or images).
	 */
	void loadGraphics();

	/**
	 * Draws the tile with additional parameters
	 *
	 * @param x            x position
	 * @param y            y position
	 * @param graphics     graphics object
	 * @param seed         seed for randomization, should be a constant for a given world position
	 * @param animation    animation state
	 * @param surroundings surroundings of the tile
	 */
	default void drawBackground(int x, int y, GRAPHICS graphics, int seed, float animation, boolean[] surroundings)
	{

	}

	/**
	 * Draws the tile
	 *
	 * @param x        x position
	 * @param y        y position
	 * @param graphics graphics object
	 */
	void drawBackground(int x, int y, GRAPHICS graphics);

	/**
	 * Draws the tile with transparency
	 * @param x x position
	 * @param y y position
	 * @param graphics graphics object
	 */
	default void drawTransparent(int x, int y, GRAPHICS graphics)
	{
		drawBackground(x, y, graphics);
	}

	/**
	 * @return whether this tile can draw another layer on top of GameObjects
	 */
	default boolean hasForeground()
	{
		return false;
	}

	/**
	 * Draws the foreground of the tile
	 *
	 * @param x            x position
	 * @param y            y position
	 * @param graphics     graphics object
	 * @param seed         seed for randomization, should be a constant for a given world position
	 * @param animation    animation state
	 * @param surroundings surroundings of the tile
	 */
	default void drawForeground(int x, int y, GRAPHICS graphics, int seed, float animation, boolean[] surroundings)
	{
	}
}
