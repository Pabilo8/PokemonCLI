package pl.pokemoncli.logic.dialogue;

import lombok.Getter;
import pl.pokemoncli.logic.characters.GameObject;

/**
 * @author Pabilo8
 * @since 18.11.2024
 */
public class Dialogue
{
	@Getter
	private DialogueNode currentNode;
	@Getter
	private final GameObject gameObject;

	private int selectedResponseIndex;

	public Dialogue(DialogueNode startNode, GameObject gameObject)
	{
		this.currentNode = startNode;
		this.gameObject = gameObject;
		this.selectedResponseIndex = 0;
	}

	public void selectNextResponse()
	{
		selectedResponseIndex = (selectedResponseIndex+1)%currentNode.getResponses().size();
	}

	public void selectPreviousResponse()
	{
		selectedResponseIndex = (selectedResponseIndex-1+currentNode.getResponses().size())%currentNode.getResponses().size();
	}

	public DialogueResponse getSelectedResponse()
	{
		return currentNode.getResponses().get(selectedResponseIndex);
	}

	public void advanceDialogue()
	{
		currentNode = getSelectedResponse().getNextNode();
		selectedResponseIndex = 0;
	}
}