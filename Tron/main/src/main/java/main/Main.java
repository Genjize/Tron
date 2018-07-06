package main;

import java.sql.SQLException;

import controller.ControllerFacade;
import controller.TronController;
import model.Dimension;
import model.Direction;
import model.LightCycle;
import model.ModelFacade;
import model.Position;
import model.TronModel;
import model.Wall;
import view.TronView;
import view.ViewFacade;

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

		tronModel.buildArea(new Dimension(600, 400));
		tronModel.addMobile(new LightCycle(0, Direction.UP, new Position(50, 360), "player1"));
		tronModel.addMobile(new LightCycle(1, Direction.UP, new Position(500, 360), "player2"));
		//tronModel.addMobileless(new Wall(new Position(1, 1), "player2"));
	

		final TronController tronController = new TronController(tronModel);
		final TronView tronView = new TronView(tronController, tronModel, tronModel);
		tronController.setViewSystem(tronView);
		tronController.play();
    	
       final ControllerFacade controller = new ControllerFacade(new ViewFacade(), new ModelFacade());

        
        		// BDD  //
        try {
            controller.start();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        } 
    }

}
