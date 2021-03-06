package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Direction;
import model.IMobile;
import model.IMobileless;
import model.ITronModel;
import view.IViewSystem;


public class TronController implements IOrderPerformer {
	private static int						TIME_SLEEP	= 30;
	private final ITronModel	tronModel;
	private boolean								isGameOver	= false;
	private IViewSystem						viewSystem;
	private int y;

	public TronController(final ITronModel tronModel) {
		super();
		this.tronModel = tronModel;
	}
	
    public ITronModel getTronModel() {
        return this.tronModel;
    }

	@Override
	public void orderPerform(final IUserOrder userOrder) {
		if (userOrder != null) {
			final IMobile lightcycle = this.tronModel.getMobileByPlayer(userOrder.getPlayer());
			if (lightcycle != null) {
				Direction direction  = lightcycle.getDirection();
				
				switch (userOrder.getOrder()) {

					case LEFT:
						
						if(direction == Direction.LEFT) {
							direction = Direction.DOWN;
						}
						
						else if(direction == Direction.RIGHT) {
							direction = Direction.UP;
						}
						
						else if(direction == Direction.UP) {
							direction = Direction.LEFT;
						}
						
						else if(direction == Direction.DOWN) {
							direction = Direction.RIGHT;
						}
						
						break;
						
					case RIGHT:
						
						if(direction == Direction.LEFT) {
							direction = Direction.UP;
						}
						
						else if(direction == Direction.RIGHT) {
							direction = Direction.DOWN;
						}
						
						else if(direction == Direction.UP) {
							direction = Direction.RIGHT;
						}
						
						else if(direction == Direction.DOWN) {
							direction = Direction.LEFT;
						}
						
						break;
						
					case NOP:
						
					default:
						direction = this.tronModel.getMobileByPlayer(userOrder.getPlayer()).getDirection();
						break;
						
				}
				lightcycle.setDirection(direction);
			}
		}
	}
	
	private boolean isWeaponOnMobile(final IMobile mobile, final IMobileless weapon) {
		if (((weapon.getPosition().getX() / weapon.getWidth()) >= (mobile.getPosition().getX() / weapon.getWidth()))
				&& ((weapon.getPosition().getX() / weapon.getWidth()) <= ((mobile.getPosition().getX() + mobile.getWidth()) / weapon.getWidth()))) {
			if (((weapon.getPosition().getY() / weapon.getHeight()) >= (mobile.getPosition().getY() / weapon.getHeight()))
					&& ((weapon.getPosition().getY() / weapon.getHeight()) <= ((mobile.getPosition().getY() + mobile.getHeight()) / weapon.getHeight()))) {
				this.y = mobile.getPlayer();
				return true;
			}
		}
		return false;
	}

	private void manageCollision(final IMobile mobile, final IMobileless weapon) {
		final ArrayList<IMobile> target = new ArrayList<IMobile>();
		boolean isTargetHit = false;

		for (final IMobile mobiles : this.tronModel.getMobiles()) {
			if (this.isWeaponOnMobile(mobile, weapon)) {
				target.add(mobiles);
			}
		}
		for (@SuppressWarnings("unused") final IMobile mobiles : target) {
			isTargetHit = isTargetHit || mobile.hit();
		}
		if (isTargetHit) {
			this.tronModel.removeMobileless(weapon);
			this.isGameOver = true;
		}
	}

	public void play() throws SQLException{
		long begin = System.currentTimeMillis();
		this.gameLoop();
		if(y==2) {
			this.viewSystem.displayMessage("Player1 / BLUE - WIN");
			this.viewSystem.closeAll();
			this.getTronModel().setExampleByInt(1,(System.currentTimeMillis()-begin)/1000);
		}
		else if(y==1) {
			this.viewSystem.displayMessage("Player2 / RED - WIN");
			this.viewSystem.closeAll();
		    this.getTronModel().setExampleByInt(2,(System.currentTimeMillis()-begin)/1000);
		}
	}

	private void gameLoop() {
		while (!this.isGameOver) {
			try {
				Thread.sleep(TIME_SLEEP);
			} catch (final InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			final ArrayList<IMobile> initialMobiles = new ArrayList<IMobile>();
			final ArrayList<IMobileless> initialMobilesless = new ArrayList<IMobileless>();
			
			for (final IMobile mobile : this.tronModel.getMobiles()) {
				initialMobiles.add(mobile);
			}
			for (final IMobileless mobileless : this.tronModel.getMobilesless()) {
				initialMobilesless.add(mobileless);
			}
			
			for (final IMobile mobile : initialMobiles){
				mobile.move();

				
				for (final IMobileless mobileless : initialMobilesless) {
					if (mobileless.isWeapon()) {
						this.manageCollision(mobile, mobileless);
					}
				}
			}
			this.tronModel.setMobilesHavesMoved();
		}
		
	}


	public void setViewSystem(final IViewSystem viewSystem) {
		this.viewSystem = viewSystem;
	}
}
