package Game_Source;

import java.awt.Color;
import java.awt.Graphics2D;

import Game_Source.Element;

public class Shoot extends Element {

	private boolean enemy;

	public Shoot() {
		setWidth(5);
		setHeight(5);
	}

	public Shoot(boolean enemy) {
		this();
		this.enemy = enemy;
	}

	@Override
	public void refresh() {
	}

	@Override
	public void draw(Graphics2D g) {
		if (!isActive())
			return;

		g.setColor(enemy ? Color.RED : Color.WHITE);

		g.fillRect(getPx(), getPy(), getWidth(), getHeight());
	}

}

