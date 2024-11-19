package pl.pokemoncli.logic.combat.pokemon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.display.PokemonSprite;

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
			PokemonSprite.POKEMON_NULL, PokemonSprite.POKEMON_NULL),

	BULBASAUR(PokemonType.GRASS, PokemonType.POISON, 45, 49, 49, 65, 65, 45,
			PokemonSprite.BULBASAUR_FRONT, PokemonSprite.BULBASAUR_BACK),
	CHARMANDER(PokemonType.FIRE, PokemonType.NONE, 39, 52, 43, 60, 50, 65,
			PokemonSprite.CHARMANDER_FRONT, PokemonSprite.CHARMANDER_BACK),
	SQUIRLTLE(PokemonType.WATER, PokemonType.NONE, 44, 48, 65, 50, 64, 43,
			PokemonSprite.SQUIRTLE_FRONT, PokemonSprite.SQUIRTLE_BACK),
	CATERPIE(PokemonType.BUG, PokemonType.NONE, 45, 30, 35, 20, 20, 45,
			PokemonSprite.CATERPIE_FRONT, PokemonSprite.CATERPIE_BACK),
	METAPOD(PokemonType.BUG, PokemonType.NONE, 50, 20, 55, 25, 25, 30,
			PokemonSprite.METAPOD_FRONT, PokemonSprite.METAPOD_BACK),
	BUTTERFREE(PokemonType.BUG, PokemonType.FLYING, 60, 45, 50, 90, 80, 70,
			PokemonSprite.BUTTERFREE_FRONT, PokemonSprite.BUTTERFREE_BACK),
	WEEDLE(PokemonType.BUG, PokemonType.POISON, 40, 35, 30, 20, 20, 50,
			PokemonSprite.WEEDLE_FRONT, PokemonSprite.WEEDLE_BACK),
	KAKUNA(PokemonType.BUG, PokemonType.POISON, 45, 25, 50, 25, 25, 35,
			PokemonSprite.KAKUNA_FRONT, PokemonSprite.KAKUNA_BACK),
	BEEDRILL(PokemonType.BUG, PokemonType.POISON, 65, 90, 40, 45, 80, 75,
			PokemonSprite.BEEDRILL_FRONT, PokemonSprite.BEEDRILL_BACK),
	PIDGEY(PokemonType.NORMAL, PokemonType.NONE, 40, 45, 40, 35, 35, 56,
			PokemonSprite.PIDGEY_FRONT, PokemonSprite.PIDGEY_BACK),
	RATTATA(PokemonType.NORMAL, PokemonType.FLYING, 30, 56, 35, 25, 35, 72,
			PokemonSprite.RATTATA_FRONT, PokemonSprite.RATTATA_BACK),
	EEVEE(PokemonType.NORMAL, PokemonType.NONE, 55, 55, 50, 45, 65, 55,
			PokemonSprite.EEVEE_FRONT, PokemonSprite.EEVEE_BACK),
	;

	private final PokemonType type1;
	private final PokemonType type2;
	private final int hp;
	private final int attack;
	private final int defence;
	private final int spAttack;
	private final int spDefence;
	private final int speed;
	private final PokemonSprite front, back;
}
