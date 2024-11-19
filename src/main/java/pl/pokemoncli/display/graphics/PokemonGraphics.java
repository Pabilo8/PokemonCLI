package pl.pokemoncli.display.graphics;

import pl.pokemoncli.display.DoubleBufferedTerminal;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public enum PokemonGraphics
{
	POKEMON_NULL(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	BULBASAUR_BACK("pokemon/bulbasaur.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	BULBASAUR_FRONT("pokemon/bulbasaur.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	CHARMANDER_BACK("pokemon/charmander.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	CHARMANDER_FRONT("pokemon/charmander.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	SQUIRTLE_BACK("pokemon/squirtle.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	SQUIRTLE_FRONT("pokemon/squirtle.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	CATERPIE_BACK("pokemon/caterpie.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	CATERPIE_FRONT("pokemon/caterpie.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	METAPOD_BACK("pokemon/metapod.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	METAPOD_FRONT("pokemon/metapod.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	BUTTERFREE_BACK("pokemon/butterfree.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	BUTTERFREE_FRONT("pokemon/butterfree.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	WEEDLE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	WEEDLE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	KAKUNA_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	KAKUNA_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	BEEDRILL_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	BEEDRILL_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	PIDGEY_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	PIDGEY_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	RATTATA_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	RATTATA_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS),
	EEVEE_BACK("pokemon/eevee.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	EEVEE_FRONT("pokemon/eevee.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false);
	public static final int POKEMON_SIZE_X = 23;
	public static final int POKEMON_SIZE_Y = 12;
	private final Color foreground;
	private final Color background;
	private final boolean unMirrored;
	private final String filePath;
	String[] graphics;


	PokemonGraphics(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreground = foreground;
		this.background = background;
		this.filePath = null;
		this.unMirrored = false;
	}

	PokemonGraphics(String filePath, Color foreground, Color background, boolean unMirrored)
	{
		this.filePath = filePath;
		this.foreground = foreground;
		this.background = background;
		this.unMirrored = unMirrored;
		this.graphics = null;
	}

	public void loadGraphics()
	{
		if(filePath!=null)
		{
			this.graphics = AsciiArtLoader.loadAsciiArt(filePath, POKEMON_SIZE_X, POKEMON_SIZE_Y, AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS);
			if(!unMirrored)
				this.graphics = AsciiArtLoader.mirrorAsciiArt(graphics);
		}
	}

	public void draw(int offsetX, int offsetY, DoubleBufferedTerminal terminal)
	{
		for(int y = 0; y < POKEMON_SIZE_Y; y++)
			for(int x = 0; x < POKEMON_SIZE_X; x++)
				terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background);
	}
}
