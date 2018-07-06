package model;

public class Wall extends MobileLess implements IWall{
	private static int		WIDTH									= 30;
	private static int		HEIGHT								= 30;

	public Wall(final Direction direction,final Position position, final String image) {
		super(direction, position, new Dimension(WIDTH, HEIGHT), image);
	}
	public boolean isWeapon() {
		return true;
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

}
