package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import model.ITronModel;
import model.IMobile;

class GraphicsBuilder implements IGraphicsBuilder {
	private final ITronModel	tronModel;
	private BufferedImage					emptySky;

	public GraphicsBuilder(final ITronModel tronModel) {
		this.tronModel = tronModel;
		this.buildEmptySky();
	}

	@Override
	public void applyModelToGraphic(final Graphics graphics, final ImageObserver observer) {
		graphics.drawImage(this.emptySky, 0, 0, observer);

		for (final IMobile mobile : this.tronModel.getMobiles()) {
			this.drawMobile(mobile, graphics, observer);
		}
	}

	@Override
	public int getGlobalWidth() {
		return this.tronModel.getArea().getWidth();
	}

	@Override
	public int getGlobalHeight() {
		return this.tronModel.getArea().getHeight();
	}

	private void buildEmptySky() {
		this.emptySky = new BufferedImage(this.tronModel.getArea().getWidth(), this.tronModel.getArea().getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics = (Graphics2D) this.emptySky.getGraphics();
		graphics.drawImage(this.tronModel.getArea().getImage(), 0, 0, this.tronModel.getArea().getWidth(), this.tronModel.getArea().getHeight(), null);
	}

	private void drawMobile(final IMobile mobile, final Graphics graphics, final ImageObserver observer) {
		final BufferedImage imageMobile = new BufferedImage(mobile.getWidth(), mobile.getHeight(), Transparency.TRANSLUCENT);
		final Graphics graphicsMobile = imageMobile.getGraphics();

		graphicsMobile.drawImage(mobile.getImage(), 0, 0, mobile.getWidth(), mobile.getHeight(), observer);
		graphics.drawImage(imageMobile, mobile.getPosition().getX(), mobile.getPosition().getY(), observer);

		final boolean isHorizontalOut = (mobile.getPosition().getX() + mobile.getWidth()) > this.tronModel.getArea().getWidth();
		final boolean isVerticalOut = (mobile.getPosition().getY() + mobile.getHeight()) > this.tronModel.getArea().getHeight();

		if (isHorizontalOut) {
			final BufferedImage imageMobileH = imageMobile.getSubimage(this.tronModel.getArea().getWidth() - mobile.getPosition().getX(), 0,
					(mobile.getWidth() - this.tronModel.getArea().getWidth()) + mobile.getPosition().getX(), mobile.getHeight());
			graphics.drawImage(imageMobileH, 0, mobile.getPosition().getY(), observer);
		}

		if (isVerticalOut) {
			final BufferedImage imageMobileV = imageMobile.getSubimage(0, this.tronModel.getArea().getHeight() - mobile.getPosition().getY(), mobile.getWidth(),
					(mobile.getHeight() - this.tronModel.getArea().getHeight()) + mobile.getPosition().getY());
			graphics.drawImage(imageMobileV, mobile.getPosition().getX(), 0, observer);
		}

		if (isHorizontalOut && isVerticalOut) {
			final BufferedImage imageMobileHV = imageMobile.getSubimage(this.tronModel.getArea().getWidth() - mobile.getPosition().getX(),
					this.tronModel.getArea().getHeight() - mobile.getPosition().getY(),
					(mobile.getWidth() - this.tronModel.getArea().getWidth()) + mobile.getPosition().getX(),
					(mobile.getHeight() - this.tronModel.getArea().getHeight()) + mobile.getPosition().getY());
			graphics.drawImage(imageMobileHV, 0, 0, observer);
		}
	}
}