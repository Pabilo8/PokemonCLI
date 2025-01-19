package pl.pokemoncli.gui.display;

import lombok.AccessLevel;
import lombok.Getter;
import pl.pokemoncli.PokemonGUI;
import pl.pokemoncli.logic.SaveStateUtils;
import pl.pokemoncli.logic.SaveStateUtils.SaveObject;
import pl.pokemoncli.logic.characters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pabilo8
 * @since 17.01.2025
 */
public class LoadGameGui implements IPokemonGui
{
	@Getter(AccessLevel.PUBLIC)
	private JPanel mainPanel;
	private JButton loadGameButton;
	private JButton backToMenuButton;
	private JList<SaveObject> saveList;

	public LoadGameGui()
	{
		this.loadGameButton.addActionListener(this::loadGame);
		this.backToMenuButton.addActionListener(this::backToMenu);
		this.loadGameButton.setDefaultCapable(true);

		loadSaveObjects();
	}

	private void loadSaveObjects()
	{
		File saveDir = new File("saves/");
		File[] saveFiles = saveDir.listFiles((dir, name) -> name.endsWith(".pok"));
		List<SaveObject> saveObjects = new ArrayList<>();

		if(saveFiles!=null)
		{
			for(File saveFile : saveFiles)
			{
				SaveObject saveObject = SaveStateUtils.loadGame(saveFile);
				saveObjects.add(saveObject);
			}
		}

		saveList.setListData(saveObjects.toArray(new SaveObject[0]));
		saveList.setCellRenderer(new SaveObjectRenderer());
	}

	private void loadGame(ActionEvent actionEvent)
	{
		// Implement the logic to load the selected game
	}

	private void backToMenu(ActionEvent actionEvent)
	{
		PokemonGUI pok = PokemonGUI.getInstance();
		pok.changeGui(pok.getMainMenuDisplay());
	}

	private static class SaveObjectRenderer implements ListCellRenderer<SaveObject>
	{
		@Override
		public Component getListCellRendererComponent(JList<? extends SaveObject> list, SaveObject saveObject,
													  int index, boolean isSelected, boolean cellHasFocus)
		{
			Player player = saveObject.getPlayer();
			if(player==null)
				player = new Player("Ash", -1, -1, 5);
			BufferedImage iconImage = saveObject.getIconImage();

			JPanel panel = new JPanel(new BorderLayout());
			JLabel iconLabel = new JLabel(new ImageIcon(iconImage));
			JLabel textLabel = new JLabel("<html><b>"+player.getName()+"</b><br/>"+saveObject.getDescription()+"</html>");

			panel.add(iconLabel, BorderLayout.WEST);
			panel.add(textLabel, BorderLayout.CENTER);

			if(isSelected)
			{
				panel.setBackground(list.getSelectionBackground());
				textLabel.setForeground(list.getSelectionForeground());
			}
			else
			{
				panel.setBackground(list.getBackground());
				textLabel.setForeground(list.getForeground());
			}

			return panel;
		}
	}
}