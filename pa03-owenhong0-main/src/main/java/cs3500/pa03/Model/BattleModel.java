package cs3500.pa03.Model;

import cs3500.pa03.Coord.Board;
import cs3500.pa03.Coord.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ships.Ship;
import cs3500.pa03.Ships.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents a model of the battleship game
 */
public class BattleModel implements Model {
  private Board userBoard;
  private Board computerBoard;
  private List<Ship> userShips;
  private List<Ship> computerShips;
  private int height = 0;
  private int width = 0;
  private final Random random = new Random();
  private String endUser;
  private String endComp;

  /**
   * determines whether given dimensions are valid board sizes
   *
   * @param dimensions inputted dimensions
   * @return whether a board can be constructed with such dimensions or not
   */
  public boolean checkDimensions(int[] dimensions) {
    boolean validDimensions = true;
    for (int i : dimensions) {
      if (!(i >= 6 && i <= 15)) {
        validDimensions = false;
      }
    }
    return validDimensions;
  }

  /**
   * gets both players' boards to be displayed
   *
   * @return a string representing instructions and each board to the user
   */
  public String getBoardsToDisplay() {
    StringBuilder builder = new StringBuilder();
    builder.append("Opponent Board Data: \n");
    computerBoard.hideBoard();
    builder.append(computerBoard.getBoardToDisplay());
    builder.append("Your Board:\n");
    builder.append(userBoard.getBoardToDisplay());
    builder.append("Please Enter " + notSunk(userShips) + " Shots:\n" +
        "Remember the board starts at (0, 0) and goes to dimensions - 1\n" +
        "------------------------------------------------------------------\n");
    return builder.toString();
  }

  /**
   * gets the smallest dimension of the current battleship board
   *
   * @return an int of the smaller dimension of the battleship board
   */
  public int getSmallestDim() {
    return Math.min(height, width);
  }

  /**
   * determines whether given fleet specifications are valid for the game
   *
   * @param fleetSpec inputted fleet specifications
   * @return whether a fleet can be constructed or not
   */
  public boolean checkFleet(int[] fleetSpec) {
    int sum = 0;
    for (int i : fleetSpec) {
      if (i < 1) {
        return false;
      }
      sum += i;
    }

    return sum <= getSmallestDim();
  }

  /**
   * sets the dimensions of the battleship board to given dimensions
   *
   * @param inputDimensions inputted dimensions
   */
  public void setDimensions(int[] inputDimensions) {
    height = inputDimensions[0];
    width = inputDimensions[1];
  }

  /**
   * gets the list of ships a user is playing with
   *
   * @return a list of user's ships
   */
  public List<Ship> getUserShips() {
    return userShips;
  }

  /**
   * gets the list of computer ships
   *
   * @return a list of ships controlled by the cpu
   */
  public List<Ship> getComputerShips() {
    return computerShips;
  }

  /**
   * converts fleet specifications into a map of ships and how many of each there are
   *
   * @param fleetSpecs inputted fleet specifications
   * @return a map of a fleet
   */
  public Map<ShipType, Integer> convertFleet(int[] fleetSpecs) {
    Map<ShipType, Integer> fleet = new LinkedHashMap<>();
    fleet.put(ShipType.CARRIER, fleetSpecs[0]);
    fleet.put(ShipType.BATTLESHIP, fleetSpecs[1]);
    fleet.put(ShipType.DESTROYER, fleetSpecs[2]);
    fleet.put(ShipType.SUBMARINE, fleetSpecs[3]);
    return fleet;
  }

  /**
   * gets a randomly generated fleet
   *
   * @return a map of a randomly specified fleet
   */
  public Map<ShipType, Integer> setRandomFleet() {
    int[] randomShips = new int[] {1, 1, 1, 1};
    int remainingShips = getSmallestDim() - Arrays.stream(randomShips).sum();
    while (remainingShips > 1) {
      int add = random.nextInt(1, remainingShips);
      randomShips[random.nextInt(4)] += add;
      remainingShips -= add;
    }
    randomShips[random.nextInt(4)]++;
    Map<ShipType, Integer> fleet = new LinkedHashMap<>();
    fleet.put(ShipType.CARRIER, randomShips[0]);
    fleet.put(ShipType.BATTLESHIP, randomShips[1]);
    fleet.put(ShipType.DESTROYER, randomShips[2]);
    fleet.put(ShipType.SUBMARINE, randomShips[3]);
    return fleet;
  }

  /**
   * returns the number of ships specified by the user
   *
   * @param fleetSelection inputted fleet specifications
   * @return total number of ships inputted
   */
  public int getShips(int[] fleetSelection) {
    return Arrays.stream(fleetSelection).sum();
  }

  /**
   * sets the cpu's board to a given board
   *
   * @param inputBoard an inputted board
   */
  public void addCompBoard(Board inputBoard) {
    computerBoard = inputBoard;
  }

  /**
   * gets the computer's board
   *
   * @return a board controlled by the cpu
   */
  public Board getComputerBoard() {
    return computerBoard;
  }

  /**
   * sets the user's board to a given board
   *
   * @param inputBoard an inputted board
   */
  public void addUserBoard(Board inputBoard) {
    userBoard = inputBoard;
  }

  /**
   * gets the current user's board
   *
   * @return a board representing the user
   */
  public Board getUserBoard() {
    return userBoard;
  }

  /**
   * gets the height of the board
   *
   * @return an int of the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * gets the width of the board
   *
   * @return the number of columns in the board
   */
  public int getWidth() {
    return width;
  }

  /**
   * sets the user's ships to a given list of ships
   *
   * @param ships an inputted list of ships
   */
  public void addUserShips(List<Ship> ships) {
    userShips = ships;
  }

  /**
   * sets the computer's ships to a given list of ships
   *
   * @param ships an inputted list of ships
   */
  public void addCompShips(List<Ship> ships) {
    computerShips = ships;
  }

  /**
   * determines whether inputted shots are valid shots on the board
   *
   * @param inputShots an inputted array of coordinates
   * @return whether all of those shots fall into the game requirements
   */
  public boolean checkShots(int[] inputShots) {
    int x = 0;
    int y = 1;

    while (x < userShips.size()) {
      if (inputShots[x] > width
          || inputShots[y] > height
          || inputShots[x] < 0
          || inputShots[y] < 0) {
        return false;
      }
      x += 2;
      y += 2;
    }
    return true;
  }

  /**
   * converts an array of shot coordinates into a list of coordinates
   *
   * @param inputShots an inputted array of shot coordinates
   * @return a list of coordinates of shots
   */
  public List<Coord> convertShots(int[] inputShots) {
    ArrayList<Coord> result = new ArrayList<>();
    int x = 0;
    int y = 1;

    while (x < inputShots.length) {
      Coord newCoord = new Coord(inputShots[x], inputShots[y]);
      result.add(newCoord);
      x += 2;
      y += 2;
    }
    return result;
  }

  /**
   * generates a list of random coordinate shots
   *
   * @return a list of coordinates of shots
   */
  public List<Coord> randomShots() {
    ArrayList<Coord> result = new ArrayList<>();
    while (result.size() < notSunk(computerShips)) {
      result.add(new Coord(random.nextInt(height), random.nextInt(width)));
    }
    return result;
  }

  /**
   * updates the computer's board with hits and misses
   *
   * @param inputCoord an inputted list of hits shots
   * @param inputMisses an inputted list of missed shots
   */
  public void updateCompBoard(List<Coord> inputCoord, List<Coord> inputMisses) {
    updateBoardHelper(inputCoord, inputMisses, computerBoard);
  }

  /**
   * updates the user's board with hits and misses
   *
   * @param hitList an inputted list of hit shots
   * @param missList an inputted list of missed shots
   */
  @Override
  public void updateUserBoard(List<Coord> hitList, List<Coord> missList) {
    updateBoardHelper(hitList, missList, userBoard);
  }

  /**
   * the helper to set shot coordinates to be seen and missed ones to be displayed as well
   *
   * @param hitList an inputted list of hit coordinates
   * @param missList an inputted list of missed coordinates
   * @param inputBoard an inputted board to update
   */
  private void updateBoardHelper(List<Coord> hitList, List<Coord> missList, Board inputBoard) {
    for (Coord coord: hitList) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (coord.getRow() == i && coord.getCol() == j) {
            inputBoard.getBoard()[i][j].setShot();
            inputBoard.getBoard()[i][j].unHide();
          }
        }
      }
    }

    for (Coord coord: missList) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (coord.getRow() == i && coord.getCol() == j) {
            inputBoard.getBoard()[i][j].setShot();
          }
        }
      }
    }
  }

  /**
   * updates the user's board with hits and misses to be displayed to the computer
   *
   * @param hits an inputted list of hit coordinates
   * @param misses an inputted list of missed coordinates
   */
  public void showUserShots(List<Coord> hits, List<Coord> misses) {
    showHelper(hits, misses, computerBoard);
  }

  /**
   * updates the computer's board with hits and misses to be displayed to the user
   *
   * @param hits an inputted list of hit coordinates
   * @param misses an inputted list of missed coordinates
   */
  public void showOppShots(List<Coord> hits, List<Coord> misses) {
    showHelper(hits, misses, userBoard);
  }

  /**
   * the helper that sets coordinates to be displayed to the opponent on a given board
   *
   * @param hits an inputted list of hit coordinates
   * @param misses an inputted list of missed coordinates
   * @param inputBoard an inputted board to be updated
   */
  private void showHelper(List<Coord> hits, List<Coord> misses, Board inputBoard) {
    for (Coord coord: hits) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (coord.getRow() == i && coord.getCol() == j) {
            inputBoard.getBoard()[i][j].setShowOpp();
          }
        }
      }
    }

    for (Coord coord: misses) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (coord.getRow() == i && coord.getCol() == j) {
            inputBoard.getBoard()[i][j].setShowOpp();
          }
        }
      }
    }
  }

  /**
   * determines whether all ships in a given list are sunk
   *
   * @param ships a given list of ships
   * @return whether all ships are sunk in that list
   */
  @Override
  public boolean allSunk(List<Ship> ships) {
    boolean allSunk = true;
    for (Ship s: ships) {
      if (!s.isSunk()) {
        allSunk = false;
      }
    }
    return allSunk;
  }

  /**
   * sets the user's end game message
   *
   * @param result a given end state of the game
   * @param reason a string that describes the end state
   */
  @Override
  public void setUserEndMessage(GameResult result, String reason) {
    endUser = "You have obtained a " + result + " because " + reason;
  }

  /**
   *  sets the cpu's end game message
   *
   * @param result a given end state of the game
   * @param reason a string that describes the end state
   */
  @Override
  public void setCompEndMessage(GameResult result, String reason) {
    endComp = "You have obtained a " + result + " because " + reason;
  }

  /**
   * gets the collective end message for the end of the game
   *
   * @return the string that combines all end states
   */
  public String getEndMessages() {
    StringBuilder builder = new StringBuilder();
    builder.append(endUser + "\n");
    builder.append(endComp);
    return builder.toString();
  }

  /**
   * gets the number of ships that are not sunk
   *
   * @return int of operational ships
   */
  @Override
  public int allUnsunk() {
    int counter = 0;
    for (Ship s: userShips) {
      if (!s.isSunk()) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * gets the number of floating ships in a list of ships
   *
   * @param inputShips a given list of ships
   * @return number of operational ships
   */
  private int notSunk(List<Ship> inputShips) {
    int count = 0;
    for (Ship s : inputShips) {
      if (!s.isSunk()) {
        count ++;
      }
    }
    return count;
  }

  /**
   * gets the user's end of game message
   *
   * @return a string representing the end state of the game
   */
  public String getEndUser() {
    return endUser;
  }

  /**
   * gets the computer's end of game message
   *
   * @return a string representing the end state of the game
   */
  public String getEndComp() {
    return endComp;
  }
}
