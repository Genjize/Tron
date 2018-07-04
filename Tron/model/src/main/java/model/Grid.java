package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Grid implements IArea {
	private static String		IMAGE	= "grid.png";
	private final IDimension	dimension;
	private Image						image;

	public Grid(final IDimension dimension) {
		this.dimension = dimension;
		try {
			this.image = ImageIO.read(new File("images/" + IMAGE));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IDimension getDimension() {
		return this.dimension;
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
	public Image getImage() {
		return this.image;
	}
}
