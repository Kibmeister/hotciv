Test list for HotCiv Beta
===================
[OK] Refactor AlphaCiv to use compositional design 
    [OK] This implies creating a Winner strategy and a world aging strategy 
    [OK] Create an interface winner strategy, responsible for selecting the winner of the game 
    [OK] Create an interface age strategy, responsible for controlling the aging of the world 
    [OK] By doing so, we achieve a compositional design by delegating the responsibility to two interfaces
[] The winner is the player that first concurs all the cities in the world 
[] Between 4000BC and 100BC the game progresses with 100 years per round 
[] World age progression around the birth og christ, -100 , -1, +1, +50 
[] Between 50AD and 1750 the game  age progresses with 50 years per round 
[] Between 1750 and 1900 the game age progresses with 25 yesrs per round
[] Between 1900 and 1970 the game age progresses with 5 years per round 
[] After 1970 the game age progresses with 1 year per round