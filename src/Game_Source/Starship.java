package Game_Source;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import Game_Source.Element;

public class Starship extends Element {
	
	ImageIcon Imagem = new ImageIcon("Sprites_Source/starship.png");
	public Starship() {
		setWidth(30);
	    setHeight(40);
		setImage(new ImageIcon("Sprites_Source/starship.png"));
	}
	
	@Override
	public void refresh() {
	}
	
	public void desenhar(Graphics2D g) {
		g.drawImage(Imagem.getImage(),null, null);
		}
}
