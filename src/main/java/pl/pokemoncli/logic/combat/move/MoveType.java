package pl.pokemoncli.logic.combat.move;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.logic.combat.pokemon.PokemonType;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
@AllArgsConstructor
@Getter
public enum MoveType
{
	COVET(PokemonType.NORMAL, MoveCategory.PHYSICAL, 60, 20, 25),
	TACKLE(PokemonType.NORMAL, MoveCategory.PHYSICAL, 40, 100, 35),
	GROWL(PokemonType.NORMAL, MoveCategory.STATUS, 0, 100, 40),
	TAIL_WHIP(PokemonType.NORMAL, MoveCategory.STATUS, 0, 100, 30);

	private final String name = name().toLowerCase().replace('_', ' ');
	private final PokemonType type;
	private final MoveCategory category;
	private final int power;
	private final int accuracy;
	private final int pp;
}
