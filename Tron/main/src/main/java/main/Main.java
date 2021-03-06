package main;

import java.sql.SQLException;

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
     * @throws SQLException 
     */
    public static void main(final String[] args) throws SQLException {
    	
		final TronModel tronModel = new TronModel();

		tronModel.buildArea(new Dimension(600, 400));
		tronModel.addMobile(new LightCycle(1, Direction.UP, new Position(50, 300), "player1"));
		tronModel.addMobile(new LightCycle(2, Direction.UP, new Position(500, 300), "player2"));
		//tronModel.addMobileless(new Wall(Direction.UP,new Position(200, 200), "player2"));
		//tronModel.addMobileless(new Wall(Direction.UP,new Position(300, 200), "player1"));
		
	

		final TronController tronController = new TronController(tronModel);
		final TronView tronView = new TronView(tronController, tronModel, tronModel);
		tronController.setViewSystem(tronView);
        try {
        	tronController.play();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        } 
    

    }
}
