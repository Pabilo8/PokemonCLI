package pl.pokemoncli.gui.graphics;

import lombok.AccessLevel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
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
		URL resource = this.getClass().getResource(path);

		ImageIcon icon = resource==null?new ImageIcon(new BufferedImage(24, 24, BufferedImage.TYPE_INT_RGB)):
				new ImageIcon(resource);
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