package pl.pokemoncli.logic;

import lombok.Getter;
import pl.pokemoncli.logic.characters.Character;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.Player;

/**
 * @author Pabilo8
 * @since 16.11.2024
 */
@Getter
public class Fight
{
	private final Player player;
	private final Character enemy;

	public Fight(Player player, Enemy enemy)
	{
		this.player = player;
		this.enemy = enemy;
	}

	public void start()
	{

	}
}
