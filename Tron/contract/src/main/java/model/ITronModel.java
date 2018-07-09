package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITronModel {
	public void buildArea(final IDimension dimension);

	public IArea getArea();

	public void addMobile(final IMobile mobile);

	public void removeMobile(final IMobile mobile);

	public ArrayList<IMobile> getMobiles();

	public IMobile getMobileByPlayer(int player);

	public void setMobilesHavesMoved();

	public ArrayList<IMobileless> getMobilesless();
	
	public void addMobileless(final IMobileless mobileless);

	public void removeMobileless(final IMobileless mobileless);
	
	void setExampleByInt(final int player, final long time) throws SQLException;
}