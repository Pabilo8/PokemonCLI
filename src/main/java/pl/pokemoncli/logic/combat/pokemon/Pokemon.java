package pl.pokemoncli.logic.combat.pokemon;

import lombok.Getter;
import lombok.Setter;
import pl.pokemoncli.logic.combat.move.Move;
import pl.pokemoncli.logic.combat.move.MoveType;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */

@Getter
@Setter
public class Pokemon implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private String name;
	private PokemonSpecies species;
	private int level;
	private int hp;
	private int currentHp;
	private int attack;
	private int defence;
	private int spAttack;
	private int spDefence;
	private int speed;

	private ArrayList<Move> moves;

	private final int maxMoves = 4;

	public Pokemon(String name, PokemonSpecies species, int hp, int attack, int defence, int spAttack, int spDefence, int speed)
	{
		this.name = name;
		this.species = species;
		this.level = 0;
		this.hp = hp;
		this.currentHp = hp;
		this.attack = attack;
		this.defence = defence;
		this.spAttack = spAttack;
		this.spDefence = spDefence;
		this.speed = speed;
		this.moves = new ArrayList<>();
	}

	public Pokemon(PokemonSpecies species, int level)
	{
		this(species.name(), species, species.getHp(), species.getAttack(), species.getDefence(), species.getSpAttack(), species.getSpDefence(), species.getSpeed());
		this.level = level;
	}

	public void reduceCurrentHp(int amount)
	{
		if(currentHp < amount)
			currentHp = 0;
		else
			currentHp -= amount;
	}

	public void increaseCurrentHp(int amount)
	{
		currentHp += amount;
	}

	public void addAttack(Move attack)
	{
		moves.add(attack);
	}

	public void replaceAttack(Move newAttack, int oldAttackId)
	{
		moves.add(oldAttackId, newAttack);
		moves.remove(oldAttackId+1);
	}

	public Pokemon withMoves(Move... attacks)
	{
		for(Move attack : attacks)
			addAttack(attack);
		return this;
	}

	public Pokemon withMoves(MoveType... attacks)
	{
		return withMoves(Arrays.stream(attacks)
				.map(Move::new)
				.toArray(Move[]::new)
		);
	}

	public void levelUp()
	{
		level++;
		hp += 2;
		attack += 1;
		defence += 1;
		spAttack += 1;
		spDefence += 1;
		speed += 1;
		currentHp = hp;
	}
}
