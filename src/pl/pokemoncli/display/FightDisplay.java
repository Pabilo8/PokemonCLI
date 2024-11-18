package pl.pokemoncli.display;

import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.pokemon.Pokemon;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public class FightDisplay extends BaseDisplay
{
	protected final int BarSize = 20;

	public FightDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	private void drawHpBar(Pokemon pokemon, int bar_X, int bar_Y, boolean visiblePoints) {
		int currPercentage = (pokemon.getCurrentHp()*100/pokemon.getHp())/5;
		int lines = visiblePoints? 5 : 4;

		// Bar Background
		for (int i = -1; i <= BarSize + 4; i++)
		{
			for (int j = 0; j < lines-1;j++)
			{
				drawString(" ",bar_X+i, bar_Y+j);
			}
			drawString("▃",bar_X+i, bar_Y+lines-1);
		}

		// Line 1 (Name)
		drawString(pokemon.getName(),bar_X, bar_Y);

		// Line 2 (Level)
		drawString("LvL: " + pokemon.getLevel(),bar_X+2, bar_Y+1);

		// line 3 (HP: ▓▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒)
		drawString("HP: ",bar_X, bar_Y+2);
		if (pokemon.getCurrentHp() != 0 && pokemon.getCurrentHp() != pokemon.getHp())
			++currPercentage;
		for (int i = 0; i<currPercentage; i++)
		{
			drawString("▒",bar_X + 4 + i, bar_Y+2);
		}
		for (int i = currPercentage; i < BarSize; i++)
		{
			drawString("▓",bar_X + 4 + i, bar_Y+2);
		}


		// line 4 (Current HP / Max HP)
		if(visiblePoints)
        {
			drawString(pokemon.getCurrentHp() + " / " + pokemon.getHp(),bar_X+5, bar_Y+3);
		}
    }

	public void drawFightScreen(Fight fight, int Display_X, int Display_Y) {
		Color foreground = new Color(0x0A3A0A);
		Color background = new Color(0x4C7C4F);
		// clear terminal
		for(int y = 0; y < terminal.getHeight(); y++)
		{
			for(int x = 0; x < terminal.getWidth(); x++)
			{
				terminal.drawColor(x, y, '░', foreground, background);
			}
		}

		// draw enemy pokemon
		fight.getCurrEnemyPokemon().getFront().draw(Display_X - PokemonSprite.POKEMON_SIZE_X-4, 2, terminal);
		drawHpBar(fight.getCurrEnemyPokemon(), 7, 2, false);

		// draw player pokemon
		fight.getCurrPlayerPokemon().getBack().draw(4, Display_Y-PokemonSprite.POKEMON_SIZE_Y-2, terminal);
		drawHpBar(fight.getCurrPlayerPokemon(),4 + PokemonSprite.POKEMON_SIZE_X + 2, Display_Y-PokemonSprite.POKEMON_SIZE_Y-2, true);

		// draw options menu
		//drawString("Fight!", terminal.getWidth() - 2, 2);
		//drawString("Player", 2, 4);
		//drawString("Enemy", terminal.getWidth() - 7, 4);
	}
}