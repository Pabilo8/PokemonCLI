package pl.pokemoncli.display;

import com.googlecode.lanterna.terminal.Terminal;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Player;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class GameDisplay
{
	private final Terminal terminal;
	private final Level level;
	private final Player player;

	public GameDisplay(Terminal terminal, Level level, Player player)
	{
		this.terminal = terminal;
		this.level = level;
		this.player = player;
	}

	public void drawWholeMap(int gameX, int gameY)
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
				visibleMap[x][y].getTile().draw(drawX, drawY, terminal);
			}

		// Draw characters on the visible map
		level.getCharacters().forEach(c -> {
			if(Math.abs(c.getX()-playerX) <= visibleWidth/2&&Math.abs(c.getY()-playerY) <= visibleHeight/2)
				updateDrawCharacter(c, gameX, gameY);
		});
	}

	public void updateDrawTile(int x, int y)
	{
		Tile tile = level.getMap()[x][y].getTile();
		tile.draw(x*Tile.TILE_SIZE_X, y*Tile.TILE_SIZE_Y, terminal);
	}

	public void updateDrawCharacter(Character character, int gameX, int gameY)
	{
		int startX = Math.max(0, player.getX()-gameX/Tile.TILE_SIZE_X/2);
		int startY = Math.max(0, player.getY()-gameY/Tile.TILE_SIZE_Y/2);
		int cX = character.getX()-startX, cY = character.getY()-startY;

		if(cX < 0||cY < 0||cX*Tile.TILE_SIZE_X > gameX||cY*Tile.TILE_SIZE_Y > gameY)
			return;

		Tile sprite = character.getCurrentSprite();
		sprite.drawWithBackground(level.getTerrain(cX, cY).getTile(),
				cX*Tile.TILE_SIZE_X, cY*Tile.TILE_SIZE_Y, terminal);
	}
}