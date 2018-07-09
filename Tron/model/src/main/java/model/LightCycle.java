package model;

public class LightCycle extends Mobile {
	private static int	SPEED		= 2;
	private static int	WIDTH		= 5;
	private static int	HEIGHT	= 5;

	private final int		player;

	public LightCycle(final int player, final Direction direction, final Position position, final String image) {
		super(player, direction, position, new Dimension(WIDTH, HEIGHT), SPEED, image);
		this.player = player;
	}

	@Override
	public boolean isPlayer(final int player) {
		return this.player == player;
	}

	@Override
	public boolean hit() {
		this.getTronModel().removeMobile(this);
		return true;
	}
}
