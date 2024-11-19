package pl.pokemoncli.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pokemoncli.logic.Fight.ActionType;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.move.MoveType;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
class FightTest
{
	Player player;
	Enemy enemy;
	Fight fight;

	@BeforeEach
	void setUp()
	{
		player = (Player)new Player("Player", 10, 5, 2)
				.withPokemon(new Pokemon(PokemonSpecies.BULBASAUR, 5)
						.withMoves(MoveType.TACKLE))
				.withPokemon(new Pokemon(PokemonSpecies.CHARMANDER, 5)
						.withMoves(MoveType.TACKLE));
		enemy = (Enemy)new Enemy("Enemy", 8, 4, 1)
				.withPokemon(new Pokemon(PokemonSpecies.EEVEE, 1)
						.withMoves(MoveType.TACKLE));

		fight = new Fight(player, enemy);
	}

	@Test
	void testFightInitialization()
	{
		assertNotNull(fight.getPlayer(), "Player should not be null");
		assertNotNull(fight.getEnemy(), "Enemy should not be null");
	}

	@Test
	void testFightOutcome()
	{
		Pokemon enemyPokemon = enemy.getPokemon(0);
		Pokemon playerPokemon = player.getPokemon(0);

		fight.userAction(ActionType.ATTACK);

		assertTrue(enemyPokemon.getCurrentHp() <= enemyPokemon.getHp()||
						playerPokemon.getCurrentHp() <= playerPokemon.getHp(),
				"One of the pokemons should take damage");
	}
}