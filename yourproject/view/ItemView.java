package yourproject.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import yourproject.model.Equipment;

/**
 * The ItemView class represents the graphical user interface (GUI)
 * for the Lab Equipment Checkout system. It acts as the View component
 * in the MVC pattern, handling user inputs, data display, and window layout.
 */
public class ItemView extends JFrame {
  private final JTable table;
  private final ItemTableModel tableModel;

  private final JTextField nameField = new JTextField(15);
  private final JTextField descField = new JTextField(15);

  private final JButton createButton = new JButton("Create");
  private final JButton updateButton = new JButton("Update");
  private final JButton deleteButton = new JButton("Delete");
  private final JButton clearButton = new JButton("Clear");

  private final JLabel statusLabel = new JLabel("Ready.");

  /**
   * Constructs the ItemView frame, setting up window properties,
   * the data table, input fields, and control buttons.
   * 
   * @param initialItems the initial list of Equipment items to display in the
   *                     table
   */
  public ItemView(List<Equipment> initialItems) {
    setTitle("Lab Equipment Checkout");
    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10, 10));

    // Table Setup
    tableModel = new ItemTableModel(initialItems);
    table = new JTable(tableModel);
    add(new JScrollPane(table), BorderLayout.CENTER);

    // Form & Button Panel (South)
    JPanel southPanel = new JPanel(new BorderLayout());

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    inputPanel.add(new JLabel("Name:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Description:"));
    inputPanel.add(descField);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    buttonPanel.add(createButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(clearButton);

    southPanel.add(inputPanel, BorderLayout.NORTH);
    southPanel.add(buttonPanel, BorderLayout.CENTER);
    southPanel.add(statusLabel, BorderLayout.SOUTH);

    add(southPanel, BorderLayout.SOUTH);
  }

  /** @return the main JTable displaying equipment items */
  public JTable getTable() {
    return table;
  }

  /** @return the table model managing the equipment data */
  public ItemTableModel getTableModel() {
    return tableModel;
  }

  /** @return the text entered in the name input field */
  public String getNameInput() {
    return nameField.getText();
  }

  /** @return the text entered in the description input field */
  public String getDescInput() {
    return descField.getText();
  }

  /** Sets the text of the name input field */
  public void setNameInput(String name) {
    nameField.setText(name);
  }

  /** Sets the text of the description input field */
  public void setDescInput(String desc) {
    descField.setText(desc);
  }

  /** Updates the status message displayed at the bottom of the window */
  public void setStatus(String status) {
    statusLabel.setText(status);
  }

  /** @return the Create action button */
  public JButton getCreateButton() {
    return createButton;
  }

  /** @return the Update action button */
  public JButton getUpdateButton() {
    return updateButton;
  }

  /** @return the Delete action button */
  public JButton getDeleteButton() {
    return deleteButton;
  }

  /** @return the Clear action button */
  public JButton getClearButton() {
    return clearButton;
  }
}
