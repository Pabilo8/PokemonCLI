package pl.pokemoncli.logic;

import lombok.Getter;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.pokemon.Pokemon;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
@Getter
public class Fight
{
	private final Player player;
	private final Enemy enemy;
	private int currPlayerPokemon;
	private int currEnemyPokemon;
	private Button button;
	private boolean mainMenu;
	private Button secondMenu;

	public Fight(Player player, Enemy enemy)
	{
		this.player = player;
		this.enemy = enemy;
		this.currPlayerPokemon = 0;
		this.currEnemyPokemon = 0;
		this.button = Button.FIGHT;
		this.mainMenu = true;
	}

	public Level.ActionResult moveButton(int x, int y)
	{
		switch (button) {
			case FIGHT -> {
				if (x > 0) { button = Button.POKEMON; return new Level.ActionResult(Level.ResultType.MOVE);}
				if (y < 0) { button = Button.ITEM; return new Level.ActionResult(Level.ResultType.MOVE);}
			}
			case POKEMON -> {
				if (x < 0) { button = Button.FIGHT; return new Level.ActionResult(Level.ResultType.MOVE);}
				if (y < 0) { button = Button.RUN; return new Level.ActionResult(Level.ResultType.MOVE);}
			}
			case ITEM -> {
				if (x > 0) { button = Button.RUN; return new Level.ActionResult(Level.ResultType.MOVE);}
				if (y > 0) { button = Button.FIGHT; return new Level.ActionResult(Level.ResultType.MOVE);}
			}
			case RUN -> {
				if (x < 0) { button = Button.ITEM; return new Level.ActionResult(Level.ResultType.MOVE);}
				if (y > 0) { button = Button.POKEMON; return new Level.ActionResult(Level.ResultType.MOVE);}
			}
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	public Level.ActionResult goBack() {
		mainMenu = false;
		return new Level.ActionResult(Level.ResultType.MOVE);
	}

	public Level.ActionResult selectButton()
	{
		if (mainMenu)
		{
			switch (button)
			{
				case FIGHT -> {secondMenu = Button.FIGHT; goBack() ; }
				case POKEMON -> {secondMenu = Button.POKEMON; goBack() ;}
				case ITEM -> {secondMenu = Button.ITEM; goBack() ;}
				case RUN -> {}
			}
		} else {
			switch (button)
			{
				case FIGHT -> {}
				case POKEMON -> {}
				case ITEM -> {}
				case RUN -> {}
			}
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	public Pokemon getEnemyPokemon()
	{
		return enemy.getPokemon(currEnemyPokemon);
	}

	public Pokemon getPlayerPokemon()
	{
		return player.getPokemon(currPlayerPokemon);
	}

	public void changeEnemyPokemon(int newPokemon)
	{
		if (enemy.getPokemons().size() <= newPokemon) {
			currEnemyPokemon = newPokemon;
		}
	}

	public void changePlayerPokemon(int newPokemon)
	{
		if (player.getPokemons().size() <= newPokemon) {
			currPlayerPokemon = newPokemon;
		}
	}


	public enum Button
	{
		FIGHT,
		POKEMON,
		ITEM,
		RUN
	}
}
