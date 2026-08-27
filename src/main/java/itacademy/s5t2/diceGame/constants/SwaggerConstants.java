package itacademy.s5t2.diceGame.constants;

import static itacademy.s5t2.diceGame.constants.CommonConstants.SOFTWARE_NAME;

/**
 * Constants class for Swagger API documentation
 *
 * @author bowser-chris
 */
public class SwaggerConstants {

	///// MAIN LAYER /////
	/// dice game application
	public static final String SWAGGER_TITLE = SOFTWARE_NAME + " API";
	public static final String SWAGGER_DESCRIPTION = "Welcome to " + SOFTWARE_NAME + "! "
			+ "You feeling lucky? Well? Do ya Punk!?";
	public static final String TERMS_SERVICE = "Free to use";
	public static final String CONTACT_NAME = "Christian";
	public static final String CONTACT_EMAIL = "email.com";
	public static final String LICENSE_NAME = "API License";
	public static final String LICENSE_URL = "Affiliated " + SOFTWARE_NAME + " website to be placed here";
	public static final String SECURITY_NAME_JWT = "jwtopenapi";
	public static final String SECURITY_SCHEME_BASIC = "basic";
	public static final String SECURITY_NAME_BEARER = "Bearer Authentication";
	public static final String SECURITY_SCHEME_BEARER = "bearer";
	public static final String SECURITY_FORMAT = "JWT";

	///// BUSINESS LAYER /////
	/// controller dto player
	public static final String TAG_NAME_PLAYER_CONTROLLER = "Player controller options";
	public static final String DESCRIPTION_PLAYER_CONTROLLER = "This controller contains the methods to play the game";
	public static final String SUMMARY_ADDPLAYER = "Add a new Player";
	public static final String DESCRIPTION_ADDPLAYER = "Checks if name isn´t already taken, then creates new player in the database";
	public static final String PARAMETER_PLAYER = "Player details needed to create Player object";
	public static final String SUMMARY_GET_1_PLAYER = "Returns player by id";
	public static final String DESCRIPTION_GET_1_PLAYER = "Finds and returns player by their id";
	public static final String PARAMETER_PLAYER_ID = "Player id needed to return player object";
	public static final String SUMMARY_PLAY_GAME = "ROLL OR DIE!";
	public static final String DESCRIPTION_PLAY_GAME = "Selected player rolls a die, then results are saved in DB";
	public static final String PARAMETER_PLAY_GAME = "Id of Player needed in order to play the game";
	public static final String SUMMARY_DELETE_ROLLS = "Delete all game rolls";
	public static final String DESCRIPTION_DELETE_ROLLS = "Selected player deletes their game history";
	public static final String SUMMARY_GET_ALL_GAMES = "Returns list of games";
	public static final String DESCRIPTION_GET_ALL_GAMES = "Returns the game history of the selected player";
	public static final String PARAMETER_PLAYER_ID_ALL_GAMES = "Id of Player needed in order to retrieve their games";
	public static final String SUMMARY_GET_ALL_RATIO = "Gets a list of players";
	public static final String DESCRIPTION_GET_ALL_RATIO = "Gets a list of all players and their respective success rate";
	public static final String PARAMETER_PLAYER_NAME_SUCCESS_RATIO = "Player name as an option, in case need to search specific player";
	public static final String SUMMARY_TOTAL_AVERAGE = "Returns average ranking of all players";
	public static final String DESCRIPTION_TOTAL_AVERAGE = "Returns an average success rate of all players in the DB";
	public static final String SUMMARY_WORSE_SUCCESS = "Returns worst ranking";
	public static final String DESCRIPTION_WORSE_SUCCESS = "Returns the player with the worst sucess rate";
	public static final String SUMMARY_BEST_SUCCESS = "Returns best ranking";
	public static final String DESCRIPTION_BEST_SUCCESS = "Returns the player with the best sucess rate";
	public static final String SUMMARY_UPDATE_PLAYER = "Update the Player";
	public static final String DESCRIPTION_UPDATE_PLAYER = "Finds player by name and updates them in the DB";
	public static final String PARAMETER_UPDATE_PLAYER = "Player details needed to update Player name only";
	/// domain dice game
	public static final String DESCRIPTION_CLASS_DICEGAME = "Details of a Dice Game object";
	public static final String DESCRIPTION_GAME_ID = "Unique id of the DiceGame";
	public static final String EXAMPLE_GAME_ID = "1";
	public static final String DESCRIPTION_RESULT_FIRST = "Value of 1st Die";
	public static final String NAME_RESULT_FIRST = "dieResult1";
	public static final String DESCRIPTION_RESULT_SECOND = "Value of 2nd Die";
	public static final String NAME_RESULT_SECOND = "dieResult2";
	public static final String DESCRIPTION_RESULT_GAME = "Result of the Game";
	public static final String NAME_REUSLT_GAME = "gameResult";
	/// domain player
	public static final String DESCRIPTION_CLASS_PLAYER = "Details of Player object";
	public static final String DESCRIPTION_PLAYER_ID = "Unique id of the Player";
	public static final String NAME_PLAYER_ID = "idPlayer";
	public static final String EXAMPLE_PLAYER_ID = "1";
	public static final String DESCRIPTION_PLAYER_REGISTRATION = "Player's registration date";
	public static final String NAME_PLAYER_REGISTRATION = "registrationDate";
	public static final String DESCRIPTION_PLAYER_NAME = "Player name";
	public static final String NAME_PLAYER_NAME = "playerName";
	public static final String EXAMPLE_PLAYER_NAME = "ANONYMOUS";
	public static final String DESCRIPTION_PLAYER_SUCCESS = "Player success rate";
	public static final String NAME_PLAYER_SUCCESS = "successRate";
	public static final String DESCRIPTION_PLAYER_RATIO = "Player win/loss ratio";
	public static final String NAME_PLAYER_RATIO = "Win/Loss Ratio";
	public static final String EXAMPLE_PLAYER_RATIO = "{}";
	public static final String DESCRIPTION_PLAYER_GAMES = "List of games a player has played";
	public static final String NAME_PLAYER_GAMES = "Game List";
	public static final String EXAMPLE_PLAYER_GAMES = "[]";

	///// CONFIG LAYER /////
	/// config swagger
	public static final String SWAGGER_DESCRIPTION_DEMO = SOFTWARE_NAME + " demo application";
	public static final String SWAGGER_VERSION = "v1.0.3";
	public static final String SWAGGER_LICENSE = "Apache 2.0";
	public static final String SWAGGER_SPRINGDOC_URL = "http://springdoc.org";
	public static final String SWAGGER_DESCRIPTION_EXTERNAL = SOFTWARE_NAME + " Wiki Documentation";
	public static final String SWAGGER_URL_STRING = "website";

	///// SECURITY LAYER /////
	/// controller auth
	public static final String TAG_NAME_AUTH_CONTROLLER = "Authentication";
	public static final String DESCRIPTION_AUTH_CONTROLLER = "This controller allows to register, update or authenticate the player and generates the access token to play the game";
	public static final String SUMMARY_SIGNUP = "Registers a player";
	public static final String DESCRIPTION_SIGNUP = "Registers a player within the database";
	public static final String PARAMETER_REGISTER_DTO = "Details of user to register 1st time";
	public static final String SUMMARY_AUTHENTICATE = "Checks login credentials";
	public static final String DESCRIPTION_AUTHENTICATE = "Login section to check input credentials";
	public static final String PARAMETER_LOGIN_DTO = "Login details to be inputted";
	/// controller user
	public static final String SUMMARY_USER_AUTHENTICATED = "Sign in successful";
	public static final String DESCRIPTION_USER_AUTHENTICATED = "Player signed in successfully and is sent to their homepage";
	public static final String SUMMARY_ALL_USERS = "Gets all users";
	public static final String DESCRIPTION_ALL_USERS = "Returns all users/players in DB";
	/// domain user
	public static final String DESCRIPTION_CLASS_USER = "Details of User object";
	public static final String DESCRIPTION_USER_ID = "Unique id of the User for Database";
	public static final String NAME_USER_ID = "id";
	public static final String DESCRIPTION_USER_USERNAME = "User name";
	public static final String NAME_USER_USERNAME = "username";
	public static final String DESCRIPTION_USERNAME_PASSWORD = "User password";
	public static final String NAME_USERNAME_PASSWORD = "password";
	public static final String DESCRIPTION_ACCOUNT_ON = "If account is enabled or not";
	public static final String NAME_ACCOUNT_ON = "enabled";
	public static final String DESCRIPTION_ROLE = "Role given to account";
	public static final String NAME_ROLE = "enabled";
	/// dto login user
	public static final String DESCRIPTION_USERNAME = "This is the username of the player required to login";
	public static final String EXAMPLE_USERNAME = "buckRogers";
	public static final String DESCRIPTION_PASSWORD = "This is the password of the player required to login";
	public static final String EXAMPLE_PASSWORD = "bucknekked";
	/// dto register user
	public static final String DESCRIPTION_USERNAME_REGISTRATION = "This is the name of the player.";
	public static final String EXAMPLE_USERNAME_REGISTRATION = "Chris";
	public static final String DESCRIPTION_PASSWORD_REGISTRATION = "This is the password that will be required to login";
	public static final String EXAMPLE_PASSWORD_REGISTRATION = "rollerderby123";
	/// response login
	public static final String DESCRIPTION_TOKEN = "This is the token created when user has been authenticated";
	public static final String DESCRIPTION_EXPIRATION = "This is the time value left for the token. Default is 1hr";

	public SwaggerConstants() {
	}
}

