package pl.pokemoncli.logic.dialogue;

import lombok.Getter;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
@Getter
public class DialogueResponse
{
	private final String text;
	private final DialogueNode nextNode;

	public DialogueResponse(String text, DialogueNode nextNode)
	{
		this.text = text;
		this.nextNode = nextNode;
	}

}