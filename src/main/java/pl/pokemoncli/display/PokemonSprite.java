package pl.pokemoncli.display;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public enum PokemonSprite
{
	POKEMON_NULL(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	BULBASAUR_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"     ▄▀▀▀▄             ",
			"   ▄▄▀   ▀▀▀▄▄         ",
			" ▄▀           █▄▀▀▄    ",
			" █          ▄▀▀   █    ",
			"  █▄▄▄   █▀▀       █▄  ",
			"  █  ▄▀▀▀ ▀ ▄▄     ▀█  ",
			"   ▀▀▀▄     ▀██    ▄▀  ",
			"      █   █▄▄▄▄▄▄▀▀    ",
			"       ▀▀▀             ",
			"                       "
	}),
	BULBASAUR_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"             ▄▀▀▀▄     ",
			"         ▄▄▀▀▀   ▀▄▄   ",
			"    ▄▀▀▄█           ▀▄ ",
			"    █   ▀▀▄          █ ",
			"  ▄█       ▀▀█   ▄▄▄█  ",
			"  █▀     ▄▄ ▀ ▀▀▀▄  █  ",
			"  ▀▄    ██▀     ▄▀▀▀   ",
			"    ▀▀▄▄▄▄▄▄█   █      ",
			"             ▀▀▀       ",
			"                       "
	}),
	CHARMANDER_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"     ▄       ▄▄▄       ",
			"   ▄▀ █    ▄▀   ▀▄     ",
			"   █ ▄ █  ▄▀ ▄▄  ▀▄    ",
			"   ▀▄ █  ▄▀  ▀▀  ▄▀    ",
			"    ▀▄ ▀█  ▀▄ ▀█▀      ",
			"      ▀▄█  ▀ ▄▄▀▄      ",
			"        █▀ ▀█▀         ",
			"         ▀▀▀           ",
			"                       "
	}),
	CHARMANDER_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"       ▄▄▄       ▄     ",
			"     ▄▀   ▀▄    █ ▀▄   ",
			"    ▄▀  ▄▄ ▀▄  █ ▄ █   ",
			"    ▀▄  ▀▀  ▀▄  █ ▄▀   ",
			"      ▀█▀ ▄▀  █▀ ▄▀    ",
			"      ▄▀▄▄ ▀  █▄▀      ",
			"         ▀█▀ ▀█        ",
			"           ▀▀▀         ",
			"                       "
	}),
	SQUIRTLE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"               ▄▄▄▄    ",
			" ▄▀▀▀▄     ▄▄▀▀    ▀▄  ",
			"█ ▄   █ ▄▀█         █▄ ",
			"▀▄ █  ▄▀  █   ▄▄     █ ",
			"  ▀▀█▄▀  ▄█   ██   ▄▀  ",
			"     █  █  ▀▀▄▄▄▄ █    ",
			"     █  █▄▄▄█▀ ▄▀▀     ",
			"      █ █   ▄▄█▄▀      ",
			"       █▀ ▀█▀          ",
			"        ▀▀▀            ",
			"                       "
	}),
	SQUIRTLE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"    ▄▄▄▄               ",
			"  ▄▀    ▀▀▄▄     ▄▀▀▀▄ ",
			" ▄█         █▀▄ █   ▄ █",
			" █     ▄▄   █  ▀▄  █ ▄▀",
			"  ▀▄   ██   █▄  ▀▄█▀▀  ",
			"    █ ▄▄▄▄▀▀  █  █     ",
			"     ▀▀▄ ▀█▄▄▄█  █     ",
			"      ▀▄█▄▄   █ █      ",
			"          ▀█▀ ▀█       ",
			"            ▀▀▀        ",
			"                       "
	}),
	CATERPIE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"              ▄        ",
			"         ▄▀▀▄█ █       ",
			"        ▄▀▄▄▄▀ ▀▄      ",
			"       █  ▄▄ █▀ ▀▄     ",
			"     ▄ █  ▀▀▄   ▄▀     ",
			"    █ █ █▄▄▄▀▀█▀       ",
			"    ▀█▀█▀▄   ▄▀        ",
			"     ▀█▄  ▀█▀          ",
			"        ▀▀▀            ",
			"                       "
	}),
	CATERPIE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"        ▄              ",
			"       █ █▄▀▀▄         ",
			"      ▄▀ ▀▄▄▄▀▄        ",
			"     ▄▀ ▀█ ▄▄  █       ",
			"     ▀▄   ▄▀▀  █ ▄     ",
			"       ▀█▀▀▄▄▄█ █ █    ",
			"        ▀▄   ▄▀█▀█▀    ",
			"          ▀█▀  ▄█▀     ",
			"            ▀▀▀        ",
			"                       "
	}),
	METAPOD_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"       ▄▄▄             ",
			"       ▀▄ ▀▀▄          ",
			"        █    █         ",
			"       █ ▄█▀  █        ",
			"     ▄▀        █       ",
			"      ▀▀▄     ▄▀       ",
			"        █▀   ▄▀        ",
			"       █   ▄▄▀         ",
			"        ▀▀▀            ",
			"                       "
	}),
	METAPOD_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"             ▄▄▄       ",
			"          ▄▀▀ ▄▀       ",
			"         █    █        ",
			"        █  ▀█▄ █       ",
			"       █        ▀▄     ",
			"       ▀▄     ▄▀▀      ",
			"        ▀▄   ▀█        ",
			"         ▀▄▄   █       ",
			"            ▀▀▀        ",
			"                       "
	}),
	BUTTERFREE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                    ▄▄▄",
			"          ▄▄      ▄█▀▀▀",
			"         █▀ █   ▄█▀ ▄▄▄",
			" ▄▄▄▄    █   █ █▀ ▄█▀  ",
			"█▀   ▀▀▄ █   █▄█▄██    ",
			"█ ▀▀▄▄▄▄▀▄█▄▀▀ ▀ ▀▄▀▄  ",
			"▀█    █▄  ██  ▄▄  ▀▄█  ",
			" ▀█▄▄▀  █▄ █ █  █ ▄▄▀  ",
			"   ▀█▄ ▀ ▄██▀▄██▄▀▀    ",
			"     ▀▄▀▀███   ▄▀      ",
			"       ▀▀▀█▀██▀█       ",
			"          ▀▄▀ ▀        "
	}),
	BUTTERFREE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"▄▄▄                    ",
			"▀▀▀█▄      ▄▄          ",			
			"▄▄▄ ▀█▄   █ ▀█         ",			
			"  ▀█▄ ▀█ █   █    ▄▄▄▄ ",			
			"    ██▄█▄█   █ ▄▀▀   ▀█",			
			"  ▄▀▄▀ ▀ ▀▀▄█▄▀▄▄▄▄▀▀ █",			
			"  █▄▀  ▄▄  ██  ▄█    █▀",			
			"  ▀▄▄ █  █ █ ▄█  ▀▄▄█▀ ",			
			"    ▀▀▄██▄▀██▄ ▀ ▄█▀   ",			
			"      ▀▄   ███▀▀▄▀     ",			
			"       █▀██▀█▀▀▀       ",			
			"        ▀ ▀▄▀          "
	}),
	WEEDLE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	WEEDLE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	KAKUNA_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	KAKUNA_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	BEEDRILL_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	BEEDRILL_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	PIDGEY_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	PIDGEY_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	RATTATA_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	RATTATA_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       ",
			"                       "
	}),
	EEVEE_BACK(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"     ▄ █▀▀▀▀▄▄         ",
			"   ▄▀█▄▄█     ▀▀▀▀▀▄   ",
			" ▄▀   █▄ ▄▄ ▀   ██▄ ▀█ ",
			" █    █ ▀▄▄█▄    ▀▀  █ ",
			" █     █▀    ▀     ▄▀  ",
			"  ▀▄▄  ▀ ▀▄       ▀▄   ",
			"     ▀█   ▄█▄       █  ",
			"     █   ▄ █  █▄▄▄▀▀▄  ",
			"     ▀▄▄▄▀▀▀▄ ▀▄  ▀▀▀  ",
			"             ▀▀▀       ",
			"                       "
	}),
	EEVEE_FRONT(new Color(0x0A2A0A), new Color(0x4C7C4F), new String[]{
			"                       ",
			"         ▄▄▀▀▀▀█ ▄     ",
			"   ▄▀▀▀▀▀     █▄▄█▀▄   ",
			" █▀ ▄██   ▀ ▄▄ ▄█   ▀▄ ",
			" █  ▀▀    ▄█▄▄▀ █    █ ",
			"  ▀▄     ▀    ▀█     █ ",
			"   ▄▀       ▄▀ ▀  ▄▄▀  ",
			"  █       ▄█▄   █▀     ",
			"  ▄▀▀▄▄▄█  █ ▄   █     ",
			"  ▀▀▀  ▄▀ ▄▀▀▀▄▄▄▀     ",
			"       ▀▀▀             ",
			"                       "
	});
	public static final int POKEMON_SIZE_X = 23;
	public static final int POKEMON_SIZE_Y = 12;
	final Color foreground;
	final Color background;
	final String[] graphics;

	PokemonSprite(Color foreground, Color background, String[] graphics)
	{
		this.graphics = graphics;
		this.foreground = foreground;
		this.background = background;
	}

	public void draw(int offsetX, int offsetY, DoubleBufferedTerminal terminal)
	{
		for(int y = 0; y < POKEMON_SIZE_Y; y++)
			for(int x = 0; x < POKEMON_SIZE_X; x++)
				terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background);
	}
}
