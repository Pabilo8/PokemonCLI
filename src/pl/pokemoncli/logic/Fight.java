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
	private int currPlayerPokemonID;
	private int tempPlayerPokemonID;
	private int currEnemyPokemonID;
	private Button button;
	private boolean mainMenu;
	private Button secondMenu;

	public Fight(Player player, Enemy enemy)
	{
		this.player = player;
		this.enemy = enemy;
		this.currPlayerPokemonID = getFirstPokemon();
		this.tempPlayerPokemonID = currPlayerPokemonID;
		this.currEnemyPokemonID = 0;
		this.button = Button.FIGHT;
		this.mainMenu = true;
	}

	private int getFirstPokemon() {
		for (int i = 0; i < player.getMaxPokemons(); i++) {
			if (player.getPokemon(i).getCurrentHp() > 0) {
				return i;
			}
        }
		return 0;
	}

	public Level.ActionResult moveButton(int x, int y) {
		if (!isMainMenu()) {
			switch (secondMenu) {
				case POKEMON -> {
					if (y>0) {
						if (player.getPokemons().getFirst() != player.getPokemon(tempPlayerPokemonID)) { tempPlayerPokemonID--;}
					} else if (y<0) {
						if (player.getPokemons().getLast() != player.getPokemon(tempPlayerPokemonID)) { tempPlayerPokemonID++;}
					}
				}
				case ITEM -> {}
			}
		}
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

	public Level.ActionResult goBack(boolean mainMenu, Button newButton) {
		this.mainMenu = mainMenu;
		this.secondMenu = newButton;
		this.button = Button.FIGHT;
		return new Level.ActionResult(Level.ResultType.MOVE);
	}

	public Level.ActionResult selectButton() {
		if (mainMenu)
		{
			switch (button) {
				case FIGHT -> { return goBack(false, Button.FIGHT);}
				case POKEMON -> { return goBack(false, Button.POKEMON);}
				case ITEM -> { return goBack(false, Button.ITEM);}
			}
		} else {
			switch (secondMenu)
			{
				case FIGHT -> {
					switch (button) {
						case FIGHT -> useAttack(getCurrPlayerPokemon(), 0, getCurrEnemyPokemon());
						case POKEMON -> useAttack(getCurrPlayerPokemon(), 1, getCurrEnemyPokemon());
						case ITEM -> useAttack(getCurrPlayerPokemon(), 2, getCurrEnemyPokemon());
						case RUN -> useAttack(getCurrPlayerPokemon(), 3, getCurrEnemyPokemon());
						default -> {return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
					}
				}
				case POKEMON -> currPlayerPokemonID = tempPlayerPokemonID;
				case ITEM -> {}
				default -> {return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
			}
			return goBack(true, null);
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	private void useAttack(Pokemon attackingPokemon, int moveID, Pokemon targetPokemon) {
		int L = attackingPokemon.getLevel(), P = attackingPokemon.getAttacks().get(moveID).getPower(), A, D;
		switch (attackingPokemon.getAttacks().get(moveID).getCategory()) {
			case PHISICAL -> {
				A = attackingPokemon.getAttack();
				D = targetPokemon.getDefence();
				targetPokemon.reduceCurrentHp((((2 * L / 5 + 2) * A * P / D) / 50) + 2);
			}
			case SPECIAL -> {
				A = attackingPokemon.getSpAttack();
				D = targetPokemon.getSpDefence();
				targetPokemon.reduceCurrentHp((((2 * L / 5 + 2) * A * P / D) / 50) + 2);
			}
		}
		attackingPokemon.getAttacks().get(moveID).reduceCurrentPp(1);
    }

	public Pokemon getCurrEnemyPokemon() {
		return enemy.getPokemon(currEnemyPokemonID);
	}

	public Pokemon getCurrPlayerPokemon() {
		return player.getPokemon(currEnemyPokemonID);
	}

	public void changeEnemyPokemon(int newPokemon) {
		if (enemy.getPokemons().size() <= newPokemon) {
			currEnemyPokemonID = newPokemon;
		}
	}

	public void changePlayerPokemon(int newPokemon) {
		if (player.getPokemons().size() <= newPokemon) {
			currPlayerPokemonID = newPokemon;
			tempPlayerPokemonID = newPokemon;
		}
	}

	public enum Button {
		FIGHT,
		POKEMON,
		ITEM,
		RUN
	}
}
