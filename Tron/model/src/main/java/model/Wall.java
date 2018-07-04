package model;

public class Wall extends MobileLess implements IWall{
	private static int		WIDTH									= 30;
	private static int		HEIGHT								= 30;
	private static String	IMAGE									= "wall";

	public Wall(final Position position) {
		super(position, new Dimension(WIDTH, HEIGHT), IMAGE);
	}
	public boolean isWeapon() {
		return true;
	}

}
