package pl.pokemoncli.logic.dialogue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.sound.AudioSystem.Track;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
@Getter
@AllArgsConstructor
public class DialogueResponse
{
	private final String text;
	private final Track audio;
	private final DialogueNode nextNode;

	public DialogueResponse(String text, DialogueNode nextNode)
	{
		this.text = text;
		this.audio = null;
		this.nextNode = nextNode;
	}
}