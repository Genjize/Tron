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
	
	private final Position olderposition;

	public Mobile(final int player,final Direction direction, final Position position, final Position olderPosition, final Dimension dimension, final int speed, final String image) {
		this.direction = direction;
		this.position = position;
		this.olderposition = olderPosition;
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
	public Position getOlderPosition() {
		return this.olderposition;
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
				break;
			
			case RIGHT:
				this.moveRight();
				break;
				
			case UP:
				this.moveUp();
				break;
				
			case DOWN:
				this.moveDown();
				break;

			default:
				
				break;
		}
		try {
			this.createWalls();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createWalls() throws IOException {
		int x = 0;
		int y = 0;
		
		x = (getPosition().getX());
		y = (getPosition().getY());
			switch (direction) {
			
			case UP:
				y = (y + getHeight() + 2);
		
				break;
			case RIGHT:
				x = (x - getHeight() - 2);
				
				break;
			case DOWN:
				y = (y - getHeight() - 2);
				
				break;
			case LEFT:
				x = (x + getHeight() + 2);
				
				break;
			default:
				break;
		}
				if(x>600) {
					x = x - 600;
			}
			
				else if(x<0) {
					x = x + 600;
				
			}
				if(y>400) {
					y = y - 400;
			}
			
				else if(y<0) {
					y = y + 400;
			}
				
			Position position1 = new Position(x,y);
			this.tronModel.addMobileless(new Wall(direction, position1, "player1"));
			
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
