package pl.pokemoncli.gui.display;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class DialogueGui implements KeyHandlingDisplay
{
	private JPanel mainPanel;
	private JButton button1;
	private JTextPane textPane1;
	private JComboBox comboBox1;

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		return null;
	}
}
