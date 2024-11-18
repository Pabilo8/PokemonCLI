package pl.pokemoncli.logic.pokemon;

import lombok.Getter;
import lombok.Setter;
import pl.pokemoncli.display.PokemonSprite;

import java.util.ArrayList;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */

@Getter
@Setter
public class Pokemon
{
    private final int id;
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
    private final PokemonSprite front;
    private final PokemonSprite back;
    private ArrayList<Move> Attacks;
    private final ArrayList<Move> moveSet;

    public Pokemon(int id, String name, PokemonType type1, PokemonType type2, int hp, int attack, int defence, int spAttack, int spDefence, int speed, PokemonSprite front, PokemonSprite back, ArrayList<Move> moveSet) {
        this.id = id;
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
        this.Attacks = new ArrayList<>();
        this.moveSet = moveSet;
    }

    public Pokemon(Pokemon pokemon, int level) {
        this.id = pokemon.id;
        this.name = pokemon.name;
        this.type1 = pokemon.type1;
        this.type2 = pokemon.type2;
        this.level = level;
        this.hp = pokemon.hp;
        this.currentHp = pokemon.hp;
        this.attack = pokemon.attack;
        this.defence = pokemon.defence;
        this.spAttack = pokemon.spAttack;
        this.spDefence = pokemon.spDefence;
        this.speed = pokemon.speed;
        this.front = pokemon.front;
        this.back = pokemon.back;
        this.Attacks = new ArrayList<>();
        this.moveSet = pokemon.moveSet;
    }

    public void reduceCurrentHp(int amount) {
        currentHp -= amount;
    }

    public void increaseCurrentHp(int amount) {
        currentHp += amount;
    }

}
