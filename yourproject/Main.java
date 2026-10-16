package yourproject;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import yourproject.model.ItemRepository;
import yourproject.model.InMemoryItemRepository;
import yourproject.view.ItemView;
import yourproject.view.ReservationView;
import yourproject.controller.ItemController;
import yourproject.controller.ReservationController;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      ItemRepository repo = new InMemoryItemRepository();

      ItemView view = new ItemView(repo.findAll()); // Pass initial items here
      new ItemController(repo, view);
      view.setVisible(true);

      // Launch Reservation View
      ReservationView reservationView = new ReservationView(repo.findAll(), new ArrayList<>());
      new ReservationController(repo, reservationView);
      reservationView.setVisible(true);
    });
  }
}
