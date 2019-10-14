package BrickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private boolean play=false;
	private int score=0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map;
	private int win=900;
	
	public Gameplay() {
		map=new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start(); 
	}
	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		// border
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(0,0,3,592);
		
		// scores
		g.setColor(Color.yellow);
		g.drawString("Your Score: "+score, 590, 40);
		g.drawString("You Won", win, win);
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		g.dispose();
		
		
			
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir=-ballYdir;
			}
			
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickwidth+80;
						int brickY=i*map.brickheight+50;
						int brickwidth = map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect=new Rectangle(brickX, brickY, brickwidth, brickheight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width) {
								ballXdir=-ballXdir;
							}
							else
							{
								ballYdir=-ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0) {
				ballXdir=-ballXdir;
			}
			if(ballposY<0) {
				ballYdir=-ballYdir;
			}
			if(ballposX>670) {
				ballXdir=-ballXdir;
			}
		}
		if(score==105)
			{
			win=300;
			ballposY=1000000;
			}
		
		repaint();
			
	}
		
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600)
				playerX=600;
			else
				moveRight();
		}
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
        	if(playerX<10)
				playerX=10;
			else
				moveLeft();
		}
	}

	public void moveRight() {
		play = true;
		playerX+=20;
		
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
		
	}

}
