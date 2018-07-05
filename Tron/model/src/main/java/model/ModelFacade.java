package model;

import model.dao.GameDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * <h1>The Class ModelFacade provides a facade of the Model component.</h1>
 *
 * @author Jean-Aymeric DIET jadiet@cesi.fr
 * @version 1.0
 */
public final class ModelFacade implements IModel {

    /**
     * Instantiates a new model facade.
     */
    public ModelFacade() {
        super();
    }

    
    public void setExampleByInt(final int player, final int time) throws SQLException {
        GameDAO.setExampleByInt(player, time);
    }

}
