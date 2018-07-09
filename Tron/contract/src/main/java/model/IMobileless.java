package model;

import java.awt.Image;

public interface IMobileless {

	public IPosition getPosition();

	public IDimension getDimension();

	public int getWidth();

	public int getHeight();

	public Image getImage();

	public void placeInArea(IArea area);

	public void setTronModel(ITronModel tronModel);

	public boolean isWeapon();
	
	public Direction getDirection();

	public void setDirection(final Direction direction);

}
