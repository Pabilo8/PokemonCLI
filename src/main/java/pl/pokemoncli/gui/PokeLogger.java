package pl.pokemoncli.gui;

import com.esotericsoftware.minlog.Log.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author Pabilo8
 * @since 16.12.2024
 */
public class PokeLogger extends Logger
{
	private long firstLogTime = new Date().getTime();

	@Override
	public void log(int level, String category, String message, Throwable ex)
	{
		StringBuilder builder = new StringBuilder(256);
		long time = (new Date()).getTime()-this.firstLogTime;
		long minutes = time/60000L;
		long seconds = time/1000L%60L;
		if(minutes <= 9L)
		{
			builder.append('0');
		}

		builder.append(minutes);
		builder.append(':');
		if(seconds <= 9L)
		{
			builder.append('0');
		}

		builder.append(seconds);
		switch(level)
		{
			case 1:
				builder.append("\u001B[34m TRACE: ");
				break;
			case 2:
				builder.append("\u001B[35m DEBUG: ");
				break;
			case 3:
				builder.append("  INFO: ");
				break;
			case 4:
				builder.append("\u001B[33m  WARN: ");
				break;
			case 5:
				builder.append("\u001B[31m ERROR: ");
		}

		if(category!=null)
		{
			builder.append('[');
			builder.append(category);
			builder.append("] ");
		}

		builder.append(message);
		if(ex!=null)
		{
			StringWriter writer = new StringWriter(256);
			ex.printStackTrace(new PrintWriter(writer));
			builder.append('\n');
			builder.append(writer.toString().trim());
		}
		builder.append("\u001B[0m");

		this.print(builder.toString());
	}
}
