import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;

public class MinePanel extends JPanel{

	private int columns;
	private int rows;
	private int numOfMines;
	private MineButton[][] buttons;
	private int openedButtons;
	private int maxButtons;
	private boolean isWin;
	private boolean isGameOver;
	private boolean gameStarted;
	private double time;
	private int numFlags;
	
	public MinePanel(int rows, int columns, int numOfMines){
		this.numOfMines = numOfMines;
		this.rows = rows;
		this.columns = columns;
		this.maxButtons = rows*columns - numOfMines;
		
		buttons = new MineButton[rows][columns];
		this.setLayout(new GridLayout(rows, columns));
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++){
				buttons[i][j] = new MineButton();
				this.add(buttons[i][j]);
				
				buttons[i][j].addMouseListener(new MouseAdapter(){		
					public void mouseClicked(MouseEvent e){
						if (e.getButton() == MouseEvent.BUTTON3){
							if (!gameStarted)	gameStarted = true;
							MineButton button = (MineButton)e.getComponent();
							if (!button.isDisabled() && !isGameOver)	{
								if (button.getState() == MineButton.CLEAR){
									button.setState(MineButton.FLAG);
									numFlags++;
								}
								else{
									button.setState(MineButton.CLEAR);
									numFlags--;
								}
								repaint();
							}
						}
					}
				});
				
				buttons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!gameStarted)	gameStarted = true;				
						MineButton button = (MineButton)e.getSource();
						if (button.getState() == MineButton.CLEAR && !isGameOver){
							button.disableButton();
							oneMoreButtonOpened();
							button.setState(button.getType());
							if (button.getState() == MineButton.MINE){
								button.thisMineClicked();
								showAllMines();
								System.out.println("LOST");
							}
						}
						repaint();
					}
				});
				
			}
		
		//initialize numOfMines - number of mines
		int minesSet = 0; //number of mines already set on the board
		int i; int j;
		Random rand = new Random();
		while (minesSet < this.numOfMines){
			i = rand.nextInt(rows);
			j = rand.nextInt(columns);
			if (!(buttons[i][j].getType() == MineButton.MINE)){ //if button[i][j] is not a mine
				buttons[i][j].setType(MineButton.MINE);	//set it to be a mine
				minesSet++;
				int left = (i == 0) ? 0 : i-1;
				int right = (i == rows-1) ? rows-1 : i+1;
				int top = (j == 0) ? 0 : j-1;
				int bottom = (j == columns-1) ? columns-1 : j+1;
					for (int x = left; x <= right; x++)
						for (int y = top; y <= bottom; y++){
							if (!(buttons[x][y].getType() == MineButton.MINE))
								buttons[x][y].setNumber(buttons[x][y].getNumber()+1);
						}
			}
		}
	}
	
	public MinePanel(){
		this(10,10,10);
	}
	
	public void showAllMines(){
		isGameOver = true;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++){
				if ((buttons[i][j].getState() == MineButton.CLEAR) && 
						buttons[i][j].getType() == MineButton.MINE){
					buttons[i][j].disableButton();
					buttons[i][j].setState(buttons[i][j].getType());
				}			
			}
	}
	
	public void oneMoreButtonOpened(){
		openedButtons++;
		if (openedButtons == maxButtons){
			isWin = true;
			isGameOver = true;
			System.out.println("WIN");
			for (int i = 0; i < rows; i++)
				for (int j = 0; j < columns; j++){
					if (buttons[i][j].getType() == MineButton.MINE && 
							buttons[i][j].getState() == MineButton.CLEAR)
						buttons[i][j].setState(MineButton.FLAG);
				}
		}
	}
	
	public double getTime(){
		return this.time;
	}
	
	public int getNumFlags(){
		return this.numFlags;
	}
	
	//check if game is over
	public boolean isGameOver(){
		return this.isGameOver;
	}
	
	public boolean gameStarted(){
		return this.gameStarted;
	}
	
	//main test method
	public static void main(String[] args){
		JFrame frame = new JFrame();
		MinePanel panel = new MinePanel();
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
