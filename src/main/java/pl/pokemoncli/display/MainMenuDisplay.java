package pl.pokemoncli.display;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.display.graphics.AsciiArtLoader;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class MainMenuDisplay extends BaseDisplay implements KeyHandlingDisplay
{
	private final Color backgroundColor = new Color(0, 0, 0);
	private final Color textColor = new Color(165, 86, 86);

	private final String[] logo;
	private int selectedOption = 0;
	private final List<String> options = Arrays.asList("New  Game", "Load Game", "   Exit  ");

	public MainMenuDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
		logo = AsciiArtLoader.loadAsciiArt("logo.txt", 67, 18, new String[0]);
	}

	public void drawMainMenu()
	{
		drawLogo();
		drawMenuOptions();
	}

	private void drawLogo()
	{
		for(int i = 0; i < logo.length; i++)
			drawStringColor(logo[i], 10, 1+i, textColor, backgroundColor);
	}

	private void drawMenuOptions()
	{
		for(int i = 0; i < options.size(); i++)
		{
			String option = options.get(i);
			if(i==selectedOption)
				option = "> "+option+" <";
			else
				option = "  "+option+"  ";
			drawStringColor(option, 10, 20+i, textColor, backgroundColor);
		}
	}

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		switch(key.getCharacter())
		{
			case 'w' -> selectedOption = (selectedOption-1+options.size())%options.size();
			case 's' -> selectedOption = (selectedOption+1)%options.size();
			case ' ' ->
			{
				return switch(selectedOption)
				{
					case 0 -> new ActionResult(ResultType.NEW_GAME);
					case 1 -> new ActionResult(ResultType.LOAD_GAME);
					case 2 -> new ActionResult(ResultType.EXIT_GAME);
					default -> throw new IllegalStateException("Unexpected value: "+selectedOption);
				};
			}
		}

		//not handled, will do nothing
		return new ActionResult(ResultType.MET_OBSTACLE);
	}
}
