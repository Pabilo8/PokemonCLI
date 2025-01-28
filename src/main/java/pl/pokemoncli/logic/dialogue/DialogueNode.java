package pl.pokemoncli.logic.dialogue;

import lombok.Getter;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
@Getter
public class DialogueNode
{
	private final String message;
	@Nullable
	private final Track audio;
	private final List<DialogueResponse> responses;

	public DialogueNode(String message, DialogueResponse... responses)
	{
		this.message = message;
		this.audio = null;
		this.responses = List.of(responses);
	}

	public DialogueNode(String message, Track audio, DialogueResponse... responses)
	{
		this.message = message;
		this.audio = audio;
		this.responses = List.of(responses);
	}

}