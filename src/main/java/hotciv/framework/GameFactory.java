package hotciv.framework;

public interface GameFactory {

   WinnerStrategy createWinnerStrategy ();
   AgingStrategy createAgingStrategy ();
   UnitStrategy createUnitStrategy ();
   WorldLayoutStrategy createWorldLayoutStrategy ();
   AttackStrategy createAttackStrategy ();
}
