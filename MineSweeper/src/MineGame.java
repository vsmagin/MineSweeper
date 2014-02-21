import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class MineGame extends JFrame{
	private JButton jbtNew = new JButton("New Game");
	private JComboBox<String> jcbGame = new JComboBox<String>(new String[] {"Small", "Medium", "Large"});
	private JTextField jtfTime = new JTextField(5);
	private JTextField jtfMines = new JTextField(3);
	
	private MinePanel minePanel;
	private JPanel menuPanel = new JPanel();
	private JPanel gamePanel = new JPanel();
	private Box box = new Box(BoxLayout.Y_AXIS);
	
	private double time;	//how long you have been playing the game
	private Timer timer = new Timer(100, new TimerListener());	//timer with 0.1s delay
	
	private static final int SMALL = 0;
	private static final int MEDIUM = 1;
	private static final int LARGE = 2;
	private int xDim = 10;
	private int yDim = 10;
	private int numMines = 10;
	
	public MineGame(){
		this.ResetGame();
		
        jtfTime.setText("" + time);
		jtfTime.setHorizontalAlignment(JTextField.RIGHT);
		jtfMines.setText("" + (numMines - minePanel.getNumFlags()));
		jtfMines.setHorizontalAlignment(JTextField.RIGHT);
		
		menuPanel.setLayout(new FlowLayout());
		menuPanel.add(jbtNew);
		menuPanel.add(new JLabel("Your TIME:"));
		menuPanel.add(jtfTime);
		menuPanel.add(new JLabel("Mines left:"));
		menuPanel.add(jtfMines);
		menuPanel.add(jcbGame);
		
		jbtNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ResetGame();
			}
		});
		
		jcbGame.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateState(jcbGame.getSelectedIndex());
				ResetGame();
			}
		});

        this.setLayout(new BorderLayout());
        this.add(menuPanel, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.SOUTH);
	}
	
	private void ResetGame(){
		box.removeAll();
		gamePanel.removeAll();
		
		time = 0;
		timer.start();
		minePanel = new MinePanel(yDim,xDim,numMines);
        box.add(Box.createVerticalGlue());
        box.add(minePanel);   
        box.add(Box.createVerticalGlue()); 
        gamePanel.add(box);
		validate();
	}
	
	private void updateState(int newState){
		switch (newState){
			case SMALL:
				xDim = 10; yDim = 10; numMines = 10;
				break;
			case MEDIUM:
				xDim = 15; yDim = 15; numMines = 40;
				break;
			case LARGE:
				xDim = 30; yDim = 15; numMines = 100;
				break;
			default:
					break;
		}	
	}
	
	private void UpdateTime(){
		jtfTime.setText(String.format("%.1f",time));
		if (minePanel.isGameOver())	timer.stop();	
	}
	
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (minePanel.gameStarted())	time+=0.1;
			jtfMines.setText("" + (numMines - minePanel.getNumFlags()));
			UpdateTime();
		}
	}
	
	public static void main(String[] args){
		MineGame frame = new MineGame();
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
