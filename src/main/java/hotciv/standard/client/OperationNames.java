package hotciv.standard.client;



public class OperationNames {

    // Contains constant names for each Game method

    public static final String GET_TILE_AT = "game-get-tile-at";
    public static final String GET_UNIT_AT = "game-get-unit-at";
    public static final String GET_CITY_AT = "game-get-city-at";
    public static final String GET_PLAYER_IN_TURN = "game-get-player-in-turn";
    public static final String GET_WINNER = "game-get-winner";
    public static final String GET_AGE = "game-get-age";
    public static final String MOVE_UNIT = "game-move-unit";
    public static final String END_OF_TURN = "game-end-of-turn";
    public static final String CHANGE_WORKFORCE_FOCUS_IN_CITY_AT = "game-change-workforce-focus-in-city-at";
    public static final String CHANGE_PRODUCTION_IN_CITY_AT = "game-change-production-in-city-at";
    public static final String PERFORM_UNIT_ACTION_AT = "game-perform-unit-action-at";


    // Constants for City methods
    public static final String CITY_GET_OWNER = "city-get-owner";
    public static final String CITY_GET_SIZE = "city-get-size";
    public static final String CITY_GET_TREASURY = "city-get-treasury";
    public static final String CITY_GET_PRODUCTION = "city-get-production";
    public static final String CITY_GET_WORKFORCE_FOCUS = "city-get-workforce-focus";

    // Constant for Tile method
    public static final String TILE_GET_TYPESTRING = "tile-get-typestring";

    // Constants for Unit methods
    public static final String UNIT_GET_DEFENSIVE_STRENGTH = "unit-get-defensive-strength";
    public static final String UNIT_GET_OFFENSIVE_STRENGTH = "unit-get-offensive-strength";
    public static final String UNIT_GET_MOVECOUNT = "unit-get-movecount";
    public static final String UNIT_GET_TYPESTRING = "unit-get-typestring";
    public static final String UNIT_GET_OWNER = "unit-get-owner";
}