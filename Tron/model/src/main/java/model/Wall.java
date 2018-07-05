package model;

public class Wall extends MobileLess implements IWall{
	private static int		WIDTH									= 30;
	private static int		HEIGHT								= 30;

	public Wall(final Position position, final String image) {
		super(position, new Dimension(WIDTH, HEIGHT), image);
	}
	public boolean isWeapon() {
		return true;
	}

}
