package pl.pokemoncli.logic.combat.pokemon;

import lombok.Getter;
import lombok.Setter;
import pl.pokemoncli.display.graphics.PokemonGraphics;
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
	private final PokemonType type1;
	private final PokemonType type2;
	private int level;
	private int hp;
	private int currentHp;
	private int attack;
	private int defence;
	private int spAttack;
	private int spDefence;
	private int speed;

	private final PokemonGraphics front;
	private final PokemonGraphics back;

	private ArrayList<Move> moves;

	private final int maxMoves = 4;

	public Pokemon(String name, PokemonType type1, PokemonType type2,
				   int hp, int attack, int defence, int spAttack, int spDefence, int speed,
				   PokemonGraphics front, PokemonGraphics back)
	{
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.level = 0;
		this.hp = hp;
		this.currentHp = hp;
		this.attack = attack;
		this.defence = defence;
		this.spAttack = spAttack;
		this.spDefence = spDefence;
		this.speed = speed;
		this.front = front;
		this.back = back;
		this.moves = new ArrayList<>();
	}

	public Pokemon(PokemonSpecies species, int level)
	{
		this(species.name(), species.getType1(), species.getType2(),
				species.getHp(), species.getAttack(), species.getDefence(), species.getSpAttack(), species.getSpDefence(), species.getSpeed(),
				species.getFront(), species.getBack());
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
