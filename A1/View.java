import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class View extends JPanel {
	public void paintComponent(Graphics g) {
		// paint the specific part of component
		super.paintComponent(g);
		g.clearRect(0, 0, 800, 600);
		Snake snake = Snake.snake;
		String string;
        
		// The first start
		if (snake.end && snake.first) {
			// set the background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			// set the text
			g.setFont(new Font("TimesRoman", Font.BOLD, 34));
			g.setColor(new Color(16763904));
			string = "Name: Weifeng Jiang";
			g.drawString(string, 40, 40);
			string = "Userid: w48jiang";
			g.drawString(string, 40, 80);
			string = "How To Play:";
			g.drawString(string, 40, 150);
			g.setFont(new Font("TimesRoman", Font.BOLD, 36));
			string = "Up: \u2191 or W";
			g.drawString(string, 60, 190);
			string = "Down: \u2193 or S";
			g.drawString(string, 360, 190);
			string = "Left: \u2190 or A";
			g.drawString(string, 60, 230);
			string = "Right: \u2192 or D";
			g.drawString(string, 360, 230);
			string = "Pause: SPACE";
			g.drawString(string, 60, 270);
			string = "Start in EASY: E";
			g.drawString(string, 60, 400);
			string = "Start in HARD: H";
			g.drawString(string, 60, 450);
			string = "Press \"ESC\" to EXIT!";
			g.drawString(string, 60, 510);
		} 
		// the page of end
		else if (snake.end && !(snake.first)) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			g.setColor(new Color(16763904));
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			string = "Oops! You lose the game!";
			g.drawString(string, 120, 150);
			string = "Your Score: " + snake.score;
			g.drawString(string, 180, 210);
			string = "Restart in EASY: Press E";
			g.drawString(string, 120, 400);
			string = "Restart in HARD: Press H";
			g.drawString(string, 120, 440);
			string = "Press \"ESC\" to EXIT!";
			g.drawString(string, 120, 500);
		} 
		// the page of running
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			// paint the body of snake
			for (Point i : snake.snakeBody) {
				g.setColor(Color.WHITE);
				g.fillRect(i.x * 10, i.y * 10, 10, 10);
				g.setColor(new Color(26112));
				g.fillRect(i.x * 10, i.y * 10, 9, 9);
			}

			g.setColor(new Color(13408512));
			// paint the point of snake head
			g.fillRect(snake.head.x * 10, snake.head.y * 10, 10, 10);
			
			// paint the fruit
			g.setColor(Color.RED);
			g.fillRect(snake.fruit.x * 10, snake.fruit.y * 10, 10, 10);
			
			// paint the wall in hard level
			if (snake.level) {
				g.setColor(Color.GRAY);
				for (Point i : snake.wall) {
					g.fillRect(i.x * 10, i.y * 10, 10, 10);
				}
			}
			
			// paint the record
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			string = "SCORE: " + snake.score;
			g.drawString(string, 0, 575);
			string = "SPEED: " + snake.speed;
			g.drawString(string, 700, 575);
			string = "FPS: " + snake.frameRate;
			g.drawString(string, 700, 555);
			
			// paint the sign of pause
			if (snake.pause) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 34));
				g.setColor(new Color(16763904));
				string = "PAUSE";
				g.drawString(string, 350, 300);
			}
		
		}
	}
}
