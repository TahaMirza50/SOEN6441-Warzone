package model.gamePhases;

import controller.MapEditor;
import model.abstractClasses.GameController;
import model.abstractClasses.GamePhase;
import utils.GameEngine;

import java.util.List;

/**
 * The type Map editor phase.
 *  @author Taha Mirza
 *  @author  Shariq Anwar
 */
public class MapEditorPhase extends GamePhase {

    /**
     * Instantiates a new Map editor phase.
     *
     * @param p_GameEngine the p game engine
     */
    public MapEditorPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public GameController getController() {
        return new MapEditor(this.d_GameEngine);
    }
}
