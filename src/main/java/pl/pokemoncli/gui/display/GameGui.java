package pl.pokemoncli.gui.display;

import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class GameGui implements KeyHandlingDisplay, IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JPanel sidePanel;
	private JPanel gameDrawPanel;

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		return null;
	}

	private void createUIComponents()
	{
		// TODO: place custom component creation code here
		gameDrawPanel = new GameDrawPanel();
	}

	@Override
	public void onInit()
	{
		PokemonGUI.getInstance().getAudioSystem().play(Track.GAME);
	}

	/**
	 * @author Pabilo8
	 * @since 19.01.2025
	 */
	public static class GameDrawPanel extends JPanel
	{
		public GameDrawPanel()
		{

		}

		@Override
		public void paintComponents(Graphics g)
		{
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 100, 100);
		}
	}
}
