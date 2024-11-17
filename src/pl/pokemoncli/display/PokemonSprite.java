package pl.pokemoncli.display;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public enum PokemonSprite {
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
        //TODO: 16.11.2024 batch drawing using System.arraycopy
        for(int y = 0; y < POKEMON_SIZE_Y; y++)
            for(int x = 0; x < POKEMON_SIZE_X; x++)
                terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background);
    }

    public void drawWithBackground(Tile background, int offsetX, int offsetY, DoubleBufferedTerminal terminal)
    {
        for(int y = 0; y < POKEMON_SIZE_Y; y++)
            for(int x = 0; x < POKEMON_SIZE_X; x++)
                if(graphics[y].charAt(x)!=' ')
                    terminal.drawColor(offsetX+x, offsetY+y, graphics[y].charAt(x), foreground, background==null?this.background: background.background);
    }
}
