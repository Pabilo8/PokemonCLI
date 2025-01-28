package pl.pokemoncli.gui.display;

import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Fight.ActionType;
import pl.pokemoncli.logic.KeyHandlingDisplay;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
	private JButton fightButton;
	private JButton pokemonButton;
	private JButton itemButton;
	private JButton runButton;
	private JLabel labelStatus;

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
		if(fight!=null)
		{
			Pokemon playerPokemon = fight.getCurrPlayerPokemon();
			Pokemon enemyPokemon = fight.getCurrEnemyPokemon();
			playerPokemonHP.setValue(playerPokemon.getCurrentHp());
			enemyPokemonHP.setMaximum(enemyPokemon.getHp());
		}

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
		((ImagePanel)this.playerPokemon).setImage(playerPokemon.getSpecies().name()+"_BACK");

		Pokemon enemyPokemon = fight.getCurrEnemyPokemon();
		enemyPokemonName.setText(enemyPokemon.getName());
		enemyPokemonHP.setMaximum(enemyPokemon.getHp());
		enemyPokemonHP.setValue(enemyPokemon.getCurrentHp());
		((ImagePanel)this.enemyPokemon).setImage(enemyPokemon.getSpecies().name()+"_FRONT");

		labelStatus.setText("What will "+playerPokemon.getName()+" do?");

		SwingUtilities.invokeLater(() -> this.mainPanel.repaint());
		displayMainMenu(fight);
	}

	private void displayMainMenu(Fight fight)
	{
		fightButton.setAction(new FightAction(fight, ActionType.FIGHT));
		pokemonButton.setAction(new FightAction(fight, ActionType.POKEMON));
		itemButton.setAction(new FightAction(fight, ActionType.ITEM));
		runButton.setAction(new FightAction(fight, ActionType.RUN));
	}

	private void displayFightMenu(Fight fight)
	{

	}

	private void displayPokemonMenu(Fight fight)
	{

	}

	private void displayItemMenu(Fight fight)
	{

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

	}

	private void createUIComponents()
	{
		// TODO: place custom component creation code here 
		this.playerPokemon = new ImagePanel();
		this.enemyPokemon = new ImagePanel();
	}
}
