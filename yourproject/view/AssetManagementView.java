package yourproject.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import yourproject.model.Equipment;

public class AssetManagementView extends JFrame {
  private JTextField searchField;
  private JButton addButton, deleteButton, editButton, toggleViewButton;
  private JPanel cardPanel;
  private CardLayout cardLayout;

  // Tables or panels for views
  private JTable equipmentTable;
  private AssetTableModel tableModel;

  // Track current view state for toggling
  private boolean isTableShowing = true;

  public AssetManagementView(List<Equipment> initialData) {
    setTitle("Asset Management");
    setSize(900, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    setLayout(new BorderLayout());

    // 1. Top Header & Back Button Panel
    JPanel topPanel = new JPanel(new BorderLayout());
    JLabel titleLabel = new JLabel("Asset Management");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    JButton backButton = new JButton("Back to Administrative Home");
    JPanel headerWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    headerWrapper.add(backButton);

    topPanel.add(titleLabel, BorderLayout.WEST);
    topPanel.add(headerWrapper, BorderLayout.EAST);
    add(topPanel, BorderLayout.NORTH);

    // 2. Center Control Bar (Search & CRUD Action Buttons)
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchField = new JTextField(20);
    searchField.setToolTipText("Search items...");

    addButton = new JButton("Add Equipment");
    deleteButton = new JButton("Delete Equipment");
    editButton = new JButton("Edit Equipment");
    toggleViewButton = new JButton("Switch to Calendar View");

    controlPanel.add(new JLabel("Search:"));
    controlPanel.add(searchField);
    controlPanel.add(addButton);
    controlPanel.add(deleteButton);
    controlPanel.add(editButton);
    controlPanel.add(toggleViewButton);

    // 3. Main Content Area (Using CardLayout to switch views like Next.js state)
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Setup Table View
    tableModel = new AssetTableModel(initialData);
    equipmentTable = new JTable(tableModel);
    JScrollPane tableScrollPane = new JScrollPane(equipmentTable);

    // Setup Calendar View (Replace this panel with your custom calendar or
    // third-party component like JCalendar)
    JPanel calendarPanel = createCalendarComponent();

    cardPanel.add(tableScrollPane, "TABLE");
    cardPanel.add(calendarPanel, "CALENDAR");

    // Combine Center and Card Panel
    JPanel centerWrapper = new JPanel(new BorderLayout());
    centerWrapper.add(controlPanel, BorderLayout.NORTH);
    centerWrapper.add(cardPanel, BorderLayout.CENTER);
    add(centerWrapper, BorderLayout.CENTER);

    // View toggle listener (akin to changing view state in web frontend)
    toggleViewButton.addActionListener(e -> {
      if (isTableShowing) {
        cardLayout.show(cardPanel, "CALENDAR");
        toggleViewButton.setText("Switch to Table View");
      } else {
        cardLayout.show(cardPanel, "TABLE");
        toggleViewButton.setText("Switch to Calendar View");
      }
      isTableShowing = !isTableShowing;
    });
  }

  /**
   * Helper method to build or return your calendar component.
   * You can replace this layout with a custom grid or an external library panel.
   */
  private JPanel createCalendarComponent() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel calendarHeader = new JLabel("Reservation Calendar Grid", JLabel.CENTER);
    calendarHeader.setFont(new Font("Arial", Font.BOLD, 18));
    panel.add(calendarHeader, BorderLayout.NORTH);

    // Example placeholder grid for days of the month
    JPanel gridPanel = new JPanel(new GridLayout(0, 7, 5, 5));
    String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    for (String day : daysOfWeek) {
      gridPanel.add(new JLabel(day, JLabel.CENTER));
    }
    // Add sample day cells
    for (int i = 1; i <= 31; i++) {
      gridPanel.add(new JButton("Day " + i));
    }

    panel.add(new JScrollPane(gridPanel), BorderLayout.CENTER);
    return panel;
  }

  public JTextField getSearchField() {
    return searchField;
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
  }

  public JButton getEditButton() {
    return editButton;
  }

  public JButton getToggleViewButton() {
    return toggleViewButton;
  }

  public JTable getEquipmentTable() {
    return equipmentTable;
  }

  public AssetTableModel getTableModel() {
    return tableModel;
  }
}
