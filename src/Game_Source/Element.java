package Game_Source;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class Element {
	private int px;
	private int py;
	private int width;
	private int height;
	private int vel;
	private boolean active;
	private Color color;
	
	private ImageIcon image;
	
	public Element() {}
	
	public Element(int px, int py, int width, int height) {
		this.px = px;
		this.py = py;
		this.width = width;
		this.height = height;
	}
	
	public void refresh() {}
	
	
	public void draw(Graphics2D g) {
		if (image == null) {
			g.setColor(color);
			g.fillRect(px, py, width, height);
		} else {
			g.drawImage(image.getImage(), px, py, null);
		}
	}
	
	public void incPx(int x) { px = px + x; }
	
	public void incPy(int y) { py = py + y; }
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getPx() {
		return px;
	}
	
	public void setPx(int px ) {
		this.px = px;
	}
	
	public int getPy() {
		return py;
	}
	
	public void setPy(int py) {
		this.py = py;
	}
	
	public int getVel() {
		return vel;
	}
	
	public void setVel(int vel) {
		this.vel = vel;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
