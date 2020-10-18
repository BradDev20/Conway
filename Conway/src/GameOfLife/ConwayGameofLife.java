package GameOfLife;

import java.awt.Color;


import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

@SuppressWarnings("serial")
class DrawGrid extends JComponent{
	
	public DrawGrid() {
		
		setSize(new Dimension(1000,1000));
		
	}
	/**
	 * Draws a grid to visualize the cells better
	 */
	public void paint(Graphics g) {
		
		for(int i = 0; i <= 35; i++) {
		g.drawLine(5, (i*20), 985, (i*20));
		}
		for(int j = 0; j <= 49; j++) {
		g.drawLine((j*20)+5, 0, (j*20)+5, 700);
			}
		
	}
	
}

public class ConwayGameofLife {
	
	public static JFrame lifeBoard = new JFrame();
	public static final int gridSize = 35;
	static Integer numGens = 0;
	static final boolean alive = true;
	static final boolean dead = false;
	static boolean doUpdate = false;
	public static boolean started = false;
	public static JLayeredPane panel = new JLayeredPane();
	public static JPanel drawGrid = new JPanel();
	public static JButton reset = new JButton("Reset");
	public static JButton clear = new JButton("Clear");
	public static JButton start = new JButton("Start");
	public static JButton pause = new JButton("Pause");
	public static JButton rules = new JButton("Rules");
	public static JTextField generations = new JTextField(numGens);
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static Pixel[][] pixelGrid, backup;
	
	private static void onMouseClicked(MouseEvent e) {
		Pixel p = (Pixel)e.getSource();
		if(!doUpdate) {
			
			if(p.isAlive()) {
				p.setStatus(dead);
			}
			else {
				p.setStatus(alive);
			}
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		//Initializes the JFrame, defines its dimensions and location
		lifeBoard.setSize(new Dimension(1500,1500));
		lifeBoard.setResizable(false);
		lifeBoard.setBounds(dim.width/2-500,dim.height/2-500,1000,1000);
		lifeBoard.setTitle("Conway's Game of Life");
		lifeBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lifeBoard.setBackground(Color.BLACK);
		
		panel.setSize(new Dimension(1000,1000));
		panel.setPreferredSize(new Dimension(500,500));
		
		drawGrid.setPreferredSize((new Dimension(500,500)));
		
		generations.setFont(new Font("Courier", Font.BOLD,40));
		generations.setText("Generation: " + numGens.toString());
		generations.setEditable(false);
		generations.setBorder(BorderFactory.createEmptyBorder());
		
		pixelGrid = new Pixel[gridSize+14][gridSize];
		backup = new Pixel[gridSize+14][gridSize];
		/**
		 * Creates all of the cells and adds their MouseListener
		 */
		for(int i = 0; i < gridSize+14; i++) {
	    	
	    	for(int j = 0; j < gridSize; j++) {
	    		
	    		pixelGrid[i][j] = new Pixel(i,j);
	    		backup[i][j] = new Pixel(i,j);
	    		pixelGrid[i][j].setLocation((i*20)+5,j*20);
	    		pixelGrid[i][j].addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    onMouseClicked(e);
	                }
	            });
	    		panel.add(pixelGrid[i][j],JLayeredPane.DEFAULT_LAYER);
	    		
	    	}
	    	
	    }
		
		//The ActionListener for the start button
		start.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ev) {
			      
				   pause.setEnabled(true);
				   if(!started){
					   
					   for(int i = 0; i < gridSize+14; i++) {
				    		for(int j = 0; j < gridSize; j++) {
				    			
				    			backup[i][j].setStatus(pixelGrid[i][j].isAlive());
				    			
				    		}
				    	}
					   
				   }
				   
				   clear.setEnabled(false);
				   doUpdate = true;
				   started = true;
			   }
				   
			   
			});
		
		//The ActionListener for the pause button
		pause.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ev) {
			      
				   clear.setEnabled(true);
				   doUpdate = false;
				   
			   }
			});
		
		//The ActionListener for the reset button
		reset.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    
		    	clear.setEnabled(true);
		    	pause.setEnabled(false);
		    	doUpdate = false;
		    	numGens = 0;
		    	generations.setText("Generation: " + numGens.toString());
		    	//started = false;
		    	for(int i = 0; i < gridSize+14; i++) {
		    		for(int j = 0; j < gridSize; j++) {
		    			
		    			pixelGrid[i][j].setStatus(backup[i][j].isAlive());
		    			
		    		}
		    	}
		    	lifeBoard.revalidate();
		    	lifeBoard.repaint();
		    	
		    	
		    }
		});
		
		//The ActionListener for the clear button
		clear.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ev) {
			      
				   clear.setEnabled(true);
				   pause.setEnabled(false);
				   doUpdate = false;
				   started = false;
				   numGens = 0;
				   generations.setText("Generation: " + numGens.toString());
				   for(int i = 0; i < gridSize+14; i++) {
			    		for(int j = 0; j < gridSize; j++) {
			    			
			    			pixelGrid[i][j].setStatus(dead);
			    			backup[i][j].setStatus(dead);
			    			
			    		}
			    	}
				   
			   }
			});
		
		//The ActionListener for the clear button, creates the temporary rule screen
		rules.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ev) {
			      
				  if(pause.isEnabled())
					  pause.doClick();
				  
				  JFrame temp = new JFrame();
				  temp.setResizable(false);
				  temp.setBounds(dim.width/2-250,dim.height/2-250,500,500);
				  JPanel tempPanel = new JPanel();
				  JLabel ruleLabel = new JLabel();
				  JTextArea ruleText = new JTextArea();
				  ruleLabel.setBounds(300,200,150,50);
				  ruleText.setBounds(50,250,450,300);
				  ruleText.setBackground(null);
				  
				  ruleLabel.setFont(new Font("Courier", Font.BOLD,50));
				  ruleText.setFont(new Font("Courier", Font.PLAIN,20));
				  
				  ruleLabel.setHorizontalAlignment(JLabel.CENTER);
				  ruleLabel.setText("RULES");
				  
				  ruleText.setLineWrap(true);
				  ruleText.setWrapStyleWord(true);
				  ruleText.setText("To start the game, click on cells to populate them, then click start\n\nIf a cell has less than 2 neighbors, or more than 3, the cell dies\n\nIf a cell has 2 or 3 members, it survives\n\nIf an empty area has 3 neighbors, it becomes populated");
				  
				  tempPanel.setSize(new Dimension(500,500));
				  tempPanel.add(ruleLabel);
				  tempPanel.add(ruleText);
				  temp.add(tempPanel);
				  temp.setVisible(true);
				   
			   }
			});
		
		/**
		 * Defines the dimensions and font of the buttons, then adds them to the JPanel
		 */
		generations.setBounds(600, 785, 350, 50);
		start.setBounds(250,750,150,50);
		pause.setBounds(425,750,150,50);
		reset.setBounds(250,825,150,50);
		clear.setBounds(425,825,150,50);
		rules.setBounds(75,785,150,50);
		
		start.setFont(new Font("Courier", Font.BOLD,20));
		pause.setFont(new Font("Courier", Font.BOLD,20));
		reset.setFont(new Font("Courier", Font.BOLD,20));
		clear.setFont(new Font("Courier", Font.BOLD,20));
		rules.setFont(new Font("Courier", Font.BOLD,20));
		
		
		panel.add(new DrawGrid(),JLayeredPane.PALETTE_LAYER);
		
		panel.add(start,JLayeredPane.DEFAULT_LAYER);
		panel.add(pause,JLayeredPane.DEFAULT_LAYER);
		panel.add(reset,JLayeredPane.DEFAULT_LAYER);
		panel.add(clear,JLayeredPane.DEFAULT_LAYER);
		panel.add(rules,JLayeredPane.DEFAULT_LAYER);
		panel.add(generations,JLayeredPane.DEFAULT_LAYER);
		
		panel.setBackground(Color.WHITE);
		lifeBoard.add(panel);
		lifeBoard.revalidate();
		lifeBoard.repaint();
		//lifeBoard.pack();
		lifeBoard.setVisible(true);
		
		
		/**
		 * Game Handler
		 */
		while(true) {
			
			while(!doUpdate) {
				Thread.sleep(10);
			}
			while(doUpdate) {
				//scheduler.schedule(new Thread(()->update()), 500, TimeUnit.MILLISECONDS);
				
				update();
				
				Thread.sleep(250);
			}
			lifeBoard.revalidate();
			lifeBoard.repaint();
			
		}
		
	}
	
	/**
	 * Updates the game with these rules:
	 * 
	 * If a cell has 1 or less neighbors, or 4 or more neighbors, it dies
	 * A cell with 2 or 3 neighbors lives
	 * If an unpopulated area has exactly 3 cells nearby, it becomes populated
	 */
	public static void update() {

		byte b = 0;
		 
		for(Pixel[] pixelArray : pixelGrid) {
			for(Pixel pixel : pixelArray) {
			
				b = amountOfNeighbors(pixel);
				if(pixel.isAlive()) {
				if(b == -1) {
					pixel.setMarkedForDeath(true);
					pixel.setStatusShouldChange(true);
					break;
					}
				if(b < 2) {
					pixel.setStatusShouldChange(true);
					}
				if(b > 3) {
					pixel.setStatusShouldChange(true);
					}
				
				}
				else if(!pixel.isAlive()) {
					if(b == 3) {
					pixel.setStatusShouldChange(true);
					
					}
					
				}	
			
			}
			
		}
		for(Pixel[] pixelArray2 : pixelGrid) {
			for(Pixel pixel2 : pixelArray2) {
			
				
				if(pixel2.statusShouldChange()) {
					pixel2.swapStatus();
					pixel2.setStatusShouldChange(false);
				}
				if(pixel2.isMarked()) {
					pixel2.setStatus(dead);
				}
				
			}
			
		}
		//System.out.println(amtAlive);
		
		numGens++;
		generations.setText("Generation: " + numGens.toString());
		lifeBoard.revalidate();
		lifeBoard.repaint();
		//if(amtAlive <= 0)
			//doUpdate = false;
		
	}
	/**
	 * 
	 * @param p the Pixel that is being checked
	 * @return a byte that is the amount of neighbors the Pixel has, or -1 if the Pixel is at the edge of the board
	 */
	private static byte amountOfNeighbors(Pixel p) {
		
		byte n = 0;
		int row = p.getRow();
		int col = p.getColumn();
		
		if(row-1 < 0 || col-1 < 0 || row+1 > pixelGrid.length || col+1 > pixelGrid[0].length) {
			return -1;
		}
			
			//top left
			if(row-1 >= 0 && col-1 >= 0) {	
				if(pixelGrid[row-1][col-1].isAlive())
				n++;	
			}
			
			//top center
			if(col-1 >= 0) {
				if(pixelGrid[row][col-1].isAlive())
					n++;	
			}
			
			//top right
			if(row+1 < pixelGrid.length && col-1 >= 0) {
				if(pixelGrid[row+1][col-1].isAlive())
					n++;	
			}
			
			//center left
			if(row-1 >= 0) {
				if(pixelGrid[row-1][col].isAlive())
					n++;	
			}
			
			//center right
			if(row+1 < pixelGrid.length) {
				if(pixelGrid[row+1][col].isAlive())
					n++;	
			}
			
			//bottom left
			if(row-1 >= 0 && col+1 < pixelGrid[0].length) {	
				if(pixelGrid[row-1][col+1].isAlive())
				n++;	
			}
			
			//bottom center
			if(col+1 < pixelGrid[0].length) {
				if(pixelGrid[row][col+1].isAlive())
					n++;	
			}
			
			//bottom right
			if(row+1 < pixelGrid.length && col+1 < pixelGrid[0].length) {
				if(pixelGrid[row+1][col+1].isAlive())
					n++;	
			}
			
		return n;	

		
	}

}
