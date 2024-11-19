package pl.pokemoncli.display.graphics;


import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class AsciiArtLoader
{
	public static String[] FALLBACK_POKEMON_GRAPHICS = new String[]{
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
	};
	public static String[] FALLBACK_TILE_GRAPHICS = new String[]{
			"      ",
			"      ",
			"      "
	};

	public static String[] loadAsciiArt(String filePath, int expectedWidth, int expectedHeight, @Nullable String[] fallback)
	{
		try
		{
			String[] strings = loadAsciiArt(filePath);
			return tailorAsciiArt(strings, expectedWidth, expectedHeight);
		} catch(IOException e)
		{
			System.err.printf("Could not load ASCII art for %s, %s%n", filePath, e.getMessage());
			return fallback;
		}
	}

	public static String[] tailorAsciiArt(String[] asciiArt, int expectedWidth, int expectedHeight)
	{
		String[] tailoredArt = new String[expectedHeight];
		for(int i = 0; i < expectedHeight; i++)
		{
			if(i < asciiArt.length)
			{
				if(asciiArt[i].length() > expectedWidth)
					tailoredArt[i] = asciiArt[i].substring(0, expectedWidth);
				else
					tailoredArt[i] = String.format("%-"+expectedWidth+"s", asciiArt[i]);
			}
			else
				tailoredArt[i] = " ".repeat(expectedWidth);
		}
		return tailoredArt;
	}


	private static String[] loadAsciiArt(String filePath) throws IOException
	{
		List<String> lines = new ArrayList<>();
		try(InputStream is = new FileInputStream("src/main/resources/"+filePath))
		{
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)))
			{
				String line;
				while((line = reader.readLine())!=null)
					lines.add(line);
			}
		}

		return lines.toArray(new String[0]);
	}

	public static String[] mirrorAsciiArt(String[] asciiArt)
	{
		String[] mirrored = new String[asciiArt.length];
		for(int i = 0; i < asciiArt.length; i++)
			mirrored[i] = new StringBuilder(asciiArt[i]).reverse().toString();
		return mirrored;
	}
}
