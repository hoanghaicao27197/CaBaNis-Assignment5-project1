import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author CaBaNis - Team 2.5
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;
	boolean setting;
	@SuppressWarnings("unused")
	private volatile boolean isPaused = false;
	// Background:
	ImageIcon imgbpong;

	// Buttons:
	Point pPlay, pSetting, pBack, pMenu, pSa, pName;
	ImageIcon imgbtnPlay, imgbtnSetting, imgbtnBack, imgbgStart, imgbtnMenu, imgbtnSa, imgbtnName;
	int rPlay, rSetting, rBack, rMenu, rSa, rName;
	String nameP, nameS, nameB, nameN, namePlayer1, namePlayer2;
	boolean intersec, intersec1, intersec2, intersec3, intersec4;

	// Key pressed:
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean wPressed = false;
	private boolean sPressed = false;

	// Ball position, diameter:
	Image background;
	ImageIcon ball1, ball2, ball3;
	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 23;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	SecondWindow w = new SecondWindow();
	// Player 1:
	ImageIcon imgpaddle1;
	private int playerOneX = 25;
	private int playerOneY = 250;
	private int playerOneWidth = 20;
	private int playerOneHeight = 67;

	// Player 2:
	ImageIcon imgpaddle2;
	private int playerTwoX = 465;
	private int playerTwoY = 250;
	private int playerTwoWidth = 20;
	private int playerTwoHeight = 67;

	// Paddle Speed:
	private int paddleSpeed = 7;

	// Players Score:
	private int playerOneScore = 0;
	private int playerTwoScore = 0;

	// construct a PongPanel
	public PongPanel() {
		// Background:
		/** setBackground(Color.BLACK); */

		// Button position setup:
		namePlayer1 = "Player 1";
		namePlayer2 = "Player 2";
		pBack = new Point(25, 445);
		pPlay = new Point(220, 280);
		pSetting = new Point(25, 425);
		pMenu = new Point(180, 280);
		pSa = new Point(180, 400);

		rMenu = 40;
		rSa = 35;
		rSetting = 20;
		rBack = 15;
		rPlay = 40;

		// Image links:
		nameP = "image/play.png";
		nameS = "image/SettingI.png";
		nameB = "image/back.png";
		nameN = "image/playername.png";
		imgpaddle1 = new ImageIcon("paddlesimage/paddles1.png");
		imgpaddle2 = new ImageIcon("paddlesimage/paddles2.png");
		ball1 = new ImageIcon("image/unnamed.png");
		ball2 = new ImageIcon("image/basketball.png");
		ball3 = new ImageIcon("image/Tennis.png");

		// listen to key presses
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		// call step() 75 fps
		Timer timer = new Timer(1000 / 75, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		step();
	}

	public void step() {

		/* Playing */

		if (playing) {

			// Move player 1
			if (wPressed) {
				if (playerOneY - paddleSpeed > 0) {
					playerOneY -= paddleSpeed;
				}
			}
			if (sPressed) {
				if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
					playerOneY += paddleSpeed;
				}
			}

			// Move player 2
			if (upPressed) {
				if (playerTwoY - paddleSpeed > 0) {
					playerTwoY -= paddleSpeed;
				}
			}
			if (downPressed) {
				if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
					playerTwoY += paddleSpeed;
				}
			}

			// Where will the ball be after it moves?
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// Ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				Sound.play("Sound/soundtb.wav");
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					Sound.play("Sound/soundtb.wav");
					playerTwoScore++;

					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("Sound/win.wav");
					}

					ballX = 250;
					ballY = 250;
				} else {
					Sound.play("Sound/soundpaddles.wav");
					ballDeltaX *= -1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
					}

					ballX = 250;
					ballY = 250;
				} else {
					ballDeltaX *= -1;
				}
			}

			// Move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// Stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	// Paint the game screen
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		imgbtnPlay = new ImageIcon(nameP);
		imgbtnSetting = new ImageIcon(nameS);
		imgbgStart = new ImageIcon("background/backgrstart.jpg");
		// g.setColor(Color.GREEN);

		/** Welcome Screen */

		if (showTitleScreen) {
			Image imgbpong = new ImageIcon("background/backgrstart.jpg").getImage();
			pName = new Point(000,00);
			rName = 40;
			// Game title:
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			// g.drawString("Kimochi Pong", 120, 100);
			g.drawImage(imgbpong, 0, 0, 500, 500, null);
			g.drawImage(imgbtnPlay.getImage(), pPlay.x - 20, pPlay.y - 100, rPlay * 3, rPlay * 3, null);
			g.drawImage(imgbtnSetting.getImage(), pSetting.x - rSetting, pSetting.y - rSetting, rSetting * 2,
					rSetting * 2, null);
			if (intersec4) {
				g.setColor(Color.white);
				g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20));
				g.drawString("Setting", pSetting.x + 30, pSetting.y + 10);
			}
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			// g.drawString("Press 'P' to play.", 170, 425);
		}

		/** Playing */

		else if (playing) {
			// Playing Screen:
			Image background = new ImageIcon("background/background.jpg").getImage();
			g.drawImage(background, 0, 0, 500, 500, null);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			g.setColor(Color.blue);
			g.drawString(namePlayer1, 90, 40);
			g.setColor(Color.red);
			g.drawString(namePlayer2, 320, 40);

			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			/*
			 * //draw dashed line down center for (int lineY = 0; lineY <
			 * getHeight(); lineY += 50) { g.drawLine(250, lineY, 250,
			 * lineY+25); }
			 */

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 8, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 8, playerTwoLeft, getHeight());
			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 135, 85);
			g.drawString(String.valueOf(playerTwoScore), 355, 85);

			// draw the ball
			// g.fillOval(ballX, ballY, diameter, diameter);
			g.setColor(Color.RED);
			g.drawImage(ball1.getImage(), ballX, ballY, diameter, diameter, null);

			// draw the paddles
			// g.fillRect(playerOneX, playerOneY, playerOneWidth,
			// playerOneHeight);
			// g.fillRect(playerTwoX, playerTwoY, playerTwoWidth,
			// playerTwoHeight);
			g.drawImage(imgpaddle1.getImage(), playerOneX, playerOneY, playerOneWidth, playerTwoHeight, null);
			g.drawImage(imgpaddle2.getImage(), playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight, null);
		}
		if (setting) {
			imgbtnBack = new ImageIcon(nameB);
			imgbtnName = new ImageIcon(nameN);
			pName = new Point(100, 200);
			rName = 40;
			g.drawImage(imgbgStart.getImage(), 0, 0, 500, 500, null);
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
			// g.drawString("Press 'C' to Menu.", 135, 200);
			g.drawImage(imgbtnBack.getImage(), pBack.x - rBack, pBack.y - rBack, rBack * 2, rBack * 2, null);
			g.drawImage(imgbtnName.getImage(), pName.x - 50, pName.y - 50, rName * 4, rName * 2, null);
			// g.drawString("Soccer", 80, 230);
			if (intersec2) {
				g.setColor(Color.white);
				g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20));
				g.drawString("Back", pSetting.x + 20, pSetting.y + 5);
			}
		} else if (gameOver) {
			imgbtnMenu = new ImageIcon("image/menugo.png");
			imgbtnSa = new ImageIcon("image/restartgo.png");
			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.drawImage(imgbgStart.getImage(), 0, 0, 500, 500, null);
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(namePlayer1, 30, 50);
			g.setColor(Color.RED);
			g.drawString(namePlayer2, 320, 50);
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), 80, 100);
			g.setColor(Color.RED);
			g.drawString(String.valueOf(playerTwoScore), 380, 100);
			g.drawImage(imgbtnMenu.getImage(), pMenu.x - rMenu, pMenu.y - rMenu, rMenu * 2, rMenu * 2, null);
			g.drawImage(imgbtnSa.getImage(), pSa.x - rSa, pSa.y - rSa, rSa * 2, rSa * 2, null);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.setColor(Color.BLUE);
				g.drawString("The Winner is :" + namePlayer1, 15, 200);
			} else {
				g.setColor(Color.RED);
				g.drawString("The Winner is :" + namePlayer2, 15, 200);
			}
			if (intersec) {
				g.setColor(Color.blue);
				g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20));
				g.drawString("Click to back a menu", pMenu.x + 35, pMenu.y + 25);

			}
			if (intersec1) {
				g.setColor(Color.blue);
				g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20));
				g.drawString("Click to restart a game", pSa.x + 35, pSa.y + 25);
			}
			// Draw the winner name

			// Draw Restart message
			// g.setColor(Color.BLUE);
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			// TODO Draw a restart message
			// g.drawString("Press 'Space' to restart game", 95, 335);
		}

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				showTitleScreen = false;
				playing = true;
				Sound.play("Sound/click.wav");
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				isPaused = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 250;
			ballY = 250;
			playerOneScore = 0;
			playerTwoScore = 0;
		} else if (setting && e.getKeyCode() == KeyEvent.VK_N) {
			SecondWindow w = new SecondWindow();
			w.setLocationRelativeTo(PongPanel.this);
			w.setVisible(true);
			SettingsUsername s = w.getSetings();
			System.out.println("After open window");

			// Stop and wait for user input

			if (w.dialogResult == MyDialogResult.YES) {
				System.out.printf("User settings: \n Username1: %s \n Username2: %s", s.getUserName1(),
						s.getUserName2());
				namePlayer1 = s.getUserName1();
				namePlayer2 = s.getUserName2();
			} else {
				System.out.println("User chose to cancel");
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

	public void pauseGame() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (getPointDistance(arg0.getPoint(), pPlay) <= rPlay) {
			// intersec = true;
			nameP = "image/playwm.png";
		} else {
			// intersec = false;
			nameP = "image/play.png";
		}
		if (getPointDistance(arg0.getPoint(), pName) <= rName) {
			// intersec = true;
			SecondWindow w = new SecondWindow();
			w.setLocationRelativeTo(PongPanel.this);
			w.setVisible(true);
			SettingsUsername s = w.getSetings();
			System.out.println("After open window");

			// Stop and wait for user input

			if (w.dialogResult == MyDialogResult.YES) {
				System.out.printf("User settings: \n Username1: %s \n Username2: %s", s.getUserName1(),
						s.getUserName2());
				namePlayer1 = s.getUserName1();
				namePlayer2 = s.getUserName2();
			}
		} else {
			// intersec = false;

		}
		if (getPointDistance(arg0.getPoint(), pSetting) <= rSetting) {
			intersec4 = true;
		} else {
			intersec4 = false;
		}

		if (getPointDistance(arg0.getPoint(), pBack) <= rBack) {
			intersec2 = true;

		} else {
			intersec2 = false;
		}
		if (getPointDistance(arg0.getPoint(), pMenu) <= rMenu) {
			intersec = true;

		} else {
			intersec = false;
		}
		if (getPointDistance(arg0.getPoint(), pSa) <= rSa) {
			intersec1 = true;

		} else {
			intersec1 = false;
		}
	}

	public double getPointDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (showTitleScreen) {
			if (getPointDistance(e.getPoint(), pPlay) <= rPlay) {
				Sound.play("Sound/click.wav");
				showTitleScreen = false;
				gameOver = false;
				setting = false;
				playing = true;
			}
		}

		if (showTitleScreen) {
			if (getPointDistance(e.getPoint(), pSetting) <= rSetting) {

				Sound.play("Sound/click.wav");
				setting = true;
				showTitleScreen = false;
				playing = false;
				gameOver = false;
			}
		}

		else if (setting) {
			if (getPointDistance(e.getPoint(), pBack) <= rBack) {

				Sound.play("Sound/click.wav");
				showTitleScreen = true;
				playing = false;
				setting = false;
				gameOver = false;
			}
		}
		if (gameOver) {
			if (getPointDistance(e.getPoint(), pMenu) <= rMenu) {
				Sound.play("Sound/click.wav");
				gameOver = false;
				showTitleScreen = true;
				playerOneY = 250;
				playerTwoY = 250;
				ballX = 250;
				ballY = 250;
				playerOneScore = 0;
				playerTwoScore = 0;

			} else if (getPointDistance(e.getPoint(), pSa) <= rSa) {
				gameOver = false;
				playerOneY = 250;
				playerTwoY = 250;
				ballX = 250;
				ballY = 250;
				playerOneScore = 0;
				playerTwoScore = 0;
				playing = true;

			}
		}

	}

}