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

  public AssetManagementView(List<Equipment> initialData) {
    setTitle("Asset Management");
    setSize(900, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Essential rule (2.7)
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

    // Setup Placeholder for Calendar View
    JPanel calendarPlaceholder = new JPanel();
    calendarPlaceholder.add(new JLabel("[ Calendar View Component ]"));

    cardPanel.add(tableScrollPane, "TABLE");
    cardPanel.add(calendarPlaceholder, "CALENDAR");

    // Combine Center and Card Panel
    JPanel centerWrapper = new JPanel(new BorderLayout());
    centerWrapper.add(controlPanel, BorderLayout.NORTH);
    centerWrapper.add(cardPanel, BorderLayout.CENTER);
    add(centerWrapper, BorderLayout.CENTER);

    // View toggle listener
    toggleViewButton.addActionListener(e -> {
      // Toggle logic between table and calendar
    });
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

  public JTable getEquipmentTable() {
    return equipmentTable;
  }

  public AssetTableModel getTableModel() {
    return tableModel;
  }
}
