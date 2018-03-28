package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import abstractFactory.AbstractZooFactory;
import abstractFactory.CarnivoreFactory;
import abstractFactory.HerbivoreFactory;
import abstractFactory.OmnivoreFactory;


/**
 * Class to create JDialog for creating animals with parameters added by user.
 * Extends JDialog implements ActionListener
 * @see JDialog, ActionListener
 */
public class AddAnimalDialog extends JDialog implements ActionListener {
	
	
	/**
	 * AddAnimalDialog parameters
	 */
	private ZooFrame parentFrame;
	private ZooPanel panel;
	private String[] typeOfAnimal = {"Bear","Elephant","Giraffe","Lion","Turtle"};
	private String[] animalsColors = {"Natural","Red","Blue"};
	private JPanel animalPanel;
	private JComboBox<String> animalColor;
	private JComboBox<String> animalsList;
	private JTextField animalSize;
	private JTextField animalHorizontalSpeed;
	private JTextField animalVerticalSpeed;
	private JButton done;
	private Font f;
	private static final long serialVersionUID = 1L;
	private AbstractZooFactory factory;

	
	
	/**
	 * AddAnimalDialog constructor
	 * @param frame - reference to ZooFrame
	 * @param f - font style and size
	 * @param panel - reference to parent panel, ZooPanel.
	 */
	public AddAnimalDialog(AbstractZooFactory factory,ZooFrame frame, Font f, ZooPanel panel){
		
		super(frame,true);
		this.factory = factory;
		this.parentFrame = frame;
		this.panel = panel;
		this.f = f;
		setTitle("Add animal");
		setAnimalPanel();
		setLayout(new BorderLayout());
		add(animalPanel,BorderLayout.CENTER);
		Point loc = parentFrame.getLocation();
	    setLocation(loc.x+100,loc.y+200);
	    pack();
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	
	
	/**
	 * Make animal panel that allow user to choose animal parameters
	 */
	private void setAnimalPanel(){
		setLayout(new BorderLayout());
		animalPanel = new JPanel(new GridLayout());
		JLabel chooseAnimal = new JLabel("Choose animal: ");
		animalsList = new JComboBox<String>();
		if(factory instanceof CarnivoreFactory)
			animalsList.addItem(typeOfAnimal[3]);
		else if(factory instanceof HerbivoreFactory){
			animalsList.addItem(typeOfAnimal[1]);
			animalsList.addItem(typeOfAnimal[2]);
			animalsList.addItem(typeOfAnimal[4]);
		}
		else if(factory instanceof OmnivoreFactory){
			animalsList.addItem(typeOfAnimal[0]);
		}
		JLabel chooseSize = new JLabel("Choose size(pixels-between 50 and 300): ");
		animalSize = new JTextField();
		JLabel chooseHorizontal = new JLabel("Choose horizontal speed(Unit time pixels-between 1 and 10):   ");
		animalHorizontalSpeed = new JTextField();
		JLabel chooseVertical = new JLabel("Choose vertical speed(Unit time pixels-between 1 and 10):   ");
		animalVerticalSpeed = new JTextField();
		JLabel chooseColor = new JLabel("Choose animal color:  ");
		animalColor = new JComboBox<String>(animalsColors);
		done = new JButton("Done");
		done.addActionListener(this);
		
		animalPanel.add(chooseAnimal);
		animalPanel.add(animalsList);
		animalPanel.add(chooseSize);
		animalPanel.add(animalSize);
		animalPanel.add(chooseHorizontal);
		animalPanel.add(animalHorizontalSpeed);
		animalPanel.add(chooseVertical);
		animalPanel.add(animalVerticalSpeed);
		animalPanel.add(chooseColor);
		animalPanel.add(animalColor);
		animalPanel.add(done);

		animalPanel.setLayout(new GridLayout(6, 2));
		
		for(Component comp : animalPanel.getComponents()){
			comp.setFont(f);
		}

	}

		

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Check for user mistakes in entered data
		 */
		if(e.getSource() == done){ 
			String Error = "All fields must be full";
			int size,VerSpeed,HorSpeed;
				try{
				if(animalSize.getText().equals("")
						|| animalVerticalSpeed.getText().equals("")
						|| animalVerticalSpeed.getText().equals(""))
					throw new Exception();
				}catch(Exception ex){messageError(Error); return;}
				try {
				Error = "Animal size can only be whole number";
				size = Integer.parseInt(animalSize.getText());
				Error = "Animal vertical speed can only be whole number";
				VerSpeed = Integer.parseInt(animalVerticalSpeed.getText());
				Error = "Animal horizontal speed can only be whole number";
				HorSpeed = Integer.parseInt(animalHorizontalSpeed.getText());
			
			}catch (NumberFormatException ex){messageError(Error); return; }
			if(size < 50 || size > 300){
				messageError("Animal size can only be between 50 and 300");
				return;
			}
			if(VerSpeed < 1 || VerSpeed > 10 || HorSpeed < 1 || HorSpeed > 10){
				messageError("Animal speed can only be between 1 and 10");
				return;
			}
			/**
			 * If there are no mistakes in entered animal data, call to function to add animal
			 */
			panel.AddAnimal(factory.produceAnimal(animalsList.getSelectedItem().toString(),size, animalColor.getSelectedItem().toString(), HorSpeed, VerSpeed, panel));
			dispose();
	}
		
		
	}
	
	
	
	/**
	 * Show message that describes error
	 * @param errorString - String that describes type of error
	 */
	public void messageError(String errorString){
		JOptionPane message = new JOptionPane();
    	message.setFont(f);
    	JLabel resLabel = new JLabel(errorString);
    	resLabel.setFont(f);
    	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
    	JOptionPane.showMessageDialog(parentFrame, resLabel, "Error",JOptionPane.INFORMATION_MESSAGE);
	}
}
