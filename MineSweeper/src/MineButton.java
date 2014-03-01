/**
 * 
 */

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class MineButton extends JButton{
	
	//four types of the button:
	private int type;
	public static final int EMPTY = 9;
	public static final int MINE = 10;
	public static final int NUMBER = 11;
	
	//sate of the button when it's enabled
	private int state;
	public static final int FLAG = 12;
	public static final int CLEAR = 13;
	
	private int num;	//number of mines near a button
	
	private final int size = 25;//size of the button
	private boolean isDisabled = false;
	private boolean thisMineClicked = false;	
	
	public MineButton(){
		this.setPreferredSize(new Dimension(size, size)); 
		this.state = CLEAR;
		this.type = EMPTY;
		this.num = 0;
	}
	
	public void thisMineClicked(){
		this.thisMineClicked = true; 
	}
	
	public int getType(){
		return this.type;
	}
	public void setType(int type){
		this.type = type;
		repaint();
	}
	
	public int getState(){
		return this.state;
	}	
	public void setState(int state){
		this.state = state;
	}
	
	public int getNumber(){
		return this.num;
	}
	public void setNumber(int num){
		this.type = NUMBER;
		this.num = num;
	}
	
	public void disableButton(){
		this.setEnabled(false);
		this.isDisabled = true;
	}
	public boolean isDisabled(){
		return this.isDisabled;
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		int width = this.getWidth();
		int height = this.getHeight();
		if (!isDisabled){
			if (this.state == FLAG)	{
				g.fillRect(width/3, height/5, 2, 3*height/5);
				g.setColor(Color.RED);
				g.fillPolygon(new int[] {width/3+2, 2*width/3, width/3+2},
						new int[] {height/5, 3*height/5, 3*height/5},3);
			}
		}
		else{
			switch (state){
				case MINE:	//draw mine in the button
					this.disableButton();
					if (thisMineClicked)	g.setColor(Color.RED);
					else 			g.setColor(Color.BLACK);
					g.fillOval(width/2-5, height/2-5, 10, 10);
					g.fillRect(width/2-1, height/5, 3, 3*height/5);
					g.fillRect(width/5, height/2-1, 3*width/5, 3);
					break;
				case NUMBER:
					switch (num){
						case 1:
							g.setColor(Color.GREEN);
							g.drawString("1", width/3, 2*height/3);
							break;
						case 2:
							g.setColor(Color.BLUE);
							g.drawString("2", width/3, 2*height/3);
							break;
						case 3:
							g.setColor(Color.RED);
							g.drawString("3", width/3, 2*height/3);
							break;
						case 4:
							g.setColor(Color.DARK_GRAY);
							g.drawString("4", width/3, 2*height/3);
							break;
						case 5:
							g.setColor(Color.MAGENTA);
							g.drawString("5", width/3, 2*height/3);
							break;
						case 6:
							g.setColor(Color.ORANGE);
							g.drawString("6", width/3, 2*height/3);
							break;
						case 7:
							g.setColor(Color.PINK);
							g.drawString("7", width/3, 2*height/3);
							break;
						case 8:
							g.setColor(Color.YELLOW);
							g.drawString("8", width/3, 2*height/3);
							break;
						default:
							break;
					}
				default:
					break;
			}	
		}
	}
	

	
	
	//main test method
	public static void main(String[] args){
		JFrame testFrame = new JFrame();
		testFrame.setLayout(new GridLayout(2, 2));
		MineButton button1 = new MineButton();
		MineButton button2 = new MineButton();
		MineButton button3 = new MineButton();
		MineButton button4 = new MineButton();
		button2.setType(MineButton.MINE);
		button3.setNumber(5);;
		testFrame.add(button1);
		testFrame.add(button2);
		testFrame.add(button3);
		testFrame.add(button4);
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.pack();
		testFrame.setLocationRelativeTo(null);
		testFrame.setVisible(true);
	}
}
