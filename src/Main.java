import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * c
 * 
 * @author CaBaNis - Team 2.5
 *
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int _HEIGHT = 800;
	private static final int _WIDTH = 550;
	private PongPanel pongPanel;

	public Main() {
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setTitle("Pong Game - K21T Ltd.");
		setLayout(new BorderLayout());
		pongPanel = new PongPanel();
		getContentPane().add(pongPanel, BorderLayout.CENTER);
		pack();
	
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Pong Game - K21T Ltd.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		PongPanel pongPanel = new PongPanel();
		frame.add(pongPanel, BorderLayout.CENTER);
		frame.setSize(515, 500);
		frame.setVisible(true);
		frame.setResizable(false);

	}
}