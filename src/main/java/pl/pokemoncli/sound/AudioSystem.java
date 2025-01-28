package pl.pokemoncli.sound;

import com.esotericsoftware.minlog.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sound.sampled.*;
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
	@Getter
	@Setter
	private float musicVolume = 0.5f;
	@Getter
	@Setter
	private float soundEffectVolume = 1.0f;

	public void play(Track musicTrack)
	{
		if(currentTrack==musicTrack)
			return;
		stop();

		try
		{
			AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(
					AudioSystem.class.getResourceAsStream(musicTrack.getPath())
			);
			clip = javax.sound.sampled.AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(musicVolume*control.getMaximum());
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			currentTrack = musicTrack;
		} catch(UnsupportedAudioFileException|IOException|LineUnavailableException|IllegalArgumentException e)
		{
			Log.error("AudioSystem", String.format("Error while playing track %s: %s%n", musicTrack.name(), e.getMessage()));
		}
	}

	public void soundEffect(Track track)
	{
		try
		{
			AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(
					AudioSystem.class.getResourceAsStream(track.getPath())
			);
			Clip clip = javax.sound.sampled.AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(soundEffectVolume*control.getMaximum());
			clip.start();
		} catch(NullPointerException|UnsupportedAudioFileException|IOException|LineUnavailableException|
				IllegalArgumentException e)
		{
			Log.error("AudioSystem", String.format("Error while playing sound effect %s: %s%n", track.name(), e.getMessage()));
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
		FIGHT,

		STEP,

		DIALOGUE_WRR,
		DIALOGUE_THYEND,
		DIALOGUE_CREATURE,
		DIALOGUE_JUDGEMENT,

		DIALOGUE_METAL_BAR,
		DIALOGUE_FOOL0,
		DIALOGUE_FOOL1,
		DIALOGUE_FOOL2,
		DIALOGUE_FOOL3;

		public String getPath()
		{
			return "/music/"+name().toLowerCase()+".wav";
		}
	}
}