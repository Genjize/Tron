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
		switch (this.direction) {
		
			case LEFT:
				this.moveLeft();
			try {
				this.createWalls();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
			
			case RIGHT:
				this.moveRight();
				try {
					this.createWalls();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case UP:
				this.moveUp();
				try {
					this.createWalls();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case DOWN:
				this.moveDown();
				try {
					this.createWalls();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				
				break;
		}
	}
	
	public void createWalls() throws IOException {
		
		Position oldposition = new Position(this.position.getX(),this.position.getY());

			switch (this.getDirection()) {
			case UP:
				oldposition.setY(this.position.getY() + this.getHeight() + this.getSpeed());
	
				break;
			case RIGHT:
				oldposition.setX(this.position.getX() - this.getWidth() - this.getSpeed());
			
				break;
			case DOWN:
				oldposition.setY(this.position.getY() - this.getHeight() - this.getSpeed());
			
				break;
			case LEFT:
				oldposition.setX(this.position.getX() - this.getWidth() + this.getSpeed());
			
				break;
			default:
				break;
		}
			this.tronModel.addMobileless(new Wall(this.getDirection(), oldposition, "player"+this.getPlayer()));
		
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
