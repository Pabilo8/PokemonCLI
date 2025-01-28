package pl.pokemoncli.gui.display;

import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.gui.graphics.ImageLoader;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Fight.ActionType;
import pl.pokemoncli.logic.KeyHandlingDisplay;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.move.Move;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class FightGui implements KeyHandlingDisplay, IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JPanel panelPlayer;
	private JPanel panelEnemy;
	private JPanel playerPokemon;
	private JPanel enemyPokemon;
	private JLabel playerPokemonName;
	private JProgressBar enemyPokemonHP;
	private JProgressBar playerPokemonHP;
	private JLabel enemyPokemonName;
	private JLabel labelStatus;
	private JPanel actionsPanel;
	private JPanel enemyStatsPanel;
	private JPanel playerStatsPanel;

	private ActionResult lastResult = null;

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		if(lastResult!=null&&lastResult.getResult()==ResultType.END_OF_BATTLE)
		{
			PokemonGUI.getInstance().changeGui(PokemonGUI.getInstance().getGameDisplay());
			return lastResult;
		}

		ActionResult result = lastResult;

		//Progress bar and buttons update
		if(fight!=null&&fight.getCurrEnemyPokemon().getCurrentHp() > 0)
		{
			Pokemon playerPokemon = fight.getCurrPlayerPokemon();
			Pokemon enemyPokemon = fight.getCurrEnemyPokemon();
			playerPokemonHP.setValue(playerPokemon.getCurrentHp());
			enemyPokemonHP.setMaximum(enemyPokemon.getHp());
		}
		else
			PokemonGUI.getInstance().changeGui(PokemonGUI.getInstance().getGameDisplay());

		this.lastResult = null;
		return result;
	}

	@Override
	public void onInit()
	{
		PokemonGUI.getInstance().getAudioSystem().play(Track.FIGHT);
		Fight fight = PokemonGUI.getInstance().getFight();
		this.lastResult = null;

		Pokemon playerPokemon = fight.getCurrPlayerPokemon();
		playerPokemonName.setText(playerPokemon.getName());
		playerPokemonHP.setMaximum(playerPokemon.getHp());
		playerPokemonHP.setValue(playerPokemon.getCurrentHp());
		playerPokemonHP.setForeground(PokemonGUI.COLOR_HP_PLAYER);
		((ImagePanel)this.playerPokemon).setImage(playerPokemon.getSpecies().name()+"_BACK");

		Pokemon enemyPokemon = fight.getCurrEnemyPokemon();
		enemyPokemonName.setText(enemyPokemon.getName());
		enemyPokemonHP.setMaximum(enemyPokemon.getHp());
		enemyPokemonHP.setValue(enemyPokemon.getCurrentHp());
		enemyPokemonHP.setForeground(PokemonGUI.COLOR_HP_ENEMY);
		((ImagePanel)this.enemyPokemon).setImage(enemyPokemon.getSpecies().name()+"_FRONT");

		labelStatus.setText("What will "+playerPokemon.getName()+" do?");

		((ImagePanel)this.mainPanel).setImage("gui_fight");
		this.panelEnemy.setBackground(new Color(255, 255, 255, 0));
		this.enemyPokemon.setBackground(new Color(255, 255, 255, 0));
		this.panelPlayer.setBackground(new Color(255, 255, 255, 0));
		this.playerPokemon.setBackground(new Color(255, 255, 255, 0));

		SwingUtilities.invokeLater(() -> this.mainPanel.repaint());
		displayMainMenu(fight);
	}

	private void displayMainMenu(Fight fight)
	{
		actionsPanel.removeAll();
		actionsPanel.setLayout(new GridLayout(2, 2));

		JButton fightButton = new JButton("Fight");
		fightButton.addActionListener(e -> displayFightMenu(fight));
		actionsPanel.add(fightButton);

		JButton pokemonButton = new JButton("Pokemon");
		pokemonButton.addActionListener(e -> displayPokemonMenu(fight));
		actionsPanel.add(pokemonButton);

		JButton itemButton = new JButton("Item");
		itemButton.addActionListener(e -> displayItemMenu(fight));
		actionsPanel.add(itemButton);

		JButton runButton = new JButton("Run");
		runButton.setAction(new FightAction(fight, ActionType.RUN));
		actionsPanel.add(runButton);

		actionsPanel.revalidate();
		actionsPanel.repaint();
	}

	private void displayFightMenu(Fight fight)
	{
		actionsPanel.removeAll();
		actionsPanel.setLayout(new GridLayout(0, 1));

		Pokemon playerPokemon = fight.getCurrPlayerPokemon();
		ArrayList<Move> moves = playerPokemon.getMoves();
		for(int i = 0; i < moves.size(); i++)
		{
			Move attack = moves.get(i);
			final int attackIndex = i;

			JButton attackButton = new JButton(attack.getName());
			attackButton.addActionListener(e -> {
				ActionType.FIGHT.setId(attackIndex);
				PokemonGUI.getInstance().getAudioSystem().soundEffect(Track.ATTACK);
				fight.userAction(ActionType.FIGHT);
				//Refresh the GUI after performing the action
				onInit();
			});
			actionsPanel.add(attackButton);
		}

		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> displayMainMenu(fight));
		actionsPanel.add(backButton);

		actionsPanel.revalidate();
		actionsPanel.repaint();
	}

	private void displayPokemonMenu(Fight fight)
	{
		actionsPanel.removeAll();
		actionsPanel.setLayout(new BorderLayout());

		DefaultListModel<Pokemon> listModel = new DefaultListModel<>();
		listModel.addAll(fight.getPlayer().getPokemons());

		JList<Pokemon> pokemonList = new JList<>(listModel);
		pokemonList.setCellRenderer(new PokemonListCellRenderer());
		actionsPanel.add(new JScrollPane(pokemonList), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JButton chooseButton = new JButton("Choose");
		chooseButton.addActionListener(e -> {
			int selectedIndex = pokemonList.getSelectedIndex();
			if(selectedIndex!=-1)
			{
				fight.setCurrPlayerPokemonID(selectedIndex);
				onInit();
			}
		});
		bottomPanel.add(chooseButton);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> displayMainMenu(fight));
		bottomPanel.add(backButton);

		actionsPanel.add(bottomPanel, BorderLayout.SOUTH);

		actionsPanel.revalidate();
		actionsPanel.repaint();
	}

	private void displayItemMenu(Fight fight)
	{
		actionsPanel.removeAll();
		actionsPanel.setLayout(new GridLayout(1, 1));

		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> displayMainMenu(fight));
		actionsPanel.add(backButton);

		actionsPanel.revalidate();
		actionsPanel.repaint();
	}

	private static class FightAction extends AbstractAction
	{
		private final Fight fight;
		private final ActionType actionType;

		public FightAction(Fight fight, ActionType actionType)
		{
			super(actionType.name());
			this.fight = fight;
			this.actionType = actionType;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			fight.userAction(actionType);
			if(actionType==ActionType.RUN)
				PokemonGUI.getInstance().getFightDisplay().lastResult = new ActionResult(ResultType.END_OF_BATTLE);
			PokemonGUI.getInstance().handleKeyInput(new Key(' '));
		}
	}

	@Override
	public void loadGraphics()
	{
		ImageLoader loader = ImageLoader.getInstance();
		loader.loadImage("gui_fight", "/gui/mainmenu/fight.png");
	}

	private void createUIComponents()
	{
		// TODO: place custom component creation code here
		this.mainPanel = new ImagePanel();
		this.playerPokemon = new ImagePanel();
		this.enemyPokemon = new ImagePanel();
	}
}
