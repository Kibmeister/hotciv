package hotciv.standard.client;



public class OperationNames {

    // Contains constant names for each Game method

    public static final String GAME_PREFIX = "game";
    public static final String CITY_PREFIX = "city";
    public static final String UNIT_PREFIX = "unit";
    public static final String TILE_PREFIX = "tile";

    public static final String GET_TILE_AT = GAME_PREFIX +"-get-tile-at";
    public static final String GET_UNIT_AT = GAME_PREFIX +"-get-unit-at";
    public static final String GET_CITY_AT = GAME_PREFIX +"-get-city-at";
    public static final String GET_PLAYER_IN_TURN = GAME_PREFIX +"-get-player-in-turn";
    public static final String GET_WINNER = GAME_PREFIX +"-get-winner";
    public static final String GET_AGE = GAME_PREFIX +"-get-age";
    public static final String MOVE_UNIT = GAME_PREFIX +"-move-unit";
    public static final String END_OF_TURN = GAME_PREFIX +"-end-of-turn";
    public static final String CHANGE_WORKFORCE_FOCUS_IN_CITY_AT = GAME_PREFIX +"-change-workforce-focus-in-city-at";
    public static final String CHANGE_PRODUCTION_IN_CITY_AT = GAME_PREFIX +"-change-production-in-city-at";
    public static final String PERFORM_UNIT_ACTION_AT = GAME_PREFIX +"-perform-unit-action-at";


    // Constants for City methods
    public static final String CITY_GET_OWNER = CITY_PREFIX + "-get-owner";
    public static final String CITY_GET_SIZE = CITY_PREFIX + "-get-size";
    public static final String CITY_GET_TREASURY = CITY_PREFIX + "-get-treasury";
    public static final String CITY_GET_PRODUCTION = CITY_PREFIX + "-get-production";
    public static final String CITY_GET_WORKFORCE_FOCUS = CITY_PREFIX + "-get-workforce-focus";

    // Constant for Tile method
    public static final String TILE_GET_TYPESTRING = TILE_PREFIX + "-get-typestring";

    // Constants for Unit methods
    public static final String UNIT_GET_DEFENSIVE_STRENGTH = UNIT_PREFIX + "-get-defensive-strength";
    public static final String UNIT_GET_OFFENSIVE_STRENGTH = UNIT_PREFIX + "-get-offensive-strength";
    public static final String UNIT_GET_MOVECOUNT = UNIT_PREFIX + "-get-movecount";
    public static final String UNIT_GET_TYPESTRING = UNIT_PREFIX + "-get-typestring";
    public static final String UNIT_GET_OWNER = UNIT_PREFIX + "-get-owner";
}