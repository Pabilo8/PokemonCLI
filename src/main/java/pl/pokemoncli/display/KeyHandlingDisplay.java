package pl.pokemoncli.display;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
public interface KeyHandlingDisplay
{
	ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key);
}
