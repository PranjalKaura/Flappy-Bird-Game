

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;

public class FlappyBird implements ActionListener,MouseListener,KeyListener{
	private static int Height=800, Width=800,score=0;
	public static FlappyBird flappyBird=null;
	public Renderer renderer;
	public Rectangle bird;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public int ticks,ymotion;
	public boolean gameOver=false,started=false;
	
	public FlappyBird() {
		System.out.println("enter constructor ");
		JFrame frame=new JFrame();
		columns=new ArrayList<Rectangle>();
		frame.setTitle("Flappy Bird");
		Timer timer=new Timer(20,this);
		renderer=new Renderer();
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Height,Width);
		bird=new Rectangle(Height/2 - 10 , Width/2 -10, 20, 20);
		frame.add(renderer);
		rand=new Random();
		frame.setVisible(true);
		frame.setResizable(false);
		timer.start();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		
		
	}
	public void addColumn(boolean start) {
		int space=300;
		int width=100;
		int height=50+rand.nextInt(300);
		if(start) {
		columns.add(new Rectangle(Width + width + columns.size()*300, Height-height-120, width, height));
		columns.add(new Rectangle(Width + width + (columns.size()-1)*300, 0, width, Height-height-space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600, Height-height-120, width, height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, width, Height-height-space));
		}
		
		
	}
	
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ticks++;
		int speed=10;
		if(started) {
		for(int u=0;u<columns.size();u++) {
			columns.get(u).x-=speed;
		}
		if(ticks%2==0 && ymotion<15) {
			ymotion+=2;
		}
		for(int u=0;u<columns.size();u++) {
			
			if(columns.get(u).x + columns.get(u).width<0) {
				if(columns.get(u).y==0) {
					addColumn(false);
				}
				columns.remove(u);
				
			}
			
		}
		bird.y+=ymotion;
		
		for(Rectangle column:columns) {
			if(column.y==0 && bird.x + bird.width/2>column.x+ column.width/2 -speed && bird.x + bird.width/2<column.x+ column.width/2 + speed ) {
				score++;
			}
			
			if(column.intersects(bird)) {
				gameOver=true;
				bird.x=column.x-bird.width;
			}
		}
		if(bird.y>Height-120 || bird.y<0) {
			gameOver=true;
		}
		if(gameOver) {
			bird.y=Height-120-bird.height;
		}
		renderer.repaint();
		}
		
	}
	
	
	public void repaint(Graphics g) {
		System.out.println("enter repaint");
		g.setColor(Color.cyan);
		g.fillRect(0, 0	, Width, Height);
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		g.setColor(Color.orange);
		g.fillRect(0, Height-120, Width ,120);
		g.setColor(Color.GREEN);
		g.fillRect(0, Height-120, Width ,20);
		for(Rectangle column:columns) {
			paintColumn(g, column);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 80));
		if(!gameOver && !started) {
			g.drawString("Click to Start", 60, Height/2-50);
		}
		if(gameOver) {
			g.drawString("GameOverSucka!", 60, Height/2+50);
			
			g.drawString("Your Score: " + score, 100, Height/2+130);
		}
		if(!gameOver) {
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Score: " + score, Width-300, 100);
		}
		
		
		
	}
	public void jump() {
		if(gameOver) {
			bird=new Rectangle(Height/2 - 10 , Width/2 -10, 20, 20);
			columns.clear();
			ymotion=0;
			score=0;
			gameOver=false;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
		}
		if(!started) {
			started=true;
					
		}
		else if(started && !gameOver) {
			if(ymotion>0) {
				ymotion=0;
				
			}
			ymotion-=10;
		}
	}

	public static void main(String[] args) {
	 flappyBird=new FlappyBird();
		

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			jump();
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
