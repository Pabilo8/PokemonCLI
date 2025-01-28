package pl.pokemoncli.gui.graphics;

import pl.pokemoncli.logic.AbstractPokemonGraphics;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 28.01.2025
 */
public enum GUIPokemonGraphics implements AbstractPokemonGraphics<Graphics>
{
	POKEMON_NULL("/gui/tiles/empty.png"),
	BULBASAUR_BACK("/gui/pokemons/bulbasaur/back.png"),
	BULBASAUR_FRONT("/gui/pokemons/bulbasaur/front.png"),
	CHARMANDER_BACK("/gui/pokemons/charmander/back.png"),
	CHARMANDER_FRONT("/gui/pokemons/charmander/front.png"),
	SQUIRTLE_BACK("/gui/pokemons/squirtle/back.png"),
	SQUIRTLE_FRONT("/gui/pokemons/squirtle/front.png"),
	CATERPIE_BACK("/gui/pokemons/caterpie/back.png"),
	CATERPIE_FRONT("/gui/pokemons/caterpie/front.png"),
	METAPOD_BACK("/gui/pokemons/metapod/back.png"),
	METAPOD_FRONT("/gui/pokemons/metapod/front.png"),
	BUTTERFREE_BACK("/gui/pokemons/butterfree/back.png"),
	BUTTERFREE_FRONT("/gui/pokemons/butterfree/front.png"),
	WEEDLE_BACK("/gui/pokemons/weedle/back.png"),
	WEEDLE_FRONT("/gui/pokemons/weedle/front.png"),
	KAKUNA_BACK("/gui/pokemons/kakuna/back.png"),
	KAKUNA_FRONT("/gui/pokemons/kakuna/front.png"),
	BEEDRILL_BACK("/gui/pokemons/beedrill/back.png"),
	BEEDRILL_FRONT("/gui/pokemons/beedrill/front.png"),
	PIDGEY_BACK("/gui/pokemons/pidgey/back.png"),
	PIDGEY_FRONT("/gui/pokemons/pidgey/front.png"),
	RATTATA_BACK("/gui/pokemons/rattata/back.png"),
	RATTATA_FRONT("/gui/pokemons/rattata/front.png"),
	EEVEE_BACK("/gui/pokemons/eevee/back.png"),
	EEVEE_FRONT("/gui/pokemons/eevee/front.png");

	private final String filePath;

	GUIPokemonGraphics(String filePath)
	{
		this.filePath = filePath;
	}

	public void loadGraphics()
	{
		ImageLoader.getInstance().loadImage(name(), filePath);
	}

	public Image getImage()
	{
		return ImageLoader.getInstance().getImage(name());
	}
}
