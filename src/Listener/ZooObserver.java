package Listener;

import java.util.Observable;
import java.util.Observer;

import animals.Animal;
import graphics.ZooPanel;



/**
 * Class to observe and listen to animals objects, get changes  and also contains run.
 * Extends Thread implements Observer 
 * @see Thread, Observer
 */
public class ZooObserver extends Thread implements Observer {
	
	
	/**
	 * ZooObserver attributes
	 */
	private ZooPanel panel;
	
	
	
	
	/**
	 * Constructor
	 * @param panel - reference to ZooPanel
	 */
	public ZooObserver(ZooPanel panel){
		this.panel = panel;
	}
	
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		synchronized(this){
			this.notify();
		}
		
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
			}
			for(Animal a:panel.animalsList){
				if(a.getIsRunnig()){
				if(a.getChanges()){
					a.setChanges(false);
					panel.repaint();
					
				}
					
				}
			}
			synchronized(this){
				panel.findPrey();
			}	
			
		}
	
		}



	
}
