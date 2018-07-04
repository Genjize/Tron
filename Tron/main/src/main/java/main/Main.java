package main;

import controller.TronController;
import model.Dimension;
import model.Direction;
import model.LightCycle;
import model.Position;
import model.TronModel;
import view.TronView;

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
    	
		final TronModel tronModel = new TronModel();

		tronModel.buildArea(new Dimension(1000, 700));
		tronModel.addMobile(new LightCycle(0, Direction.RIGHT, new Position(10, 60), "F4U"));
		tronModel.addMobile(new LightCycle(1, Direction.RIGHT, new Position(10, 590), "Zero"));
	

		final TronController tronController = new TronController(tronModel);
		final TronView tronView = new TronView(tronController, tronModel, tronModel);
		tronController.setViewSystem(tronView);
		tronController.play();
    	
       // final ControllerFacade controller = new ControllerFacade(new ViewFacade(), new ModelFacade());

        
        		// BDD  //
       /* try {
            controller.start();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        } */
    }

}
