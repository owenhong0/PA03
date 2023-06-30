package cs3500.pa03.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Coord.Board;
import cs3500.pa03.Coord.Coord;
import cs3500.pa03.Model.BattleModel;
import cs3500.pa03.Model.Comp;
import cs3500.pa03.Model.Model;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the BattleView class
 */
class BattleViewTest {
  private View testView;
  private View mockView;
  private StringWriter writer;
  private MockAppendable mock;
  private Model model;

  @BeforeEach
  public void setUp() {
    writer = new StringWriter();
    testView = new BattleView(writer);
    mock = new MockAppendable();
    mockView = new BattleView(mock);
    model = new BattleModel();
  }

  /**
   * checks tests for the printWelcome method
   */
  @Test
  void printWelcome() {
    int[] ints = new int[] {10, 10};
    for (int i = 0; i < 2; i++) {
      assertEquals(ints[i], testView.printWelcome(new StringReader("10 10"))[i]);
    }

    Runnable runnable = () -> mockView.printWelcome(new StringReader("10 10"));
    runnable.run();
  }

  /**
   * checks tests for the printInvalidBoard method
   */
  @Test
  void printInvalidBoard() {
    int[] ints = new int[] {10, 10};
    for (int i = 0; i < 2; i++) {
      assertEquals(ints[i], testView.printInvalidBoard(new StringReader("10 10"))[i]);
    }

    Runnable runnable = () -> mockView.printInvalidBoard(new StringReader("10 10"));
    runnable.run();
  }

  /**
   * checks tests for the printFleetSelection method
   */
  @Test
  void printFleetSelection() {
    int[] ints = new int[] {1, 1, 1, 1};
    for (int i = 0; i < 2; i++) {
      assertEquals(ints[i], testView.printFleetSelection(new StringReader("1 1 1 1"), 4)[i]);
    }

    Runnable runnable = () -> mockView.printFleetSelection(new StringReader("1 1 1 1"), 4);
    runnable.run();
  }

  /**
   * checks tests for the printInvalidFleet method
   */
  @Test
  void printInvalidFleet() {
    int[] ints = new int[] {1, 1, 1, 1};
    for (int i = 0; i < 2; i++) {
      assertEquals(ints[i], testView.printInvalidFleet(new StringReader("1 1 1 1"), 4)[i]);
    }

    Runnable runnable = () -> mockView.printInvalidFleet(new StringReader("1 1 1 1"), 4);
    runnable.run();
  }

  /**
   * checks tests for the printBoards method
   */
  @Test
  void printBoards() {
    Board test = new Board(10, 10);
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 1));
    shots.add(new Coord(1, 2));
    shots.add(new Coord(1, 3));
    shots.add(new Coord(1, 4));
    model.addCompBoard(test);
    model.addUserBoard(test);
    model.addUserShips(new Comp(model,
        "CPU", shots).setup(10, 10, model.setRandomFleet()));
    model.addCompShips(new Comp(model,
        "CPU2", shots).setup(10, 10, model.setRandomFleet()));

    testView.printBoards(model.getBoardsToDisplay());
    assertEquals(model.getBoardsToDisplay(), writer.toString());
  }

  /**
   * checks tests for the printSalvo method
   */
  @Test
  void printSalvo() {
    int[] ints = new int[] {1, 1, 1, 1};
    for (int i = 0; i < 2; i++) {
      assertEquals(ints[i], testView.printSalvo(new StringReader("1 1 1 1"), 2)[i]);
    }

    Runnable runnable = () -> mockView.printSalvo(new StringReader("1 1 1 1"), 2);
    runnable.run();
  }

  /**
   * checks tests for the printEnd method
   */
  @Test
  void printEnd() {
    testView.printEnd("You Lost!");
    assertEquals("You Lost!", writer.toString());
  }
}