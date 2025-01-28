package pl.pokemoncli.cli.main_display;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import lombok.Getter;
import pl.pokemoncli.cli.BaseDisplay;
import pl.pokemoncli.cli.DoubleBufferedTerminal;
import pl.pokemoncli.cli.graphics.CLIPokemonGraphics;
import pl.pokemoncli.logic.AbstractPokemonGraphics;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Fight.ActionType;
import pl.pokemoncli.logic.KeyHandlingDisplay;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.dialogue.Dialogue;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
public class FightDisplay extends BaseDisplay implements KeyHandlingDisplay
{
	private final Color foreground = new Color(0x0A3A0A);
	private final Color background = new Color(0x4C7C4F);
	protected final int barSize = 20;

	@Getter
	private ActionType button;
	@Getter
	private ActionType secondMenu;

	public FightDisplay(DoubleBufferedTerminal terminal)
	{
		super(terminal);
		this.button = ActionType.FIGHT;
	}

	private void drawPokemon(Pokemon pokemon, int barX, int barY, boolean visiblePoints)
	{
		int lines = visiblePoints?5: 4;

		for(int i = -1; i <= barSize+4; i++)
		{
			for(int j = 0; j < lines-1; j++)
				drawString(" ", barX+i, barY+j);
			drawString("▃", barX+i, barY+lines-1);
		}

		drawString(pokemon.getName(), barX, barY);
		drawString("LvL: "+pokemon.getLevel(), barX+2, barY+1);
		drawString("HP: ", barX, barY+2);
		drawHealthBar(pokemon, barX, barY);

		if(visiblePoints)
			drawString(pokemon.getCurrentHp()+" / "+pokemon.getHp(), barX+5, barY+3);
	}

	private void drawHealthBar(Pokemon pokemon, int barX, int barY)
	{
		int currPercentage = (pokemon.getCurrentHp()*100/pokemon.getHp())/5;
		if(pokemon.getCurrentHp()!=0&&pokemon.getCurrentHp()!=pokemon.getHp())
			++currPercentage;
		for(int i = 0; i < currPercentage; i++)
			terminal.draw(barX+4+i, barY+2, '▒');
		for(int i = currPercentage; i < barSize; i++)
			terminal.draw(barX+4+i, barY+2, '▓');
	}

	public void drawFightScreen(Fight fight, int displayX, int displayY)
	{
		for(int y = 0; y < displayY; y++)
			for(int x = 0; x < displayX; x++)
				terminal.drawColor(x, y, '░', foreground, background);

		AbstractPokemonGraphics<?> front = fight.getCurrEnemyPokemon().getSpecies().getFront();
		assert front instanceof CLIPokemonGraphics;
		((CLIPokemonGraphics)front).draw(displayX-CLIPokemonGraphics.POKEMON_SIZE_X-4, 2, terminal);
		drawPokemon(fight.getCurrEnemyPokemon(), 7, 2, false);

		AbstractPokemonGraphics<?> back = fight.getCurrEnemyPokemon().getSpecies().getBack();
		assert back instanceof CLIPokemonGraphics;
		((CLIPokemonGraphics)back).draw(4, displayY-CLIPokemonGraphics.POKEMON_SIZE_Y-2, terminal);
		drawPokemon(fight.getCurrPlayerPokemon(), 4+CLIPokemonGraphics.POKEMON_SIZE_X+2, displayY-CLIPokemonGraphics.POKEMON_SIZE_Y-2, true);
	}

	public Level.ActionResult moveButton(Fight fight, int x, int y)
	{
		if(!fight.isMainMenu())
		{
			switch(secondMenu)
			{
				case POKEMON -> cyclePokemon(fight, y);
				case ITEM -> {}
			}
		}
		else
		{
			int currentIndex = button.ordinal();
			if(x > 0)
			{
				button = ActionType.values()[(currentIndex+1)%4];
			}
			else if(x < 0)
			{
				button = ActionType.values()[(currentIndex+3)%4];
			}
			else if(y > 0)
			{
				button = ActionType.values()[(currentIndex+2)%4];
			}
			else if(y < 0)
			{
				button = ActionType.values()[(currentIndex+2)%4];
			}
			return new Level.ActionResult(Level.ResultType.MOVE);
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	public Level.ActionResult selectButton(Fight fight)
	{
		if(fight.isMainMenu())
		{
			switch(button)
			{
				case FIGHT -> {return goBack(fight, false, ActionType.FIGHT);}
				case POKEMON -> {return goBack(fight, false, ActionType.POKEMON);}
				case ITEM -> {return goBack(fight, false, ActionType.ITEM);}
				case RUN ->
				{
					if(fight.getDiceRoll().nextInt(100) < 50+fight.getRunAttempts()+(fight.getCurrPlayerPokemon().getSpeed()-fight.getCurrEnemyPokemon().getSpeed()))
						return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
					else
						fight.userAction(Fight.ActionType.RUN);
				}
			}
		}
		else
		{
			switch(secondMenu)
			{
				case ActionType.FIGHT ->
				{
					int attackId;
					Fight.ActionType attack = Fight.ActionType.FIGHT;
					switch(button)
					{
						case FIGHT -> attackId = 0;
						case POKEMON -> attackId = 1;
						case ITEM -> attackId = 2;
						case RUN -> attackId = 3;
						default -> {return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
					}
					if(fight.getCurrPlayerPokemon().getMoves().get(attackId).getCurrentPp() > 0)
					{
						attack.setId(attackId);
						fight.userAction(attack);
						return goBack(fight, true, null);
					}
					else
					{
						//TODO: 18.11.2024 log lack of PP
					}
				}
				case ActionType.POKEMON ->
				{
					if(fight.isLockedChoose())
					{
						if(fight.getCurrPlayerPokemonID()!=fight.getTempPlayerPokemonID())
						{
							fight.setCurrPlayerPokemonID(fight.getTempPlayerPokemonID());
							fight.setLockedChoose(false);
							return goBack(fight, true, null);
						}
					}
					else if(fight.getCurrPlayerPokemonID()==fight.getTempPlayerPokemonID())
						fight.setMainMenu(true);
					else
					{
						fight.userAction(Fight.ActionType.POKEMON);
						return goBack(fight, true, null);
					}
				}
				case ActionType.ITEM -> {}
				default -> {return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
			}
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	public Level.ActionResult goBack(Fight fight, boolean mainMenu, ActionType newButton)
	{
		fight.setMainMenu(mainMenu);
		this.secondMenu = newButton;
		this.button = ActionType.FIGHT;

		if(fight.getCurrEnemyPokemon().getCurrentHp()==0)
		{
			fight.getEnemy().reduceUsablePokemons(1);
			if(fight.getEnemy().getUsablePokemons() <= 0)
				return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
			if(fight.getCurrEnemyPokemonID()!=fight.getEnemy().getMaxPokemons())
				fight.setCurrEnemyPokemonID(fight.getCurrEnemyPokemonID()+1);
		}

		if(fight.getCurrPlayerPokemon().getCurrentHp()==0)
		{
			fight.getPlayer().reduceUsablePokemons(1);
			if(fight.getPlayer().getUsablePokemons() <= 0)
				return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
			fight.setLockedChoose(true);
			fight.setMainMenu(false);
			this.secondMenu = ActionType.POKEMON;
		}
		return new Level.ActionResult(Level.ResultType.MOVE);
	}

	private void cyclePokemon(Fight fight, int y)
	{
		if(y > 0&&fight.getPlayer().getPokemons().getFirst()!=fight.getPlayer().getPokemon(fight.getTempPlayerPokemonID()))
		{
			fight.setTempPlayerPokemonID(fight.getTempPlayerPokemonID()-1);
		}
		else if(y < 0&&fight.getPlayer().getPokemons().getLast()!=fight.getPlayer().getPokemon(fight.getTempPlayerPokemonID()))
		{
			fight.setTempPlayerPokemonID(fight.getTempPlayerPokemonID()+1);
		}
	}

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		if(key.getKind().equals(Kind.Escape))
			return goBack(fight, true, button);
		return switch(key.getCharacter())
		{
			case 'w' -> moveButton(fight, 0, 1);
			case 'a' -> moveButton(fight, -1, 0);
			case 's' -> moveButton(fight, 0, -1);
			case 'd' -> moveButton(fight, 1, 0);
			case ' ' ->
			{
				if(button==ActionType.RUN&&fight.isMainMenu())
					yield new ActionResult(ResultType.END_OF_BATTLE);
				else yield selectButton(fight);
			}
			default -> null;
		};
	}
}