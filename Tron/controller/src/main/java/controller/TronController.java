package controller;

import java.io.IOException;
import java.util.ArrayList;


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
			final IMobile plane = this.tronModel.getMobileByPlayer(userOrder.getPlayer());
			if (plane != null) {
				Direction direction;
				switch (userOrder.getOrder()) {

					case RIGHT:
						direction = Direction.RIGHT;
						break;
					case LEFT:
						direction = Direction.LEFT;
						break;
					case NOP:
					default:
						direction = this.tronModel.getMobileByPlayer(userOrder.getPlayer()).getDirection();
						break;
						
				}
				plane.setDirection(direction);
			}
		}
	}

	/*
	private void lauchMissile(final int player) throws IOException {
		final IMobile plane = this.tronModel.getMobileByPlayer(player);
		if (plane != null) {
			final Position position = new Position(plane.getPosition().getX() + ((plane.getWidth() - Missile.getWidthWithADirection(plane.getDirection())) / 2),
					plane.getPosition().getY() + ((plane.getHeight() - Missile.getHeightWithADirection(plane.getDirection())) / 2));
			this.tronModel.addMobile(new Missile(plane.getDirection(), position));
			switch (plane.getDirection()) {
				case RIGHT:
					position.setX(position.getX() + plane.getWidth() + plane.getSpeed());
					break;
				case LEFT:
					position.setX(position.getX() - plane.getWidth() - plane.getSpeed());
					break;
				default:
					break;
			}
		}
	} */

	private boolean isWeaponOnMobile(final IMobile mobile, final IMobile weapon) {
		if (((weapon.getPosition().getX() / weapon.getWidth()) >= (mobile.getPosition().getX() / weapon.getWidth()))
				&& ((weapon.getPosition().getX() / weapon.getWidth()) <= ((mobile.getPosition().getX() + mobile.getWidth()) / weapon.getWidth()))) {
			if (((weapon.getPosition().getY() / weapon.getHeight()) >= (mobile.getPosition().getY() / weapon.getHeight()))
					&& ((weapon.getPosition().getY() / weapon.getHeight()) <= ((mobile.getPosition().getY() + mobile.getHeight()) / weapon.getHeight()))) {
				return true;
			}
		}
		return false;
	}

	private void manageCollision(final IMobile weapon) {
		final ArrayList<IMobile> target = new ArrayList<IMobile>();
		boolean isTargetHit = false;

		for (final IMobile mobile : this.tronModel.getMobiles()) {
			if (this.isWeaponOnMobile(mobile, weapon)) {
				target.add(mobile);
			}
		}
		for (final IMobile mobile : target) {
			isTargetHit = isTargetHit || mobile.hit();
		}
		if (isTargetHit) {
			this.tronModel.removeMobile(weapon);
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
			for (final IMobile mobile : this.tronModel.getMobiles()) {
				initialMobiles.add(mobile);
			}
			for (final IMobile mobile : initialMobiles) {
				mobile.move();
				if (mobile.isWeapon()) {
					this.manageCollision(mobile);
				}
			}
			this.tronModel.setMobilesHavesMoved();
		}
	}

	public void setViewSystem(final IViewSystem viewSystem) {
		this.viewSystem = viewSystem;
	}
}
