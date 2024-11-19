package pl.pokemoncli.display;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.logic.dialogue.DialogueResponse;

import java.awt.*;
import java.util.List;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
public class DialogueDisplay extends BaseDisplay implements KeyHandlingDisplay
{
	private final Color backgroundColor = new Color(0, 0, 0);
	private final Color textColor = new Color(255, 255, 255);
	private final Color selectedTextColor = new Color(255, 255, 0);

	public DialogueDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	public void drawDialogue(Dialogue dialogue, int gameX, int gameY)
	{
		int y = 2;
		List<DialogueResponse> responses = dialogue.getCurrentNode().getResponses();
		DialogueResponse selectedResponse = dialogue.getSelectedResponse();


		// Draw the current message
		drawStringColor(dialogue.getCharacter().getName(), 2, y++, textColor, backgroundColor);
		drawStringWrappedColor(dialogue.getCurrentNode().getMessage(), 2, y++, gameX-5, textColor, backgroundColor);
		y += 2;

		// Draw the responses

		for(DialogueResponse response : responses)
		{
			drawStringWrappedColor(response.getText(), 4, y++, gameX-5,
					response==selectedResponse?selectedTextColor: textColor, backgroundColor);
		}
	}

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		return switch(key.getCharacter())
		{
			case 'w' ->
			{
				dialogue.selectNextResponse();
				yield new ActionResult(ResultType.DIALOG_PROGRESS);
			}
			case 's' ->
			{
				dialogue.selectPreviousResponse();
				yield new ActionResult(ResultType.DIALOG_PROGRESS);
			}
			case ' ' ->
			{
				dialogue.advanceDialogue();
				yield new ActionResult(ResultType.DIALOG_PROGRESS);
			}
			default -> null;
		};
	}
}
