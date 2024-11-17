package pl.pokemoncli.display;

import pl.pokemoncli.logic.characters.Player;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class PanelDisplay extends BaseDisplay
{
	private final Color backgroundColor = new Color(127, 127, 127);
	private final Color textColor = new Color(255, 255, 255);

	public PanelDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	public void drawSidePanel(Player player, int gameX, int gameY)
	{
		// Draw Frame
		for(int y = 0; y < gameY; y++)
			for(int x = gameX; x < terminal.getWidth(); x++)
				terminal.drawColor(x, y, ' ', textColor, backgroundColor);

		// Draw Stats
		drawString("X: "+player.getX(), gameX+2+15, 2);
		drawString("Y: "+player.getY(), gameX+2+15, 3);

		drawString("Name: "+player.getName(), gameX+2, 2);
		drawString("HP: "+player.getHealth(), gameX+2, 3);

		drawString("Inventory:", gameX+2, 5);

		Tile.BLOCKED.draw(gameX+2, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+8, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+16, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+24, 7, terminal);
	}
}