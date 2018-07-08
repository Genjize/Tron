package view;

import java.awt.event.KeyEvent;

import controller.IOrderPerformer;
import controller.Order;
import controller.UserOrder;

class EventPerformer implements IEventPerformer {
	private final IOrderPerformer orderPerformer;

	public EventPerformer(final IOrderPerformer orderPerformer) {
		this.orderPerformer = orderPerformer;
	}

	@Override
	public void eventPerform(final KeyEvent keyCode) {
		final UserOrder userOrder = this.keyCodeToUserOrder(keyCode.getKeyCode());
		if (userOrder != null) {
			this.orderPerformer.orderPerform(userOrder);
		}
	}

	private UserOrder keyCodeToUserOrder(final int keyCode) {
		UserOrder userOrder;
		switch (keyCode) {
			case KeyEvent.VK_RIGHT:
				userOrder = new UserOrder(2, Order.RIGHT);
				break;
			case KeyEvent.VK_LEFT:
				userOrder = new UserOrder(2, Order.LEFT);
				break;

				
			case KeyEvent.VK_D:
				userOrder = new UserOrder(1, Order.RIGHT);
				break;
			case KeyEvent.VK_Q:
				userOrder = new UserOrder(1, Order.LEFT);
				break;

				
			default:
				userOrder = null;
		}
		return userOrder;
	}
}
