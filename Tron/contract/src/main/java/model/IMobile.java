package model;

import java.awt.Image;

public interface IMobile {

	public Direction getDirection();

	public void setDirection(final Direction direction);

	public IPosition getPosition();

	public IDimension getDimension();

	public int getWidth();

	public int getHeight();

	public int getSpeed();

	public Image getImage();

	public void move();

	public void placeInArea(IArea area);

	public boolean isPlayer(int player);

	public void setTronModel(ITronModel tronModel);

	public boolean hit();

	public boolean isWeapon();

	public String getImageName();
	
	public void setImageName(String imageName);
	
	public int getPlayer();
	
	public void setPlayer(int player);
	
}