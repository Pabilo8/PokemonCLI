package pl.pokemoncli.cli.side_display;

import pl.pokemoncli.cli.DoubleBufferedTerminal;
import pl.pokemoncli.cli.main_display.FightDisplay;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Fight.ActionType;
import pl.pokemoncli.logic.combat.move.Move;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public class FightPanelDisplay extends GamePanelDisplay
{
	private static final int BUTTON_WIDTH = 17;
	private static final int BUTTON_HEIGHT = 5;

	private static final String TEXT_EMPTY = "█               █";
	private static final String TEXT_FIGHT = "█     Fight     █";
	private static final String TEXT_POKEMON = "█    Pokemon    █";
	private static final String TEXT_ITEM = "█     Item      █";
	private static final String TEXT_RUN = "█      Run      █";

	private static final Color COLOR_BACKGROUND = new Color(0x7F7F7F);
	private static final Color COLOR_TEXT = new Color(0xFFFFFF);
	private static final Color COLOR_SELECT = new Color(0x1A1A1A);


	public FightPanelDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
	}

	// draw button
	private void drawButton(int gameX, int gameY, String textDisplayed)
	{
		drawString("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█", gameX, gameY);
		drawString("█               █", gameX, gameY+1);
		drawString(textDisplayed, gameX, gameY+2);
		drawString("█               █", gameX, gameY+3);
		drawString("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█", gameX, gameY+4);
	}

	private void drawSelectedButton(int gameX, int gameY, String textDisplayed)
	{
		drawStringColor("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█", gameX, gameY, COLOR_SELECT, COLOR_BACKGROUND);
		drawStringColor("█               █", gameX, gameY+1, COLOR_SELECT, COLOR_BACKGROUND);
		drawStringColor(textDisplayed, gameX, gameY+2, COLOR_SELECT, COLOR_BACKGROUND);
		drawStringColor("█               █", gameX, gameY+3, COLOR_SELECT, COLOR_BACKGROUND);
		drawStringColor("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█", gameX, gameY+4, COLOR_SELECT, COLOR_BACKGROUND);
	}

	private void drawMainMenuButton(Fight fight, FightDisplay fightDisplay, int buttonX, int buttonY, ActionType button)
	{
		String buttonText = switch(button)
		{
			case FIGHT -> TEXT_FIGHT;
			case POKEMON -> TEXT_POKEMON;
			case ITEM -> TEXT_ITEM;
			case RUN -> TEXT_RUN;
		};

		if(fightDisplay.getButton()==button)
			drawSelectedButton(buttonX, buttonY, buttonText);
		else
			drawButton(buttonX, buttonY, buttonText);
	}

	private void drawAttacksMenuButton(Fight fight, FightDisplay fightDisplay, int buttonX, int buttonY, ActionType button)
	{
		Move move = switch(button)
		{
			case FIGHT -> fight.getCurrPlayerPokemon().getMoves().get(0);
			case POKEMON -> fight.getCurrPlayerPokemon().getMoves().get(1);
			case ITEM -> fight.getCurrPlayerPokemon().getMoves().get(2);
			case RUN -> fight.getCurrPlayerPokemon().getMoves().get(3);
		};

		if(fightDisplay.getButton()==button)
			drawSelectedButton(buttonX, buttonY, TEXT_EMPTY);
		else
			drawButton(buttonX, buttonY, TEXT_EMPTY);
		drawString(move.getName(), buttonX+2, buttonY+1);
		drawString("PP: "+move.getCurrentPp()+"/"+move.getPp(), buttonX+2, buttonY+2);
		drawString("PWR:"+move.getPower(), buttonX+2, buttonY+3);
		drawString("ACC:"+move.getAccuracy(), buttonX+9, buttonY+3);
	}

	// draw pokemon list
	private void drawPokemonList(Fight fight, int gameX, int gameY, int currPokemon)
	{
		for(int i = 0; i < fight.getPlayer().getMaxPokemons(); i++)
			//TODO: 19.11.2024 check, unsure
			if(i < fight.getPlayer().getPokemons().size())
				if(i==currPokemon)
					drawStringColor("⮚ "+fight.getPlayer().getPokemon(i).getName(), gameX, gameY+i, COLOR_SELECT, COLOR_BACKGROUND);
				else
					drawString("⮚ "+fight.getPlayer().getPokemon(i).getName(), gameX, gameY+i);
			else
				drawString("⮚ ", gameX, gameY+i);
	}

	private static final ActionType[][] BUTTON_OPTIONS = {
			{ActionType.FIGHT, ActionType.POKEMON},
			{ActionType.ITEM, ActionType.RUN}
	};


	// draw main fight panel
	public void drawMenuPanel(Fight fight, FightDisplay fightDisplay, int gameX, int gameY)
	{
		// Draw Current Pokemon
		if(fight.getCurrPlayerPokemonID()==fight.getTempPlayerPokemonID())
			drawPokemonList(fight, gameX+2, 4, fight.getCurrPlayerPokemonID());

		// Draw Menu
		if(fight.isMainMenu())
		{
			drawMainMenuButton(fight, fightDisplay, gameX+2, gameY-BUTTON_HEIGHT-7, ActionType.FIGHT);
			drawMainMenuButton(fight, fightDisplay, gameX+BUTTON_WIDTH+4, gameY-BUTTON_HEIGHT-7, ActionType.POKEMON);
			drawMainMenuButton(fight, fightDisplay, gameX+2, gameY-6, ActionType.ITEM);
			drawMainMenuButton(fight, fightDisplay, gameX+BUTTON_WIDTH+4, gameY-6, ActionType.RUN);
		}
		else
			switch(fightDisplay.getSecondMenu())
			{
				case ActionType.FIGHT ->
				{
					drawAttacksMenuButton(fight, fightDisplay, gameX+2, gameY-BUTTON_HEIGHT-7, ActionType.FIGHT);
					drawAttacksMenuButton(fight, fightDisplay, gameX+BUTTON_WIDTH+4, gameY-BUTTON_HEIGHT-7, ActionType.POKEMON);
					drawAttacksMenuButton(fight, fightDisplay, gameX+2, gameY-6, ActionType.ITEM);
					drawAttacksMenuButton(fight, fightDisplay, gameX+BUTTON_WIDTH+4, gameY-6, ActionType.RUN);
				}
				case ActionType.POKEMON -> drawPokemonList(fight, gameX+2, 4, fight.getTempPlayerPokemonID());
				case ActionType.ITEM -> {} //TODO: 17.11.2024 item selection screen
			}

	}
}