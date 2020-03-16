package Game_Source;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import Game_Source.Element;

public class Invader extends Element {

	enum Types {
		ALIEN_A, ALIEN_B, ALIEN_C, BOSS
	}

	private Types type;
	private boolean open;
	
	ImageIcon AlienA1 = new ImageIcon("Sprites_Source/alien_a1.png");
	ImageIcon AlienA2 = new ImageIcon("Sprites_Source/alien_a2.png");
	ImageIcon AlienB1 = new ImageIcon("Sprites_Source/alien_b1.png");
	ImageIcon AlienB2 = new ImageIcon("Sprites_Source/alien_b2.png");
	ImageIcon AlienC1 = new ImageIcon("Sprites_Source/alien_c1.png");
	ImageIcon AlienC2 = new ImageIcon("Sprites_Source/alien_c2.png");
	ImageIcon Ufo = new ImageIcon("Sprites_Source/ufo.png");

	public Invader(Types t) {
		this.type = t;
		setWidth(30);
		setHeight(25); 
	}

	@Override
	public void refresh() {
		open = !open;
	}

	@Override
	public void draw(Graphics2D g) {

		if (!isActive())
			return;

		int width = getWidth();

		if (type == Types.ALIEN_A) {

			width = width - 2;

			if (open) {
				g.drawImage(AlienA2.getImage(), getPx(), getPy(), width, getHeight(), null);

			} else {
				g.drawImage(AlienA1.getImage(),getPx(), getPy(), width, getHeight(), null);
			}

		} else if (type == Types.ALIEN_B) {

			if (open)
				g.drawImage(AlienB1.getImage(), getPx(), getPy(), width, getHeight(), null);
			else
				g.drawImage(AlienB2.getImage(), getPx(), getPy(), width, getHeight(), null);

		} else if (type == Types.ALIEN_C) {

			width = width + 4;

			if (open) {
				g.drawImage(AlienC2.getImage(), getPx(), getPy(), width, getHeight(), null);

			} else {
				g.drawImage(AlienC1.getImage(), getPx(), getPy(), width, getHeight(), null);
			}

		} else {
			width = width + 25;
			g.drawImage(Ufo.getImage(), getPx(), getPy(), width, getHeight(), null);
		}

	}

	public int getScore() {
		switch (type) {
		case ALIEN_A:
			return 300;

		case ALIEN_B:
			return 200;

		case ALIEN_C:
			return 100;

		default:
			return 1000;
		}
	}
}

