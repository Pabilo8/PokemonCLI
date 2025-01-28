package pl.pokemoncli.logic;

import lombok.Getter;
import lombok.Setter;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.move.MoveCategory;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;

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

	@Setter
	private int currPlayerPokemonID;
	@Setter
	private int tempPlayerPokemonID;
	@Setter
	private int currEnemyPokemonID;

	@Setter
	private boolean mainMenu;
	@Setter
	private boolean lockedChoose;

	private final Random diceRoll = new Random();
	private int runAttempts;

	public Fight(Player player, Enemy enemy)
	{
		this.player = player;
		this.enemy = enemy;
		this.currPlayerPokemonID = getFirstPokemon();
		this.tempPlayerPokemonID = currPlayerPokemonID;
		this.currEnemyPokemonID = 0;
		this.mainMenu = true;
		this.runAttempts = 0;
		this.lockedChoose = false;
	}

	private int getFirstPokemon()
	{
		for(int i = 0; i < player.getMaxPokemons(); i++)
			if(player.getPokemon(i).getCurrentHp() > 0)
				return i;
		return 0;
	}

	public void userAction(ActionType actionType)
	{
		switch(actionType)
		{
			case FIGHT ->
			{
				if(getCurrPlayerPokemon().getSpeed() > getCurrEnemyPokemon().getSpeed())
				{
					useAttack(getCurrPlayerPokemon(), actionType.id, getCurrEnemyPokemon());
					useAttack(getCurrEnemyPokemon(), diceRoll.nextInt(getCurrEnemyPokemon().getMoves().size()), getCurrPlayerPokemon());
				}
				else
				{
					useAttack(getCurrEnemyPokemon(), diceRoll.nextInt(getCurrEnemyPokemon().getMoves().size()), getCurrPlayerPokemon());
					useAttack(getCurrPlayerPokemon(), actionType.id, getCurrEnemyPokemon());
				}
			}
			case POKEMON ->
			{
				useAttack(getCurrEnemyPokemon(), diceRoll.nextInt(getCurrEnemyPokemon().getMoves().size()), getCurrPlayerPokemon());
				currPlayerPokemonID = tempPlayerPokemonID;
			}
			case ITEM ->
			{
				useAttack(getCurrEnemyPokemon(), diceRoll.nextInt(getCurrEnemyPokemon().getMoves().size()), getCurrPlayerPokemon());
			}
			case RUN ->
			{
				runAttempts++;
				useAttack(getCurrEnemyPokemon(), diceRoll.nextInt(getCurrEnemyPokemon().getMoves().size()), getCurrPlayerPokemon());
			}
		}
	}

	private void useAttack(Pokemon attackingPokemon, int moveID, Pokemon targetPokemon)
	{
		double critical = 1;
		if(diceRoll.nextInt(100) < attackingPokemon.getMoves().get(moveID).getAccuracy())
		{
			if(diceRoll.nextInt(100) < attackingPokemon.getSpeed())
				critical = 1.5;
			calculateDamage(attackingPokemon, moveID, targetPokemon, critical);
		}
		attackingPokemon.getMoves().get(moveID).reduceCurrentPp(1);
	}

	public void calculateDamage(Pokemon attackingPokemon, int moveID, Pokemon targetPokemon, double critical)
	{
		int level = attackingPokemon.getLevel();
		int power = attackingPokemon.getMoves().get(moveID).getPower();
		int attack, defence;

		switch(attackingPokemon.getMoves().get(moveID).getCategory())
		{
			case MoveCategory.PHYSICAL ->
			{
				attack = attackingPokemon.getAttack();
				defence = targetPokemon.getDefence();
				targetPokemon.reduceCurrentHp((int)(critical*((((2.0*level/5+2)*attack*power/defence)/50)+2)));
			}
			case MoveCategory.SPECIAL ->
			{
				attack = attackingPokemon.getSpAttack();
				defence = targetPokemon.getSpDefence();
				targetPokemon.reduceCurrentHp((int)(critical*((((2.0*level/5+2)*attack*power/defence)/50)+2)));
			}
		}
	}

	public Pokemon getCurrEnemyPokemon()
	{
		return enemy.getPokemon(currEnemyPokemonID);
	}

	public Pokemon getCurrPlayerPokemon()
	{
		return player.getPokemon(currPlayerPokemonID);
	}

	public enum ActionType
	{
		FIGHT,
		POKEMON,
		ITEM,
		RUN;

		@Setter
		private int id;
	}
}