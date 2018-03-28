package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import animals.Animal;
import decorator.ColoredAnimal;
import decorator.ColoredAnimalDecorator;



/**
 * Class to create JDialog for decorate animal (paint with color blue or red, chosen by user).
 * Extends JDialog implements ActionListener
 * @see JDialog, ActionListener
 */
public class DecorateDialog extends JDialog implements ActionListener {

	
	/**
	 * DecorateDialog attributes
	 */
	private static final long serialVersionUID = 1L;
	
	private ZooFrame parentFrame;
	private Vector <Animal> animalsList;
	private Font f;
	private JPanel animalPanel;
	private JPanel colorPanel;
	private JComboBox<String> animalsInfo;
	private JButton OK;
	private JRadioButton Red,Blue;
	
	
	
	
	/**
	 * Constructor
	 * @param frame - reference to zooFrame
	 * @param animalsList - List of animals objects 
	 * @param f - font
	 */
	public DecorateDialog(ZooFrame frame,Vector<Animal> animalsList,Font f){
		super(frame,true);
		this.parentFrame = frame;
		this.animalsList = new Vector<Animal>();
		for(Animal a:animalsList){
			this.animalsList.add(a);
		}
		this.f = f;
		setTitle("Decorate an animal");
		
		setAnimalPanel();
		setLayout(new BorderLayout(1,1));
		add(animalPanel,BorderLayout.WEST);
		add(colorPanel,BorderLayout.EAST);
		Point loc = parentFrame.getLocation();
	    setLocation(loc.x+100,loc.y+200);
	    pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	
	
	/**
	 * Set animal panel - panel that holds combo box with all animals with natural color,
	 * two radio boxes with red or blue options to paint the chosen animal and a button to confirm.
	 * (Also holds labels with strings that describes what user need to do)
	 */
	private void setAnimalPanel() {
		setLayout(new BorderLayout());
		animalPanel = new JPanel();
		colorPanel = new JPanel();
		animalPanel.setLayout(new GridLayout(3, 1));
		colorPanel.setLayout(new GridLayout(3, 1));
		
		JLabel decorateLabel = new JLabel("Select animal to decorate");
		JLabel colorLabel = new JLabel("Choose decoration color");
		Red = new JRadioButton("Red");
		Blue = new JRadioButton("Blue");
		OK = new JButton("OK");
		OK.addActionListener(this);
		animalsInfo = new JComboBox<String>();
		animalsInfo.addItem("No animal");
		int counter = 1;
		for(Animal a: animalsList){
			
			String info = counter + "[" + a.getAnimalName() + ": running=" + a.getIsRunnig() +
					", weight=" + a.getWeight() + ", color=" + a.getColor();
			animalsInfo.addItem(info);
			counter++;
		}
		
		animalPanel.add(decorateLabel,BorderLayout.EAST);
		animalPanel.add(animalsInfo,BorderLayout.EAST);
		animalPanel.add(OK,BorderLayout.CENTER);
		
		colorPanel.add(colorLabel, BorderLayout.EAST);
		colorPanel.add(Red,BorderLayout.EAST);
		colorPanel.add(Blue,BorderLayout.EAST);
		
		for(Component comp : animalPanel.getComponents()){
			comp.setFont(f);
		}
		for(Component comp : colorPanel.getComponents()){
			comp.setFont(f);
		}
		
		
	}

	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == OK){
			if(animalsInfo.getSelectedItem().equals("No animal")){
				messageError("You selected no animal to decorate");
				return;
			}
			if(Red.isSelected() && Blue.isSelected()){
				messageError("You need to select only one color");
				return;
			}
			if(!Red.isSelected() && !Blue.isSelected()){
				messageError("Select color");
				return;
			}
			ColoredAnimal animal = new ColoredAnimalDecorator(animalsList.get(animalsInfo.getSelectedIndex()-1));
			if(Red.isSelected())
				animal.PaintAnimal("Red");
			else
				animal.PaintAnimal("Blue");
			dispose();
		}
		
	}
	
	
	
	
	/**
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
