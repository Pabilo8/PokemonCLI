package pl.pokemoncli;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import pl.pokemoncli.display.GameDisplay;
import pl.pokemoncli.display.PanelDisplay;
import pl.pokemoncli.display.Tile;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.characters.Player;

import java.nio.charset.Charset;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Main
{
	static final Main INSTANCE = new Main();
	static int GAME_X, GAME_Y;
	Level level;
	Player player;
	PanelDisplay panelDisplay;
	GameDisplay gameDisplay;

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

		panelDisplay = new PanelDisplay(terminal);
		gameDisplay = new GameDisplay(terminal, level, player);

		while(true)
			try
			{
				if(flushTimer==FLUSH_DELAY)
				{
					terminal.applyBackgroundColor(127, 127, 127);
					terminal.clearScreen();
					gameDisplay.drawWholeMap(GAME_X, GAME_Y);
					panelDisplay.drawSidePanel(player, GAME_X, GAME_Y);
					flushTimer = 0;
				}

				Key key = terminal.readInput();
				if(key!=null)
				{
					int px = player.getX(), py = player.getY();
					if(handleKeyInput(key))
						flushTimer = FLUSH_DELAY-1;
				}
				Thread.sleep(100);
				flushTimer++;
			} catch(InterruptedException e)
			{
				e.printStackTrace();
			}
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

	private void loadGame()
	{
		level = new Level(15, 15);
		player = new Player("Ash", 5, 5, 100, 100);
		level.addCharacter(player);

		level.setTerrain(2, 2, Terrain.BLOCKED);
		level.setTerrain(3, 2, Terrain.BLOCKED);
		level.setTerrain(4, 2, Terrain.BLOCKED);
		level.setTerrain(4, 3, Terrain.BLOCKED);
	}
}