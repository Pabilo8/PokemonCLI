package pl.pokemoncli.sound;

import lombok.NoArgsConstructor;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Pabilo8
 * @since 17.11.2024
 */
@NoArgsConstructor
public class AudioSystem
{
	private Track currentTrack;
	private Clip clip;

	public void play(Track musicTrack)
	{
		if(currentTrack==musicTrack)
			return;
		stop();

		try
		{
			AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(new File(musicTrack.getPath()));
			clip = javax.sound.sampled.AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			currentTrack = musicTrack;
		} catch(UnsupportedAudioFileException|IOException|LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		if(clip==null)
			return;
		clip.stop();
		clip.close();
	}

	public enum Track
	{
		MAIN_MENU,
		GAME,
		FIGHT;

		public String getPath()
		{
			return "src/pl/pokemoncli/resources/"+name().toLowerCase()+".wav";
		}
	}
}