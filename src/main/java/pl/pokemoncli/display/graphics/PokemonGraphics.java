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
	WEEDLE_BACK("pokemon/weedle.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	WEEDLE_FRONT("pokemon/weedle.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	KAKUNA_BACK("pokemon/kakuna.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	KAKUNA_FRONT("pokemon/kakuna.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	BEEDRILL_BACK("pokemon/beedrill.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	BEEDRILL_FRONT("pokemon/beedrill.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	PIDGEY_BACK("pokemon/pidgey.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	PIDGEY_FRONT("pokemon/pidgey.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	RATTATA_BACK("pokemon/rattata.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	RATTATA_FRONT("pokemon/rattata.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true),
	EEVEE_BACK("pokemon/eevee.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), false),
	EEVEE_FRONT("pokemon/eevee.txt", new Color(0x0A2A0A), new Color(0x4C7C4F), true);
	public static final int POKEMON_SIZE_X = 23;
	public static final int POKEMON_SIZE_Y = 12;
	private final Color foreground;
	private final Color background;
	private final boolean mirrored;
	private final String filePath;
	String[] graphics;


	PokemonGraphics(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreground = foreground;
		this.background = background;
		this.filePath = null;
		this.mirrored = false;
	}

	PokemonGraphics(String filePath, Color foreground, Color background, boolean mirrored)
	{
		this.filePath = filePath;
		this.foreground = foreground;
		this.background = background;
		this.mirrored = mirrored;
		this.graphics = null;
	}

	public void loadGraphics()
	{
		if(filePath!=null)
		{
			this.graphics = AsciiArtLoader.loadAsciiArt(filePath, POKEMON_SIZE_X, POKEMON_SIZE_Y, AsciiArtLoader.FALLBACK_POKEMON_GRAPHICS);
			if(mirrored)
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
