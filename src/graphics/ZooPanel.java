package graphics;

import animals.*;
import food.EFoodType;
import memento.ZooMemento;
import plants.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import Listener.ZooObserver;
import abstractFactory.AbstractZooFactory;
import abstractFactory.CarnivoreFactory;
import abstractFactory.HerbivoreFactory;
import abstractFactory.OmnivoreFactory;




/**
 * Class to create "main" panel, and do all actions that refer to this panel,
 * and add additional panel with buttons to perform actions
 * Extends Mobile class and implements IEdible,IDrawable,IAnimalBehavior and Runnable interfaces
 * @see JPanel,Runnable,ActionListener
 */
public class ZooPanel extends JPanel implements Runnable,ActionListener {

	
	/**
	 * ZooPanel parameters
	 */
	private static final int POOLSIZE  = 5;
	private static ZooPanel instance = null;
	
	private ZooFrame frame;
	private JPanel panel,bottom1,bottom2;
    private JButton AddAnimal,Sleep,WakeUp,Clear,Food,Info,Decorate,Exit,Duplicate,SaveState,RestoreState;
    private BufferedImage background = null;
	private Font f;
	private ZooObserver controller;
	public Vector<Animal> animalsList; 
	public Plant plant;
	public boolean meat;
	private ImageIcon meatImage;
	private static final long serialVersionUID = 1L;
	private static final String BACKGROUND_PATH = "src/pictures//savanna.jpg";
	private ExecutorService threadPool;
	
	private ArrayList<ZooMemento> mementos;
	
	
	
	/**
	 * ZooPanel constructor
	 * @param f - font size and style
	 * @param frame - parent frame, reference to ZooFrame
	 */
	private ZooPanel(Font f,ZooFrame frame){
		
		threadPool = Executors.newFixedThreadPool(POOLSIZE);
		this.frame = frame;
		this.f = f;
		this.plant = null;
		this.meat = false;
		this.meatImage = new ImageIcon("src/pictures//meat.gif");
		animalsList = new Vector<Animal>();
		mementos = new ArrayList<ZooMemento>();
		setLayout(new BorderLayout());
		panel = new JPanel(new BorderLayout());
		bottom1 = new JPanel(new GridLayout());
		bottom2 = new JPanel(new GridLayout());
		AddAnimal = new JButton("Add Animal");
		Sleep = new JButton("Sleep");
		WakeUp = new JButton("Wake up");
		Clear = new JButton("Clear");
		Food = new JButton("Food");
		Info = new JButton("Info");
		Decorate = new JButton("Decorate");
		Exit = new JButton("Exit");
		Duplicate = new JButton("Duplicate");
		SaveState = new JButton("Save State");
		RestoreState = new JButton("Restore State");
		
		/**
		 * Add buttons to panel that sits on "main" panel
		 */
		bottom1.add(AddAnimal);
		bottom1.add(Sleep);
		bottom1.add(WakeUp);
		bottom1.add(Clear);
		bottom1.add(Food);
		bottom1.add(Info);
		bottom2.add(Decorate);
		bottom2.add(Duplicate);
		bottom2.add(SaveState);
		bottom2.add(RestoreState);
		bottom2.add(Exit); 
		
		
		/**
		 * Add actions to buttons on panel
		 */
		for(Component comp: bottom1.getComponents()){
			if(comp instanceof JButton){
				((JButton)comp).setFont(f);
				((JButton)comp).addActionListener(this);;
			}
		}
		
		for(Component comp: bottom2.getComponents()){
			if(comp instanceof JButton){
				((JButton)comp).setFont(f);
				((JButton)comp).addActionListener(this);;
			}
		}
		
		panel.add(bottom1, BorderLayout.NORTH);
		panel.add(bottom2,BorderLayout.SOUTH);
		add(panel,BorderLayout.SOUTH);
		
		controller = new ZooObserver(this);
		controller.start();
				
	}
	
	
	
	
	/**
	 * Get instance of ZooPanel, if panel exist return instance, if not, create new instance
	 * @param f - font
	 * @param frame - reference to ZooFrame
	 * @return instanse of ZooPanel
	 */
	public static ZooPanel getInstance(Font f,ZooFrame frame) {
	      if(instance == null) {
	         instance = new ZooPanel(f,frame);
	      }
	      return instance;
	   }

	
	
	
	@Override
	public void run() {
		while(true){
			synchronized(this){
				try{
					Thread.sleep(50);
				}catch(InterruptedException e){
					return;
				}
			for(Animal a:animalsList){
				if(a.getIsRunnig()){
				if(a.getChanges()){
					a.setChanges(false);
					repaint();
				}
				}
			}	
			}
			
		}
	
		}


	
	
	
		@Override
		  protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		     if(background != null) 
		    	 g.drawImage(background,0,0,getWidth(),getHeight(), this);
		     if(plant != null)
		    	 plant.drawObject(g);
		     if(meat == true)
		    	 meatImage.paintIcon(this, g,this.getWidth()/2 - meatImage.getIconWidth()/2 ,this.getHeight()/2 - meatImage.getIconHeight()/2 );
		     for(Animal a:animalsList){
		    	 if(a.getIsRunnig()){
		    		 a.drawObject(g);
		    	 }
		    	 }
		  
		     }
		
		
		
		
		
		/**
		 * Set background image to panel
		 * @param type- background type - image or green or none
		 */
		public void setBackgroundImg(String type){
			if(type == "Image"){
			 try { 
				 background = ImageIO.read(new File(BACKGROUND_PATH)); 
				 repaint();
			 } 
			  catch (IOException e) { System.out.println("Cannot load image"); }
			}
			if(type == "Green"){
				background = null;
				setBackground(Color.GREEN);	
			}
			if(type == "None"){
				background = null;
				setBackground(null);
				repaint();
			}			
		}
		
		
		
		
		
		/**
		 * Create animal object
		 * @param animal - type of animal
		 * @param color - animal colour
		 * @param size - animal size
		 * @param horSpeed - animal horizontal speed
		 * @param verSpeed - animal vertical speed
		 * @return
		 */
		public AbstractZooFactory createAnimalFactory(String type) {
		      switch(type){
		      case "Carnivore":
		    	  return new CarnivoreFactory();
		      case "Herbivore":
		    	  return new HerbivoreFactory();
		      case "Omnivore":
		    	  return new OmnivoreFactory();
		      }
		      return null;
		   }
		
		
		
		
		
		/**
		 * Add new animal - if there are no more than 10 animals already
		 * @param animal - animal name
		 * @param color - animal colour
		 * @param size - animal size
		 * @param horSpeed - animal horizontal speed
		 * @param verSpeed - animal vertical speed
		 */
		public void AddAnimal(Animal new_animal){
			if(animalsList.size() >= 10){
            	JLabel resLabel = new JLabel("You cannot add more than 10 animals");
            	resLabel.setFont(f);
            	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
            	JOptionPane.showMessageDialog(frame, resLabel, "message",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				animalsList.add(new_animal);
				Future<?> task = threadPool.submit(new_animal);
				new_animal.setFutureTask(task);
				if(animalsList.size() > 5){
					new_animal.setIsRunnig(false);
				}
				else{
					new_animal.setIsRunnig(true);
				}
				
			}
		}
		
		

		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == AddAnimal){
				JLabel resLabel = new JLabel("Please choose animal factory");
            	resLabel.setFont(f);
            	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
				String[] buttons = { "Herbivore", "Omnivore", "Carnivore"};    
				int result = JOptionPane.showOptionDialog(frame, resLabel, "Animal factory", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[2]);
				if(result == JOptionPane.CLOSED_OPTION)
					return;
				@SuppressWarnings("unused")
				AbstractZooFactory factory = createAnimalFactory(buttons[result]);
				@SuppressWarnings("unused")
				AddAnimalDialog animalDialog = new AddAnimalDialog(createAnimalFactory(buttons[result]),frame,f,this);
			}	
			else if(e.getSource() == Exit){
				for(Animal a:animalsList)
					a.getFutureTask().cancel(true);
				threadPool.shutdown();
				synchronized(this){
					controller.interrupt();
				}
				animalsList = null;
				frame.dispose();
				System.exit(0);
				
			}
			else if(e.getSource() == Food){
            	JLabel resLabel = new JLabel("Please choose food");
            	resLabel.setFont(f);
            	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
				String[] buttons = { "Meat", "Cabbage", "Lettuce"};    
				int result = JOptionPane.showOptionDialog(frame, resLabel, "Food for animals", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[2]);
				if(result == 0){
					meat = true;
					repaint();
				}
				else if(result == 1){
					plant = Cabbage.getInstance(this);
					repaint();
				}
				else if(result == 2){
					plant = Lettuce.getInstance(this);
					repaint();
				}
		}
			else if(e.getSource() == Sleep){
				 for(Animal a:animalsList){
					 if(a.getSusspended() == false)
						 a.setSuspended();
		    	 }
			}
			else if(e.getSource() == WakeUp){
				for(Animal a:animalsList)
					if(a.getSusspended() == true)
						a.setResumed();
			}
			else if(e.getSource() == Clear){
				int count = ((ThreadPoolExecutor) threadPool).getActiveCount();
				for(int i=count-1;i>=0;i--){
					synchronized(this){
					animalsList.get(i).getFutureTask().cancel(true);
					animalsList.remove(i);
					}
				}
				for(Animal a: animalsList){
					a.setChanges(true);
					a.setIsRunnig(true);
				}
				plant = null;
				meat = false;
				repaint();	
			}
			
			else if(e.getSource() == Decorate){
				Vector<Animal> naturalAnimals = new Vector<Animal>();
				 for(Animal a:animalsList){
					 if(a.getColor().equals("Natural"))
						 naturalAnimals.add(a);
				 }
				 if(naturalAnimals.size() == 0){
					 JLabel resLabel = new JLabel("You have no animals for decoration");
					 resLabel.setFont(f);
					 UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
					 JOptionPane.showMessageDialog(frame, resLabel, "Message",JOptionPane.INFORMATION_MESSAGE);
				 }
				 else{
					 @SuppressWarnings("unused")
					DecorateDialog dec = new DecorateDialog(frame,naturalAnimals,f);
				 }
			}
			
			else if(e.getSource() == Info){
				Object columnNames[] = {"Animal", "Color", "Weight", "Hor.speed","Ver.speed","Eat counter" };
				DefaultTableModel model = new DefaultTableModel(); 
				
				
				JTable table = new JTable(model); 
				for(int i=0; i<6; i++)
					model.addColumn(columnNames[i]);
				int totalCount = 0;
				for(Animal a:animalsList){
					model.addRow(new Object[]{a.getAnimalName(),a.getColor(),a.getWeight(),a.getHorSpeed(),a.getVerSpeed(),a.getEatCount()});
					totalCount += a.getEatCount();
				}
				model.addRow(new Object[]{"total",null,null,null,null,totalCount});
				UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
				UIManager.put("Table.font", new FontUIResource(f));
				table.getTableHeader().setFont(f);
				table.setFont(f);
				table.setRowHeight(this.getHeight()/20);
				JScrollPane scrollpane = new JScrollPane(table);
				scrollpane.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/2));
				
				JOptionPane.showMessageDialog(null, scrollpane,"", JOptionPane.PLAIN_MESSAGE);
			}
			
			else if(e.getSource() == Duplicate){
				@SuppressWarnings("unused")
				DuplicateDialog dg = new DuplicateDialog(frame,this,animalsList,f);
				repaint();
			}
			else if(e.getSource() == SaveState){
				if(mementos.size()>=3){
					 JLabel resLabel = new JLabel("You can't save more than 3 states");
					 resLabel.setFont(f);
					 UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
					 JOptionPane.showMessageDialog(frame, resLabel, "Message",JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					mementos.add(new ZooMemento(animalsList,plant,meat));
					JLabel resLabel = new JLabel("State saved");
					resLabel.setFont(f);
					UIManager.put("OptionPane.buttonFont", new FontUIResource(f));
					JOptionPane.showMessageDialog(frame, resLabel, "Message",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if(e.getSource() == RestoreState){
				JLabel resLabel = new JLabel("Please choose state to restore");
            	resLabel.setFont(f);
            	UIManager.put("OptionPane.buttonFont", new FontUIResource(f)); 
				String[] buttons = { "State 1", "State 2", "State 3","Cancel"};    
				int result = JOptionPane.showOptionDialog(frame, resLabel, "Saved states", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[3]);
				int i = -1;
				if(mementos.isEmpty()){
					 resLabel = new JLabel("You have not saved states");
					 resLabel.setFont(f);
					 UIManager.put("OptionPane.buttonFont", new FontUIResource(f));
					 JOptionPane.showMessageDialog(frame, resLabel, "Message",JOptionPane.INFORMATION_MESSAGE);
					 return;
				}
				if(result == 0 && mementos.size() >=1)
					i=0;
				else if(result == 1 && mementos.size() >= 2)
					i=1;
				else if(result == 2 && mementos.size() >= 3)
					i=2;
				else if(result == 3)
					return;
				else if(result == JOptionPane.CLOSED_OPTION)
					return;
				else{
					 resLabel = new JLabel("You have not saved state number: " + (result+1) + " ,try another state");
					 resLabel.setFont(f);
					 UIManager.put("OptionPane.buttonFont", new FontUIResource(f));
					 JOptionPane.showMessageDialog(frame, resLabel, "Message",JOptionPane.INFORMATION_MESSAGE);
					 return;
				}
				
				
				for(Animal a:animalsList)
					a.getFutureTask().cancel(true);
				threadPool.shutdown();
				threadPool = Executors.newFixedThreadPool(POOLSIZE);


				animalsList = mementos.get(i).getAnimals();
				
				plant = mementos.get(i).getPlant();
				meat = mementos.get(i).getMeat();
				
				
				
				for(Animal animal: animalsList){
					Future<?> task = ((ExecutorService)threadPool).submit(animal);
					animal.setFutureTask(task);
				}

				mementos.remove(i);
								
			}

}

		
		
		
		/**
		 * Animal eats food given the by user (Not another animal)
		 * @param animal - animal that got to the food first
		 */
		public void eatFood(Animal animal) {
			if(plant != null){
				if(animal.getDiet().canEat(plant.getFoodType()))
					plant = null;
			}
			if(meat == true){
				if(animal.getDiet().canEat(EFoodType.MEAT))
				meat = false;
			}
			animal.eatInc();
			repaint();	
		}
		
		
		
		
		/**
		 * Check if one animal can eat another, if true, "kill" eaten animal
		 */
		public void findPrey(){
			boolean prey_eaten = false;
			for(Animal predator : animalsList){
				for(Animal prey : animalsList){
					if(predator != prey 
							&& predator.getDiet().canEat(prey.getFoodType())
							&& predator.getWeight()/prey.getWeight() >= 2 
							&&  Math.abs(predator.getLocation().getX() - prey.getLocation().getX()) < prey.getSize()
							&& Math.abs(predator.getLocation().getY() - prey.getLocation().getY()) < prey.getSize()){
						predator.eatInc();
						System.out.println("The " + predator + "cought up the " + prey + " ==> ");
						prey.getFutureTask().cancel(true);
						animalsList.remove(prey);
						repaint();
						prey_eaten = true;
						break;
					}
				}
				if(prey_eaten == true)
					break;
			}
		}

		
		
		/**
		 * Get animal from given index in list
		 * @param i - index 
		 * @return null if index is out of range (bigger than the size of the list or smaller than 0).
		 *         if index is in range, return animal in that index.
		 */
		public Animal getAnimal(int i) {
			if(animalsList.size() > i && i>0)
				return this.animalsList.get(i);
			else
				return null;
		}
		
		
		
		
}
