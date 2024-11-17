package pl.pokemoncli.display;

import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Player;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class GameDisplay extends BaseDisplay
{
	public GameDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	public void drawWholeMap(Player player, Level level, int gameX, int gameY, int currentTicks)
	{
		int visibleWidth = gameX/Tile.TILE_SIZE_X;
		int visibleHeight = gameY/Tile.TILE_SIZE_Y;
		int playerX = player.getX();
		int playerY = player.getY();

		Terrain[][] visibleMap = level.getVisibleMap(playerX, playerY, visibleWidth, visibleHeight);

		for(int y = 0; y < visibleHeight; y++)
			for(int x = 0; x < visibleWidth; x++)
			{
				int drawX = x*Tile.TILE_SIZE_X;
				int drawY = y*Tile.TILE_SIZE_Y;
				visibleMap[x][y].getTile(currentTicks).draw(drawX, drawY, terminal);
			}

		// Draw characters on the visible map
		level.getCharacters().forEach(c -> {
			if(Math.abs(c.getX()-playerX) <= visibleWidth/2&&Math.abs(c.getY()-playerY) <= visibleHeight/2)
				updateDrawCharacter(playerX, playerY, c, gameX, gameY);
		});
	}

/*	public void updateDrawTile(int x, int y)
	{
		Tile tile = level.getMap()[x][y].getTile();
		tile.draw(x*Tile.TILE_SIZE_X, y*Tile.TILE_SIZE_Y, terminal);
	}*/

	public void updateDrawCharacter(int playerX, int playerY, Character character, int gameX, int gameY)
	{
		int startX = Math.max(0, playerX-gameX/Tile.TILE_SIZE_X/2);
		int startY = Math.max(0, playerY-gameY/Tile.TILE_SIZE_Y/2);
		int cX = character.getX()-startX, cY = character.getY()-startY;

		if(cX < 0||cY < 0||cX*Tile.TILE_SIZE_X > gameX||cY*Tile.TILE_SIZE_Y > gameY)
			return;

		Tile sprite = character.getCurrentSprite();
		sprite.drawTrnsparent(cX*Tile.TILE_SIZE_X, cY*Tile.TILE_SIZE_Y, terminal);
	}
}