package pl.pokemoncli.logic.dialogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.NPC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
class DialogueTest
{
	Enemy unrelatedCharacter;

	@BeforeEach
	void setUp()
	{
		unrelatedCharacter = new Enemy("Test", 1, 1, 1);
	}

	@Test
	void testSelectNextResponse()
	{
		DialogueNode node = new DialogueNode("Message", new DialogueResponse("Response1", null), new DialogueResponse("Response2", null));
		Dialogue dialogue = new Dialogue(node, unrelatedCharacter);

		dialogue.selectNextResponse();
		assertEquals("Response2", dialogue.getSelectedResponse().getText());

		dialogue.selectNextResponse();
		assertEquals("Response1", dialogue.getSelectedResponse().getText());
	}

	@Test
	void testSelectPreviousResponse()
	{
		DialogueNode node = new DialogueNode("Message", new DialogueResponse("Response1", null), new DialogueResponse("Response2", null));
		Dialogue dialogue = new Dialogue(node, unrelatedCharacter);

		dialogue.selectPreviousResponse();
		assertEquals("Response2", dialogue.getSelectedResponse().getText());

		dialogue.selectPreviousResponse();
		assertEquals("Response1", dialogue.getSelectedResponse().getText());
	}

	@Test
	void testAdvanceDialogue()
	{
		DialogueNode nextNode = new DialogueNode("Next Message", new DialogueResponse("End", null));
		DialogueNode startNode = new DialogueNode("Start Message", new DialogueResponse("Go to next", nextNode));
		Dialogue dialogue = new Dialogue(startNode, unrelatedCharacter);

		dialogue.advanceDialogue();
		assertEquals("Next Message", dialogue.getCurrentNode().getMessage());
	}

	@Test
	void testCharacterDialogue()
	{
		DialogueNode node = new DialogueNode("Message",
				new DialogueResponse("Response1", null),
				new DialogueResponse("Response2", null));
		NPC npc = new NPC("Test", 1, 1)
				.withDialogue(node);

		Dialogue dialogue = new Dialogue(npc);

		assertNotNull(dialogue.getCurrentNode());
		assertEquals("Message", dialogue.getCurrentNode().getMessage());
	}
}

