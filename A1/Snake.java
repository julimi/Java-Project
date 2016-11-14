import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {
	public static Snake snake;
	public JFrame jframe;
	public View view;
	public Timer timer;
	public ArrayList<Point> snakeBody = new ArrayList<Point>();
	public ArrayList<Point> wall = new ArrayList<Point>();
	public static final int up = 0, down = 1, left = 2, right = 3;
	public int frameRate, dir, snakeLength, score, nextTick, speed;
	public Point head, fruit;
	public Random random;
	public boolean end, first, pause, level;
	
	public Snake(int f, int s) {	
		speed = s;
		frameRate = f;
		jframe = new JFrame("Snake");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(800, 600);
		jframe.setResizable(false);
		jframe.add(view = new View());
		jframe.addKeyListener(this);
		beforeStart();
	}
     
	public void beforeStart() {
		end = true;
		first = true;
		level = false;
	}
	public void Start() {
		end = false;
		pause = false;
		first = false;
		snakeLength = 15;
		nextTick = 0;
		score = 0;
		dir = -1; // fix the head at the beginning
		head = new Point(12, 21);
		snakeBody.clear();
		wall.clear();
		
		// Hard level
		if (level) {
			for (int i = 10; i < 16; i++) {
				wall.add(new Point(10, i));
				wall.add(new Point(70, i));
				wall.add(new Point(i, 10));
				wall.add(new Point(i, 49));
			}
			for (int i = 70; i > 64; i--) {
				wall.add(new Point(i, 10));
				wall.add(new Point(i, 49));
			}
			for (int i = 49; i > 43; i--) {
				wall.add(new Point(70, i));
				wall.add(new Point(10, i));
			}
			for (int i = 14; i < 45; i++) {
				wall.add(new Point(72, i));
			}
			for (int i = 13; i < 35; i++) {
				wall.add(new Point(i, 52));
			}
			for (int i = 37; i < 48; i++) {
				wall.add(new Point(i, 55));
			}
			wall.add(new Point(23, 20));
			wall.add(new Point(23, 21));
			wall.add(new Point(23, 22));
			wall.add(new Point(24, 20));
			wall.add(new Point(24, 21));
			wall.add(new Point(24, 22));
			wall.add(new Point(25, 20));
			wall.add(new Point(25, 21));
			wall.add(new Point(25, 22));
			wall.add(new Point(56, 20));
			wall.add(new Point(56, 21));
			wall.add(new Point(56, 22));
			wall.add(new Point(57, 20));
			wall.add(new Point(57, 21));
			wall.add(new Point(57, 22));
			wall.add(new Point(58, 20));
			wall.add(new Point(58, 21));
			wall.add(new Point(58, 22));
			wall.add(new Point(23, 40));
			wall.add(new Point(23, 41));
			wall.add(new Point(23, 42));
			wall.add(new Point(24, 40));
			wall.add(new Point(24, 41));
			wall.add(new Point(24, 42));
			wall.add(new Point(25, 40));
			wall.add(new Point(25, 41));
			wall.add(new Point(25, 42));
			wall.add(new Point(56, 40));
			wall.add(new Point(56, 41));
			wall.add(new Point(56, 42));
			wall.add(new Point(57, 40));
			wall.add(new Point(57, 41));
			wall.add(new Point(57, 42));
			wall.add(new Point(58, 40));
			wall.add(new Point(58, 41));
			wall.add(new Point(58, 42));
			for (int i = 13; i < 65; i++) {
				wall.add(new Point(i, 6));
			}
			wall.add(new Point(13, 7));
			wall.add(new Point(13, 8));
			wall.add(new Point(64, 7));
			wall.add(new Point(64, 8));
		}
		
		random = new Random();
		fruit = new Point(random.nextInt(80), random.nextInt(58));
		while ((fruit.x == head.x && fruit.y == head.y) || (level && hitWall(fruit.x, fruit.y))) {
			fruit.setLocation(new Point(random.nextInt(80), random.nextInt(58)));
		}
		timer = new Timer(frameRate, this);
		timer.start();
	}
    
	// restrict the snake eat self
	public boolean eatSelf(int x, int y) {
		for (Point i : snakeBody) {
			if (i.x == x && i.y == y) {
				return true;
			}
		}
		return false;
	}
	
	// restrict the snake hit the wall
	public boolean hitWall(int x, int y) {
		for (Point i : wall) {
			if (i.x == x && i.y == y) {
				return true;
			}
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent args) {
		// System.out.println(head.x + ", " + head.y);
		
		if (head != null && nextTick % (11 - speed) == 0 && !end && !pause) {
			// go forward
			snakeBody.add(new Point(head.x, head.y));
			
			// hard level
			if (level) {
				if (dir == up) {
					if (head.y - 1 >= 0 && !eatSelf(head.x, head.y - 1) && !hitWall(head.x, head.y - 1)) {
						head = new Point(head.x, head.y - 1);
					} else {
						end = true;
					}
				}
				if (dir == down) {
					if (head.y + 1 < 58 && !eatSelf(head.x, head.y + 1) && !hitWall(head.x, head.y + 1)) {
						head = new Point(head.x, head.y + 1);
					} else {
						end = true;
					}
				}
				if (dir == left) {
					if (head.x - 1 >= 0 && !eatSelf(head.x - 1, head.y) && !hitWall(head.x - 1, head.y)) {
						head = new Point(head.x - 1, head.y);
					} else {
						end = true;
					}
				}
				if (dir == right) {
					if (head.x + 1 < 80 && !eatSelf(head.x + 1, head.y) && !hitWall(head.x + 1, head.y)) {
						head = new Point(head.x + 1, head.y);
					} else {
						end = true;
					}
				}
			}
			// easy level
			else {
				if (dir == up) {
					if (head.y - 1 >= 0 && !eatSelf(head.x, head.y - 1)) {
						head = new Point(head.x, head.y - 1);
					} else {
						end = true;
					}
				}
				if (dir == down) {
					if (head.y + 1 < 58 && !eatSelf(head.x, head.y + 1)) {
						head = new Point(head.x, head.y + 1);
					} else {
						end = true;
					}
				}
				if (dir == left) {
					if (head.x - 1 >= 0 && !eatSelf(head.x - 1, head.y)) {
						head = new Point(head.x - 1, head.y);
					} else {
						end = true;
					}
				}
				if (dir == right) {
					if (head.x + 1 < 80 && !eatSelf(head.x + 1, head.y)) {
						head = new Point(head.x + 1, head.y);
					} else {
						end = true;
					}
				}
			}
			
		}
		
		// end
		if (end) {
			timer.stop();
		}
		
		// eat fruit
		if (fruit != null && (fruit.x == head.x && fruit.y == head.y)) {
			score += 100;
			snakeLength += 3;
			fruit.setLocation(new Point(random.nextInt(80), random.nextInt(58)));
			while ((fruit.x == head.x && fruit.y == head.y) || (level && hitWall(fruit.x, fruit.y)) || eatSelf(fruit.x, fruit.y)) {
				fruit.setLocation(new Point(random.nextInt(80), random.nextInt(58)));
			}
		}
		
		// cut it as a standard snake
		if (snakeBody.size() > snakeLength) {
			snakeBody.remove(0);
		}
		
		view.repaint();
		nextTick++;
	}
    
	// key control
	public void keyPressed(KeyEvent e) {
		// get the input key
		int i = e.getKeyCode();
        
		// direction
		if ((i == KeyEvent.VK_UP || i == KeyEvent.VK_W) && dir != down && !pause) {
			dir = up;
		}
		if ((i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S) && dir != up && !pause) {
			dir = down;
		}
		if ((i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A) && dir != right && !pause) {
			dir = left;
		}
		if ((i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D) && dir != left && !pause) {
			dir = right;
		}
		
		// start in different levels
		if (i == KeyEvent.VK_E) {
			if (end) {
				level = false;
				Start();
			}
		}
		if (i == KeyEvent.VK_H) {
			if (end) {
				level = true;
				Start();
			}
		}
        
		// pause
		if (i == KeyEvent.VK_SPACE) {
			if (pause) pause = false;
			else pause = true;
		}
		
		// exit
		if (i == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
	}

	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	public static void main(String[] args) {
		int f,s;
		if (args.length != 2) {
			f = 25;
			s = 8;
		} else {
		
			f = Integer.parseInt(args[0]);
			s = Integer.parseInt(args[1]);
		}
		//int f = 25, s = 2;
		snake = new Snake(f,s);
	}
}
