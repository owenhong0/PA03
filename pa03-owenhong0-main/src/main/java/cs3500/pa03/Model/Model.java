package cs3500.pa03.Model;

import cs3500.pa03.Coord.Board;
import cs3500.pa03.Coord.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ships.Ship;
import cs3500.pa03.Ships.ShipType;
import java.util.List;
import java.util.Map;

/**
 * represents the interface for the model of the battleship game
 */
public interface Model {

  /**
   * determines whether given dimensions are valid board sizes
   *
   * @param dimensions inputted dimensions
   * @return whether a board can be constructed with such dimensions or not
   */
  boolean checkDimensions(int[] dimensions);

  /**
   * determines whether given fleet specifications are valid for the game
   *
   * @param fleetSelection inputted fleet specifications
   * @return whether a fleet can be constructed or not
   */
  boolean checkFleet(int[] fleetSelection);

  /**
   * gets the smallest dimension of the current battleship board
   *
   * @return an int of the smaller dimension of the battleship board
   */
  int getSmallestDim();

  /**
   * sets the dimensions of the battleship board to given dimensions
   *
   * @param boardDimensions inputted dimensions
   */
  void setDimensions(int[] boardDimensions);

  /**
   * sets the cpu's board to a given board
   *
   * @param compBoard an inputted board
   */
  void addCompBoard(Board compBoard);

  /**
   * sets the user's board to a given board
   *
   * @param userBoard an inputted board
   */
  void addUserBoard(Board userBoard);

  /**
   * gets both players' boards to be displayed
   *
   * @return a string representing instructions and each board to the user
   */
  String getBoardsToDisplay();

  /**
   * determines whether inputted shots are valid shots on the board
   *
   * @param shotSelections an inputted array of coordinates
   * @return whether all of those shots fall into the game requirements
   */
  boolean checkShots(int[] shotSelections);

  /**
   * gets the height of the board
   *
   * @return an int of the height
   */
  int getHeight();

  /**
   * gets the width of the board
   *
   * @return the number of columns in the board
   */
  int getWidth();

  /**
   * converts fleet specifications into a map of ships and how many of each there are
   *
   * @param fleetSelection inputted fleet specifications
   * @return a map of a fleet
   */
  Map<ShipType, Integer> convertFleet(int[] fleetSelection);

  /**
   * returns the number of ships specified by the user
   *
   * @param fleetSelection inputted fleet specifications
   * @return total number of ships inputted
   */
  int getShips(int[] fleetSelection);

  /**
   * gets a randomly generated fleet
   *
   * @return a map of a randomly specified fleet
   */
  Map<ShipType, Integer> setRandomFleet();

  /**
   * sets the user's ships to a given list of ships
   *
   * @param p1Ships an inputted list of ships
   */
  void addUserShips(List<Ship> p1Ships);

  /**
   * sets the computer's ships to a given list of ships
   *
   * @param p2Ships an inputted list of ships
   */
  void addCompShips(List<Ship> p2Ships);

  /**
   * converts an array of shot coordinates into a list of coordinates
   *
   * @param shotSelections an inputted array of shot coordinates
   * @return a list of coordinates of shots
   */
  List<Coord> convertShots(int[] shotSelections);

  /**
   * generates a list of random coordinate shots
   *
   * @return a list of coordinates of shots
   */
  List<Coord> randomShots();

  /**
   * gets the computer's board
   *
   * @return a board controlled by the cpu
   */
  Board getComputerBoard();

  /**
   * updates the computer's board with hits and misses
   *
   * @param hitList an inputted list of hits shots
   * @param missList an inputted list of missed shots
   */
  void updateCompBoard(List<Coord> hitList, List<Coord> missList);

  /**
   * gets the current user's board
   *
   * @return a board representing the user
   */
  Board getUserBoard();

  /**
   * updates the user's board with hits and misses
   *
   * @param hitList an inputted list of hit shots
   * @param missList an inputted list of missed shots
   */
  void updateUserBoard(List<Coord> hitList, List<Coord> missList);

  /**
   * updates the user's board with hits and misses to be displayed to the computer
   *
   * @param shotsThatHitOpponentShips an inputted list of hit coordinates
   * @param misses an inputted list of missed coordinates
   */
  void showUserShots(List<Coord> shotsThatHitOpponentShips, List<Coord> misses);

  /**
   * updates the computer's board with hits and misses to be displayed to the user
   *
   * @param shotsThatHitOpponentShips an inputted list of hit coordinates
   * @param misses an inputted list of missed coordinates
   */
  void showOppShots(List<Coord> shotsThatHitOpponentShips, List<Coord> misses);

  /**
   * determines whether all ships in a given list are sunk
   *
   * @param ships a given list of ships
   * @return whether all ships are sunk in that list
   */
  boolean allSunk(List<Ship> ships);

  /**
   * sets the user's end game message
   *
   * @param result a given end state of the game
   * @param reason a string that describes the end state
   */
  void setUserEndMessage(GameResult result, String reason);

  /**
   *  sets the cpu's end game message
   *
   * @param result a given end state of the game
   * @param reason a string that describes the end state
   */
  void setCompEndMessage(GameResult result, String reason);

  /**
   * gets the collective end message for the end of the game
   *
   * @return the string that combines all end states
   */
  String getEndMessages();

  /**
   * gets the number of ships that are not sunk
   *
   * @return int of operational ships
   */
  int allUnsunk();

  /**
   * gets the list of ships a user is playing with
   *
   * @return a list of user's ships
   */
  List<Ship> getUserShips();

  /**
   * gets the list of computer ships
   *
   * @return a list of ships controlled by the cpu
   */
  List<Ship> getComputerShips();

  /**
   * gets the user's end of game message
   *
   * @return a string representing the end state of the game
   */
  String getEndUser();

  /**
   * gets the computer's end of game message
   *
   * @return a string representing the end state of the game
   */
  String getEndComp();
}
