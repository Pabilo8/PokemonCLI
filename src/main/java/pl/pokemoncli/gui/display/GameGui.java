package pl.pokemoncli.gui.display;

import com.googlecode.lanterna.input.Key;
import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.logic.KeyHandlingDisplay;
import pl.pokemoncli.logic.Fight;
import pl.pokemoncli.logic.Level;
import pl.pokemoncli.logic.Level.ActionResult;
import pl.pokemoncli.logic.Level.ResultType;
import pl.pokemoncli.logic.characters.Player;
import pl.pokemoncli.logic.combat.pokemon.Pokemon;
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.sound.AudioSystem.Track;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 14.01.2025
 */
public class GameGui implements KeyHandlingDisplay, IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JPanel sidePanel;
	private JList<Pokemon> pokemonList;
	private JLabel labelPlayerName;
	private JLabel labelXPos;
	private JLabel labelYPos;
	private GameDrawPanel gamePanel;

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{

		ActionResult result = gamePanel.isAnimationClear()?switch(key.getCharacter())
		{
			case 'w' -> level.moveCharacterBy(player, 0, -1);
			case 'a' -> level.moveCharacterBy(player, -1, 0);
			case 's' -> level.moveCharacterBy(player, 0, 1);
			case 'd' -> level.moveCharacterBy(player, 1, 0);
			case 'p' -> new ActionResult(ResultType.SAVE_GAME);
			default -> null;
		}: null;

		if(result!=null&&result.getResult()==ResultType.MOVE)
		{
			PokemonGUI pok = PokemonGUI.getInstance();
			pok.getAudioSystem().soundEffect(Track.STEP);
			gamePanel.setMoveAnimation();
		}

		labelXPos.setText("X: "+player.getX());
		labelYPos.setText("Y: "+player.getY());
		return result;
	}

	public GameGui()
	{
		pokemonList.setCellRenderer(new PokemonListCellRenderer());
	}

	private void createUIComponents()
	{
		// TODO: place custom component creation code here
		gamePanel = new GameDrawPanel();
	}

	@Override
	public void onInit()
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		pok.getAudioSystem().play(Track.GAME);

		pokemonList.setListData(pok.getPlayer().getPokemons().toArray(new Pokemon[0]));
		labelPlayerName.setText(pok.getPlayer().getName());
		//TODO: 19.01.2025 dynamic updates for x,y
		labelXPos.setText("X: "+pok.getPlayer().getX());
		labelYPos.setText("Y: "+pok.getPlayer().getY());
		gamePanel.onInit();
	}

	@Override
	public void onExit()
	{
		gamePanel.onExit();
	}
}
