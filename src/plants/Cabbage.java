package plants;

import java.awt.Graphics;

import graphics.ZooPanel;

public class Cabbage extends Plant {

	private static Cabbage instance = null;
	
	private Cabbage(ZooPanel pan) {
		super(pan);
		this.loadImages("lettuce");
	}
	
	
		   public static Cabbage getInstance(ZooPanel pan) {
		      if(instance == null) {
		         instance = new Cabbage(pan);
		      }
		      return instance;
		   }

	@Override
	public void drawObject(Graphics g) {
		g.drawImage(img1, pan.getWidth()/2-img1.getWidth()/2, pan.getHeight()/2-img1.getHeight()/2, pan);
	}
}
