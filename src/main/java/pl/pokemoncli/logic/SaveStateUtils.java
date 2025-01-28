package pl.pokemoncli.logic;

import com.esotericsoftware.minlog.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pokemoncli.logic.characters.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Pabilo8
 * @since 19.11.2024
 */
public class SaveStateUtils
{
	@Getter
	@AllArgsConstructor
	public static class SaveObject implements Serializable
	{
		private final Player player;
		private final BufferedImage iconImage;
		private final String description;

		public SaveObject()
		{
			this.player = null;
			this.iconImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
			this.description = "Error";
		}
	}

	public static SaveObject loadGame(File file)
	{
		if(!file.exists()||!file.getName().endsWith(".pok"))
		{
			Log.error("SaveStateUtils", "No save file found, starting new game.");
			return new SaveObject();
		}

		try(ZipInputStream zis = new ZipInputStream(new FileInputStream(file)))
		{
			ZipEntry entry;
			Player player = null;
			BufferedImage iconImage = null;
			String description = null;

			while((entry = zis.getNextEntry())!=null)
			{
				switch(entry.getName())
				{
					case "player.dat":
						try(ObjectInputStream ois = new ObjectInputStream(new FilterInputStream(zis)
						{
							@Override
							public void close()
							{
								// Do not close the underlying stream
							}
						}))
						{
							player = (Player)ois.readObject();
						}
						break;
					case "save_icon.png":
						iconImage = ImageIO.read(zis);
						break;
					case "description.txt":
						description = new String(zis.readAllBytes());
						break;
				}
			}

			if(player!=null&&iconImage!=null&&description!=null)
			{
				return new SaveObject(player, iconImage, description);
			}
		} catch(IOException|ClassNotFoundException e)
		{
			Log.error("SaveStateUtils", "Error loading game.");
		}
		return new SaveObject();
	}

	public static boolean saveGame(SaveObject saveObject, File file)
	{
		if(!file.getName().endsWith(".pok"))
		{
			Log.error("SaveStateUtils", "Invalid file extension.");
			return false;
		}

		try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file)))
		{
			//Save player object
			zos.putNextEntry(new ZipEntry("player.dat"));
			try(ObjectOutputStream oos = new ObjectOutputStream(new FilterOutputStream(zos)
			{
				@Override
				public void close()
				{
					// Do not close the underlying stream
				}
			}))
			{
				oos.writeObject(saveObject.getPlayer());
			}
			zos.closeEntry();

			//Save icon image
			zos.putNextEntry(new ZipEntry("save_icon.png"));
			ImageIO.write(saveObject.getIconImage(), "png", zos);
			zos.closeEntry();

			//Save description
			zos.putNextEntry(new ZipEntry("description.txt"));
			zos.write(saveObject.getDescription().getBytes());
			zos.closeEntry();

			return true;
		} catch(IOException e)
		{
			Log.error("SaveStateUtils", "Error saving game."+e.getMessage());
		}
		return false;
	}
}
