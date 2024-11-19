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
		drawString("Name: "+player.getName(), gameX+2, 1);
		drawString("X: "+player.getX(), gameX+2, 2);
		drawString("Y: "+player.getY(), gameX+20, 2);

		//Draw Pokemon list
		drawString("Pokemons:", gameX+2, 3);
		for(int i=0;i<player.getMaxPokemons();i++)
		{
			if (player.getPokemon(i).getId() != 0) {
				drawString("⮚ " + player.getPokemon(i).getName(), gameX + 2, 4 + i);
			} else {
				drawString("⮚ ", gameX + 2, 4 + i);
			}
		}
	}
}