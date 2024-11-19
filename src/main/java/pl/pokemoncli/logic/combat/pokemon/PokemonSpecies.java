package pl.pokemoncli.logic.combat.pokemon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.display.graphics.PokemonGraphics;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
@AllArgsConstructor
@Getter
public enum PokemonSpecies
{
	//Default Pokemon used for error handling
	MISSINGNO(PokemonType.NONE, PokemonType.NONE, 1, 0, 0, 1, 1, 1,
			PokemonGraphics.POKEMON_NULL, PokemonGraphics.POKEMON_NULL),

	BULBASAUR(PokemonType.GRASS, PokemonType.POISON, 45, 49, 49, 65, 65, 45,
			PokemonGraphics.BULBASAUR_FRONT, PokemonGraphics.BULBASAUR_BACK),
	CHARMANDER(PokemonType.FIRE, PokemonType.NONE, 39, 52, 43, 60, 50, 65,
			PokemonGraphics.CHARMANDER_FRONT, PokemonGraphics.CHARMANDER_BACK),
	SQUIRLTLE(PokemonType.WATER, PokemonType.NONE, 44, 48, 65, 50, 64, 43,
			PokemonGraphics.SQUIRTLE_FRONT, PokemonGraphics.SQUIRTLE_BACK),
	CATERPIE(PokemonType.BUG, PokemonType.NONE, 45, 30, 35, 20, 20, 45,
			PokemonGraphics.CATERPIE_FRONT, PokemonGraphics.CATERPIE_BACK),
	METAPOD(PokemonType.BUG, PokemonType.NONE, 50, 20, 55, 25, 25, 30,
			PokemonGraphics.METAPOD_FRONT, PokemonGraphics.METAPOD_BACK),
	BUTTERFREE(PokemonType.BUG, PokemonType.FLYING, 60, 45, 50, 90, 80, 70,
			PokemonGraphics.BUTTERFREE_FRONT, PokemonGraphics.BUTTERFREE_BACK),
	WEEDLE(PokemonType.BUG, PokemonType.POISON, 40, 35, 30, 20, 20, 50,
			PokemonGraphics.WEEDLE_FRONT, PokemonGraphics.WEEDLE_BACK),
	KAKUNA(PokemonType.BUG, PokemonType.POISON, 45, 25, 50, 25, 25, 35,
			PokemonGraphics.KAKUNA_FRONT, PokemonGraphics.KAKUNA_BACK),
	BEEDRILL(PokemonType.BUG, PokemonType.POISON, 65, 90, 40, 45, 80, 75,
			PokemonGraphics.BEEDRILL_FRONT, PokemonGraphics.BEEDRILL_BACK),
	PIDGEY(PokemonType.NORMAL, PokemonType.NONE, 40, 45, 40, 35, 35, 56,
			PokemonGraphics.PIDGEY_FRONT, PokemonGraphics.PIDGEY_BACK),
	RATTATA(PokemonType.NORMAL, PokemonType.FLYING, 30, 56, 35, 25, 35, 72,
			PokemonGraphics.RATTATA_FRONT, PokemonGraphics.RATTATA_BACK),
	EEVEE(PokemonType.NORMAL, PokemonType.NONE, 55, 55, 50, 45, 65, 55,
			PokemonGraphics.EEVEE_FRONT, PokemonGraphics.EEVEE_BACK),
	;

	private final PokemonType type1;
	private final PokemonType type2;
	private final int hp;
	private final int attack;
	private final int defence;
	private final int spAttack;
	private final int spDefence;
	private final int speed;
	private final PokemonGraphics front, back;
}
