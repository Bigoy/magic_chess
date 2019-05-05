package com.tssss.bysj.game.core.other;

public class GameController {
    private static GameController gameController;

    private GameController() {
    }

    public static GameController getGameController() {
        if (gameController == null) {
            gameController = new GameController();
        }
        return gameController;
    }

    public void prepare() {
        // Running PositionService, updating army's state.
    }

    public void run() {
    }

    public void gameOver() {
    }
}
