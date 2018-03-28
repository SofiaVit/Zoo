package graphics;



import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.plaf.FontUIResource;



/**
 * Class to create "main" frame, and do all actions that refer to this frame,
 * also contains JMenu and main
 * Extends Mobile class and implements IEdible,IDrawable,IAnimalBehavior and Runnable interfaces
 * @see ActionListener
 */
public class ZooFrame extends JFrame implements ActionListener {
	
	
	/**
	 * ZooFrame properties
	 */
	private ZooPanel panel;
	private JFrame frame;
	/**
	 * Menu bar
	 */
	private JMenuBar menu;
	
	/**
	 * Tabs for menu bar
	 */
	private JMenu file,background,help;
	
	/**
	 * Items for tabs in menu bar
	 */
	private JMenuItem Exit,Image,Green,None,Help;
	
	private static int width;
	private static int height;
	private static Font f;
	
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * ZooFrame constructor
	 */
	private ZooFrame(){
		
		super("Zoo");
		
		setPreferredSize(new Dimension(width* 2 / 3, height* 2 / 3));// set the jframe height and width
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame will close on x

		setJMenuBar(CreateMenu());
		
		panel = ZooPanel.getInstance(f, this);
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}
	
	
	
	/**
	 * Create JMenuBar to perform actions like help, change background and more
	 * @return
	 */
	private JMenuBar CreateMenu(){
		menu = new JMenuBar(); //Create menu bar
		//Create tabs in menu bar and add events to them
		file = new JMenu("File");
		background = new JMenu("Background");
		help = new JMenu("Help");
		//Create and add item for file tab
		Exit = new JMenuItem("Exit");
		Exit.setFont(new Font("Arial", Font.PLAIN, (int)width/90));
		file.add(Exit);
		
	
		//Create and add items for background tab
		Image = new JMenuItem("Image");
		background.add(Image);
		background.addSeparator();
		Green = new JMenuItem("Green");
		Green.addActionListener(this);
		background.add(Green);
		background.addSeparator();
		None = new JMenuItem("None");
		background.add(None);
		
		for(int i=0;i < background.getItemCount();i++){
			Component item = background.getItem(i);
			if(item instanceof JMenuItem){
				item.setFont(f);
				((JMenuItem)item).addActionListener(this);
			}
		}
		

		//Create and add item for help tab
		Help = new JMenuItem("Help");
		Help.setFont(f);
		help.add(Help);
        Help.addActionListener(this);
        
      //Create and add item for file tab
		menu.add(file);
		menu.add(background);
		menu.add(help);
	
		setJMenuBar(menu);

		for(Component comp: menu.getComponents())
			comp.setFont(f);

		return menu;
						
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource() == Help ){
            	JLabel resLabel = new JLabel("Home Work 3\n GUI@Threads ");
            	resLabel.setFont(f);
            	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
            	JOptionPane.showMessageDialog(frame, resLabel, "message",JOptionPane.INFORMATION_MESSAGE);
		 }
		 else if(e.getSource() == Exit )
			 System.exit(0);
		 else if(e.getSource() == Image)
			 panel.setBackgroundImg("Image");
		 else if(e.getSource() == Green)
			 panel.setBackgroundImg("Green");
		 else if(e.getSource() == None)
			 panel.setBackgroundImg("None");
		 
	}

	
	
	/**
	 * Main
	 * @param args
	 */
	public static void main (String args[]){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height = screenSize.height;
		width = screenSize.width;
		f = new Font("ARIAL",Font.PLAIN,(int)width/90);
		@SuppressWarnings("unused")
		ZooFrame zoo = new ZooFrame();
	
	}
	
}
