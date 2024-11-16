package pl.pokemoncli.display;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import lombok.Getter;

import java.awt.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public class DoubleBufferedTerminal
{
	private final Terminal terminal;
	private final char[][] drawBuffer;
	private final boolean[][] changeBuffer;
	private final int[][][] foregroundColorBuffer;
	private final int[][][] backgroundColorBuffer;
	private boolean changes = false;

	@Getter
	private final int width;
	@Getter
	private final int height;

	public DoubleBufferedTerminal()
	{
		this.terminal = TerminalFacade.createTerminal(StandardCharsets.UTF_8);
		TerminalSize terminalSize = terminal.getTerminalSize();
		this.width = terminalSize.getColumns();
		this.height = terminalSize.getRows();
		this.drawBuffer = new char[width][height];
		this.changeBuffer = new boolean[width][height];
		this.foregroundColorBuffer = new int[width][height][3];
		this.backgroundColorBuffer = new int[width][height][3];
	}

	public void init()
	{
		terminal.enterPrivateMode();
		terminal.setCursorVisible(false);
		terminal.clearScreen();
	}

	public void end()
	{

	}

	public boolean draw(int x, int y, char c)
	{
		if(x < 0||x >= width||y < 0||y >= height)
			return false;
		drawBuffer[x][y] = c;
		return changeBuffer[x][y] = changes = true;
	}

	public void drawColor(int x, int y, char c, Color foreground, Color background)
	{
		if(!draw(x, y, c))
			return;
		foregroundColorBuffer[x][y][0] = foreground.getRed();
		foregroundColorBuffer[x][y][1] = foreground.getGreen();
		foregroundColorBuffer[x][y][2] = foreground.getBlue();
		backgroundColorBuffer[x][y][0] = background.getRed();
		backgroundColorBuffer[x][y][1] = background.getGreen();
		backgroundColorBuffer[x][y][2] = background.getBlue();
	}

	public void flush()
	{
		if(!changes)
			return;

		int[] prevBGColor = null, prevFGColor = null;

		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				if(changeBuffer[x][y])
				{
					terminal.moveCursor(x, y);
					if(prevBGColor!=backgroundColorBuffer[x][y])
					{
						terminal.applyBackgroundColor(backgroundColorBuffer[x][y][0], backgroundColorBuffer[x][y][1], backgroundColorBuffer[x][y][2]);
						prevBGColor = backgroundColorBuffer[x][y];
					}
					if(prevFGColor!=foregroundColorBuffer[x][y])
					{
						terminal.applyForegroundColor(foregroundColorBuffer[x][y][0], foregroundColorBuffer[x][y][1], foregroundColorBuffer[x][y][2]);
						prevFGColor = foregroundColorBuffer[x][y];
					}

					terminal.putCharacter(drawBuffer[x][y]);
					changeBuffer[x][y] = false;
				}
		terminal.flush();
		changes = false;
	}

	public Key readInput()
	{
		return terminal.readInput();
	}
}