package Game_Source;

import Game_Source.Element;

public class Physic {

	public static boolean colide(Element a, Element b) {
		if (!a.isActive() || !b.isActive())
			return false;

		final int plA = a.getPx() + a.getWidth();
		final int plB = b.getPx() + b.getWidth();
		final int paA = a.getPy() + a.getHeight();
		final int paB = b.getPy() + b.getHeight();

		if (plA > b.getPx() && a.getPx() < plB && paA > b.getPy() && a.getPy() < paB) {
			return true;
		}

		return false;
	}

	public static boolean colideX(Element a, Element b) {
		if (!a.isActive() || !b.isActive())
			return false;

		if (a.getPx() + a.getWidth() >= b.getPx() && a.getPx() <= b.getPx() + b.getWidth()) {
			return true;
		}

		return false;
	}

}
