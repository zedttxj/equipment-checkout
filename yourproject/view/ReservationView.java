package yourproject.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import yourproject.model.Equipment;
import yourproject.model.Reservation;

public class ReservationView extends JFrame {
  private JTabbedPane tabbedPane;

  // Available Equipment Panel Components
  private JTable availableTable;
  private AssetTableModel availableTableModel;
  private JButton clearButton, reserveButton;

  // Reservations Panel Components
  private JTable activeReservationsTable;
  private ReservationTableModel activeReservationsModel;
  private JButton returnButton;

  private JButton refreshButton = new JButton("Refresh");
  private JButton refreshReservationsButton = new JButton("Refresh");

  public ReservationView(List<Equipment> availableEquipment, List<Reservation> activeReservations) {
    setTitle("Asset Reservation System");
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Top Navigation / Header Info
    JPanel topInfoPanel = new JPanel();
    topInfoPanel.setLayout(new BoxLayout(topInfoPanel, BoxLayout.Y_AXIS));
    JLabel headerLabel = new JLabel("Asset Reservation System");
    headerLabel.setFont(new Font("Arial", Font.BOLD, 22));

    JTextArea infoText = new JTextArea(
        "The OpCom room also has spare keyboards, computer mice, soundbars, clickers, power cables, velcro, and computer cables.");
    infoText.setWrapStyleWord(true);
    infoText.setLineWrap(true);
    infoText.setEditable(false);
    infoText.setOpaque(false);

    topInfoPanel.add(headerLabel);
    topInfoPanel.add(infoText);
    add(topInfoPanel, BorderLayout.NORTH);

    // Tabbed Interface (Mimicking the responsive view layout)
    tabbedPane = new JTabbedPane();

    // --- Tab 1: Available Equipment to Reserve ---
    JPanel reservePanel = new JPanel(new BorderLayout());
    availableTableModel = new AssetTableModel(availableEquipment);
    availableTable = new JTable(availableTableModel);
    reservePanel.add(new JScrollPane(availableTable), BorderLayout.CENTER);

    JPanel reserveButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    clearButton = new JButton("Clear Selection");
    reserveButton = new JButton("Reserve Selected");
    reserveButtonPanel.add(clearButton);
    reserveButtonPanel.add(reserveButton);
    reserveButtonPanel.add(refreshButton);
    reservePanel.add(reserveButtonPanel, BorderLayout.SOUTH);

    tabbedPane.addTab("Reserve Equipment", reservePanel);

    // --- Tab 2: Active & Past Reservations ---
    JPanel reservationsPanel = new JPanel(new BorderLayout());
    activeReservationsModel = new ReservationTableModel(activeReservations);
    activeReservationsTable = new JTable(activeReservationsModel);
    reservationsPanel.add(new JScrollPane(activeReservationsTable), BorderLayout.CENTER);

    JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    returnButton = new JButton("Return Selected Item");
    returnButtonPanel.add(returnButton);
    returnButtonPanel.add(refreshReservationsButton);
    reservationsPanel.add(returnButtonPanel, BorderLayout.SOUTH);

    tabbedPane.addTab("My Reservations", reservationsPanel);

    add(tabbedPane, BorderLayout.CENTER);
  }

  // Getters for the Controller to attach action listeners
  public JTable getAvailableTable() {
    return availableTable;
  }

  public AssetTableModel getAvailableTableModel() {
    return availableTableModel;
  }

  // Add this public getter method
  public JButton getRefreshButton() {
    return refreshButton;
  }

  public JButton getClearButton() {
    return clearButton;
  }

  public JButton getReserveButton() {
    return reserveButton;
  }

  public JButton getReturnButton() {
    return returnButton;
  }

  public void refreshAvailableEquipment(List<Equipment> equipmentList) {
    availableTableModel.updateData(equipmentList);
  }

  public JButton getRefreshReservationsButton() {
    return refreshReservationsButton;
  }

  public JTable getActiveReservationsTable() {
    return activeReservationsTable;
  }

  public void refreshActiveReservations(List<Reservation> reservationList) {
    activeReservationsModel.updateData(reservationList);
  }
}
