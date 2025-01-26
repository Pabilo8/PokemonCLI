package pl.pokemoncli;

import com.esotericsoftware.minlog.Log;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.cli.KeyHandlingDisplay;
import pl.pokemoncli.gui.PokeLogger;
import pl.pokemoncli.gui.display.*;
import pl.pokemoncli.gui.graphics.GUITileGraphics;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.PokemonCommon;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.SpriteHandler.SimpleSpriteHandler;
import pl.pokemoncli.logic.characters.Door;
import pl.pokemoncli.logic.characters.Enemy;
import pl.pokemoncli.logic.characters.NPC;
import pl.pokemoncli.logic.characters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
@Getter
public class PokemonGUI extends PokemonCommon
{
	private final JFrame window;
	@Getter(AccessLevel.PUBLIC)
	private static PokemonGUI instance;

	//GUI
	private final MainMenuGui mainMenuDisplay;
	private final SettingsGui settingsDisplay;
	private final LoadGameGui loadGameDisplay;
	private final DialogueGui dialogueDisplay;
	private final FightGui fightDisplay;
	private final GameGui gameDisplay;

	private IPokemonGui currentGui;
	private final IPokemonGui[] allGuis;

	public PokemonGUI()
	{
		this.mainMenuDisplay = new MainMenuGui();
		this.settingsDisplay = new SettingsGui();
		this.loadGameDisplay = new LoadGameGui();
		this.gameDisplay = new GameGui();
		this.fightDisplay = new FightGui();
		this.dialogueDisplay = new DialogueGui();
		this.allGuis = new IPokemonGui[]{mainMenuDisplay, settingsDisplay, loadGameDisplay, dialogueDisplay, fightDisplay, gameDisplay};

		this.window = new JFrame("Pokemon (GUI Edition)");
	}

	public static void main(String[] args)
	{
		Log.setLogger(new PokeLogger());
		//Set FlatLaf look and feel
		if(!FlatDarculaLaf.setup())
			Log.error("PokemonGUI", "Failed to set FlatLaf look and feel");
		UIManager.put("defaultFont", new Font("JetBrains Mono", Font.PLAIN, 18));

		instance = new PokemonGUI();

		//Load level
		instance.loadGame();
		//Load Graphics
		instance.loadGraphics();
		//Display the Game
		instance.displayMenu();
	}

	private void displayMenu()
	{
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		changeGui(mainMenuDisplay);
		window.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(currentGui!=null&&currentGui instanceof KeyHandlingDisplay)
					((KeyHandlingDisplay)currentGui).handleKeyInput(level, player, dialogue, fight, new Key(e.getKeyChar()));
			}

			@Override
			public void keyPressed(KeyEvent e)
			{

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});
		window.setFocusable(true);
		window.setVisible(true);
	}

	@Override
	protected void saveGame()
	{
		//TODO: 19.01.2025 game saving
	}

	@Override
	protected void loadGraphics()
	{
		mainMenuDisplay.loadGraphics();

		//
		Terrain.GRASS.setTileGraphics(GUITileGraphics.GRASS);
		Terrain.BEACH.setTileGraphics(GUITileGraphics.BEACH);
		Terrain.BEACH2.setTileGraphics(GUITileGraphics.BEACH2);
		Terrain.ROAD.setTileGraphics(GUITileGraphics.ROAD);
		Terrain.FLOOR.setTileGraphics(GUITileGraphics.FLOOR);
		Terrain.BLOCKED.setTileGraphics(GUITileGraphics.BLOCKED);
		Terrain.VOID.setTileGraphics(GUITileGraphics.VOID);
		Terrain.BUSH1.setTileGraphics(GUITileGraphics.BUSH1);
		Terrain.BUSH2.setTileGraphics(GUITileGraphics.BUSH2);
		Terrain.TREE_LEAVES.setTileGraphics(GUITileGraphics.TREE_LEAVES);
		Terrain.TREE_TRUNK.setTileGraphics(GUITileGraphics.TREE_TRUNK);
		Terrain.TREE_LEAVES_SOLID.setTileGraphics(GUITileGraphics.TREE_LEAVES);
		Terrain.WATER_STILL.setTileGraphics(GUITileGraphics.WATER_STILL1,
				GUITileGraphics.WATER_STILL1, GUITileGraphics.WATER_STILL2, GUITileGraphics.WATER_STILL2);
		Terrain.WATER_FLOWING.setTileGraphics(GUITileGraphics.WATER_FLOWING1,
				GUITileGraphics.WATER_FLOWING2, GUITileGraphics.WATER_FLOWING3, GUITileGraphics.WATER_FLOWING4);
		Terrain.BRIDGE1.setTileGraphics(GUITileGraphics.BRIDGE1);
		Terrain.BRIDGE2.setTileGraphics(GUITileGraphics.BRIDGE2);
		Terrain.DOOR.setTileGraphics(GUITileGraphics.DOOR);
		Terrain.HOUSE_WALL_LEFT.setTileGraphics(GUITileGraphics.HOUSE_WALL_LEFT);
		Terrain.HOUSE_WALL_RIGHT.setTileGraphics(GUITileGraphics.HOUSE_WALL_RIGHT);
		Terrain.HOUSE_WALL_LEFT_BOTTOM.setTileGraphics(GUITileGraphics.HOUSE_WALL_LEFT_BOTTOM);
		Terrain.HOUSE_WALL_RIGHT_BOTTOM.setTileGraphics(GUITileGraphics.HOUSE_WALL_RIGHT_BOTTOM);
		Terrain.HOUSE_WALL_LEFT_ROOF.setTileGraphics(GUITileGraphics.HOUSE_WALL_LEFT_ROOF);
		Terrain.HOUSE_WALL_RIGHT_ROOF.setTileGraphics(GUITileGraphics.HOUSE_WALL_RIGHT_ROOF);
		Terrain.HOUSE_WALL_MIDDLE_ROOF.setTileGraphics(GUITileGraphics.HOUSE_WALL_MIDDLE_ROOF);
		Terrain.HOUSE_WALL_ROOF_LEFT.setTileGraphics(GUITileGraphics.HOUSE_ROOF_TOP_LEFT);
		Terrain.HOUSE_WALL_ROOF_RIGHT.setTileGraphics(GUITileGraphics.HOUSE_ROOF_TOP_RIGHT);
		Terrain.HOUSE_WALL_ROOF_MIDDLE.setTileGraphics(GUITileGraphics.HOUSE_ROOF_TOP_MIDDLE);
		Terrain.HOUSE_WALL.setTileGraphics(GUITileGraphics.HOUSE_WALL);
		Terrain.HOUSE_WALL_BOTTOM.setTileGraphics(GUITileGraphics.HOUSE_WALL_BOTTOM);

		SpriteHandler.registerHandler(Player.class, new SpriteHandler<>()
		{
			@Override
			protected GUITileGraphics getSprite(Player gameObject)
			{
				return switch(gameObject.getDirection())
				{
					case 1 -> GUITileGraphics.PLAYER_LEFT;
					case 2 -> GUITileGraphics.PLAYER_RIGHT;
					default -> GUITileGraphics.PLAYER_VERTICAL;
				};
			}
		});
		SpriteHandler.registerHandler(Enemy.class, new SimpleSpriteHandler<>(GUITileGraphics.ENEMY_VERTICAL));
		SpriteHandler.registerHandler(NPC.class, new SimpleSpriteHandler<>(GUITileGraphics.NPC_VERTICAL));
		SpriteHandler.registerHandler(Door.class, new SimpleSpriteHandler<>(GUITileGraphics.DOOR_OPENABLE));

		for(GUITileGraphics value : GUITileGraphics.values())
			value.loadGraphics();
		//TODO: 26.01.2025 load pokemon graphics
		/*for(PokemonGraphics value : PokemonGraphics.values())
			value.loadGraphics();*/
	}

	public void changeGui(IPokemonGui gui)
	{
		SwingUtilities.invokeLater(() -> {
			//Exit parent container
			if(currentGui!=null)
			{
				currentGui.onExit();
			}
			//Change GUI
			window.setContentPane(gui.getMainPanel());
			currentGui = gui;
			window.revalidate();
			window.repaint();
			//Init new GUI
			gui.onInit();
		});
	}
}
