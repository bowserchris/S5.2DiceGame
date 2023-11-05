package itacademy.s5t2.diceGame.constants;

import java.util.HashMap;

public class CommonConstants {	//perhaps make various classes, one constantstring/constantmessages and one with methods, to distinguish what is being called
	
	private CommonConstants() {
		
	}
	
	//Software INFO
	public static final String SOFTWARE_NAME = "Roll or Die\u2122";
	
	//Die constant sides
	public static final int SIDES = 6;
	
	//DiceGame constant win results & condition
	public static final String WINS = "Win";
	public static final String LOSSES = "Lose";
	public static final int WIN_CONDITION = 7;
	
	//Player name constant
	public static final String ANONYMOUS = "ANONYMOUS";
	
	//requesthandlers for swagger
	public static final String[] AUTH_WHITELIST = {
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui.html",
	       // "/index.html",
	        "/webjars/**",
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	      //  "/v2/api-docs",
	      //  "/**",
	     //   "/login/**",
	    //    "/signup/**",
			"/auth/**",			//*1
		//	"/api/v1/auth/**", 
	};
	
	//URL mappings for authentication controller
	//start with /auth
	public static final String INDEX = "/";
	public static final String AUTH_INDEX = "/auth";
	public static final String SIGNUP = "/signup"; 	//goes like /auth/signup
	public static final String LOGIN = "/login";
	
	//URL mappings for UserController
	//start with /users
	public static final String USER_INDEX = "/users";
	public static final String AUTHENTICATED = "/home"; // or "/me" goes like /users/player
	public static final String GET_ALL_USERS = USER_INDEX + "/"; //get all users goes like /users/
	
	//URL mappings for player controller IS CROSSORIGINS NEEDED?
	//START WITH "/"
	public static final String ORIGIN = "http://localhost:8080";
	public static final String PLAYER = "players";
	public static final String ID = "/{id}";
	public static final String SAVE_PLAYER = PLAYER;					//Post "/players" same as update
	public static final String GET_ALL_PLAYERS = PLAYER + INDEX; 		//Get: /players/
	public static final String RANKINGS = PLAYER + "/ranking"; 			//Get: /players/ranking
	public static final String RANKINGS_LOSER = RANKINGS + "/loser";	//Get: /players/ranking/loser
	public static final String RANKINGS_WINNER = RANKINGS + "/winner";	//Get: /players/ranking/winner 
	public static final String PLAYER_ID_PATH = PLAYER + ID;
	
	//URL mappings for game controller
	//START WITH "/players/{id}"
	public static final String GAMES_ALL_OR_PLAY = PLAYER_ID_PATH + "/games/";
	public static final String GAMES_DELETE = PLAYER_ID_PATH +  "/games";
	
	//Security constants
	public static final String BEARER = "Bearer ";
	public static final String HEADER_TYPE_OBJECT = "Accept=application/json";
	public static final String CSRF_NAME = "_csrf";
	public static final String CRUD_METHOD_GET = "GET";
	public static final String CRUD_METHOD_POST = "POST";
	public static final String CRUD_METHOD_PUT = "PUT";
	public static final String CRUD_METHOD_DELETE = "DELETE";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String MEDIA_TYPE_JSON = "application/json";

	//Message constructs
	public static final String PLAYER_NAME = "Player name "; //name value goes in between eg PLAYER_NAME + name + DOES_NOT_EXIST
	public static final String PLAYER_ID = "Player id "; // id value goes in between
	public static final String DICE_GAME_ID = "Dice game id ";
	public static final String DOES_NOT_EXIST = " does not exist.";
	
	//error messages
	public static final String PLAYER_EXISTS = "Player already exists.";
	public static final String NAME_PASSWORD_INCORRECT = "The username or password is incorrect";
	public static final String LOCKED = "The account is locked";
	public static final String UNAUTHORIZED = "You are not authorized to access this resource";
	public static final String JWT_INVALID = "The JWT signature is invalid";
	public static final String JWT_EXPIRED = "The JWT token has expired";
	public static final String UNKNOWN_INTERNAL_ERROR = "Unknown internal server error, check current business logic";
	public static final String VALIDATION_ERROR = "Validation Errors, try with different input";
	public static final String CONSTRAINT_VIOLATION = "Constraint Violation";
	public static final String PLAYER_NOT_FOUND = "Player Not Found";	//also used for user NOT FOUND
	public static final String GAME_NOT_FOUND = "Dice Game Not Found";
	public static final String INVALID_USER = "Invalid user or password";
	public static final String INTERNAL_SERVER_ERR = "Internal Server Error, check current operation";
	public static final String APPLICATION_ERROR = "Application specific error, check current operation";
	public static final String LIST_IS_EMPTY = "The list is empty. There are no games in the Database";
	public static final String USER_UNAUTHENTICATED = "User is not authenticated";
	
	//response messages
	public static final String SUCCESSFUL = "Request successfuly made";
	public static final String PLAYER_CREATED = "The player was created successfully";
	public static final String PLAYER_UPDATED = "The player was updated successfully";
	public static final String LIST_RETURNED = "The list was returned successfully";
	public static final String GAME_CREATED = "Were ya lucky punk?";
	public static final String SNAKE_EYES = "SNAKE (*) (*) EYES ";
	public static final String GAME_DELETED = "Games were deleted correctly";
	
	//security error messages
	public static final String NOT_SET_USER_AUTHENTICATION = "Could not set user authentication in security context";
	
	//swagger api code responses
	public static final String CODE_200 = "200";
	public static final String CODE_201 = "201";
	public static final String CODE_204 = "204";
	public static final String CODE_400 = "400";
	public static final String CODE_401 = "401";
	public static final String CODE_403 = "403";
	public static final String CODE_404 = "404";
	public static final String CODE_500 = "500";
	public static final String CODE_1001 = "1001";
	
	public static final String PROPERTY_DESCRIPTION = "description";
	
	
	//calculates average success rate in various classes
	public static double calculateAverageSuccessRate(double wins, double size) {
		return (wins / size) * 100;
	}
	
	//return messages with variables
	public static String returnNameDoesNotExistMSG(String name) {
		return PLAYER_NAME + name + DOES_NOT_EXIST;
	}
	
	public static String returnPlayerIdDoesNotExistMSG(long id) {
		return PLAYER_ID + id + DOES_NOT_EXIST;
	}
	
	public static String returnDiceGameIdDoesNotExistMSG(long id) {
		return DICE_GAME_ID + id + DOES_NOT_EXIST;
	}
	
	public static HashMap<String, Integer> createPlayerMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(CommonConstants.WINS, 0);
		map.put(CommonConstants.LOSSES, 0);
		return map;
	}
	/// can annotations be called in as constants?? apireponse is too much and very cluttered
	// saw a record object/type/class used instead of a dto. is it like lombok without importing? has its own getters and setters

}
