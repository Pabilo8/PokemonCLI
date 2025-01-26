package pl.pokemoncli.gui.display;

import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.gui.graphics.GUITileGraphics;
import pl.pokemoncli.logic.AbstractTileGraphics;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.characters.GameObject;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 19.01.2025
 */
public class GameDrawPanel extends JComponent implements IPokemonGui
{
	private static final int TILE_SIZE = 64;
	private final Timer fps = new Timer(20, e -> this.repaint());
	private PokemonGUI pok;

	public GameDrawPanel()
	{
		super();
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600, 600));
		setVisible(true);
		setDoubleBuffered(true);
	}

	@Override
	public void paintComponent(Graphics g)
	{
//		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);

		//TODO: 26.01.2025 implement bim bim bam bam
		//TODO: 26.01.2025 matrix, 5x5
		int visibleWidth = getWidth()/TILE_SIZE;
		int visibleHeight = getHeight()/TILE_SIZE;
		int playerX = pok.getPlayer().getX();
		int playerY = pok.getPlayer().getY();

		Terrain[][] visibleMap = pok.getLevel().getVisibleMap(playerX, playerY, visibleWidth, visibleHeight);

		for(int y = 0; y < visibleHeight; y++)
			for(int x = 0; x < visibleWidth; x++)
			{
				int drawX = x*TILE_SIZE;
				int drawY = y*TILE_SIZE;
				AbstractTileGraphics<?> tile = visibleMap[x][y].getTile(0);
				if(tile instanceof GUITileGraphics)
					((GUITileGraphics)tile).draw(drawX, drawY, g);
			}

		// Draw characters on the visible map
		pok.getLevel().getGameObjects().forEach(c -> {
			if(Math.abs(c.getX()-playerX) <= visibleWidth/2&&Math.abs(c.getY()-playerY) <= visibleHeight/2)
				updateDrawCharacter(g, playerX, playerY, c);
		});
	}

	public void updateDrawCharacter(Graphics graphics, int playerX, int playerY, GameObject gameObject)
	{
		int startX = Math.max(0, playerX-getWidth()/TILE_SIZE/2);
		int startY = Math.max(0, playerY-getHeight()/TILE_SIZE/2);
		int cX = gameObject.getX()-startX, cY = gameObject.getY()-startY;

		if(cX < 0||cY < 0||cX*TILE_SIZE >= getWidth()||cY*TILE_SIZE >= getHeight())
			return;

		GUITileGraphics sprite = (GUITileGraphics)SpriteHandler.getHandler(gameObject.getClass()).getSpriteFor(gameObject);
		if(sprite==null)
			return;
		sprite.draw(cX*TILE_SIZE, cY*TILE_SIZE, graphics);
	}

	@Override
	public JPanel getMainPanel()
	{
		return null;
	}

	@Override
	public void onInit()
	{
		fps.start();
		pok = PokemonGUI.getInstance();
	}

	@Override
	public void onExit()
	{
		fps.stop();
	}
}
