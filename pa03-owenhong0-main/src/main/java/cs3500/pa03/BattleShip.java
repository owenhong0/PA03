package cs3500.pa03;

import cs3500.pa03.Controller.BattleController;
import cs3500.pa03.Model.BattleModel;
import cs3500.pa03.Model.Model;
import java.io.InputStreamReader;

/**
 * represents a battleship game
 */
public class BattleShip {

  /**
   * the main method to kick off the battleship application
   */
  public void run() {
    Appendable output = System.out;
    Readable input = new InputStreamReader(System.in);
    Model model = new BattleModel();
    BattleController controller = new BattleController(input, output, model);
    controller.run();
  }
}
