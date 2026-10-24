package yourproject.controller;

import yourproject.view.ReservationView;
import yourproject.view.ReservationTableModel;
import yourproject.model.ItemRepository;
import yourproject.model.Equipment;
import yourproject.model.Reservation;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ReservationController {
  private final ItemRepository repository;
  private final ReservationView view;
  private Runnable onMutate; // Passable mutate function
  private boolean isUpdating = false; // Guard flag

  // Constructor that accepts the repository, view, and an optional mutate
  // callback
  public ReservationController(ItemRepository repository, ReservationView view) {
    this.repository = repository;
    this.view = view;

    // Wire up the Reserve button action
    this.view.getReserveButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = view.getAvailableTable().getSelectedRow();
        if (selectedRow >= 0) {
          int modelRow = view.getAvailableTable().convertRowIndexToModel(selectedRow);
          Equipment selectedEquipment = view.getAvailableTableModel().getEquipmentAt(modelRow);

          int generatedId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
          Reservation newReservation = new Reservation(
              generatedId,
              1, // Default userId
              selectedEquipment.getId(),
              new Date(),
              null, // Active reservations have no end date yet
              "Standard",
              "Reserved via UI");

          repository.addReservation(newReservation);

          JOptionPane.showMessageDialog(view, "Reserved: " + selectedEquipment.getName());

          // Refresh all tables
          refreshData();
        } else {
          JOptionPane.showMessageDialog(view, "Please select an equipment item to reserve.");
        }
      }
    });

    // Wire up the Return button action
    this.view.getReturnButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = view.getActiveReservationsTable().getSelectedRow();
        if (selectedRow >= 0) {
          int modelRow = view.getActiveReservationsTable().convertRowIndexToModel(selectedRow);

          // Cast the model to ReservationTableModel to access getReservationAt
          Reservation selectedReservation = ((ReservationTableModel) view.getActiveReservationsTable().getModel())
              .getReservationAt(modelRow);

          // Update the end date to current time to mark it as returned/past
          selectedReservation.setEndDate(new Date());

          JOptionPane.showMessageDialog(view, "Selected reservation returned.");

          // Refresh all views
          refreshData();
        } else {
          JOptionPane.showMessageDialog(view, "Please select an active reservation to return.");
        }
      }
    });
  }

  public void refreshData() {
    if (isUpdating)
      return; // Prevent re-entry loop

    isUpdating = true;
    try {
      view.refreshAvailableEquipment(repository.findAvailable());
      view.refreshActiveReservations(repository.findActiveReservations());
      view.refreshPastReservations(repository.findPastReservations());
      // Trigger the custom mutate callback whenever data is refreshed/mutated
      if (onMutate != null) {
        onMutate.run();
      }
    } finally {
      isUpdating = false; // Always reset the flag
    }
  }

  public void setOnMutate(Runnable onMutate) {
    this.onMutate = onMutate;
  }
}
