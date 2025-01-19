package pl.pokemoncli.gui.display;

import pl.pokemoncli.logic.combat.pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PokemonListCellRenderer extends JPanel implements ListCellRenderer<Pokemon>
{
	private JLabel nameLabel;
	private JLabel levelLabel;
	private JLabel hpLabel;
	private JProgressBar hpBar;
	private JLabel imageLabel;

	public PokemonListCellRenderer()
	{
		setLayout(new BorderLayout(5, 5));
		imageLabel = new JLabel();
		nameLabel = new JLabel();
		levelLabel = new JLabel();
		hpLabel = new JLabel();
		hpBar = new JProgressBar();

		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		textPanel.add(nameLabel);
		textPanel.add(levelLabel);
		textPanel.add(hpLabel);

		add(imageLabel, BorderLayout.WEST);
		add(textPanel, BorderLayout.CENTER);
		add(hpBar, BorderLayout.SOUTH);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Pokemon> list, Pokemon pokemon, int index, boolean isSelected, boolean cellHasFocus)
	{
		nameLabel.setText(pokemon.getName());
		levelLabel.setText("Level: "+pokemon.getLevel());
		hpLabel.setText("HP: "+pokemon.getCurrentHp()+"/"+pokemon.getHp());
		hpBar.setMaximum(pokemon.getHp());
		hpBar.setValue(pokemon.getCurrentHp());

		// Placeholder for the image
		imageLabel.setIcon(new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB)));

		if(isSelected)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
}