package cs3500.pa03.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.Coord.Board;
import cs3500.pa03.Coord.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ships.Ship;
import cs3500.pa03.Ships.ShipType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * checks tests for the BattleModel class
 */
class BattleModelTest {
  private Model testModel;

  @BeforeEach
  public void setUp() {
    testModel = new BattleModel();
  }

  /**
   * checks tests for the checkDimensions method
   */
  @Test
  void checkDimensions() {
    assertFalse(testModel.checkDimensions(new int[] {5, 5}));
    assertFalse(testModel.checkDimensions(new int[] {5, 15}));
    assertTrue(testModel.checkDimensions(new int[] {6, 15}));
    assertTrue(testModel.checkDimensions(new int[] {8, 8}));
  }

  /**
   * checks tests for the getBoardsToDisplay method
   */
  @Test
  void getBoardsToDisplay() {
    testModel.setDimensions(new int[] {10, 10});
    Board testBoard = new Board(10, 10);
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 1));
    shots.add(new Coord(1, 2));
    shots.add(new Coord(1, 3));
    shots.add(new Coord(1, 4));
    testModel.addCompBoard(testBoard);
    testModel.addUserShips(new Comp(testModel,
        "CPU", shots).setup(10, 10, testModel.setRandomFleet()));
    testModel.addCompShips(new Comp(testModel,
        "CPU2", shots).setup(10, 10, testModel.setRandomFleet()));
    testModel.addUserBoard(testBoard);
    assertEquals("Opponent Board Data: \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "Your Board:\n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "- - - - - - - - - - \n"
            + "Please Enter 10 Shots:\n"
            + "Remember the board starts at (0, 0) and goes to dimensions - 1\n"
            + "------------------------------------------------------------------\n",
        testModel.getBoardsToDisplay());
  }

  /**
   * checks tests for the getSmallestDim method
   */
  @Test
  void getSmallestDim() {
    testModel.setDimensions(new int[] {6, 15});
    assertEquals(6, testModel.getSmallestDim());
  }

  /**
   * checks tests for the checkFleet method
   */
  @Test
  void checkFleet() {
    testModel.setDimensions(new int[] {6, 15});
    assertTrue(testModel.checkFleet(new int[] {1, 1, 1, 1}));
    assertTrue(testModel.checkFleet(new int[] {1, 1, 1, 2}));
    assertTrue(testModel.checkFleet(new int[] {2, 1, 1, 2}));
    assertFalse(testModel.checkFleet(new int[] {2, 10, 1, 2}));
    assertFalse(testModel.checkFleet(new int[] {2, 3, 1, 2}));
    assertFalse(testModel.checkFleet(new int[] {2, 3, 1, 0}));
  }

  /**
   * checks tests for the setDimensions method
   */
  @Test
  void setDimensions() {
    assertEquals(0, testModel.getHeight());
    assertEquals(0, testModel.getWidth());
    testModel.setDimensions(new int[] {10, 10});

    assertEquals(10, testModel.getHeight());
    assertEquals(10, testModel.getWidth());
  }

  /**
   * checks tests for the convertFleet method
   */
  @Test
  void convertFleet() {
    Map<ShipType, Integer> testFleet = new LinkedHashMap<>();
    testFleet.put(ShipType.CARRIER, 1);
    testFleet.put(ShipType.BATTLESHIP, 1);
    testFleet.put(ShipType.DESTROYER, 1);
    testFleet.put(ShipType.SUBMARINE, 1);

    assertEquals(testFleet, testModel.convertFleet(new int[] {1, 1, 1, 1}));

    Map<ShipType, Integer> testFleet2 = new LinkedHashMap<>();
    testFleet2.put(ShipType.CARRIER, 2);
    testFleet2.put(ShipType.BATTLESHIP, 2);
    testFleet2.put(ShipType.DESTROYER, 2);
    testFleet2.put(ShipType.SUBMARINE, 2);
    assertEquals(testFleet2, testModel.convertFleet(new int[] {2, 2, 2, 2}));
  }

  /**
   * checks tests for the setRandomFleet method
   */
  @Test
  void setRandomFleet() {
    Map<ShipType, Integer> testFleet = new LinkedHashMap<>();
    testFleet.put(ShipType.CARRIER, 1);
    testFleet.put(ShipType.BATTLESHIP, 1);
    testFleet.put(ShipType.DESTROYER, 1);
    testFleet.put(ShipType.SUBMARINE, 1);

    Map<ShipType, Integer> testFleet2 = testModel.setRandomFleet();
    assertNotNull(testFleet2);
    for (ShipType type : testFleet2.keySet()) {
      assertTrue(testFleet2.get(type) >= 1);
    }
  }

  /**
   * checks tests for the getShips method
   */
  @Test
  void getShips() {
    assertEquals(4, testModel.getShips(new int[] {1, 1, 1, 1}));
    assertEquals(8, testModel.getShips(new int[] {2, 2, 2, 2}));
  }

  /**
   * checks tests for the addCompBoard method
   */
  @Test
  void addCompBoard() {
    assertNull(testModel.getComputerBoard());
    testModel.addCompBoard(new Board(10, 10));
    assertNotNull(testModel.getComputerBoard());
  }

  /**
   * checks tests for the getComputerBoard method
   */
  @Test
  void getComputerBoard() {
    assertNull(testModel.getComputerBoard());
    Board testBoard = new Board(10, 10);
    testModel.addCompBoard(testBoard);
    assertEquals(testBoard, testModel.getComputerBoard());
  }

  /**
   * checks tests for the addUserBoard method
   */
  @Test
  void addUserBoard() {
    assertNull(testModel.getUserBoard());
    testModel.addUserBoard(new Board(10, 10));
    assertNotNull(testModel.getUserBoard());
  }

  /**
   * checks tests for the getUserBoard method
   */
  @Test
  void getUserBoard() {
    assertNull(testModel.getUserBoard());
    Board testBoard = new Board(10, 10);
    testModel.addUserBoard(testBoard);
    assertEquals(testBoard, testModel.getUserBoard());
  }

  /**
   * checks tests for the getHeight method
   */
  @Test
  void getHeight() {
    assertEquals(0, testModel.getHeight());
    testModel.setDimensions(new int[] {6, 6});
    assertEquals(6, testModel.getHeight());
  }

  /**
   * checks tests for the getWidth method
   */
  @Test
  void getWidth() {
    assertEquals(0, testModel.getWidth());
    testModel.setDimensions(new int[] {6, 6});
    assertEquals(6, testModel.getWidth());
  }

  /**
   * checks tests for the addUserShips method
   */
  @Test
  void addUserShips() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    assertNull(testModel.getUserShips());
    testModel.addUserShips(shipList);
    assertTrue(testModel.getUserShips().size() > 0);
  }

  /**
   * checks tests for the addCompShips method
   */
  @Test
  void addCompShips() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    assertNull(testModel.getComputerShips());
    testModel.addCompShips(shipList);
    assertTrue(testModel.getComputerShips().size() > 0);
  }

  /**
   * checks tests for the checkShots method
   */
  @Test
  void checkShots() {
    testModel.setDimensions(new int[] {6, 6});
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 1));
    shots.add(new Coord(1, 2));
    shots.add(new Coord(1, 3));
    shots.add(new Coord(1, 4));
    testModel.addUserShips(new Comp(testModel,
        "CPU", shots).setup(6, 6, testModel.setRandomFleet()));
    assertTrue(testModel.checkShots(new int[] {1, 2, 2, 3, 3, 4, 4, 5, 5, 6}));
    assertFalse(testModel.checkShots(new int[] {8, 2, 2, 3, 3, 4, 4, 5, 5, 6}));
  }

  /**
   * checks tests for the convertShots method
   */
  @Test
  void convertShots() {
    ArrayList<Coord> testCoords = new ArrayList<>();
    testCoords.add(new Coord(1, 1));
    testCoords.add(new Coord(2, 2));
    List<Coord> modelCoords = testModel.convertShots(new int[] {1, 1, 2, 2});
    System.out.println("Row: "
        + modelCoords.get(0).getRow() + " Col: "
        + modelCoords.get(0).getCol());
    System.out.println("Row: "
        + modelCoords.get(1).getRow()
        + " Col: "
        + modelCoords.get(1).getCol());

    for (int i = 0; i < testCoords.size(); i++) {
      assertEquals(testCoords.get(i).getCol(), modelCoords.get(i).getCol());
      assertEquals(testCoords.get(i).getRow(), modelCoords.get(i).getRow());
    }
  }

  /**
   * checks tests for the randomShots method
   */
  @Test
  void randomShots() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    testModel.addCompShips(shipList);
    assertNotNull(testModel.randomShots());
  }

  /**
   * checks tests for the updateCompBoard method
   */
  @Test
  void updateCompBoard() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    testModel.addCompShips(shipList);

    Board testBoard = new Board(10, 10);
    testModel.setDimensions(new int[] {10, 10});
    testModel.addCompBoard(testBoard);
    for (Coord[] row : testBoard.getBoard()) {
      for (Coord coord : row) {
        assertFalse(coord.getShot());
        assertFalse(coord.getHide());
      }
    }

    ArrayList<Coord> testHits = new ArrayList<>();
    testHits.add(new Coord(1, 1));
    testHits.add(new Coord(2, 2));

    ArrayList<Coord> testMisses = new ArrayList<>();
    testHits.add(new Coord(3, 1));
    testHits.add(new Coord(4, 2));

    testModel.updateCompBoard(testHits, testMisses);

    int counter = 0;
    for (Coord[] row : testModel.getComputerBoard().getBoard()) {
      for (Coord coord : row) {
        if (coord.getShot()) {
          counter++;
        }
      }
    }
    System.out.println(testBoard.getBoardToDisplay());
    assertTrue(counter > 0);
  }

  /**
   * checks tests for the updateUserBoard method
   */
  @Test
  void updateUserBoard() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    testModel.addUserShips(shipList);

    Board testBoard = new Board(10, 10);
    testModel.setDimensions(new int[] {10, 10});
    testModel.addUserBoard(testBoard);
    for (Coord[] row : testBoard.getBoard()) {
      for (Coord coord : row) {
        assertFalse(coord.getShot());
        assertFalse(coord.getHide());
      }
    }

    ArrayList<Coord> testHits = new ArrayList<>();
    testHits.add(new Coord(1, 1));
    testHits.add(new Coord(2, 2));

    ArrayList<Coord> testMisses = new ArrayList<>();
    testHits.add(new Coord(3, 1));
    testHits.add(new Coord(4, 2));

    testModel.updateUserBoard(testHits, testMisses);

    int counter = 0;
    for (Coord[] row : testModel.getUserBoard().getBoard()) {
      for (Coord coord : row) {
        if (coord.getShot()) {
          counter++;
        }
      }
    }
    System.out.println(testBoard.getBoardToDisplay());
    assertTrue(counter > 0);
  }

  /**
   * checks tests for the showUserShots method
   */
  @Test
  void showUserShots() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    testModel.addUserShips(shipList);

    Board testBoard = new Board(10, 10);
    testModel.setDimensions(new int[] {10, 10});
    testModel.addUserBoard(testBoard);
    testModel.addCompBoard(testBoard);
    for (Coord[] row : testBoard.getBoard()) {
      for (Coord coord : row) {
        assertFalse(coord.getShot());
        assertFalse(coord.getHide());
      }
    }

    ArrayList<Coord> testHits = new ArrayList<>();
    testHits.add(new Coord(1, 1));
    testHits.add(new Coord(2, 2));

    ArrayList<Coord> testMisses = new ArrayList<>();
    testHits.add(new Coord(3, 1));
    testHits.add(new Coord(4, 2));

    testModel.showUserShots(testHits, testMisses);

    int counter = 0;
    for (Coord[] row : testModel.getUserBoard().getBoard()) {
      for (Coord coord : row) {
        if (coord.getShowOpp()) {
          counter++;
        }
      }
    }
    assertTrue(counter > 0);
  }

  /**
   * checks tests for the showOppShots method
   */
  @Test
  void showOppShots() {
    List<Ship> shipList = new ArrayList<>();
    Ship testShip = new Ship(ShipType.BATTLESHIP);
    Ship testShip2 = new Ship(ShipType.CARRIER);
    shipList.add(testShip);
    shipList.add(testShip2);
    testModel.addUserShips(shipList);

    Board testBoard = new Board(10, 10);
    testModel.setDimensions(new int[] {10, 10});
    testModel.addUserBoard(testBoard);
    testModel.addCompBoard(testBoard);
    for (Coord[] row : testBoard.getBoard()) {
      for (Coord coord : row) {
        assertFalse(coord.getShot());
        assertFalse(coord.getHide());
      }
    }

    ArrayList<Coord> testHits = new ArrayList<>();
    testHits.add(new Coord(1, 1));
    testHits.add(new Coord(2, 2));

    ArrayList<Coord> testMisses = new ArrayList<>();
    testHits.add(new Coord(3, 1));
    testHits.add(new Coord(4, 2));

    testModel.showUserShots(testHits, testMisses);

    int counter = 0;
    for (Coord[] row : testModel.getUserBoard().getBoard()) {
      for (Coord coord : row) {
        if (coord.getShowOpp()) {
          counter++;
        }
      }
    }
    assertTrue(counter > 0);
  }

  /**
   * checks tests for the allSunk method
   */
  @Test
  void allSunk() {
    List<Ship> testShips = new ArrayList<>();
    testShips.add(new Ship(ShipType.BATTLESHIP));
    testShips.add(new Ship(ShipType.BATTLESHIP));
    testShips.add(new Ship(ShipType.BATTLESHIP));
    for (int i = 0; i < testShips.size(); i++) {
      for (Coord c : testShips.get(i).getCoords()) {
        c.setShot();
      }
    }
    assertTrue(testModel.allSunk(testShips));

    List<Ship> testShips2 = new ArrayList<>();
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    for (int i = 0; i < testShips.size(); i++) {
      testShips2.get(i).addCoord(new Coord(10, 10));
    }
    assertFalse(testModel.allSunk(testShips2));
  }

  /**
   * checks tests for the getEndUser method
   */
  @Test
  void getEndUser() {
    assertNull(testModel.getEndUser());
    testModel.setUserEndMessage(GameResult.WIN, "You sunk all other ships");
    assertEquals("You have obtained a WIN because You sunk all other ships",
        testModel.getEndUser());
  }

  /**
   * checks tests for the getEndComp method
   */
  @Test
  void getEndComp() {
    assertNull(testModel.getEndComp());
    testModel.setCompEndMessage(GameResult.WIN, "You sunk all other ships");
    assertEquals("You have obtained a WIN because You sunk all other ships",
        testModel.getEndComp());
  }

  /**
   * checks tests for the setUserEndMessage method
   */
  @Test
  void setUserEndMessage() {
    assertNull(testModel.getEndUser());
    testModel.setUserEndMessage(GameResult.WIN, "You sunk all other ships");
    assertEquals("You have obtained a WIN because You sunk all other ships",
        testModel.getEndUser());
  }

  /**
   * checks tests for the setCompEndMessage method
   */
  @Test
  void setCompEndMessage() {
    assertNull(testModel.getEndComp());
    testModel.setCompEndMessage(GameResult.WIN, "You sunk all other ships");
    assertEquals("You have obtained a WIN because You sunk all other ships",
        testModel.getEndComp());
  }

  /**
   * checks tests for the getEndMessages method
   */
  @Test
  void getEndMessages() {
    testModel.setCompEndMessage(GameResult.WIN, "You sunk all other ships");
    testModel.setUserEndMessage(GameResult.LOSE, "All your ships sunk");
    assertEquals("You have obtained a LOSE because All your ships sunk\n"
        + "You have obtained a WIN because You sunk all other ships", testModel.getEndMessages());
  }

  /**
   * checks tests for the allUnsunk method
   */
  @Test
  void allUnsunk() {
    List<Ship> testShips = new ArrayList<>();
    testShips.add(new Ship(ShipType.BATTLESHIP));
    testShips.add(new Ship(ShipType.BATTLESHIP));
    testShips.add(new Ship(ShipType.BATTLESHIP));
    for (int i = 0; i < testShips.size(); i++) {
      for (Coord c : testShips.get(i).getCoords()) {
        c.setShot();
      }
    }
    testModel.addUserShips(testShips);
    assertEquals(0, testModel.allUnsunk());

    List<Ship> testShips2 = new ArrayList<>();
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    testShips2.add(new Ship(ShipType.BATTLESHIP));
    for (int i = 0; i < testShips.size(); i++) {
      testShips2.get(i).addCoord(new Coord(10, 10));
    }

    testModel.getUserShips().clear();
    testModel.addUserShips(testShips2);
    assertEquals(3, testModel.allUnsunk());
  }


}