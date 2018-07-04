package model;

public class Wall extends MobileLess{
	private static int		WIDTH									= 30;
	private static int		HEIGHT								= 30;
	private static String	IMAGE									= "wall";

	public Wall(final Position position) {
		super(position, new Dimension(WIDTH, HEIGHT), IMAGE);
	}

	public static int getWidthWithADirection(final Direction direction) {
		switch (direction) {
			case UP:
			case DOWN:
				return HEIGHT;
			case RIGHT:
			case LEFT:
			default:
				return WIDTH;
		}
	}

	public static int getHeightWithADirection(final Direction direction) {
		switch (direction) {
			case UP:
			case DOWN:
				return WIDTH;
			case RIGHT:
			case LEFT:
			default:
				return HEIGHT;
		}
	}
	public boolean isWeapon() {
		return true;
	}

}
