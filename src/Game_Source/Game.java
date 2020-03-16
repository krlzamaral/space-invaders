package Game_Source;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Game_Source.Element;
import Game_Source.Physic;
import Game_Source.Text;
import Game_Source.Invader.Types;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WIN_HEIGHT = 680;

	private static final int WIN_WIDTH = 540;
	
	private int level = 1;
	
	private int FPS = 1000 / (20 * level);

	private JPanel screen;

	private Graphics2D g2d;

	private BufferedImage buffer;

	private boolean[] keyboard = new boolean[5];

	public Game() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setKey(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setKey(e.getKeyCode(), true);
			}
		});

		buffer = new BufferedImage(WIN_WIDTH, WIN_HEIGHT, BufferedImage.TYPE_INT_RGB);

		g2d = buffer.createGraphics();

		screen = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}
		};

		getContentPane().add(screen);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WIN_WIDTH, WIN_HEIGHT);
		setVisible(true);

	}

	private void setKey(int key, boolean pressed) {
		switch (key) {
		
		case KeyEvent.VK_LEFT:
			keyboard[0] = pressed;
			break;
			
		case KeyEvent.VK_RIGHT:
			keyboard[1] = pressed;
			break;
			
		case KeyEvent.VK_SPACE:
			keyboard[2] = pressed;
			break;
		}
	}

	private int lifes = 5;

	private Element life = new Starship();

	private Element shootstarship;

	private Element shootboss;

	private Element[] shoots = new Shoot[3];

	private Text text = new Text();

	private Invader boss;

	private Element starship;		

	private Invader[][] invaders = new Invader[10][5];

	private Invader.Types[] invadersMatrix = { Types.ALIEN_A, Types.ALIEN_A, Types.ALIEN_B, Types.ALIEN_C, Types.ALIEN_C };

	private int baseLine = 60;

	private int spacing = 15;

	private int destroyed = 0;

	private int dir;

	private int enemies;

	private int stepCount;

	boolean newLine;

	boolean moveEnemies;

	private int count;

	private int score;

	Color backgroundColor = new Color(15,15,15);

	private Random rand = new Random();
	
	ImageIcon title = new ImageIcon("Sprites_Source/space_invaders_logo.png");
	
	private void titleScreen( ) {
		try {	
			AudioInputStream as = AudioSystem.getAudioInputStream(new File("Audio_Source/background_music.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(as);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			g2d.setColor(backgroundColor);	
			g2d.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);	
			g2d.drawImage(title.getImage(), 0, 0, 520, 640, null, null);
			
			while(!keyboard[2]) {
				
					screen.repaint();	
					g2d.setColor(Color.WHITE);
					text.draw(g2d, "Press \"Space\" Start Game", 130, 350);	
					screen.repaint();
			
			}
			
			clip.stop();
			clip.close();
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}

	private void loadingGame() {

		starship = new Starship();
		starship.setVel(3);
		starship.setActive(true);
		starship.setPx(screen.getWidth() / 2 - starship.getWidth() / 2);
		starship.setPy(screen.getHeight() - starship.getHeight() - baseLine);

		shootstarship = new Shoot();
		shootstarship.setVel(-15);

		boss = new Invader(Invader.Types.BOSS);

		shootboss = new Shoot(true);
		shootboss.setVel(20);
		shootboss.setHeight(15);

		for (int i = 0; i < shoots.length; i++) {
			shoots[i] = new Shoot(true);
		}

		for (int i = 0; i < invaders.length; i++) {
			for (int j = 0; j < invaders[i].length; j++) {
				Invader e = new Invader(invadersMatrix[j]);

				e.setActive(true);

				e.setPx(i * e.getWidth() + (i + 1) * spacing);
				e.setPy(j * e.getHeight() + j * spacing + baseLine);

				invaders[i][j] = e;
			}
		}

		dir = 1;

		enemies = invaders.length * invaders[0].length;

		stepCount = enemies / level;

	}

	public void startGame() {
		long nextRefresh = 0;
		
		try {	
			AudioInputStream as = AudioSystem.getAudioInputStream(new File("Audio_Source/danger_zone.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(as);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			g2d.setColor(backgroundColor);	
			g2d.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);	
			g2d.drawImage(title.getImage(), 0, 0, 520, 640, null, null);
			
			while (lifes > 0) {
				if (System.currentTimeMillis() >= nextRefresh){

					g2d.setColor(backgroundColor);
					g2d.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);

					if (destroyed == enemies) {
						destroyed = 0;
						level++;
						loadingGame();

						continue;
					}

					if (count > stepCount) {
						moveEnemies = true;
						count = 0;
						stepCount = enemies - destroyed - level * level;

					} else {
						count++;
					}

					if (starship.isActive()) {
						if (keyboard[0] && starship.getPx() > 0) {
							starship.setPx(starship.getPx() - starship.getVel());

						} else if (keyboard[1] && starship.getPx() < 491) {
							starship.setPx(starship.getPx() + starship.getVel());
						}
					}

					if (keyboard[2] && !shootstarship.isActive()) {
						shootstarship.setPx(starship.getPx() + starship.getWidth() / 2 - shootstarship.getWidth() / 2);
						shootstarship.setPy(starship.getPy() - shootstarship.getHeight());
						shootstarship.setActive(true);
					}

					if (boss.isActive()) {
						boss.incPx(starship.getVel() - 1);

						if (!shootboss.isActive() && Physic.colideX(boss, starship)) {
							addShootEnemy(boss, shootboss);
						}

						if (boss.getPx() > screen.getWidth()) {
							boss.setActive(false);
						}
					}

					boolean colideLimits = false;

					for (int j = invaders[0].length - 1; j >= 0; j--) {

						for (int i = 0; i < invaders.length; i++) {

							Invader inv = invaders[i][j];
							
							if(inv.getPy() > 540) lifes = 0;

							if (!inv.isActive()) {
								continue;
							}
							

							if (Physic.colide(shootstarship, inv)) {
								inv.setActive(false);
								shootstarship.setActive(false);

								destroyed++;
								score = score + inv.getScore() * level;

								continue;
							}

							if (moveEnemies) {

								inv.refresh();

								if (newLine) {
									if (inv.getPy() < 60) break;
									inv.setPy(inv.getPy() + inv.getHeight() + spacing);
								} else {
									inv.incPx(spacing * dir);
								}

								if (!newLine && !colideLimits) {
									int pxLeft = inv.getPx() - spacing;
									int pxRight = inv.getPx() + inv.getWidth() + spacing;

									if (pxLeft <= 0 || pxRight >= screen.getWidth())
										colideLimits = true;
								}

								if (!shoots[0].isActive() && inv.getPx() < starship.getPx()) {
									addShootEnemy(inv, shoots[0]);

								} else if (!shoots[1].isActive() && inv.getPx() > starship.getPx() && inv.getPx() < starship.getPx() + starship.getWidth()) {
									addShootEnemy(inv, shoots[1]);

								} else if (!shoots[2].isActive() && inv.getPx() > starship.getPx()) {
									addShootEnemy(inv, shoots[2]);

								}

								if (!boss.isActive() && rand.nextInt(500) == destroyed) {
									boss.setPx(0);
									boss.setActive(true);

								}

							}

						}
					}

					if (moveEnemies && newLine) {
						dir *= -1;
						newLine = false;

					} else if (moveEnemies && colideLimits) {
						newLine = true;
					}

					moveEnemies = false;

					if (shootstarship.isActive()) {
						shootstarship.incPy(shootstarship.getVel());

						if (Physic.colide(shootstarship, boss)) {
							score = score + boss.getScore() * level;
							boss.setActive(false);
							shootstarship.setActive(false);

						} else if (shootstarship.getPy() < 0) {
							shootstarship.setActive(false);
						}

						shootstarship.draw(g2d);
					}

					if (shootboss.isActive()) {
						shootboss.incPy(shootboss.getVel());

						if (Physic.colide(shootboss, starship)) {
							lifes--;
							shootboss.setActive(false);

						} else if (shootboss.getPy() > screen.getHeight() - baseLine - shootboss.getHeight()) {
							shootboss.setActive(false);
						} else
							shootboss.draw(g2d);

					}

					for (int i = 0; i < shoots.length; i++) {
						if (shoots[i].isActive()) {
							shoots[i].incPy(+10);

							if (Physic.colide(shoots[i], starship)) {
								lifes--;
								shoots[i].setActive(false);

							} else if (shoots[i].getPy() > screen.getHeight() - baseLine - shoots[i].getHeight())
								shoots[i].setActive(false);

							shoots[i].draw(g2d);
						}
					}

					for (int i = 0; i < invaders.length; i++) {
						for (int j = 0; j < invaders[i].length; j++) {
							Invader e = invaders[i][j];
							e.draw(g2d);
						}
					}

					starship.refresh();
					starship.draw(g2d);

					boss.refresh();
					boss.draw(g2d);

					g2d.setColor(Color.WHITE);

					text.draw(g2d, String.valueOf(score), 10, 20);
					text.draw(g2d, "Level " + level, screen.getWidth() - 100, 20);
					text.draw(g2d, String.valueOf(lifes), 10, screen.getHeight() - 10);

					g2d.setColor(Color.CYAN);
					g2d.drawLine(0, screen.getHeight() - baseLine, screen.getWidth(), screen.getHeight() - baseLine);

					for (int i = 1; i < lifes; i++) {
						life.setPx(i * life.getWidth() + i * spacing);
						life.setPy(screen.getHeight() - life.getHeight());

						life.draw(g2d);
					}

					screen.repaint();

					nextRefresh = System.currentTimeMillis() + FPS;
				}
			}
			
			clip.stop();
			clip.close();
			
		} catch (Exception e) {
				e.printStackTrace();
		}

	}
	
	private void gameOverScreen( ) {
		
		try {	
			AudioInputStream as = AudioSystem.getAudioInputStream(new File("Audio_Source/background_music.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(as);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			g2d.setColor(backgroundColor);	
			g2d.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);	
			g2d.drawImage(title.getImage(), 0, 0, 520, 640, null, null);
			
			while(!keyboard[2]) {
				
				screen.repaint();
				g2d.setColor(Color.WHITE);
				text.draw(g2d, "G A M E      O V E R", 150, 250);
				text.draw(g2d, "SCORE:  " + String.valueOf(score), 190, 400);
				screen.repaint();
				
			}
			
			clip.stop();
			clip.close();
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}

	public void addShootEnemy(Element inimigo, Element shoot) {
		
		shoot.setActive(true);
		shoot.setPx(inimigo.getPx() + inimigo.getWidth() / 2 - shoot.getWidth() / 2);
		shoot.setPy(inimigo.getPy() + inimigo.getHeight());
		
	}

	public static void main(String[] args) {
		
		Game spaceInvaders = new Game();
		spaceInvaders.titleScreen();
		spaceInvaders.loadingGame();
		spaceInvaders.startGame();
		spaceInvaders.gameOverScreen();	
	}

}

