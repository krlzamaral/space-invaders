package Game_Source;

import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Element {

	private Font font;

	public Text() {
		font = new Font("Consolas", Font.BOLD, 20);
	}

	public Text(Font font) {
		this.font = font;
	}

	public void draw(Graphics2D g, String text) {
		draw(g, text, getPx(), getPy());
	}

	public void draw(Graphics2D g, String text, int px, int py) {
		g.setFont(font);
		g.drawString(text, px, py);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}
