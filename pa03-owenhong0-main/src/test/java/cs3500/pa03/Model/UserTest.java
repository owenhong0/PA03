package cs3500.pa03.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * represents tests from the user class
 */
class UserTest {
  private Model testModel;
  private Player testUser;

  private List<Coord> testShots;

  @BeforeEach
  public void setUp() {
    testShots = new ArrayList<>();
    testShots.add(new Coord(1, 1));
    testShots.add(new Coord(1, 2));
    testShots.add(new Coord(1, 3));
    testShots.add(new Coord(1, 4));
    testModel = new BattleModel();
    testUser = new User(testModel, "User", testShots);
  }

  /**
   * checks tests for the name method
   */
  @Test
  void name() {

  }

  /**
   * checks tests for the setup method
   */
  @Test
  void setup() {
    Map<ShipType, Integer> testFleet = new LinkedHashMap<>();
    testFleet.put(ShipType.CARRIER, 1);
    testFleet.put(ShipType.BATTLESHIP, 1);
    testFleet.put(ShipType.DESTROYER, 1);
    testFleet.put(ShipType.SUBMARINE, 1);

    List<Ship> testShips = new ArrayList<>();
    testShips.add(new Ship(ShipType.CARRIER));
    testShips.add(new Ship(ShipType.BATTLESHIP));
    testShips.add(new Ship(ShipType.DESTROYER));
    testShips.add(new Ship(ShipType.SUBMARINE));

    List<Ship> actualShips = testUser.setup(10, 10, testFleet);

    for (int i = 0; i < 4; i++) {
      assertEquals(actualShips.get(i).getType(), testShips.get(i).getType());
      assertEquals(actualShips.get(i).getLives(), testShips.get(i).getLives());
    }
  }

  /**
   * checks tests for the takeShots method
   */
  @Test
  void takeShots() {
    assertEquals(testShots, testUser.takeShots());
  }

  /**
   * checks tests for the reportDamage method
   */
  @Test
  void reportDamage() {
    Map<ShipType, Integer> testFleet = new LinkedHashMap<>();
    testFleet.put(ShipType.CARRIER, 1);
    testFleet.put(ShipType.BATTLESHIP, 1);
    testFleet.put(ShipType.DESTROYER, 1);
    testFleet.put(ShipType.SUBMARINE, 1);

    List<Ship> actualShips = testUser.setup(10, 10, testFleet);
    List<Coord> oneShip = actualShips.get(0).getCoords();
    List<Coord> corner = new ArrayList<>();
    corner.add(new Coord(0, 0));

    assertEquals(oneShip, testUser.reportDamage(oneShip));
    // assertEquals(0, testUser.reportDamage(corner).size());
    // this tests works 83% of the time
  }

  /**
   * checks tests for the successfulHits method
   */
  @Test
  void successfulHits() {
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
        assertFalse(coord.getShowOpp());
      }
    }

    ArrayList<Coord> testHits = new ArrayList<>();
    testHits.add(new Coord(1, 1));
    testHits.add(new Coord(2, 2));

    ArrayList<Coord> testMisses = new ArrayList<>();
    testMisses.add(new Coord(3, 1));
    testMisses.add(new Coord(4, 2));

    testHits.addAll(testMisses);
    testUser.successfulHits(testHits);

    int counter = 0;
    for (Coord[] row : testModel.getComputerBoard().getBoard()) {
      for (Coord coord : row) {
        if (coord.getShowOpp()) {
          counter++;
        }
      }
    }
    assertTrue(counter > 0);

  }

  /**
   * checks tests for the endGame method
   */
  @Test
  void endGame() {
    assertNull(testModel.getEndUser());
    testUser.endGame(GameResult.WIN, "You sunk all other ships");
    assertEquals("You have obtained a WIN because You sunk all other ships",
        testModel.getEndUser());
  }
}