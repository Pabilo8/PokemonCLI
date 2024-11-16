package pl.pokemoncli.display;

import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Player;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public class FightDisplay extends BaseDisplay
{
	public FightDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	public void drawFightScreen(Fight fight)
	{
		drawString("Fight!", terminal.getWidth()/2-2, 2);
		drawString("Player", 2, 4);
		drawString("Enemy", terminal.getWidth()-7, 4);
		drawString("HP: "+fight.getPlayer().getHealth(), 2, 5);
		drawString("HP: "+fight.getEnemy().getHealth(), terminal.getWidth()-7, 5);
	}
}