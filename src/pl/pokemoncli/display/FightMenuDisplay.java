package pl.pokemoncli.display;

import pl.pokemoncli.logic.Fight;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public class FightMenuDisplay extends PanelDisplay
{
	private final Color backgroundColor = new Color(0x7F7F7F);
	private final Color textColor = new Color(0xFFFFFF);
	private final Color selectColot = new Color(0x1A1A1A);
	private final int button_width = 17;
	private final int button_heigh = 5;

	public FightMenuDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	private void drawFightButton(Fight fight, int gameX, int gameY) {
		if (fight.getButton() == Fight.Button.FIGHT) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColot, backgroundColor);
			drawStringColor("█     Fight     █",gameX, gameY + 2, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColot, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColot, backgroundColor);
		} else {
			drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
			drawString("█               █",gameX, gameY + 1);
			drawString("█     Fight     █",gameX, gameY + 2);
			drawString("█               █",gameX, gameY + 3);
			drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
		}
	}

	private void drawPokemonButton(Fight fight, int gameX, int gameY) {
        if (fight.getButton() == Fight.Button.POKEMON) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColot, backgroundColor);
			drawStringColor("█    Pokemon    █",gameX, gameY + 2, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColot, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColot, backgroundColor);
		} else {
            drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
            drawString("█               █",gameX, gameY + 1);
            drawString("█    Pokemon    █",gameX, gameY + 2);
            drawString("█               █",gameX, gameY + 3);
            drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
        }
    }

	private void drawItemButton(Fight fight, int gameX, int gameY) {
        if (fight.getButton() == Fight.Button.ITEM) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColot, backgroundColor);
			drawStringColor("█     Item      █",gameX, gameY + 2, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColot, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColot, backgroundColor);
		} else {
            drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
            drawString("█               █",gameX, gameY + 1);
            drawString("█     Item      █",gameX, gameY + 2);
            drawString("█               █",gameX, gameY + 3);
            drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
        }
    }

	private void drawRunButton(Fight fight, int gameX, int gameY) {
        if (fight.getButton() == Fight.Button.RUN) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColot, backgroundColor);
			drawStringColor("█      Run      █",gameX, gameY + 2, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColot, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColot, backgroundColor);
		} else {
            drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
            drawString("█               █",gameX, gameY + 1);
            drawString("█      Run      █",gameX, gameY + 2);
            drawString("█               █",gameX, gameY + 3);
            drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
        }
    }

	private void drawAttackButton(Fight fight, int gameX, int gameY, int id)
	{
		Fight.Button button = switch (id) {
			case 1 -> Fight.Button.POKEMON;
			case 2 -> Fight.Button.ITEM;
			case 3 -> Fight.Button.RUN;
			default -> Fight.Button.FIGHT;
		};
		if (fight.getButton() == button) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 2, selectColot, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColot, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColot, backgroundColor);
			drawStringColor(fight.getPlayerPokemon().getAttacks().get(id).getName(), gameX+2, gameY+1, selectColot, backgroundColor);
			drawStringColor("PP: " + fight.getPlayerPokemon().getAttacks().get(id).getCurrentPp() + "/" + fight.getPlayerPokemon().getAttacks().get(id).getCurrentPp(), gameX+2, gameY+2, selectColot, backgroundColor);
			drawStringColor("PWR:" + fight.getPlayerPokemon().getAttacks().get(id).getPower(), gameX+2, gameY+3, selectColot, backgroundColor);
			drawStringColor("ACC:" + fight.getPlayerPokemon().getAttacks().get(id).getAccuracy(), gameX+9, gameY+3, selectColot, backgroundColor);

		} else {
			drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
			drawString("█               █",gameX, gameY + 1);
			drawString("█               █",gameX, gameY + 2);
			drawString("█               █",gameX, gameY + 3);
			drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
			drawString(fight.getPlayerPokemon().getAttacks().get(id).getName(), gameX+2, gameY+1);
			drawString("PP: " + fight.getPlayerPokemon().getAttacks().get(id).getCurrentPp() + "/" + fight.getPlayerPokemon().getAttacks().get(id).getCurrentPp(), gameX+2, gameY+2);
			drawString("PWR:" + fight.getPlayerPokemon().getAttacks().get(id).getPower(), gameX+2, gameY+3);
			drawString("ACC:" + fight.getPlayerPokemon().getAttacks().get(id).getAccuracy(), gameX+9, gameY+3);
		}
	}

	public void drawMenuPanel(Fight fight, int gameX, int gameY)
	{
		// Draw Frame
		for(int y = 0; y < gameY; y++)
			for(int x = gameX; x < terminal.getWidth(); x++)
				terminal.drawColor(x, y, ' ', textColor, backgroundColor);

		// Draw Stats
		drawString("Name: "+fight.getPlayer().getName(), gameX+2, 1);

		//Draw Pokemon list
		drawString("Pokemons:", gameX+2, 3);
		for(int i=0;i<fight.getPlayer().getMaxPokemons();i++)
		{
			if (fight.getPlayer().getPokemon(i).getId() != 0) {
				drawString("➢ " + fight.getPlayer().getPokemon(i).getName(), gameX + 2, 4 + i);
			} else {
				drawString("➢ ", gameX + 2, 4 + i);
			}
		}

		// Draw Menu
		if (fight.isMainMenu())
		{
			drawFightButton(fight, gameX + 2, gameY - button_heigh - 7);
			drawPokemonButton(fight, gameX + button_width + 4, gameY - button_heigh - 7);
			drawItemButton(fight, gameX + 2, gameY - 6);
			drawRunButton(fight, gameX + button_width + 4, gameY - 6);
		} else {
			switch (fight.getSecondMenu()) {
				case FIGHT -> {
				drawAttackButton(fight, gameX + 2, gameY - button_heigh - 7, 0);
				drawAttackButton(fight, gameX + button_width + 4, gameY - button_heigh - 7, 1);
				drawAttackButton(fight, gameX + 2, gameY - 6, 2);
				drawAttackButton(fight, gameX + button_width + 4, gameY - 6, 3);
				}
				case POKEMON -> {} //TODO: 17.11.2024 pokemon changing screen
				case ITEM -> {} //TODO: 17.11.2024 item selection screen
			}
		}

	}
}