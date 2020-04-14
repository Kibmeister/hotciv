package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class ZetaWinnerStrategy implements WinnerStrategy {
    private WinnerStrategy betaWinnerStrategy, epsilonWinnerStrategy, currentWinnerStrategy;
    private int round;
    private boolean exceeded20rounds;

    public ZetaWinnerStrategy(BetaWinnerStrategy betaWinnerStrategy, EpsilonWinnerStrategy epsilonWinnerStrategy) {
        this.betaWinnerStrategy = betaWinnerStrategy;
        this.epsilonWinnerStrategy = epsilonWinnerStrategy;
        this.currentWinnerStrategy = betaWinnerStrategy;
        this.round = 1;
        exceeded20rounds = false;
    }

    public void dualWinnerStrategy() {
        if (round > 20) {
            currentWinnerStrategy = epsilonWinnerStrategy;
        } else {
            currentWinnerStrategy = betaWinnerStrategy;
        }
    }

    @Override
    public Player getWinner(GameImpl game) {
        return currentWinnerStrategy.getWinner(game);
    }

    @Override
    public void setWinner(Player battleWinner) {

        currentWinnerStrategy.setWinner(battleWinner);
    }

    @Override
    public void roundEnded(GameImpl game) {
        if (game.getPlayerInTurn() == Player.RED) {
            round++;
        }
        dualWinnerStrategy();
    }
}
