package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import model.dao.GameDAO;

public class TronModel extends Observable implements ITronModel {
	private Grid grid;
	private final ArrayList<IMobile>	mobiles;
	private final ArrayList<IMobileless> mobilesless;

	public TronModel() {
		super();
		this.mobiles = new ArrayList<>();
		this.mobilesless = new ArrayList<>();
	}
	
    public void setExampleByInt(final int player, final long time) throws SQLException {
        GameDAO.setExampleByInt(player, time);
    }

	@Override
	public IArea getArea() {
		return this.grid;
	}

	@Override
	public void buildArea(final IDimension dimension) {
		this.grid = new Grid(dimension);
	}

	@Override
	public void addMobile(final IMobile mobile) {
		this.mobiles.add(mobile);
		mobile.setTronModel(this);
	}

	@Override
	public void removeMobile(final IMobile mobile) {
		this.mobiles.remove(mobile);
	}
	
	@Override
	public void addMobileless(final IMobileless mobileless) {
		this.mobilesless.add(mobileless);
		mobileless.setTronModel(this);
	}

	@Override
	public void removeMobileless(final IMobileless mobileless) {
		this.mobilesless.remove(mobileless);
	}

	@Override
	public ArrayList<IMobile> getMobiles() {
		return this.mobiles;
	}
	
	@Override
	public ArrayList<IMobileless> getMobilesless() {
		return this.mobilesless;
	}

	@Override
	public IMobile getMobileByPlayer(final int player) {
		for (final IMobile mobile : this.mobiles) {
			if (mobile.isPlayer(player)) {
				return mobile;
			}
		}
		return null;
	}

	@Override
	public void setMobilesHavesMoved() {
		this.setChanged();
		this.notifyObservers();
	}
}
