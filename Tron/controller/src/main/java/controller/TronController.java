package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Direction;
import model.IMobile;
import model.IMobileless;
import model.ITronModel;
import model.Position;
import model.Wall;
import view.IViewSystem;


public class TronController implements IOrderPerformer {
	private static int						TIME_SLEEP	= 30;
	private final ITronModel	tronModel;
	private boolean								isGameOver	= false;
	private IViewSystem						viewSystem;
	private int y;

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
		int x = 0;
		int y = 0;
		final IMobile plane = this.tronModel.getMobileByPlayer(player);
		if (plane != null) {
			switch (plane.getDirection()) {
			case UP:
				x = plane.getPosition().getX();
				y = (plane.getPosition().getY() + 32);
				break;
			case RIGHT:
				x = (plane.getPosition().getX() - 32);
				y = plane.getPosition().getY();
				break;
			case DOWN:
				x = plane.getPosition().getX();
				y = (plane.getPosition().getY() - 32);
				break;
			case LEFT:
				x = (plane.getPosition().getX() + 32);
				y = plane.getPosition().getY();
				break;
			default:
				break;
		}
			Position position = new Position(x,y);
			this.tronModel.addMobileless(new Wall(plane.getDirection(), position, "player1"));
		}
	} */
	
/*
	private void createWalls(final int player) throws IOException {
		final IMobile lightcycle = this.tronModel.getMobileByPlayer(player);
		
		if (lightcycle != null) {
			final Position position = new Position(lightcycle.getOlderPosition().getX(),lightcycle.getOlderPosition().getY());
			tronModel.addMobileless(new Wall(position, "player1"));
		}
	}  */

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
		if(y==1) {
			this.viewSystem.displayMessage("Player1 / BLUE - WIN");
			this.viewSystem.closeAll();
		}
		else if(y==0) {
			this.viewSystem.displayMessage("Player2 / RED - WIN");
			this.viewSystem.closeAll();
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
