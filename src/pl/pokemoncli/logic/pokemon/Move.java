package pl.pokemoncli.logic.pokemon;

import jdk.jfr.Category;
import lombok.Getter;

@Getter
public class Move {
    private String name;
    private PokemonType type;
    private MoveCategory category;
    private int power;
    private int accuracy;
    private int pp;
    private int currentPp;

    public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.currentPp = pp;
    }

    public Move(Move move) {
        this.name = move.name;
        this.type = move.type;
        this.category = move.category;
        this.power = move.power;
        this.accuracy = move.accuracy;
        this.pp = move.pp;
        this.currentPp = move.pp;
    }

    public void reduceCurrentPp(int amount) {
        currentPp -= amount;
    }

    public void increaseCurrentPp(int amount) {
        currentPp += amount;
    }

    public enum MoveCategory {
        PHISICAL,
        SPECIAL,
        STATUS
    }
}
