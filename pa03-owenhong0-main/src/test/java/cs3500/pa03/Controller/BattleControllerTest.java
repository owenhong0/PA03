package cs3500.pa03.Controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.Model.BattleModel;
import cs3500.pa03.Model.Model;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class BattleControllerTest {
  private Controller testController;
  private Model model;
  private StringWriter writer = new StringWriter();
  private Readable input;

  @Test
  void run() {
    StringBuilder builder = new StringBuilder("10 10");
    input = new StringReader(builder.toString());
    model = new BattleModel();
    testController = new BattleController(input, writer, model);
    assertThrows(NoSuchElementException.class, () -> testController.run());
  }
}