package pl.pokemoncli;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.terminal.TerminalSize;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Player;

import java.nio.charset.Charset;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Main
{
	//--- Graphics ---//
	static final Main INSTANCE = new Main();
	static int GAME_X, GAME_Y;
	Level level;
	Player player;

	//--- Flush ---//
	final static int FLUSH_DELAY = 60;
	int flushTimer = FLUSH_DELAY;

	public static void main(String[] args)
	{
		INSTANCE.loadGame();
		INSTANCE.displayGame();
	}

	private void displayGame()
	{
		Terminal terminal = TerminalFacade.createTerminal(Charset.forName("UTF8"));
		terminal.enterPrivateMode();

		TerminalSize terminalSize = terminal.getTerminalSize();
		terminal.setCursorVisible(false);
		GAME_Y = Math.min(terminalSize.getColumns(), terminalSize.getRows());
		GAME_X = (int)(GAME_Y*(Tile.TILE_SIZE_X/(float)Tile.TILE_SIZE_Y));

		while(true)
			try
			{
				if(flushTimer==FLUSH_DELAY)
				{
					terminal.applyBackgroundColor(127, 127, 127);
					terminal.clearScreen();
					drawWholeMap(terminal);
					terminal.applySGR(SGR.ENTER_BOLD);
					drawSidePanel(terminal);
					terminal.applySGR(SGR.RESET_ALL);
					flushTimer = 0;
				}

				Key key = terminal.readInput();
				if(key!=null)
				{
					//Redraw current and previous tile
					int px = player.getX(), py = player.getY();
					if(handleKeyInput(key))
					{
						updateDrawTile(terminal, px, py);
						updateDrawCharacter(terminal, player);
					}

				}
				Thread.sleep(100);
				flushTimer++;
			} catch(InterruptedException e)
			{
				e.printStackTrace();
			}

//		terminal.exitPrivateMode();
	}

	private void drawString(Terminal terminal, String displayed, int x, int y)
	{
		terminal.moveCursor(x, y);
		displayed.chars().forEach(c -> terminal.putCharacter((char)c));
	}

	private void drawSidePanel(Terminal terminal)
	{
		terminal.applyBackgroundColor(127, 127, 127);
		terminal.applyForegroundColor(255, 255, 255);

		//Draw Frame
		for(int y = 0; y < GAME_Y; y++)
			for(int x = GAME_X; x < terminal.getTerminalSize().getColumns(); x++)
			{
				terminal.moveCursor(x, y);
				if(y==0||y==GAME_Y-1||x==GAME_X||x==terminal.getTerminalSize().getColumns()-1)
					terminal.putCharacter('█');
				else
					terminal.putCharacter(' ');
			}

		//Draw Stats
		drawString(terminal, "Name: Ash", GAME_X+2, 2);
		drawString(terminal, "HP: 100", GAME_X+2, 3);

		drawString(terminal, "Inventory", GAME_X+2, 5);
	}

	private boolean handleKeyInput(Key key)
	{
		switch(key.getCharacter())
		{
			case 'w':
				level.moveCharacterBy(player, 0, -1);
				break;
			case 'a':
				level.moveCharacterBy(player, -1, 0);
				break;
			case 's':
				level.moveCharacterBy(player, 0, 1);
				break;
			case 'd':
				level.moveCharacterBy(player, 1, 0);
				break;
			default:
				return false;
		}
		return true;
	}

	private void drawWholeMap(Terminal terminal)
	{
		for(int y = 0; y*Tile.TILE_SIZE_Y < GAME_Y&&y < level.getHeight(); y++)
			for(int x = 0; x*Tile.TILE_SIZE_X < GAME_X&&x < level.getWidth(); x++)
				updateDrawTile(terminal, x, y);

		// Draw characters on the map
		level.getCharacters().forEach(c -> updateDrawCharacter(terminal, c));
	}

	private void updateDrawTile(Terminal terminal, int x, int y)
	{
		Tile tile = level.getMap()[x][y].getTile();
		tile.draw(x*Tile.TILE_SIZE_X, y*Tile.TILE_SIZE_Y, terminal);
	}

	private void updateDrawCharacter(Terminal terminal, Character character)
	{
		Tile sprite = character.getCurrentSprite();
		int cX = character.getX(), cY = character.getY();
		sprite.drawWithBackground(level.getTerrain(cX, cY).getTile(),
				cX*Tile.TILE_SIZE_X, cY*Tile.TILE_SIZE_Y, terminal);
	}

	private void loadGame()
	{
		// Create the game level and player
		level = new Level(15, 15);
		player = new Player("Ash", 5, 5, 100, 100);
		level.addCharacter(player);

		level.setTerrain(2, 2, Terrain.BLOCKED);
		level.setTerrain(3, 2, Terrain.BLOCKED);
		level.setTerrain(4, 2, Terrain.BLOCKED);
		level.setTerrain(4, 3, Terrain.BLOCKED);
	}
}