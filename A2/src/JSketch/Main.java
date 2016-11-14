package JSketch;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

public class Main {
	//public static Main m;
	JFrame jframe = new JFrame("JSketch");
	JMenuBar jmb;
	JMenu file;
	JMenu view_mb;
	JMenuItem file1;
	JMenuItem file2;
	JMenuItem file3;
	JCheckBoxMenuItem view_mb1;
	JCheckBoxMenuItem view_mb2;
	public ObjectInputStream input;
	public ObjectOutputStream output;
	public static Model model;
	public static View view;
	public static View2 view2;
	
	public Main() {
		//JFrame jframe = new JFrame("JSketch");
		this.jframe.setSize(800, 600);
		//jframe.pack();
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jframe.getContentPane().setLayout(new BorderLayout());
		
		// make the MenuBar
		ItemController itemController = new ItemController();
		jmb = new JMenuBar();
		file = new JMenu("File");
		view_mb = new JMenu("View");
		file1 = new JMenuItem("New");
		file2 = new JMenuItem("Load");
		file3 = new JMenuItem("Save");
		file1.addActionListener(itemController);
		file2.addActionListener(itemController);
		file3.addActionListener(itemController);
		view_mb1 = new JCheckBoxMenuItem("Full Size");
		view_mb2 = new JCheckBoxMenuItem("Fit to Window");
		
		file.add(file1);
		file.add(file2);
		file.add(file3);
		view_mb.add(view_mb1);
		view_mb.add(view_mb2);
		jmb.add(file);
		jmb.add(view_mb);
		jframe.setJMenuBar(jmb);
		model = new Model();
		//Controller controller = new Controller(model);
		view = new View(model);
		model.addObserver(view);
		view2 = new View2(model);
		view2.addNotify();
		//view2.setFocusable(true);
		//view2.requestFocusInWindow();
		model.addObserver(view2);
		model.notifyObservers();
		
		view.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jframe.add(view, BorderLayout.WEST);
		jframe.add(view2, BorderLayout.CENTER);
		jframe.setVisible(true);
		jframe.setResizable(true);
		
	}
	
	private class ItemController implements ActionListener {

		private ItemController() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// new
			if (e.getSource() == Main.this.file1) {
				Main.view2.clear();
				//Main.view2.parts.add(Main.model.new FillerContain(Color.WHITE));
			}
			
			// load
			if (e.getSource() == Main.this.file2) {
				try {
					Main.view2.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			// save
			if (e.getSource() == Main.this.file3) {
				try {
					Main.view2.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			Main.view2.repaint();
			Main.this.jmb.setFocusable(false);
		}
		
	}
	
	@SuppressWarnings("static-access")
	public File getfileName() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setCurrentDirectory(new File("/User/KuiLikMi/Desktop"));
		fileChooser.showOpenDialog(this.view2);
		return fileChooser.getSelectedFile();
	}
	
	public void loadFile(File fn) {
		try {
			this.input = new ObjectInputStream(new FileInputStream(fn));
		} catch (IOException ioe) {
			System.err.println("Error: load file: " + fn);
			return;
		}
	}
	
	//public static void main(String[] args) {
		//SwingUtilities.invokeLater(new Runnable(){
		//	public void run() {
				//m = new Main();
		//	}
		//});
	//}
}
