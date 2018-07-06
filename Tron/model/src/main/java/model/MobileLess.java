package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IMobileless;

class MobileLess implements IMobileless{
	
	private final Position	position;
	private final Dimension	dimension;
	private Direction direction;
	private ITronModel	tronModel;
	private Image image;
	
	public MobileLess(final Direction direction,final Position position, final Dimension dimension, final String image) {
		this.direction = direction;
		this.position = position;
		this.dimension = dimension;
		try {
			this.buildImage(image);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void buildImage(final String imageName) throws IOException {
		this.setImage(ImageIO.read(new File("images/" + imageName + ".png")));
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Position getPosition() {
		return position;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public ITronModel getTronModel() {
		return tronModel;
	}

	public void setTronModel(ITronModel tronModel) {
		this.tronModel = tronModel;
	}
	
	@Override
	public void placeInArea(final IArea area) {
		this.position.setMaxX(area.getDimension().getWidth());
		this.position.setMaxY(area.getDimension().getHeight());
	}
	
	@Override
	public boolean isWeapon() {
		return true;
	}

	@Override
	public int getWidth() {
				return this.getDimension().getWidth();
	}

	@Override
	public int getHeight() {
				return this.getDimension().getHeight();
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setDirection(final Direction direction) {
		this.direction = direction;
	}
}
