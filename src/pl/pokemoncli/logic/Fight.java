package pl.pokemoncli.logic;

import lombok.Getter;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.pokemon.Pokemon;

import java.util.Random;

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
	private int run_attempts;
	private final Random roll = new Random();
	private boolean lockedChoose;

	public Fight(Player player, Enemy enemy)
	{
		this.player = player;
		this.enemy = enemy;
		this.currPlayerPokemonID = getFirstPokemon();
		this.tempPlayerPokemonID = currPlayerPokemonID;
		this.currEnemyPokemonID = 0;
		this.button = Button.FIGHT;
		this.mainMenu = true;
		this.run_attempts = 0;
		this.lockedChoose = false;
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

		// Enemy lost pokemon
		if(getCurrEnemyPokemon().getCurrentHp() == 0) {
			enemy.reduceUsablePokemons(1);
			if(enemy.getUsablePokemons() <= 0) {
				return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
			}
			if(currEnemyPokemonID != enemy.getMaxPokemons()) {
				currEnemyPokemonID++;
			}
		}

		// Player lost pokemon
		if(getCurrPlayerPokemon().getCurrentHp() == 0) {
			player.reduceUsablePokemons(1);
			if(player.getUsablePokemons() <= 0) {
				return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
			}
			this.lockedChoose = true;
			this.mainMenu = false;
			this.secondMenu = Button.POKEMON;

		}
		return new Level.ActionResult(Level.ResultType.MOVE);
	}

	public Level.ActionResult selectButton() {
		if (mainMenu)
		{
			switch (button) {
				case FIGHT -> { return goBack(false, Button.FIGHT);}
				case POKEMON -> { return goBack(false, Button.POKEMON);}
				case ITEM -> { return goBack(false, Button.ITEM);}
				case RUN -> {
					if(roll.nextInt(100) < 50 + run_attempts + (getCurrPlayerPokemon().getSpeed() - getCurrEnemyPokemon().getSpeed())) {
						return new Level.ActionResult(Level.ResultType.END_OF_BATTLE);
					} else {
						userAction(ActionType.RUN_ATTEMPT);
					}
				}
			}
		} else {
			switch (secondMenu)
			{
				case FIGHT -> {
					int attackId;
					ActionType attack = ActionType.ATTACK;
					switch (button) {
						case FIGHT -> attackId = 0;
						case POKEMON -> attackId = 1;
						case ITEM -> attackId = 2;
						case RUN -> attackId = 3;
						default -> { return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
					}
					if (getCurrPlayerPokemon().getAttacks().get(attackId).getCurrentPp() > 0) {
						attack.setId(attackId);
						userAction(attack);
						return goBack(true, null);
					} else {
						//TODO: 18.11.2024 zrobić logi o braku PP
					}
				}
				case POKEMON -> {
					if (lockedChoose) {
						if (currPlayerPokemonID != tempPlayerPokemonID) {
							currPlayerPokemonID = tempPlayerPokemonID;
							lockedChoose = false;
							return goBack(true, null);
						}
					} else {
						if (currPlayerPokemonID == tempPlayerPokemonID) {
							mainMenu = true;
						} else {
							userAction(ActionType.POKEMON_CHANGE);
							return goBack(true, null);
						}
					}
				}
				case ITEM -> {}
				default -> { return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);}
			}
		}
		return new Level.ActionResult(Level.ResultType.MET_OBSTACLE);
	}

	private void userAction(ActionType actionType) {
		switch (actionType) {
			case ATTACK -> {
				// check faster pokemon
				if (getCurrPlayerPokemon().getSpeed() > getCurrEnemyPokemon().getSpeed()) {
					// player is first
					useAttack(getCurrPlayerPokemon(), actionType.id, getCurrEnemyPokemon());
					useAttack(getCurrEnemyPokemon(), roll.nextInt(getCurrEnemyPokemon().getAttacks().size()), getCurrPlayerPokemon());
				} else {
					// enemy is first
					useAttack(getCurrEnemyPokemon(), roll.nextInt(getCurrEnemyPokemon().getAttacks().size()), getCurrPlayerPokemon());
					useAttack(getCurrPlayerPokemon(), actionType.id, getCurrEnemyPokemon());
				}
			}
			case POKEMON_CHANGE -> {
				// enemy attack
				useAttack(getCurrEnemyPokemon(), roll.nextInt(getCurrEnemyPokemon().getAttacks().size()), getCurrPlayerPokemon());

				// change pokemon
				currPlayerPokemonID = tempPlayerPokemonID;
			}
			case ITEM_USAGE -> {
				// enemy attack
				useAttack(getCurrEnemyPokemon(), roll.nextInt(getCurrEnemyPokemon().getAttacks().size()), getCurrPlayerPokemon());

				// use item

			}
			case RUN_ATTEMPT -> {
				// player faild to run
				run_attempts++;
				// enemy attack
				useAttack(getCurrEnemyPokemon(), roll.nextInt(getCurrEnemyPokemon().getAttacks().size()), getCurrPlayerPokemon());
			}
		}
	}

	private void useAttack(Pokemon attackingPokemon, int moveID, Pokemon targetPokemon) {
		double critical = 1;
		if(roll.nextInt(100)<attackingPokemon.getAttacks().get(moveID).getAccuracy()) {
			if(roll.nextInt(100)< attackingPokemon.getSpeed()){
				critical = 1.5;
			}
			calculateDamage(attackingPokemon, moveID, targetPokemon, critical);
		}
        attackingPokemon.getAttacks().get(moveID).reduceCurrentPp(1);
    }

	public void calculateDamage(Pokemon attackingPokemon, int moveID, Pokemon targetPokemon, double critical) {
		int L = attackingPokemon.getLevel(), P = attackingPokemon.getAttacks().get(moveID).getPower(), A, D;
		switch (attackingPokemon.getAttacks().get(moveID).getCategory()) {
			case PHISICAL -> {
				A = attackingPokemon.getAttack();
				D = targetPokemon.getDefence();
				targetPokemon.reduceCurrentHp((int) (critical * ((((2.0 * L / 5 + 2) * A * P / D) / 50) + 2)));
			}
			case SPECIAL -> {
				A = attackingPokemon.getSpAttack();
				D = targetPokemon.getSpDefence();
				targetPokemon.reduceCurrentHp((int) (critical * ((((2.0 * L / 5 + 2) * A * P / D) / 50) + 2)));
			}
		}
	}

	public Pokemon getCurrEnemyPokemon() {
		return enemy.getPokemon(currEnemyPokemonID);
	}

	public Pokemon getCurrPlayerPokemon() {
		return player.getPokemon(currPlayerPokemonID);
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

	public enum ActionType {
		ATTACK,
		POKEMON_CHANGE,
		ITEM_USAGE,
		RUN_ATTEMPT;

		private int id;

		void setId(int id) {
			this.id = id;
		}
	}
}
