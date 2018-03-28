package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import animals.Animal;
import mobility.Point;





/**
 * Class to create JDialog for duplicate existing animal.
 * Extends JDialog implements ActionListener
 * @see JDialog, ActionListener
 */
public class DuplicateDialog extends JDialog implements ActionListener{
	 
	
	/**
	 * DuplacateDialog attributes
	 */
	private static final long serialVersionUID = 1L;
		private ZooFrame parentFrame;
		private ZooPanel panel;
	    private JPanel mainPanel, selectPanel, speedPanel;
	    private Animal an, clone;
	    private JComboBox<String> list;
	    private Vector <Animal> animalsList;
	    private JLabel lbl_hor, lbl_ver;
	    private JSlider sl_hor, sl_ver;
	    private JButton ok;
	 
	    
	    
	    
	    
	    /**
	     * Constructor
	     * @param frame - reference to zooFrame
	     * @param panel - reference to zooPanel
	     * @param animalsList - List of animals objects 
	     * @param f - font
	     */
	    public DuplicateDialog(ZooFrame frame,ZooPanel panel,Vector<Animal> animalsList,Font f)
	    {
	    	super(new JFrame(),"Duplicate an animal",true);
	    	parentFrame = frame;
	    	this.panel = panel;
	    	an = clone = null;
	    	
	    	this.animalsList = new Vector<Animal>();
	    	for(Animal a:animalsList){
				this.animalsList.add(a);
			}
	 
	    	
	    	
		
		setBackground(new Color(100,230,255));
	   	mainPanel = new JPanel();
		add(mainPanel);
			
		mainPanel.setLayout(new GridLayout(1,2));
			
		selectPanel = new JPanel();
		selectPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(null,"Select Animal to clone",TitledBorder.LEFT, TitledBorder.TOP, f), 
				BorderFactory.createEmptyBorder(5,5,5,5)));
		selectPanel.setLayout(new BorderLayout());
	 
		list = new JComboBox<String>();
		list.addItem("No animal");
		int counter = 1;
		for(Animal a: animalsList){
			String info = counter + "[" + a.getAnimalName() + ": running=" + a.getIsRunnig() +
					", weight=" + a.getWeight() + ", color=" + a.getColor();
			list.addItem(info);
			counter++;
		}
		list.addActionListener(this);
		selectPanel.add("North",list);
			
		ok = new JButton("OK");
		ok.addActionListener(this);
		selectPanel.add("South", ok);
		mainPanel.add(selectPanel);
			
		speedPanel = new JPanel();
		speedPanel.setLayout(new GridLayout(4,1));
		speedPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(null,"Speed may be changed...",TitledBorder.LEFT, TitledBorder.TOP, f), 
				BorderFactory.createEmptyBorder(5,5,5,5)));
			
		lbl_hor = new JLabel("Horizontal speed",JLabel.CENTER);
		speedPanel.add(lbl_hor);
	 
		
		sl_hor = new JSlider(0,10);
		sl_hor.setMajorTickSpacing(2);
		sl_hor.setMinorTickSpacing(1);
		sl_hor.setPaintTicks(true);
		sl_hor.setPaintLabels(true);
		sl_hor.setFont(f);
		speedPanel.add(sl_hor);
			
		lbl_ver = new JLabel("Vertical speed",JLabel.CENTER);
		lbl_ver.setFont(f);
		speedPanel.add(lbl_ver);
			
		sl_ver = new JSlider(0,10);
		sl_ver.setMajorTickSpacing(2);
		sl_ver.setMinorTickSpacing(1);
		sl_ver.setPaintTicks(true);
		sl_ver.setPaintLabels(true);
		sl_ver.setFont(f);
		speedPanel.add(sl_ver);
		
		
		for(Component comp : mainPanel.getComponents()){
			comp.setFont(f);
		}
		for(Component comp : selectPanel.getComponents()){
			comp.setFont(f);
		}
		for(Component comp : speedPanel.getComponents()){
			comp.setFont(f);
		}
		
		mainPanel.add(speedPanel);
		java.awt.Point loc = parentFrame.getLocation();
	    setLocation(loc.x+100,loc.y+200);
	    setSize(loc.x*3,loc.y*3);
	    //pack();
	    
		setLocationRelativeTo(null);
		setVisible(true);
		
	    }
	 
	    
	    
	    
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
	    	if(e.getSource() == ok) { //If user pressed ok. check for mistakes in input. if no mistakes, duplicate animal chosen
	    		if(!list.getSelectedItem().equals("No animal"))
	    			an = animalsList.get(list.getSelectedIndex()-1);
		    	if(an!=null) {
		    		try {
						clone = (Animal) an.clone();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		clone.setLocation(new Point(0,0));
		    		clone.setHorSpeed(sl_hor.getValue());
		    		clone.setVerSpeed(sl_ver.getValue());
		    		panel.AddAnimal(clone);
		    	}
	    		setVisible(false);
	    	}
	    	else {
		    	int index;
		    	if((index = list.getSelectedIndex()) != 0) {
		    		try { an = panel.getAnimal(index-1); } 
		    		catch (Exception e1) { System.out.println("Duplicate error!."); an = null; }
			    	if(an!=null) {
			    		sl_hor.setValue(an.getHorSpeed());
			    		sl_ver.setValue(an.getVerSpeed());
			    	}
		    	}		
	    	}
	    }

}
