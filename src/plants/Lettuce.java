package plants;

import java.awt.Graphics;

import graphics.ZooPanel;


public class Lettuce extends Plant {
	
	private static Lettuce instance = null;

	private Lettuce(ZooPanel pan) {
		super(pan);
		this.loadImages("lettuce");
	}
	
	
		   public static Lettuce getInstance(ZooPanel pan) {
		      if(instance == null) {
		         instance = new Lettuce(pan);
		      }
		      return instance;
		   }

	@Override
	public void drawObject(Graphics g) {
		g.drawImage(img1, pan.getWidth()/2-img1.getWidth()/2, pan.getHeight()/2-img1.getHeight()/2, pan);
	}
}
