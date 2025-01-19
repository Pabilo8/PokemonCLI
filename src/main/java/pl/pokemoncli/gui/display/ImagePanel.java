package pl.pokemoncli.gui.display;

import pl.pokemoncli.gui.graphics.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel
{
	private final String imageKey;
	private final double aspectRatio;

	public ImagePanel(String imageKey)
	{
		this.imageKey = imageKey;
		Image image = ImageLoader.getInstance().getImage(imageKey);
		if(image!=null)
			aspectRatio = (double)image.getWidth(null)/image.getHeight(null);
		else
			aspectRatio = 1;
	}

	@Override
	public Dimension getPreferredSize()
	{
		Dimension size = super.getPreferredSize();
		int width = size.width;
		int height = (int)(width/aspectRatio);
		return new Dimension(width, height);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Image image = ImageLoader.getInstance().getImage(imageKey);
		if(image!=null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
}