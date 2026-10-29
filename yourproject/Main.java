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

      // 1. Initialize Views
      ItemView itemView = new ItemView(repo.findAll());
      ReservationView reservationView = new ReservationView(
          repo.findAvailable(),
          repo.findActiveReservations(),
          repo.findPastReservations());

      // 2. Initialize Controllers
      ItemController itemController = new ItemController(repo, itemView);
      ReservationController reservationController = new ReservationController(repo, reservationView);

      // 3. Cross-wire them so mutations in one window automatically refresh the other
      itemController.setOnMutate(() -> reservationController.refreshData());
      reservationController.setOnMutate(() -> itemController.refreshTable());

      // 4. Show Windows
      itemView.setVisible(true);
      reservationView.setVisible(true);
    });
  }
}
