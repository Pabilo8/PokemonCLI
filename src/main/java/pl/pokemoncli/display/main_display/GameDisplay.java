package pl.pokemoncli.display.main_display;

import com.googlecode.lanterna.input.Key;
import pl.pokemoncli.display.BaseDisplay;
import pl.pokemoncli.display.DoubleBufferedTerminal;
import pl.pokemoncli.display.KeyHandlingDisplay;
import pl.pokemoncli.display.graphics.TileGraphics;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.GameObject;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.dialogue.Dialogue;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class GameDisplay extends BaseDisplay implements KeyHandlingDisplay
{
	public GameDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	public void drawWholeMap(Player player, Level level, int gameX, int gameY, int currentTicks)
	{
		int visibleWidth = gameX/TileGraphics.TILE_SIZE_X;
		int visibleHeight = gameY/TileGraphics.TILE_SIZE_Y;
		int playerX = player.getX();
		int playerY = player.getY();

		Terrain[][] visibleMap = level.getVisibleMap(playerX, playerY, visibleWidth, visibleHeight);

		for(int y = 0; y < visibleHeight; y++)
			for(int x = 0; x < visibleWidth; x++)
			{
				int drawX = x*TileGraphics.TILE_SIZE_X;
				int drawY = y*TileGraphics.TILE_SIZE_Y;
				visibleMap[x][y].getTile(currentTicks).draw(drawX, drawY, terminal);
			}

		// Draw characters on the visible map
		level.getGameObjects().forEach(c -> {
			if(Math.abs(c.getX()-playerX) <= visibleWidth/2&&Math.abs(c.getY()-playerY) <= visibleHeight/2)
				updateDrawCharacter(playerX, playerY, c, gameX, gameY);
		});
	}

	public void updateDrawCharacter(int playerX, int playerY, GameObject gameObject, int gameX, int gameY)
	{
		int startX = Math.max(0, playerX-gameX/TileGraphics.TILE_SIZE_X/2);
		int startY = Math.max(0, playerY-gameY/TileGraphics.TILE_SIZE_Y/2);
		int cX = gameObject.getX()-startX, cY = gameObject.getY()-startY;

		if(cX < 0||cY < 0||cX*TileGraphics.TILE_SIZE_X >= gameX||cY*TileGraphics.TILE_SIZE_Y >= gameY)
			return;

		TileGraphics sprite = gameObject.getCurrentSprite();
		sprite.drawTrnsparent(cX*TileGraphics.TILE_SIZE_X, cY*TileGraphics.TILE_SIZE_Y, terminal);
	}

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		return switch(key.getCharacter())
		{
			case 'w' -> level.moveCharacterBy(player, 0, -1);
			case 'a' -> level.moveCharacterBy(player, -1, 0);
			case 's' -> level.moveCharacterBy(player, 0, 1);
			case 'd' -> level.moveCharacterBy(player, 1, 0);
			default -> null;
		};
	}
}