package pl.pokemoncli.gui.display;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 19.01.2025
 */
public class GameDrawPanel extends JPanel implements IPokemonGui
{
	private final Timer odswiezanie = new Timer(20, e -> this.repaint());

	public GameDrawPanel()
	{
		super();
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600, 600));
		setVisible(true);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.WHITE);
		g.drawString("Hello World", 100, 100);
	}

	@Override
	public JPanel getMainPanel()
	{
		return null;
	}

	@Override
	public void onInit()
	{
		odswiezanie.start();
	}

	@Override
	public void onExit()
	{
		odswiezanie.stop();
	}

	@Override
	public void loadGraphics()
	{

	}


}
