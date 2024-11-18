package pl.pokemoncli.logic.pokemon;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MoveList {
    private final ArrayList<Move> moves;
    private final int MAX_MOVES_NUMBER = 10;
    private final Move MOVE_NULL;

    public MoveList()
    {
        this.moves = new ArrayList<>();
        this.MOVE_NULL = new Move("MOVE_NULL",PokemonType.NORMAL, Move.MoveCategory.STATUS,0,0,0);
        AssignMoves();
    }
    private void AssignMoves()
    {
        for(int id = 0; id <= MAX_MOVES_NUMBER; id++) {
            moves.add(MovesById(id));
        }
    }
    private Move MovesById(int id) {
        return switch (id)
        {
            case 1 -> new Move("Covet",PokemonType.NORMAL, Move.MoveCategory.PHISICAL,60,20,25);
            case 2 -> new Move("Tackle",PokemonType.NORMAL, Move.MoveCategory.PHISICAL,40,100,35);
            case 3 -> new Move("Growl",PokemonType.NORMAL, Move.MoveCategory.STATUS,0,100,40);
            case 4 -> new Move("Tail Whip",PokemonType.NORMAL, Move.MoveCategory.STATUS,0,100,30);
            default -> MOVE_NULL;
        };
    }
    public Move getMove(int id) {
        return moves.get(id);
    }
}
