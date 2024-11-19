package pl.pokemoncli.logic.characters;

import lombok.Getter;
import lombok.Setter;
import pl.pokemoncli.display.graphics.TileGraphics;
import pl.pokemoncli.logic.dialogue.DialogueNode;

/**
 * @author Pabilo8
 * @since 17.11.2024
 */
@Setter
@Getter
public class NPC extends GameObject
{
	DialogueNode dialogue;

	public NPC(String name, int x, int y)
	{
		super(name, x, y);
	}

	public NPC withDialogue(DialogueNode dialogue)
	{
		this.dialogue = dialogue;
		return this;
	}

	@Override
	public TileGraphics getCurrentSprite()
	{
		return TileGraphics.NPC_VERTICAL;
	}
}
