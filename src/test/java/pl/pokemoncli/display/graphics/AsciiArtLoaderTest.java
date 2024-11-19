package pl.pokemoncli.display.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class AsciiArtLoaderTest
{
	@Test
	void testMirrorAsciiArt()
	{
		String[] asciiArt = new String[]{
				"123",
				"456",
				"789"
		};
		String[] mirrored = AsciiArtLoader.mirrorAsciiArt(asciiArt);
		assert mirrored[0].equals("321");
		assert mirrored[1].equals("654");
		assert mirrored[2].equals("987");
	}

	@Test
	void testTailorAsciiArt()
	{
		String[] asciiArt = new String[]{
				"123",
				"456",
				"789"
		};
		String[] expected = new String[]{
				"123   ",
				"456   ",
				"789   ",
				"      "
		};
		String[] tailored = AsciiArtLoader.tailorAsciiArt(asciiArt, 6, 4);
		assertArrayEquals(expected, tailored, "The ASCII art should be tailored to the specified width and height");
	}

	@Test
	void testTailorAsciiArtTrim()
	{
		String[] asciiArt = new String[]{
				"123456",
				"789012",
				"345678",
				"901234"
		};
		String[] expected = new String[]{
				"123",
				"789",
				"345"
		};
		String[] tailored = AsciiArtLoader.tailorAsciiArt(asciiArt, 3, 3);
		assertArrayEquals(expected, tailored, "The ASCII art should be trimmed to the specified width and height");
	}
}
