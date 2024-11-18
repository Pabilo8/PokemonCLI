package pl.pokemoncli.display;

import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.pokemon.Move;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public class FightMenuDisplay extends PanelDisplay
{
	private final Color backgroundColor = new Color(0x7F7F7F);
	private final Color textColor = new Color(0xFFFFFF);
	private final Color selectColor = new Color(0x1A1A1A);
	private final int button_width = 17;
	private final int button_heigh = 5;

	public FightMenuDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	// draw button
	private void drawButtonBody(Fight fight, int gameX, int gameY) {
			drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY);
			drawString("█               █",gameX, gameY + 1);
			drawString("█               █",gameX, gameY + 2);
			drawString("█               █",gameX, gameY + 3);
			drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4);
	}

	private void drawSelectedButtonBody(Fight fight, int gameX, int gameY) {
			drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█",gameX, gameY, selectColor, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 1, selectColor, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 2, selectColor, backgroundColor);
			drawStringColor("█               █",gameX, gameY + 3, selectColor, backgroundColor);
			drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█",gameX, gameY + 4, selectColor, backgroundColor);
	}

	private void drawMainMenuButton(Fight fight, int buttonX, int buttonY, Fight.Button button) {
		if (fight.getButton() == button) {
			drawSelectedButtonBody(fight, buttonX, buttonY);
			switch (button) {
				case FIGHT -> {drawStringColor("█     Fight     █",buttonX, buttonY + 2, selectColor, backgroundColor);}
				case POKEMON -> {drawStringColor("█    Pokemon    █",buttonX, buttonY + 2, selectColor, backgroundColor);}
				case ITEM -> {drawStringColor("█     Item      █",buttonX, buttonY + 2, selectColor, backgroundColor);}
				case RUN -> {drawStringColor("█      Run      █",buttonX, buttonY + 2, selectColor, backgroundColor);}
			}
		} else {
			drawButtonBody(fight, buttonX, buttonY);
			switch (button) {
				case FIGHT -> {drawString("█     Fight     █",buttonX, buttonY + 2);}
				case POKEMON -> {drawString("█    Pokemon    █",buttonX, buttonY + 2);}
				case ITEM -> {drawString("█     Item      █",buttonX, buttonY + 2);}
				case RUN -> {drawString("█      Run      █",buttonX, buttonY + 2);}
			}
		}
	}

	private void drawAttacksMenuButton(Fight fight, int buttonX, int buttonY, Fight.Button button) {
		Move move = switch (button) {
			case FIGHT -> {yield fight.getCurrPlayerPokemon().getAttacks().get(0);}
			case POKEMON -> {yield fight.getCurrPlayerPokemon().getAttacks().get(1);}
			case ITEM -> {yield fight.getCurrPlayerPokemon().getAttacks().get(2);}
			case RUN -> {yield fight.getCurrPlayerPokemon().getAttacks().get(3);}
		};
		if (fight.getButton() == button) {
			drawSelectedButtonBody(fight, buttonX, buttonY);
			drawStringColor(move.getName(), buttonX+2, buttonY+1, selectColor, backgroundColor);
			drawStringColor("PP: " + move.getCurrentPp() + "/" + move.getPp(), buttonX+2, buttonY+2, selectColor, backgroundColor);
			drawStringColor("PWR:" + move.getPower(), buttonX+2, buttonY+3, selectColor, backgroundColor);
			drawStringColor("ACC:" + move.getAccuracy(), buttonX+9, buttonY+3, selectColor, backgroundColor);
		} else {
			drawButtonBody(fight, buttonX, buttonY);
			drawStringColor(move.getName(), buttonX+2, buttonY+1, selectColor, backgroundColor);
			drawStringColor("PP: " + move.getCurrentPp() + "/" + move.getPp(), buttonX+2, buttonY+2, selectColor, backgroundColor);
			drawStringColor("PWR:" + move.getPower(), buttonX+2, buttonY+3, selectColor, backgroundColor);
			drawStringColor("ACC:" + move.getAccuracy(), buttonX+9, buttonY+3, selectColor, backgroundColor);
		}
	}

	// draw pokemon list
	private void drawPokemonList(Fight fight, int gameX, int gameY, int currPokemon) {
		for(int i=0;i<fight.getPlayer().getMaxPokemons();i++)
		{
			if (fight.getPlayer().getPokemon(i).getId() != 0) {
                if (i == currPokemon) {
					drawStringColor("⮚ " + fight.getPlayer().getPokemon(i).getName(), gameX, gameY + i, selectColor, backgroundColor);
				} else {
                    drawString("⮚ " + fight.getPlayer().getPokemon(i).getName(), gameX, gameY + i);
                }
            } else {
				drawString("⮚ ", gameX, gameY + i);
			}
		}
	}

	// draw main fight panel
	public void drawMenuPanel(Fight fight, int gameX, int gameY) {
		// Draw Current Pokemon
		if (fight.getCurrPlayerPokemonID() == fight.getTempPlayerPokemonID())
			drawPokemonList(fight, gameX + 2, 4, fight.getCurrPlayerPokemonID());

		// Draw Menu
		if (fight.isMainMenu()) {
			drawMainMenuButton(fight, gameX + 2, gameY - button_heigh - 7, Fight.Button.FIGHT);
			drawMainMenuButton(fight, gameX + button_width + 4, gameY - button_heigh - 7, Fight.Button.POKEMON);
			drawMainMenuButton(fight, gameX + 2, gameY - 6, Fight.Button.ITEM);
			drawMainMenuButton(fight, gameX + button_width + 4, gameY - 6, Fight.Button.RUN);
		} else {
			switch (fight.getSecondMenu()) {
				case FIGHT -> {
					drawAttacksMenuButton(fight, gameX + 2, gameY - button_heigh - 7, Fight.Button.FIGHT);
					drawAttacksMenuButton(fight, gameX + button_width + 4, gameY - button_heigh - 7, Fight.Button.POKEMON);
					drawAttacksMenuButton(fight, gameX + 2, gameY - 6, Fight.Button.ITEM);
					drawAttacksMenuButton(fight, gameX + button_width + 4, gameY - 6, Fight.Button.RUN);
				}
				case POKEMON -> {
					drawPokemonList(fight, gameX + 2, 4, fight.getTempPlayerPokemonID());
				}
				case ITEM -> {} //TODO: 17.11.2024 item selection screen
			}
		}

	}
}