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
import pl.pokemoncli.logic.dialogue.Dialogue;
import pl.pokemoncli.logic.dialogue.DialogueNode;
import pl.pokemoncli.logic.dialogue.DialogueResponse;

import javax.swing.*;
import java.util.List;

/**
 * GUI class for displaying dialogue.
 *
 * @since 14.01.2025
 */
public class DialogueGui implements KeyHandlingDisplay, IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JTextPane dialogueText;
	private JPanel imagePlayer;
	private JPanel imageNPC;
	private JPanel optionsPanel;
	private JScrollPane dialogue;
	private JPanel portraitsPanel;
	private JLabel namePlayer;
	private JLabel nameNPC;

	@Override
	public ActionResult handleKeyInput(Level level, Player player, Dialogue dialogue, Fight fight, Key key)
	{
		if(key.getCharacter()==' ')
		{
			dialogue.advanceDialogue();
			updateDialogue(dialogue);
			return new ActionResult(ResultType.DIALOG_PROGRESS);
		}
		return null;
	}

	@Override
	public void onInit()
	{
		updateDialogue(PokemonGUI.getInstance().getDialogue());
	}

	@Override
	public void loadGraphics()
	{
		// Load any necessary graphics here
	}

	public void updateDialogue(Dialogue dialogue)
	{
		//Cleanup
		optionsPanel.removeAll();
		dialogueText.setText("");

		//If there is no dialogue, return to the game world
		if(dialogue==null||dialogue.getCurrentNode()==null)
		{
			PokemonGUI.getInstance().changeGui(PokemonGUI.getInstance().getGameDisplay());
			return;
		}

		//Set the images and names
		String npcName = dialogue.getGameObject().getName();
		((ImagePanel)imagePlayer).setImage("npc_ash");
		((ImagePanel)imageNPC).setImage("npc_"+npcName);
		namePlayer.setText(PokemonGUI.getInstance().getPlayer().getName());
		nameNPC.setText(npcName);

		//Repaint the images
		portraitsPanel.repaint();

		//Set the dialogue text
		DialogueNode node = dialogue.getCurrentNode();
		dialogueText.setText(node.getMessage());
		if(node.getAudio()!=null)
			PokemonGUI.getInstance().getAudioSystem().soundEffect(node.getAudio());

		//Add the response buttons
		List<DialogueResponse> responses = node.getResponses();
		for(int i = 0; i < responses.size(); i++)
		{
			DialogueResponse response = responses.get(i);
			JButton button = new JButton(response.getText());
			int finalI = i;
			//Invoke later to avoid threading issues
			button.addActionListener(e -> {
				SwingUtilities.invokeLater(() -> {
					//Progress the dialogue
					dialogue.selectResponse(finalI);
					PokemonGUI pok = PokemonGUI.getInstance();
					//Play the audio, if available
					if(response.getAudio()!=null)
						pok.getAudioSystem().soundEffect(response.getAudio());
					//Simulate CLI version's key input
					pok.handleKeyInput(new Key(' '));
				});
			});
			optionsPanel.add(button);
		}
		optionsPanel.revalidate();
		optionsPanel.repaint();
	}

	private void createUIComponents()
	{
		this.imageNPC = new ImagePanel();
		this.imagePlayer = new ImagePanel();
		// TODO: place custom component creation code here
	}
}