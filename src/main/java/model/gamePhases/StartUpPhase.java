package model.gamePhases;

import controller.GamePlay;
import controller.MapEditor;
import model.abstractClasses.GameController;
import model.abstractClasses.GamePhase;

import java.util.List;

/**
 * The type Start up phase.
 */
public class StartUpPhase extends GamePhase {

    public List<Class<? extends GamePhase>> possibleNextPhases() {
        return List.of(GameLoopPhase.class);
    }

    @Override
    public GameController getController() {
      return new GamePlay();
    }

}
