package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



class Mobile implements IMobile {
	private Direction direction;
	private final Position	position;
	private final Dimension	dimension;
	private final int speed;
	private ITronModel	tronModel;
	private Image images[];
	private String imageName;
	private int player;


	public Mobile(final int player,final Direction direction, final Position position, final Dimension dimension, final int speed, final String image) {
		this.direction = direction;
		this.position = position;
		this.dimension = dimension;
		this.speed = speed;
		this.player = player;
		
		try {
			this.buildAllimages(image);

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}
	
	@Override
	public Dimension getDimension() {
		return this.dimension;
	}

	@Override
	public void placeInArea(final IArea area) {
		this.position.setMaxX(area.getDimension().getWidth());
		this.position.setMaxY(area.getDimension().getHeight());
	}

	@Override
	public void move() {
		int x;
		int y;
		switch (this.direction) {
		
			case LEFT:
				this.moveLeft();
				x = this.position.getX() + this.speed + this.getWidth();
				if(x>600) {
					x = x - 600;
				}
				else if(x<0) {
					x = x + 600;
				}
				Position oldposition = new Position(x , this.position.getY());
				this.tronModel.addMobileless(new Wall(this.getDirection(), oldposition, "player"+this.getPlayer()));
				
				break;
			
			case RIGHT:
				this.moveRight();
				x = this.position.getX() - this.speed - this.getWidth();
				if(x>600) {
					x = x - 600;
				}
				else if(x<0) {
					x = x + 600;
				}
				Position oldposition2 = new Position(x, this.position.getY());
				this.tronModel.addMobileless(new Wall(this.getDirection(), oldposition2, "player"+this.getPlayer()));

				break;
				
			case UP:
				this.moveUp();
				y = this.position.getY() +this.speed + this.getHeight();
				if(y>400) {
					y = y - 400;
				}
				else if(y<0) {
					y = y + 400;
				}
				Position oldposition3 = new Position(this.position.getX(), y);
				this.tronModel.addMobileless(new Wall(this.getDirection(), oldposition3, "player"+this.getPlayer()));

				break;
				
			case DOWN:
				this.moveDown();
				y = this.position.getY() - this.speed - this.getHeight();
				if(y>400) {
					y = y - 400;
				}
				else if(y<0) {
					y = y + 400;
				}
				Position oldposition4 = new Position(this.position.getX(), y);
				this.tronModel.addMobileless(new Wall(this.getDirection(), oldposition4, "player"+this.getPlayer()));

				break;

			default:
				
				break;
		}
	}
	

	private void moveUp() {
		this.position.setY(this.position.getY() - this.speed);
	}

	private void moveRight() {
		this.position.setX(this.position.getX() + this.speed);
	}

	private void moveDown() {
		this.position.setY(this.position.getY() + this.speed);
	}

	private void moveLeft() {
		this.position.setX(this.position.getX() - this.speed);
	}



	@Override
	public boolean isPlayer(final int player) {
		return false;
	}

	public ITronModel getTronModel() {
		return this.tronModel;
	}

	@Override
	public void setTronModel(final ITronModel tronModel) {
		this.tronModel = tronModel;
		this.getPosition().setMaxX(this.getTronModel().getArea().getDimension().getWidth());
		this.getPosition().setMaxY(this.getTronModel().getArea().getDimension().getHeight());
	}

	@Override
	public boolean hit() {
		return false;
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public int getWidth() {
		switch (this.direction) {
			case RIGHT:
			case LEFT:
			default:
				return this.getDimension().getWidth();
		}
	}

	@Override
	public int getHeight() {
		switch (this.direction) {
			case RIGHT:
			case LEFT:
			default:
				return this.getDimension().getHeight();
		}
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}

	private void buildAllimages(final String imageName) throws IOException {
		this.images = new Image[4];
		this.images[Direction.UP.ordinal()] = ImageIO.read(new File("images/" + imageName + "_UP.png"));
		this.images[Direction.DOWN.ordinal()] = ImageIO.read(new File("images/" + imageName + "_DOWN.png"));
		this.images[Direction.RIGHT.ordinal()] = ImageIO.read(new File("images/" + imageName + "_RIGHT.png"));
		this.images[Direction.LEFT.ordinal()] = ImageIO.read(new File("images/" + imageName + "_LEFT.png"));
	}

	@Override
	public Image getImage() {
		return this.images[this.direction.ordinal()];
	}
	
	public String getImageName() {
		return this.imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
}
