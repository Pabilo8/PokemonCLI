package pl.pokemoncli.logic.characters;

import lombok.Getter;

/**
 * @author Pabilo8
 * @since 04.11.2024
 */
public class Player extends FightableCharacter
{
	@Getter
	int direction = 2;

	public Player(String name, int y, int x, int maxPokemons)
	{
		super(name, x, y, maxPokemons);
	}

	@Override
	public void setPosition(int x, int y)
	{
		int cX = this.x, cY = this.y;
		super.setPosition(x, y);

		//Change sprite based on movement
		if(x > cX)
			direction = 0;
		else if(x < cX)
			direction = 1;
		else if(y > cY)
			direction = 2;
		else if(y < cY)
			direction = 3;
	}
}
