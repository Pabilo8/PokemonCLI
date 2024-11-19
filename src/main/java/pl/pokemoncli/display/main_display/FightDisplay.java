package pl.pokemoncli.display.main_display;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import pl.pokemoncli.display.BaseDisplay;
import pl.pokemoncli.display.DoubleBufferedTerminal;
import pl.pokemoncli.display.KeyHandlingDisplay;
import pl.pokemoncli.display.graphics.PokemonGraphics;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public class FightDisplay extends BaseDisplay implements KeyHandlingDisplay
{
	private final Color foreground = new Color(0x0A3A0A);
	private final Color background = new Color(0x4C7C4F);
	protected final int barSize = 20;

	public FightDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	private void drawPokemon(Pokemon pokemon, int barX, int barY, boolean visiblePoints)
	{
		int lines = visiblePoints?5: 4;

		// Bar Background
		for(int i = -1; i <= barSize+4; i++)
		{
			for(int j = 0; j < lines-1; j++)
				drawString(" ", barX+i, barY+j);
			drawString("▃", barX+i, barY+lines-1);
		}

		// Line 1 (Name)
		drawString(pokemon.getName(), barX, barY);

		// Line 2 (Level)
		drawString("LvL: "+pokemon.getLevel(), barX+2, barY+1);

		// line 3 (HP: ▓▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒)
		drawString("HP: ", barX, barY+2);
		drawHealthBar(pokemon, barX, barY);


		// line 4 (Current HP / Max HP)
		if(visiblePoints)
			drawString(pokemon.getCurrentHp()+" / "+pokemon.getHp(), barX+5, barY+3);
	}

	private void drawHealthBar(Pokemon pokemon, int barX, int barY)
	{
		int currPercentage = (pokemon.getCurrentHp()*100/pokemon.getHp())/5;
		if(pokemon.getCurrentHp()!=0&&pokemon.getCurrentHp()!=pokemon.getHp())
			++currPercentage;
		for(int i = 0; i < currPercentage; i++)
			terminal.draw(barX+4+i, barY+2, '▒');
		for(int i = currPercentage; i < barSize; i++)
			terminal.draw(barX+4+i, barY+2, '▓');
	}

	public void drawFightScreen(Fight fight, int displayX, int displayY)
	{
		// clear terminal
		for(int y = 0; y < displayY; y++)
			for(int x = 0; x < displayX; x++)
				terminal.drawColor(x, y, '░', foreground, background);

		// draw enemy pokemon
		fight.getCurrEnemyPokemon().getFront().draw(displayX-PokemonGraphics.POKEMON_SIZE_X-4, 2, terminal);
		drawPokemon(fight.getCurrEnemyPokemon(), 7, 2, false);

		// draw player pokemon
		fight.getCurrPlayerPokemon().getBack().draw(4, displayY-PokemonGraphics.POKEMON_SIZE_Y-2, terminal);
		drawPokemon(fight.getCurrPlayerPokemon(), 4+PokemonGraphics.POKEMON_SIZE_X+2, displayY-PokemonGraphics.POKEMON_SIZE_Y-2, true);
	}

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		if(key.getKind().equals(Kind.Escape))
			return fight.goBack(true, fight.getButton());
		return switch(key.getCharacter())
		{
			case 'w' -> fight.moveButton(0, 1);
			case 'a' -> fight.moveButton(-1, 0);
			case 's' -> fight.moveButton(0, -1);
			case 'd' -> fight.moveButton(1, 0);
			case ' ' ->
			{
				if(fight.getButton()==Fight.Button.RUN&&fight.isMainMenu())
					yield new ActionResult(Level.ResultType.END_OF_BATTLE);
				else yield fight.selectButton();
			}
			default -> null;
		};
	}
}