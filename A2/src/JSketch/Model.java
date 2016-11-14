package JSketch;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Observable;

import javax.swing.JPanel;

public class Model extends Observable {
	JPanel ColorChooser = new JPanel();
	public Color curColor;
	// tool type
	public enum Tool {
		SELECTOR,
		ERASER,
		LINE,
		CIRCLE,
		RECTANGLE,
		FILLER;
		
		
	}
	
	public Tool curTool;
	
	public int curWidth;
	public boolean curSelected;
	public Rectangle2D curHandle;
	public Color tempColor;
	public int tempWidth;
	//public Cursor curCursor;
	
	public void setSelected(boolean b) {
		this.curSelected = b;
		//this.setColor(tempColor);
		//this.setWidth(tempWidth);
		setChanged();
		notifyObservers();
	}
	public void settempColor(Color color) {
		this.tempColor = color;
		//View.setShowColor(color);
		setChanged();
		notifyObservers();
	}
	public void settempWidth(int width) {
		this.tempWidth = width;
		/*if (width == 1) {
			View.setShowWidth(0);
		} else if (width == 4) {
			View.setShowWidth(1);
		} else if (width == 6) {
			View.setShowWidth(2);
		} else {
			View.setShowWidth(3);
		}*/
		setChanged();
		notifyObservers();
	}
	public void setHandle(Rectangle2D r) {
		this.curHandle = r;
		//View2.repaint();
		setChanged();
		notifyObservers();
	}
	public void setColor(Color color) {
		this.curColor = color;
		View.setShowColor(color);
		setChanged();
		notifyObservers();
	}
	public void setWidth(int width) {
		this.curWidth = width;
		if (width == 1) {
			View.setShowWidth(0);
		} else if (width == 4) {
			View.setShowWidth(1);
		} else if (width == 6) {
			View.setShowWidth(2);
		} else {
			View.setShowWidth(3);
		}
		setChanged();
		notifyObservers();
	}
	public void setTool(Tool tool) {
		System.out.println("Tool:" + curTool);
		this.curTool = tool;
		if (tool == Tool.SELECTOR) {
			View.setShowTool("Selector");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		} else if (tool == Tool.ERASER) {
			View.setShowTool("Eraser");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (tool == Tool.LINE) {
			View.setShowTool("Line");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (tool == Tool.CIRCLE) {
			View.setShowTool("Circle");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (tool == Tool.RECTANGLE) {
			View.setShowTool("Rectangle");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else {
			View.setShowTool("Filler");
			//this.curCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		}
		System.out.println("After Tool:" + curTool);
		setChanged();
		//hasChanged();
		notifyObservers();
	}

	
	Model(){
		setChanged();
		
		this.curColor = Color.BLACK;
		//this.curType = StrokeType.LINE;
		this.curTool = Tool.LINE;
		this.curWidth = 1;
		this.curSelected = false;
		this.curHandle = null;
	}
	
}
