package pl.pokemoncli;

import com.esotericsoftware.minlog.Log;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.gui.graphics.GUIPokemonGraphics;
import pl.pokemoncli.gui.graphics.RandomGUISpriteHandler;
import pl.pokemoncli.gui.PokeLogger;
import pl.pokemoncli.gui.display.*;
import pl.pokemoncli.gui.graphics.GUITileGraphics;
import pl.pokemoncli.gui.graphics.ImageLoader;
import pl.pokemoncli.logic.Level.Terrain;
import pl.pokemoncli.logic.PokemonCommon;
import pl.pokemoncli.logic.SpriteHandler;
import pl.pokemoncli.logic.SpriteHandler.SimpleSpriteHandler;
import pl.pokemoncli.logic.characters.*;
import pl.pokemoncli.logic.combat.pokemon.PokemonSpecies;

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
		Log.info("PokemonGUI", "Loading game...");
		instance.loadGame();
		//Load Graphics
		Log.info("PokemonGUI", "Loading graphics...");
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
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				handleKeyInput(new Key(e.getKeyChar()));
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});
		window.setFocusable(true);
		window.setAutoRequestFocus(true);
		window.setVisible(true);
	}

	@Override
	public boolean handleKeyInput(Key key)
	{
		boolean result = super.handleKeyInput(key);
		if(dialogue!=null)
			changeGui(dialogueDisplay);
		if(fight!=null)
			changeGui(fightDisplay);
		return result;
	}

	@Override
	protected void saveGame()
	{
		//TODO: 19.01.2025 game saving
	}

	@Override
	protected void loadGraphics()
	{
		//Load GUI Graphics
		mainMenuDisplay.loadGraphics();

		//Set amd Load Tile Graphics
		Terrain.GRASS.setTileGraphics(GUITileGraphics.GRASS);
		Terrain.BEACH.setTileGraphics(GUITileGraphics.BEACH);
		Terrain.BEACH2.setTileGraphics(GUITileGraphics.BEACH2);
		Terrain.ROAD.setTileGraphics(GUITileGraphics.ROAD);
		Terrain.FLOOR.setTileGraphics(GUITileGraphics.FLOOR);
		Terrain.ROCK.setTileGraphics(GUITileGraphics.ROCK);
		Terrain.ROCK_SAND.setTileGraphics(GUITileGraphics.ROCK_SAND);
		Terrain.VOID.setTileGraphics(GUITileGraphics.VOID);
		Terrain.BUSH1.setTileGraphics(GUITileGraphics.BUSH1);
		Terrain.BUSH2.setTileGraphics(GUITileGraphics.BUSH2);
		Terrain.TREE_LEAVES.setTileGraphics(GUITileGraphics.TREE_LEAVES);
		Terrain.TREE_TRUNK.setTileGraphics(GUITileGraphics.TREE_TRUNK);
		Terrain.TREE_LEAVES_SOLID.setTileGraphics(GUITileGraphics.TREE_LEAVES_BIG);
		Terrain.TREE_TRUNK_SOLID.setTileGraphics(GUITileGraphics.TREE_LEAVES_BIG);
		Terrain.WATER_STILL.setTileGraphics(GUITileGraphics.WATER_STILL1,
				GUITileGraphics.WATER_STILL1, GUITileGraphics.WATER_STILL2, GUITileGraphics.WATER_STILL2);
		Terrain.WATER_FLOWING.setTileGraphics(GUITileGraphics.WATER_FLOWING1,
				GUITileGraphics.WATER_FLOWING2, GUITileGraphics.WATER_FLOWING3, GUITileGraphics.WATER_FLOWING4);
		Terrain.BRIDGE1.setTileGraphics(GUITileGraphics.BRIDGE1);
		Terrain.BRIDGE2.setTileGraphics(GUITileGraphics.BRIDGE2);
		Terrain.DOOR.setTileGraphics(GUITileGraphics.DOOR);
		Terrain.DOOR_INSIDE.setTileGraphics(GUITileGraphics.DOOR_INSIDE);
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

		//Set pokemon graphics
		PokemonSpecies.MISSINGNO.setGraphics(GUIPokemonGraphics.POKEMON_NULL, GUIPokemonGraphics.POKEMON_NULL);
		PokemonSpecies.BULBASAUR.setGraphics(GUIPokemonGraphics.BULBASAUR_FRONT, GUIPokemonGraphics.BULBASAUR_BACK);
		PokemonSpecies.CHARMANDER.setGraphics(GUIPokemonGraphics.CHARMANDER_FRONT, GUIPokemonGraphics.CHARMANDER_BACK);
		PokemonSpecies.SQUIRTLE.setGraphics(GUIPokemonGraphics.SQUIRTLE_FRONT, GUIPokemonGraphics.SQUIRTLE_BACK);
		PokemonSpecies.CATERPIE.setGraphics(GUIPokemonGraphics.CATERPIE_FRONT, GUIPokemonGraphics.CATERPIE_BACK);
		PokemonSpecies.METAPOD.setGraphics(GUIPokemonGraphics.METAPOD_FRONT, GUIPokemonGraphics.METAPOD_BACK);
		PokemonSpecies.BUTTERFREE.setGraphics(GUIPokemonGraphics.BUTTERFREE_FRONT, GUIPokemonGraphics.BUTTERFREE_BACK);
		PokemonSpecies.WEEDLE.setGraphics(GUIPokemonGraphics.WEEDLE_FRONT, GUIPokemonGraphics.WEEDLE_BACK);
		PokemonSpecies.KAKUNA.setGraphics(GUIPokemonGraphics.KAKUNA_FRONT, GUIPokemonGraphics.KAKUNA_BACK);
		PokemonSpecies.BEEDRILL.setGraphics(GUIPokemonGraphics.BEEDRILL_FRONT, GUIPokemonGraphics.BEEDRILL_BACK);
		PokemonSpecies.PIDGEY.setGraphics(GUIPokemonGraphics.PIDGEY_FRONT, GUIPokemonGraphics.PIDGEY_BACK);
		PokemonSpecies.RATTATA.setGraphics(GUIPokemonGraphics.RATTATA_FRONT, GUIPokemonGraphics.RATTATA_BACK);
		PokemonSpecies.EEVEE.setGraphics(GUIPokemonGraphics.EEVEE_FRONT, GUIPokemonGraphics.EEVEE_BACK);

		//Set Sprite Handlers
		SpriteHandler.registerHandler(Player.class, new SpriteHandler<>()
		{
			@Override
			protected GUITileGraphics getSprite(Player gameObject, float progress)
			{
				return switch(gameObject.getDirection())
				{
					case 0 ->
							getAnimation(progress, GUITileGraphics.PLAYER_RIGHT1, GUITileGraphics.PLAYER_RIGHT2, GUITileGraphics.PLAYER_RIGHT3, GUITileGraphics.PLAYER_RIGHT4);
					case 1 ->
							getAnimation(progress, GUITileGraphics.PLAYER_LEFT1, GUITileGraphics.PLAYER_LEFT2, GUITileGraphics.PLAYER_LEFT3, GUITileGraphics.PLAYER_LEFT4);
					case 2 ->
							getAnimation(progress, GUITileGraphics.PLAYER_FRONT1, GUITileGraphics.PLAYER_FRONT2, GUITileGraphics.PLAYER_FRONT3, GUITileGraphics.PLAYER_FRONT4);
					default ->
							getAnimation(progress, GUITileGraphics.PLAYER_BACK1, GUITileGraphics.PLAYER_BACK2, GUITileGraphics.PLAYER_BACK3, GUITileGraphics.PLAYER_BACK4);
				};
			}
		});
		SpriteHandler.registerHandler(WildPokemon.class, new SimpleSpriteHandler<>(GUITileGraphics.BUSH2));
		SpriteHandler.registerHandler(Enemy.class, new RandomGUISpriteHandler<Enemy>(GUITileGraphics.ENEMY0)
				.withSprite("Psi Syn", GUITileGraphics.ENEMY1)
				.withSprite("Czlowiek", GUITileGraphics.ENEMY2)
		);
		SpriteHandler.registerHandler(NPC.class, new RandomGUISpriteHandler<NPC>(GUITileGraphics.NPC0)
				.withSprite("Pies", GUITileGraphics.NPC_PIES)
		);
		SpriteHandler.registerHandler(Door.class, new SimpleSpriteHandler<>(GUITileGraphics.DOOR_OPENABLE));

		ImageLoader.getInstance().loadImage("npc_ash", "/gui/npc/ash.png");
		ImageLoader.getInstance().loadImage("npc_Pies", "/gui/npc/psi_syn.png");
		ImageLoader.getInstance().loadImage("npc_Big Smoke", "/gui/npc/big_smoke.png");

		for(var value : GUITileGraphics.values())
			value.loadGraphics();
		for(var value : GUIPokemonGraphics.values())
			value.loadGraphics();
	}

	private GUITileGraphics getAnimation(float progress, GUITileGraphics... graphics)
	{
		int index = (int)(progress*graphics.length);
		return graphics[Math.min(index, graphics.length-1)];
	}

	public void changeGui(IPokemonGui gui)
	{
		if(currentGui==gui)
			return;
		Log.info("PokemonGUI", "Changing GUI to "+gui.getClass().getSimpleName());
		SwingUtilities.invokeLater(() -> {
			//Exit parent container
			if(currentGui!=null)
				currentGui.onExit();
			//Change GUI
			window.setContentPane(gui.getMainPanel());
			currentGui = gui;
			window.revalidate();
			window.repaint();
			//Init new GUI
			gui.onInit();
		});
	}

	public void focus()
	{
		window.requestFocus();
	}
}
