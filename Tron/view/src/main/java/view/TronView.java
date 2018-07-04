package view;

import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TronView implements IViewSystem, Runnable {
	private final GraphicsBuilder	graphicsBuilder;
	private final EventPerformer	eventPerformer;
	private final Observable			observable;
	private GameFrame							gameFrame;

	public TronView(final IOrderPerformer orderPerformer, final ITronModel tronModel, final Observable observable) {
		this.observable = observable;
		this.graphicsBuilder = new GraphicsBuilder(tronModel);
		this.eventPerformer = new EventPerformer(orderPerformer);
		SwingUtilities.invokeLater(this);
	}

	@Override
	public void run() {
		this.gameFrame = new GameFrame("Tron", this.eventPerformer, this.graphicsBuilder, this.observable);
	}

	@Override
	public void displayMessage(final String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void closeAll() {
		this.gameFrame.dispose();
	}
}
