package pl.pokemoncli.gui;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class FightScreen implements KeyHandlingDisplay
{
	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		return null;
	}
}
