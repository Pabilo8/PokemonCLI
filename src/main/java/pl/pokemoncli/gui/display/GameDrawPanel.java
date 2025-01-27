package pl.pokemoncli.gui.display;

import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.gui.graphics.GUITileGraphics;
import pl.pokemoncli.logic.AbstractTileGraphics;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.characters.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Pabilo8
 * @since 19.01.2025
 */
public class GameDrawPanel extends JComponent implements IPokemonGui
{
	private static final int TILE_SIZE = 64;
	private final Timer fps = new Timer(20, e -> this.repaint());
	int tickTimer = 0;
	private PokemonGUI pok;

	public GameDrawPanel()
	{
		super();
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600, 600));
		setVisible(true);
		setDoubleBuffered(true);
		addMouseListener(
				new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						//BUG: 27.01.2025 sometimes selecting a position on sidebar Jlist makes the game lose focus
						pok.focus();
					}
				}
		);
	}

	@Override
	public void paintComponent(Graphics g)
	{
//		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		//TODO: 26.01.2025 implement bim bim bam bam
		//TODO: 26.01.2025 matrix, 5x5
		int visibleWidth = getWidth()/TILE_SIZE;
		int visibleHeight = getHeight()/TILE_SIZE+1;
		int playerX = pok.getPlayer().getX();
		int playerY = pok.getPlayer().getY();

		if(pok.getLevel().getWidth()*TILE_SIZE < getWidth())
			g.translate((getWidth()-pok.getLevel().getWidth()*TILE_SIZE)/2, 0);
		if(pok.getLevel().getHeight()*TILE_SIZE < getHeight())
			g.translate(0, (getHeight()-pok.getLevel().getHeight()*TILE_SIZE)/2);

		int startX = Math.max(0, playerX-visibleWidth/2);
		int startY = Math.max(0, playerY-visibleHeight/2);
		Terrain[][] visibleMap = pok.getLevel().getVisibleMap(playerX, playerY, visibleWidth+1, visibleHeight);

		boolean[] neighbours = new boolean[]{
				false, false, false,
				false, false, false,
				false, false, false
		};
		//Draw background tiles
		drawTiles(g, visibleHeight, visibleWidth, visibleMap, startX, startY, neighbours, false);

		// Draw characters on the visible map
		pok.getLevel().getGameObjects().forEach(c -> {
			updateDrawCharacter(g, startX, startY, c);
		});

		//Draw foreground tiles
		drawTiles(g, visibleHeight, visibleWidth, visibleMap, startX, startY, neighbours, true);


		tickTimer = (tickTimer+1)%20;
	}

	private void drawTiles(Graphics g, int visibleHeight, int visibleWidth, Terrain[][] visibleMap,
						   int startX, int startY, boolean[] neighbours, boolean foreground)
	{
		for(int y = 0; y < visibleHeight; y++)
			for(int x = 0; x < visibleWidth; x++)
			{
				int drawX = x*TILE_SIZE;
				int drawY = y*TILE_SIZE;
				AbstractTileGraphics<?> tile = visibleMap[x][y].getTile(tickTimer);
				if(tile instanceof GUITileGraphics)
					if(foreground)
					{
						if(tile.hasForeground())
							((GUITileGraphics)tile).drawForeground(drawX, drawY, g, getTileSeed(startX+x, startY+y), tickTimer, neighbours);
					}
					else
						((GUITileGraphics)tile).drawBackground(drawX, drawY, g, getTileSeed(startX+x, startY+y), tickTimer, neighbours);
			}
	}

	private int getTileSeed(int x, int y)
	{
		return (x*31+y*17)%20;
	}

	public void updateDrawCharacter(Graphics graphics, int startX, int startY, GameObject gameObject)
	{
		int cX = gameObject.getX()-startX, cY = gameObject.getY()-startY;

		if(cX < 0||cY < 0||cX*TILE_SIZE >= getWidth()||cY*TILE_SIZE >= getHeight())
			return;

		GUITileGraphics sprite = (GUITileGraphics)SpriteHandler.getHandler(gameObject.getClass()).getSpriteFor(gameObject);
		if(sprite==null)
			return;
		sprite.drawBackground(cX*TILE_SIZE, cY*TILE_SIZE, graphics);
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
