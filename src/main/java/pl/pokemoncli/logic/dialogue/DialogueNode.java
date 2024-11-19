package pl.pokemoncli.logic.dialogue;

import lombok.Getter;

import java.util.List;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
@Getter
public class DialogueNode
{
	private final String message;
	private final List<DialogueResponse> responses;

	public DialogueNode(String message, DialogueResponse... responses)
	{
		this.message = message;
		this.responses = List.of(responses);
	}

}