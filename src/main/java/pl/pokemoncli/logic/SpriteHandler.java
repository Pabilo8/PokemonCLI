package pl.pokemoncli.logic;

import lombok.RequiredArgsConstructor;
import pl.pokemoncli.logic.characters.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pabilo8
 * @since 21.01.2025
 */
public abstract class SpriteHandler<T extends GameObject, G extends AbstractTileGraphics<?>>
{
	private static final Map<Class<? extends GameObject>, SpriteHandler<?, ?>> handlers = new HashMap<>();

	public static <T extends GameObject, G extends AbstractTileGraphics<?>> void registerHandler(Class<T> clazz, SpriteHandler<T, G> handler)
	{
		handlers.put(clazz, handler);
	}

	@SuppressWarnings("unchecked")
	public static <T extends GameObject, G extends AbstractTileGraphics<?>> SpriteHandler<T, G> getHandler(Class<T> clazz)
	{
		return (SpriteHandler<T, G>)handlers.get(clazz);
	}

	public G getSpriteFor(GameObject gameObject)
	{
		return getSpriteFor(gameObject, 0);
	}

	public G getSpriteFor(GameObject gameObject, float progress)
	{
		return getSprite((T)gameObject, progress);
	}

	protected abstract G getSprite(T gameObject, float progress);

	@RequiredArgsConstructor
	public static class SimpleSpriteHandler<T extends GameObject, G extends AbstractTileGraphics<?>> extends SpriteHandler<T, G>
	{
		private final G graphics;

		@Override
		protected G getSprite(T gameObject, float progress)
		{
			return graphics;
		}
	}
}
