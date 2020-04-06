package hotciv.standard;

import hotciv.framework.*;

public class EpsilonAttackStrategy implements AttackStrategy {
    private BattleProbability battleProbability;

    public EpsilonAttackStrategy(BattleProbability battleProbability) {
        this.battleProbability = battleProbability;
    }

    @Override
    public boolean battleOutcome(Position from, Position to, GameImpl game) {
        int a = attackerStrength(from, game);
        int d = defenderStrength(to, game);
        boolean attackerWins = (a > d);
        if(attackerWins){
            game.createUnit(to, game.getUnitAt(from));
            game.removeUnit(to);
            // remove defending unit
            // move the new unit to the position of the defending unit
            return true;
        } else {
            game.removeUnit(from);
            // the attacking unit is removed from the world
            return false;
        }
    }

    public int attackerStrength(Position from, Game game) {
        int a = game.getUnitAt(from).getAttackingStrength();
        a += Utility2.getFriendlySupport(game, from, game.getUnitAt(from).getOwner());
        int b = Utility2.getTerrainFactor(game, from);
        int p = battleProbability.getAttackerProbability();
        return (a*b)*p ;
    }

    public int defenderStrength(Position to, Game game) {
        int d = game.getUnitAt(to).getDefensiveStrength();
        d += Utility2.getFriendlySupport(game,to, game.getUnitAt(to).getOwner());
        int b = Utility2.getTerrainFactor(game, to);
        int p = battleProbability.getDefenderProbability();
        return (d*b)*p;
    }
}
