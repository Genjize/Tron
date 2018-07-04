package main;


/*
import java.sql.SQLException;

import controller.ControllerFacade;
import model.ModelFacade;
import view.ViewFacade;  */

/**
 * <h1>The Class Main.</h1>
 *
 * @author Jean-Aymeric DIET jadiet@cesi.fr
 * @version 1.0
 */
public abstract class Main {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
    	
		final DogfightModel dogfightModel = new DogfightModel();

		dogfightModel.buildArea(new Dimension(1000, 700));
		dogfightModel.addMobile(new Cloud(Direction.LEFT, new Position(200, 200)));
		dogfightModel.addMobile(new Plane(0, Direction.RIGHT, new Position(10, 60), "F4U"));
		dogfightModel.addMobile(new Plane(1, Direction.RIGHT, new Position(10, 590), "Zero"));
		dogfightModel.addMobile(new Cloud(Direction.LEFT, new Position(100, 100)));
		dogfightModel.addMobile(new Cloud(Direction.LEFT, new Position(400, 400)));

		final DogfightController dogfightController = new DogfightController(dogfightModel);
		final DogfightView dogfightView = new DogfightView(dogfightController, dogfightModel, dogfightModel);
		dogfightController.setViewSystem(dogfightView);
		dogfightController.play();
    	
       // final ControllerFacade controller = new ControllerFacade(new ViewFacade(), new ModelFacade());

        
        		// BDD  //
       /* try {
            controller.start();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        } */
    }

}
