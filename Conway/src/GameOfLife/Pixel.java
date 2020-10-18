package GameOfLife;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pixel extends JLabel {
	
	private boolean isAlive = false;
	private boolean markedForDeath = false;
	private boolean change = false;
	private static Image a;
	private static Image d;
	private int row;
	private int col;
	static {
		try {
			
			a = ImageIO.read(new File("Images/Alive.png"));
			d = ImageIO.read(new File("Images/Dead.png"));
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ImageIcon ALIVE;
	private static ImageIcon DEAD;
	
	
	public Pixel() {
		
		ALIVE = new ImageIcon(a);
		DEAD = new ImageIcon(d);
		row = 0;
		col = 0;
		
	}
	
	public Pixel(int r, int c) {
		
		ALIVE = new ImageIcon(a);
		DEAD = new ImageIcon(d);
		row = r;
		col = c;
		setSize(20,20);
		this.setIcon(DEAD);
		
	}
	
	public boolean isAlive() {
		
		return isAlive;
		
	}
	
	public boolean isMarked() {
		
		return markedForDeath;
		
	}
	
	public boolean statusShouldChange() {
		
		return change;
		
	}
	
	public void setStatusShouldChange(boolean b) {
		
		change = b;
		
	}
	
	public void setIsAlive(boolean b) {
		
		isAlive = b;
		
	}
	
	public void setMarkedForDeath(boolean b) {
		
		markedForDeath = b;
		
	}
	
	public int getRow() {
		
		return row;
		
	}
	
	public int getColumn() {
		
		return col;
		
	}
	
	public void swapStatus() {
		
		if(isAlive) {
			setStatus(false);
		}
		else {
			setStatus(true);
		}
		
		
	}
	
	public void setStatus(boolean b) {
		
		isAlive = b;
		if(isAlive) {
			setIcon(ALIVE);
		}
		else {
			setIcon(DEAD);
		}
		
	}

}
