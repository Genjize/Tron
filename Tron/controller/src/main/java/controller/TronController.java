package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Direction;
import model.IMobile;
import model.IMobileless;
import model.IPosition;
import model.ITronModel;
import model.IWall;
import view.IViewSystem;


public class TronController implements IOrderPerformer {
	private static int						TIME_SLEEP	= 30;
	private final ITronModel	tronModel;
	private boolean								isGameOver	= false;
	private IViewSystem						viewSystem;

	public TronController(final ITronModel tronModel) {
		this.tronModel = tronModel;
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

/*
	private void createWalls(final int player) throws IOException {
		final IMobile lightcycle = this.tronModel.getMobileByPlayer(player);
		
		if (lightcycle != null) {
			final IPosition position = new Position(lightcycle.getPosition().getX(),lightcycle.getPosition().getY() );
			this.tronModel.addMobileless(new Wall(position, lightcycle.getImageName()));
		}
	}  */

	private boolean isWeaponOnMobile(final IMobile mobile, final IMobileless weapon) {
		if (((weapon.getPosition().getX() / weapon.getWidth()) >= (mobile.getPosition().getX() / weapon.getWidth()))
				&& ((weapon.getPosition().getX() / weapon.getWidth()) <= ((mobile.getPosition().getX() + mobile.getWidth()) / weapon.getWidth()))) {
			if (((weapon.getPosition().getY() / weapon.getHeight()) >= (mobile.getPosition().getY() / weapon.getHeight()))
					&& ((weapon.getPosition().getY() / weapon.getHeight()) <= ((mobile.getPosition().getY() + mobile.getHeight()) / weapon.getHeight()))) {
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
		for (final IMobile mobiles : target) {
			isTargetHit = isTargetHit || mobile.hit();
		}
		if (isTargetHit) {
			this.tronModel.removeMobileless(weapon);
			this.isGameOver = true;
		}
	}

	public void play() {
		this.gameLoop();
		this.viewSystem.displayMessage("Game Over !");
		this.viewSystem.closeAll();
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
