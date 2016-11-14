package JSketch;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import JSketch.Model.Tool;
//import JSketch.View2.MyKeyListener;


@SuppressWarnings("serial")
public class View extends JPanel implements Observer {
	private Model model;
	public Tool curTool;
	public static JPanel showColor = new JPanel();
	public static JButton showWidth = new JButton();
	public static JButton showTool = new JButton();
	public static ImageIcon[] icons = new ImageIcon[6];
	public static ImageIcon[] icons1 = new ImageIcon[4];
	public int curWidth;
	public Color curColor;
	Box vBox;
	JPanel toolBox;
	JPanel colorBox;
	JPanel colorChooser;
	JButton jb1;
	JButton jb2;
	JButton jb3;
	JButton jb4;
	JButton jb5;
	JButton jb6;
	JButton jb7;
	Color c1;
	Color c2;
	Color c3;
	Color c4;
	Color c5;
	Color c6;
	
	public static void setShowColor(Color color) {
		showColor.setBackground(color);
	}
	
	public static void setShowWidth(int i) {
		showWidth.setIcon(icons1[i]);
	}
	public static void setShowTool(String i) {
		showTool.setText(i);
	}
	
	View(Model model_) {
		this.model = model_;
		this.curColor = Color.BLACK;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		this.vBox = Box.createVerticalBox();
		
		
		// build the tool palette
		this.toolBox = new JPanel();
		this.toolBox.setLayout(new GridLayout(0,2));
		//ImageIcon[] icons = new ImageIcon[6];
		for (int i = 0; i < 6; i++) {
			icons[i] = new ImageIcon(""+i+".GIF");
		}
		for (int i = 0; i < 6; i++) {
			JToggleButton temp = new JToggleButton(icons[i]);
			if (i == 0) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.SELECTOR);
					}
				});
			}
			if (i == 1) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.ERASER);
						model.setHandle(null);
					}
				});
			}
			if (i == 2) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.LINE);
						model.setHandle(null);
					}
				});
			}
			if (i == 3) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.CIRCLE);
						model.setHandle(null);
					}
				});
			}
			if (i == 4) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.RECTANGLE);
						model.setHandle(null);
					}
				});
			}
			if (i == 5) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setTool(Tool.FILLER);
						model.setHandle(null);
					}
				});
			}
			this.toolBox.add(temp);
		}
		this.vBox.add(this.toolBox);
		
		// build the color palette
		this.colorBox = new JPanel();
		//colorBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.colorBox.setLayout(new GridLayout(0,2));
		//colorBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		// black
		this.jb1 = new JButton();
		this.c1 = Color.BLACK;
		this.jb1.setBackground(c1);
		this.jb1.setOpaque(true);
		this.jb1.setBorderPainted(false);
		this.jb1.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c1);
					model.setColor(c1);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb1);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c1 = color;
					jb1.setBackground(c1);
					showColor.setBackground(c1);
					model.setColor(c1);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb1);
		// red
		this.jb2 = new JButton();
		this.c2 = Color.RED;
		this.jb2.setBackground(c2);
		this.jb2.setOpaque(true);
		this.jb2.setBorderPainted(false);
		this.jb2.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c2);
					model.setColor(c2);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb2);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c2 = color;
					jb2.setBackground(c2);
					showColor.setBackground(c2);
					model.setColor(c2);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb2);
		// green
		this.jb3 = new JButton();
		this.c3 = Color.GREEN;
		this.jb3.setBackground(c3);
		this.jb3.setOpaque(true);
		this.jb3.setBorderPainted(false);
		this.jb3.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c3);
					model.setColor(c3);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb3);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c3 = color;
					jb3.setBackground(c3);
					showColor.setBackground(c3);
					model.setColor(c3);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb3);
		// blue
		this.jb4 = new JButton();
		this.c4 = Color.BLUE;
		this.jb4.setBackground(c4);
		this.jb4.setOpaque(true);
		this.jb4.setBorderPainted(false);
		this.jb4.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c4);
					model.setColor(c4);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb4);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c4 = color;
					jb4.setBackground(c4);
					showColor.setBackground(c4);
					model.setColor(c4);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb4);
		// cyan
		this.jb5 = new JButton();
		c5 = Color.CYAN;
		this.jb5.setBackground(c5);
		this.jb5.setOpaque(true);
		this.jb5.setBorderPainted(false);
		this.jb5.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c5);
					model.setColor(c5);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb5);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c5 = color;
					jb5.setBackground(c1);
					showColor.setBackground(c5);
					model.setColor(c5);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb5);
		// magenta
		this.jb6 = new JButton();
		c6 = Color.MAGENTA;
		this.jb6.setBackground(c6);
		this.jb6.setOpaque(true);
		this.jb6.setBorderPainted(false);
		this.jb6.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isLeftMouseButton(e)) {
					showColor.setBackground(c6);
					model.setColor(c6);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					@SuppressWarnings("unused")
					Window parentWindow = SwingUtilities.windowForComponent(jb6);
					JColorChooser cc = new JColorChooser();
					@SuppressWarnings("static-access")
					Color color = cc.showDialog(null, "Select a color", Color.BLACK);
					c6 = color;
					jb6.setBackground(c6);
					showColor.setBackground(c6);
					model.setColor(c6);
				}
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
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		colorBox.add(this.jb6);
		this.vBox.add(this.colorBox);
		
		// build the color chooser
		this.colorChooser = new JPanel();
		this.colorChooser.setLayout(new GridLayout(0,1));
		showColor.setBackground(Color.BLACK);
		showWidth.setBorderPainted(false);
		showTool.setBorderPainted(false);
		this.vBox.add(this.colorChooser);
		//this.colorChooser.add(showColor);
		this.colorChooser.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.jb7 = new JButton("Chooser");
		this.jb7.setBorderPainted(false);
		this.jb7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Window parentWindow = SwingUtilities.windowForComponent(jb7);
				JColorChooser cc = new JColorChooser();
				@SuppressWarnings("static-access")
				Color color = cc.showDialog(null, "Select a color", Color.BLACK);
				showColor.setBackground(color);
				model.setColor(color);
			}
		});
		this.colorChooser.add(this.jb7);
		//jb7.setSize(50, 100);
		//ccchooser.setSize(50, 50);
		//ccchooser.setBackground(Color.GRAY);
		
		
		// build the line thickness palette
		JPanel thickBox = new JPanel();
		thickBox.setLayout(new GridLayout(0, 1));
		//JButton jb8 = new JButton();
		//JButton jb9 = new JButton();
		//JButton jb10 = new JButton();
		//JButton jb11 = new JButton();
		//jb8.setBackground(Color.BLACK);
		//jb8.setBorder(new LineBorder(Color.RED));
		//jb8.setBorderPainted(false);
		//jb8.setContentAreaFilled(true);
		
		for (int i = 0; i < 4; i++) {
			icons1[i] = new ImageIcon(""+(i+7)+".png");
		}
		for (int i = 0; i < 4; i++) {
			JButton temp = new JButton(icons1[i]);
			if (i == 0) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setWidth(1);
					}
				});
			}
			if (i == 1) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setWidth(4);
					}
				});
			}
			if (i == 2) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setWidth(6);
					}
				});
			}
			if (i == 3) {
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						model.setWidth(8);
					}
				});
			}
			thickBox.add(temp);
		}
		//thickBox.add(jb8);
		//thickBox.add(jb9);
		//thickBox.add(jb10);
		//thickBox.add(jb11);
		vBox.add(thickBox);
		JPanel curtemp = new JPanel();
		curtemp.setLayout(new GridLayout(0, 1));
		showTool.setText("Selector");
		showTool.setBackground(Color.WHITE);
		showTool.setOpaque(true);
		curtemp.add(showTool);
		curtemp.add(showColor);
		showWidth.setIcon(icons1[0]);
		showWidth.setBackground(Color.WHITE);
		showWidth.setOpaque(true);
		curtemp.add(showWidth);
		vBox.add(curtemp);
		
		this.add(vBox, BorderLayout.NORTH);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

}
