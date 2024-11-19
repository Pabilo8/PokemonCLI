package pl.pokemoncli.logic;

import pl.pokemoncli.logic.characters.Player;

import java.io.*;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class SaveStateUtils
{
	public static Player loadGame(Player fallback)
	{
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("player.dat")))
		{
			return (Player)ois.readObject();
			// Implement logic to use the loaded player object
		} catch(IOException|ClassNotFoundException e)
		{
			System.err.println("No save file found, starting new game.");
		}
		return fallback;
	}

	public static boolean saveGame(Player player)
	{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("player.dat")))
		{
			oos.writeObject(player);
			return true;
		} catch(IOException e)
		{
			System.err.println("Error saving game.");
		}
		return false;
	}
}
