package pl.pokemoncli.display;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.SGR;
import pl.pokemoncli.logic.characters.Player;

/**
 * @author Pabilo8
 * @since 15.11.2024
 */
public class PanelDisplay
{
	private final Terminal terminal;

	public PanelDisplay(Terminal terminal)
	{
		this.terminal = terminal;
	}

	public void drawSidePanel(Player player, int gameX, int gameY)
	{
		terminal.applySGR(SGR.ENTER_BOLD);
		terminal.applyBackgroundColor(127, 127, 127);
		terminal.applyForegroundColor(255, 255, 255);

		// Draw Frame
		for(int y = 0; y < gameY; y++)
			for(int x = gameX; x < terminal.getTerminalSize().getColumns(); x++)
			{
				terminal.moveCursor(x, y);
				if(y==0||y==gameY-1||x==gameX||x==terminal.getTerminalSize().getColumns()-1)
					terminal.putCharacter('█');
				else
					terminal.putCharacter(' ');
			}

		// Draw Stats
		DisplayUtils.drawString(terminal, "Name: "+player.getName(), gameX+2, 2);
		DisplayUtils.drawString(terminal, "HP: "+player.getHealth(), gameX+2, 3);

		DisplayUtils.drawString(terminal, "Inventory:", gameX+2, 5);
		terminal.applySGR(SGR.RESET_ALL);

		Tile.BLOCKED.draw(gameX+2, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+8, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+16, 7, terminal);
		Tile.BLOCKED.draw(gameX+2+24, 7, terminal);
	}
}