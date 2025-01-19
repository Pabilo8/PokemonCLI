package pl.pokemoncli.gui.graphics;

import lombok.AccessLevel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pabilo8
 * @since 18.01.2025
 */
public class ImageLoader
{
	@Getter(AccessLevel.PUBLIC)
	private static final ImageLoader instance = new ImageLoader();
	private final Map<String, Image> imageMap;

	public ImageLoader()
	{
		imageMap = new HashMap<>();
	}

	public void loadImage(String key, String path)
	{
		ImageIcon icon = new ImageIcon(getClass().getResource(path));
		imageMap.put(key, icon.getImage());
	}

	public Image getImage(String key)
	{
		return imageMap.get(key);
	}

	public void clearImages()
	{
		imageMap.clear();
	}
}