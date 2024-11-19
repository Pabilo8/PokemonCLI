package pl.pokemoncli.display.side_display;

import pl.pokemoncli.display.DoubleBufferedTerminal;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.combat.move.Move;

import java.awt.*;

/**
 * @author KitsuneOkami
 * @since 17.11.2024
 */
public class FightPanelDisplay extends PanelDisplay
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

	private void drawMainMenuButton(Fight fight, int buttonX, int buttonY, Fight.Button button)
	{
		String buttonText = switch(button)
		{
			case FIGHT -> TEXT_FIGHT;
			case POKEMON -> TEXT_POKEMON;
			case ITEM -> TEXT_ITEM;
			case RUN -> TEXT_RUN;
		};

		if(fight.getButton()==button)
			drawSelectedButton(buttonX, buttonY, buttonText);
		else
			drawButton(buttonX, buttonY, buttonText);
	}

	private void drawAttacksMenuButton(Fight fight, int buttonX, int buttonY, Fight.Button button)
	{
		Move move = switch(button)
		{
			case FIGHT -> fight.getCurrPlayerPokemon().getMoves().get(0);
			case POKEMON -> fight.getCurrPlayerPokemon().getMoves().get(1);
			case ITEM -> fight.getCurrPlayerPokemon().getMoves().get(2);
			case RUN -> fight.getCurrPlayerPokemon().getMoves().get(3);
		};

		if(fight.getButton()==button)
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

	// draw main fight panel
	public void drawMenuPanel(Fight fight, int gameX, int gameY)
	{
		// Draw Current Pokemon
		if(fight.getCurrPlayerPokemonID()==fight.getTempPlayerPokemonID())
			drawPokemonList(fight, gameX+2, 4, fight.getCurrPlayerPokemonID());

		// Draw Menu
		if(fight.isMainMenu())
		{
			drawMainMenuButton(fight, gameX+2, gameY-BUTTON_HEIGHT-7, Fight.Button.FIGHT);
			drawMainMenuButton(fight, gameX+BUTTON_WIDTH+4, gameY-BUTTON_HEIGHT-7, Fight.Button.POKEMON);
			drawMainMenuButton(fight, gameX+2, gameY-6, Fight.Button.ITEM);
			drawMainMenuButton(fight, gameX+BUTTON_WIDTH+4, gameY-6, Fight.Button.RUN);
		}
		else
			switch(fight.getSecondMenu())
			{
				case FIGHT ->
				{
					drawAttacksMenuButton(fight, gameX+2, gameY-BUTTON_HEIGHT-7, Fight.Button.FIGHT);
					drawAttacksMenuButton(fight, gameX+BUTTON_WIDTH+4, gameY-BUTTON_HEIGHT-7, Fight.Button.POKEMON);
					drawAttacksMenuButton(fight, gameX+2, gameY-6, Fight.Button.ITEM);
					drawAttacksMenuButton(fight, gameX+BUTTON_WIDTH+4, gameY-6, Fight.Button.RUN);
				}
				case POKEMON -> drawPokemonList(fight, gameX+2, 4, fight.getTempPlayerPokemonID());
				case ITEM -> {} //TODO: 17.11.2024 item selection screen
			}

	}
}