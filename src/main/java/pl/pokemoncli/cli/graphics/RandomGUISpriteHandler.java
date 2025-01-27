package pl.pokemoncli.cli.graphics;

import lombok.RequiredArgsConstructor;
import pl.pokemoncli.gui.graphics.GUITileGraphics;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.characters.GameObject;

import java.util.HashMap;

/**
 * @author Pabilo8
 * @since 27.01.2025
 */
@RequiredArgsConstructor
public class RandomGUISpriteHandler<T extends GameObject> extends SpriteHandler<T, GUITileGraphics>
{
	private final GUITileGraphics defaultSprite;
	private HashMap<String, GUITileGraphics> sprites = new HashMap<>();

	public RandomGUISpriteHandler<T> withSprite(String name, GUITileGraphics sprite)
	{
		sprites.put(name, sprite);
		return this;
	}

	@Override
	protected GUITileGraphics getSprite(T gameObject, float progress)
	{
		return sprites.getOrDefault(gameObject.getName(), defaultSprite);
	}
}
