package JSketch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

//import JSketch.Model.StrokeType;
import JSketch.Model.Tool;


@SuppressWarnings("serial")
public class View2 extends JPanel implements Observer {
	private Model model;
	public final static int drawWidth = 800;
	public final static int drawHeight = 600;
	public BufferedImage image;
	public Tool curTool;
	public Color curColor;
	//public StrokeType curType;
	public int curWidth;
	public Line2D shape1;
	public Rectangle2D shape2;
	public Ellipse2D shape3;
	public Shape tempShape;
	//public Rectangle2D handle;
	public boolean selected;
	public int isLine;
	public ArrayList<Shape> shapePart = new ArrayList<Shape>();
	public ArrayList<Color> shapeColor = new ArrayList<Color>();
	public ArrayList<Color> shapeFill = new ArrayList<Color>();
	public ArrayList<Integer> shapeWidth = new ArrayList<Integer>();
	public ArrayList<Integer> shapeLine = new ArrayList<Integer>();
	double x1, y1, x2, y2;
	double rx, ry, rw, rh, rx2, ry2;
	public boolean tobeDrag;
	public int tempIndex;
	public int tempWidth;
	public Color tempColor;
	MyKeyListener kl;
	
	View2(Model model_) {
		this.model = model_;
		this.image = new BufferedImage(drawWidth, drawHeight, BufferedImage.TYPE_INT_ARGB);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		this.setVisible(true);
		//this.curType = model.curType;
		this.curColor = model.curColor;
		this.curTool = model.curTool;
		this.curWidth = model.curWidth;
		this.shape1 = null;
		this.shape2 = null;
		this.shape3 = null;
		//this.handle = null;
		this.tempShape = null;
		this.selected = false;
		this.tobeDrag = false;
		this.tempIndex = -1;
		this.kl = new MyKeyListener();
		shapePart.clear();
		shapeColor.clear();
		shapeFill.clear();
		shapeWidth.clear();
		shapeLine.clear();
		
		MyMouseListener ml = new MyMouseListener();
		addKeyListener(kl);
		addMouseListener(ml);
		addMouseMotionListener(ml);
		
		//this.setFocusable(true);
		//this.requestFocus();
		//this.requestFocusInWindow();
	}
	
	public void addRectangle(Rectangle2D r) {
		shapePart.add(r);
		shapeColor.add(model.curColor);
		shapeWidth.add(model.curWidth);
		shapeFill.add(Color.WHITE);
		shapeLine.add(2);
	}

	public void delete(int i) {
		shapePart.remove(i);
		shapeColor.remove(i);
		shapeWidth.remove(i);
		shapeFill.remove(i);
		shapeLine.remove(i);
		repaint();
	}
	
	public void fill(int i) {
		shapeFill.set(i, model.curColor);
		repaint();
	}
	public void select(Shape s, int i) {
		if (shapeFill.get(i) == Color.WHITE) {
			model.setColor(shapeColor.get(i));
		} else {
			model.setColor(shapeFill.get(i));
		}
		model.setWidth(shapeWidth.get(i));
		
		model.setHandle(s.getBounds2D());
		tempColor = model.curColor;
		tempWidth = model.curWidth;
		if (selected) {
			System.out.println("xin: " + tempWidth);
		}
	}
	public void addLine(Line2D l) {
		shapePart.add(l);
		shapeColor.add(model.curColor);
		shapeWidth.add(model.curWidth);
		shapeFill.add(Color.WHITE);
		shapeLine.add(1);
	}

	public void addCircle(Ellipse2D e) {
		shapePart.add(e);
		shapeColor.add(model.curColor);
		shapeWidth.add(model.curWidth);
		shapeFill.add(Color.WHITE);
		shapeLine.add(3);
	}

	public void clear()
	{
		createEmptyImage();
		repaint();
	}

	private void createEmptyImage()
	{
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		shapePart.clear();
		shapeColor.clear();
		shapeFill.clear();
		shapeWidth.clear();
		shapeLine.clear();
		shape1 = null;
		shape2 = null;
		shape3 = null;
		tempShape = null;
		
		model.setHandle(null);
		g2.setColor(model.curColor);
		g2.setStroke(new BasicStroke(model.curWidth,1,0));
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		//Graphics2D g2d = (Graphics2D)g;
		Graphics2D g2dd = (Graphics2D)image.getGraphics();
		
		System.out.println(shapePart.size());
		for (Shape i : shapePart) {
			int index = shapePart.indexOf(i);
			//if (index == tempIndex && tobeDrag) {
			//	continue;
			//}
			//System.out.println("x1:" + e.getX() + " y1:" + e.getY() + " index:" + index);
			//g2d.setStroke(new BasicStroke(shapeWidth.get(index),1,0));
			g2dd.setStroke(new BasicStroke(shapeWidth.get(index),1,0));
			if (shapeFill.get(index) != Color.WHITE) {
				//g2d.setColor(shapeFill.get(index));
				g2dd.setColor(shapeFill.get(index));
				//g2d.fill(i);
				g2dd.fill(i);
			}
			//g2d.setColor(shapeColor.get(index));
			g2dd.setColor(shapeColor.get(index));
			//g2d.draw(i);
			g2dd.draw(i);
		}
		
		if (model.curHandle != null) {
			if (tobeDrag) {
				model.setHandle(new Rectangle2D.Double(rx, ry, rw, rh));
	        	if (isLine == 1) {
	        		tempShape = new Line2D.Double(rx, ry, rx2, ry2);
	        	} else if (isLine == 2){
	        		tempShape = new Rectangle2D.Double(rx, ry, rw, rh);
	        	} else {
	        		tempShape = new Ellipse2D.Double(rx, ry, rw, rw);
	        	}
	        	g2dd.draw(tempShape);
	        }
	        drawHandle(g2dd, model.curHandle);
	    }
		
		if (shape1 != null) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(model.curColor);
			g2.setStroke(new BasicStroke(model.curWidth,1,0));
			g2.draw(shape1);
			
		}
		if (shape2 != null) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(model.curColor);
			g2.setStroke(new BasicStroke(model.curWidth,1,0));
			g2.draw(shape2);
			
		}
		
		if (shape3 != null) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(model.curColor);
			g2.setStroke(new BasicStroke(model.curWidth,1,0));
			g2.draw(shape3);
			
		}
		
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	public void save() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(0);
        fileChooser.showSaveDialog(this);
        File file = fileChooser.getSelectedFile();
        //FileWriter writer = new FileWriter(file + ".png");
        FileOutputStream fos = new FileOutputStream(file + ".png.io");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        file = new File(file + ".png");
		ImageIO.write(image, "PNG", file);
		oos.writeObject(shapePart);
		oos.writeObject(shapeColor);
		oos.writeObject(shapeFill);
		oos.writeObject(shapeWidth);
		oos.writeObject(model.curColor);
		oos.writeObject(model.curWidth);
		oos.writeObject(model.curTool);
		oos.close();
		fos.close();
	}
	
	@SuppressWarnings("unchecked")
	public void load() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setCurrentDirectory(new File("/User/KuiLikMi/Desktop"));
		fileChooser.showOpenDialog(this);
		File file = fileChooser.getSelectedFile();
		FileInputStream fis = new FileInputStream(file + ".io");
		ObjectInputStream ois = new ObjectInputStream(fis);
		//String name = file.getName();
		clear();
		image = ImageIO.read(file);
		try {
			shapePart = (ArrayList<Shape>) ois.readObject();
			shapeColor = (ArrayList<Color>) ois.readObject();
			shapeFill = (ArrayList<Color>) ois.readObject();
			shapeWidth = (ArrayList<Integer>) ois.readObject();
			model.setColor((Color) ois.readObject());
			model.setWidth((int) ois.readObject());
			model.setTool((Tool) ois.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Not find the object");
		}
		ois.close();
		fis.close();
		repaint();
	}
	
	public void drawHandle(Graphics2D g2d, Rectangle2D r) {
		double x = r.getX();
		double y = r.getY();
	    double w = r.getWidth();
	    double h = r.getHeight();
	    g2d.setColor(Color.BLACK);

	    g2d.fill(new Rectangle.Double(x - 3.0, y - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x + w - 3.0, y - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x + w - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x - 3.0, y + h - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y + h - 3.0, 6.0, 6.0));
	    g2d.fill(new Rectangle.Double(x + w - 3.0, y + h - 3.0, 6.0, 6.0));
	}
	class MyMouseListener extends MouseAdapter {
		private Point startPoint;
		
		public void mousePressed(MouseEvent e) {
			startPoint = e.getPoint();
			
			if (model.curTool == Tool.LINE) {
				shape1 = new Line2D.Double();
			}
			
			if (model.curTool == Tool.RECTANGLE) {
				shape2 = new Rectangle2D.Double();
			}
			
			if (model.curTool == Tool.CIRCLE) {
				shape3 = new Ellipse2D.Double();
			}
			
			if (selected && model.curTool == Tool.SELECTOR && model.curHandle != null) {
				x1 = e.getX() - 1;
				y1 = e.getY() - 1;
				tobeDrag = true;
				
				for (Shape i : shapePart) {
					int boxX = e.getX() - 1;
					int boxY = e.getY() - 1;
					if (i.intersects(boxX, boxY, 2, 2)) {
						int index = shapePart.indexOf(i);
						selected = true;
						select(i, index);
						tempIndex = index;
				tempShape = i;
				isLine = shapeLine.get(index);
				if (isLine == 1) {
					rx = ((Line2D)tempShape).getX1();
					ry = ((Line2D)tempShape).getY1();
					rx2 = ((Line2D)tempShape).getX2();
					ry2 = ((Line2D)tempShape).getY2();
					rw =  model.curHandle.getWidth();
					rh = model.curHandle.getHeight();
				} else if (isLine == 2) {
					rx = ((Rectangle2D)tempShape).getX();
					ry = ((Rectangle2D)tempShape).getY();
					rw = ((Rectangle2D)tempShape).getWidth();
					rh = ((Rectangle2D)tempShape).getHeight();
				} else {
					rx = ((Ellipse2D)tempShape).getX();
					ry = ((Ellipse2D)tempShape).getY();
					rw = ((Ellipse2D)tempShape).getWidth();
					rh = ((Ellipse2D)tempShape).getHeight();
				}
				}
				}
			}
		}
		
		public void mouseClicked(MouseEvent e) {
			if (model.curTool == Tool.ERASER) {
				int boxX = e.getX() - 1;
				int boxY = e.getY() - 1;
				for (int i = shapePart.size() - 1; i >= 0; i--) {
					if (shapePart.get(i).intersects(boxX, boxY, 2, 2)) {
						//int index = shape2Part.indexOf(i);
						//System.out.println("x1:" + e.getX() + " y1:" + e.getY() + " index:" + i);
						delete(i);
						break;
					}
				}
			}
			
			if (model.curTool == Tool.FILLER) {
				int boxX = e.getX() - 1;
				int boxY = e.getY() - 1;
				for (Shape i : shapePart) {
					if (i.intersects(boxX, boxY, 2, 2)) {
						int index = shapePart.indexOf(i);
						fill(index);
					}
				}
			}
			
			if (model.curTool == Tool.SELECTOR) {
				for (Shape i : shapePart) {
					int boxX = e.getX() - 1;
					int boxY = e.getY() - 1;
					if (i.intersects(boxX, boxY, 2, 2)) {
						int index = shapePart.indexOf(i);
						selected = true;
						select(i, index);
						tempIndex = index;
					}
				}
				addKeyListener(kl);
				addNotify();
			}
			repaint();
		}
		
		public void mouseDragged(MouseEvent e) {
			int x = Math.min(startPoint.x, e.getX());
			int y = Math.min(startPoint.y, e.getY());
			int width = Math.abs(startPoint.x - e.getX());
			int height = Math.abs(startPoint.y - e.getY());

			if (model.curTool == Tool.LINE) {
				shape1.setLine(startPoint.x ,startPoint.y, e.getX(), e.getY());
			}
			
			if (model.curTool == Tool.RECTANGLE) {
				shape2.setFrame(x, y, width, height);
				System.out.println("x:" + x + " y:" + y + " width:" + width + " height:" + height);
				System.out.println("sx:" + shape2.getX() + " sy:" + shape2.getY() + " swidth:" + shape2.getWidth() + " sheight:" + shape2.getHeight());
			}
			
			if (model.curTool == Tool.CIRCLE) {
				shape3.setFrame(x, y, width, width);
			}
			
			if (selected && model.curTool == Tool.SELECTOR && model.curHandle != null) {
				int boxX = e.getX() - 1;
				int boxY = e.getY() - 1;
				if (tempShape.intersects(boxX, boxY, 2, 2)) {
					if (isLine == 1) {
						x2 = boxX;
						y2 = boxY;
						rx += (x2 - x1);
						ry += (y2 - y2);
						rx2 += (x2 - x1);
						ry2 += (y2 - y1);
						x1 = x2;
						y1 = y2;
					} else {
					x2 = boxX;
					y2 = boxY;
					
					rx += (x2 - x1);
					ry += (y2 - y1);
					
					x1 = x2;
					y1 = y2;
					}
				}
			}
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			if (model.curTool == Tool.LINE) {
				if (shape1.getX1() != shape1.getX2() || shape1.getY1() != shape1.getY2()) {
					addLine(shape1);
				}
				shape1 = null;
				//repaint();
			}
			
			if (model.curTool == Tool.RECTANGLE) {
				if (shape2.getWidth() != 0 || shape2.getHeight() != 0) {
					addRectangle(shape2);
					System.out.println("size: " + shapePart.size());
					//System.out.println("x:" + ((Rectangle2D) shapePart.get(0)).getX() + " y:" + ((Rectangle2D) shapePart.get(0)).getY() + " width:" + ((Rectangle2D) shapePart.get(0)).getWidth() + " height:" + ((Rectangle2D) shapePart.get(0)).getHeight());
				}
				shape2 = null;
				//repaint();
			}
			
			if (model.curTool == Tool.CIRCLE) {
				if (shape3.getWidth() != 0 || shape3.getHeight() != 0) {
					addCircle(shape3);
				}
				shape3 = null;
				//repaint();
			}
			
			if (selected && model.curTool == Tool.SELECTOR && model.curHandle != null) {
				System.out.println("after index:" + tempIndex);
				shapePart.set(tempIndex, tempShape);
				tempIndex = -1;
				tempShape = null;
				tobeDrag = false;
				addKeyListener(kl);
				addNotify();
			}
			repaint();
		}
	}
	
	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int i = e.getKeyCode();
			System.out.println("daole");
			
			if (selected && model.curTool == Tool.SELECTOR && i == KeyEvent.VK_ESCAPE) {
				setSelected(false);
				model.setHandle(null);
				repaint();
			}
		}
	}
	
	public void setSelected(boolean b) {
		this.selected = b;
		model.setColor(tempColor);
		model.setWidth(tempWidth);
		//this.handle = null;
	}
	@Override
	public void addNotify() {
		super.addNotify();
		setFocusable(true);
		requestFocus();
		//requestFocusInWindow();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		repaint();
	}
}
