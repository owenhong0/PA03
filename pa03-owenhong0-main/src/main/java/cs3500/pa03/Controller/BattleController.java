package cs3500.pa03.Controller;

import cs3500.pa03.Coord.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Model.Comp;
import cs3500.pa03.Model.Model;
import cs3500.pa03.Model.Player;
import cs3500.pa03.Model.User;
import cs3500.pa03.Ships.Ship;
import cs3500.pa03.View.BattleView;
import cs3500.pa03.View.View;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a controller for the battleship game
 */
public class BattleController implements Controller {
  private View view;
  private Model model;
  private Readable input;
  private Appendable output;
  private Player player1;
  private Player player2;
  private List<Coord> userShots = new ArrayList<>();
  private List<Coord> compShots = new ArrayList<>();
  private List<Ship> p1Ships;
  private List<Ship> p2Ships;
  private int[] fleetSelection;

  /**
   * constructs a battleController
   *
   * @param inStream input stream
   * @param outStream output stream
   * @param m battleModel
   */
  public BattleController(Readable inStream, Appendable outStream, Model m) {
    input = inStream;
    output = outStream;
    view = new BattleView(output);
    model = m;
  }

  /**
   * runs the three main stages of the battleship game
   */
  public void run() {
    runSetup();
    runFleetSetup();
    runMainLoop();
  }

  /**
   * runs the setup portion of the game that determines board size
   */
  private void runSetup() {
    int[] boardDimensions = view.printWelcome(input);
    boolean givenValidDimesions = model.checkDimensions(boardDimensions);
    while (!givenValidDimesions) {
      boardDimensions = view.printInvalidBoard(input);
      givenValidDimesions = model.checkDimensions(boardDimensions);
    }
    model.setDimensions(boardDimensions);
  }

  /**
   * runs the fleet selection stage of the game which determines the user's and cpu's ships
   */
  private void runFleetSetup() {
    fleetSelection = view.printFleetSelection(input, model.getSmallestDim());
    boolean fleetSetUp = model.checkFleet(fleetSelection);
    while (!fleetSetUp) {
      fleetSelection = view.printInvalidFleet(input, model.getSmallestDim());
      fleetSetUp = model.checkFleet(fleetSelection);
    }

    player1 = new User(model, "Bob", userShots);
    player2 = new Comp(model, "CPU", compShots);
    p1Ships = player1.setup(model.getHeight(), model.getWidth(), model.convertFleet(fleetSelection));
    model.addUserShips(p1Ships);
    p2Ships = player2.setup(model.getHeight(), model.getWidth(), model.setRandomFleet());
    model.addCompShips(p2Ships);

    view.printBoards(model.getBoardsToDisplay());
  }

  /**
   * runs the main loop of the game where the user battles against the cpu simultaneously
   */
  private void runMainLoop() {
    boolean done = false;
    while(!done) {

      int[] shotSelections = view.printSalvo(input, model.allUnsunk());
      boolean checkShots = model.checkShots(shotSelections);
      while (!checkShots) {
        shotSelections = view.printSalvo(input, model.getShips(fleetSelection));
        checkShots = model.checkShots(shotSelections);
      }

      if(model.allSunk(p1Ships) && !model.allSunk(p2Ships)) {
        done = true;
        player1.endGame(GameResult.LOSE, "All of your ships were sunk");
        player2.endGame(GameResult.WIN, "CPU: All of the opponent's ships were sunk");
        break;
      } else if (!model.allSunk(p1Ships) && model.allSunk(p2Ships)) {
        done = true;
        player1.endGame(GameResult.WIN, "All of the opponent's ships were sunk");
        player2.endGame(GameResult.LOSE, "CPU: All of your ships were sunk");
        break;
      } else if (model.allSunk(p1Ships) && model.allSunk(p2Ships)) {
        done = true;
        player1.endGame(GameResult.DRAW, "Everyone's ships were sunk");
        player2.endGame(GameResult.DRAW, "CPU: Everyone's ships were sunk");
        break;
      } else {

        userShots.addAll(model.convertShots(shotSelections));
        List<Coord> p1Hits = player2.reportDamage(player1.takeShots());
        compShots.addAll(model.randomShots());
        List<Coord> p2Hits = player1.reportDamage(player2.takeShots());
        player1.successfulHits(p1Hits);
        player2.successfulHits(p2Hits);
        view.printBoards(model.getBoardsToDisplay());
        userShots.clear();
        compShots.clear();
      }
    }

    view.printBoards(model.getBoardsToDisplay());
    view.printEnd(model.getEndMessages());
  }
}
