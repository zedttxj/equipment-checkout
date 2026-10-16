package yourproject.controller;

import yourproject.view.ReservationView;
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

          // Create reservation matching your Reservation constructor:
          // (int id, int userId, int equipmentId, Date startDate, Date endDate, String
          // reservationType, String description)
          int generatedId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
          Reservation newReservation = new Reservation(
              generatedId,
              1, // Default userId
              selectedEquipment.getId(),
              new Date(),
              new Date(),
              "Standard",
              "Reserved via UI");

          repository.addReservation(newReservation);

          JOptionPane.showMessageDialog(view, "Reserved: " + selectedEquipment.getName());

          // Refresh both tables
          refreshData();
          view.refreshActiveReservations(repository.findAllReservations());
        } else {
          JOptionPane.showMessageDialog(view, "Please select an equipment item to reserve.");
        }
      }
    });

    // Wire up the Return button action (fixed to target activeReservationsTable)
    this.view.getReturnButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = view.getActiveReservationsTable().getSelectedRow();
        if (selectedRow >= 0) {
          // Return logic here
          JOptionPane.showMessageDialog(view, "Selected reservation returned.");
        } else {
          JOptionPane.showMessageDialog(view, "Please select an active reservation to return.");
        }
      }
    });

    // Wire up the Refresh button action
    this.view.getRefreshButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        refreshData();
        JOptionPane.showMessageDialog(view, "Equipment list refreshed.");
      }
    });

    // Wire up the Reservations Refresh button action
    this.view.getRefreshReservationsButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.refreshActiveReservations(repository.findAllReservations());
        JOptionPane.showMessageDialog(view, "Reservations list refreshed.");
      }
    });
  }

  public void refreshData() {
    view.refreshAvailableEquipment(repository.findAll());
  }
}
