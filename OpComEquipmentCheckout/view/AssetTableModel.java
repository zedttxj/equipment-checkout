package yourproject.view;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import yourproject.model.Equipment;

public class AssetTableModel extends AbstractTableModel {
  private final String[] columnNames = { "ID", "Name", "Description" };
  private List<Equipment> data;

  public AssetTableModel(List<Equipment> data) {
    this.data = data;
  }

  public Equipment getEquipmentAt(int row) {
    return data.get(row);
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Equipment eq = data.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return eq.getId();
      case 1:
        return eq.getName();
      case 2:
        return eq.getDescription();
      default:
        return null;
    }
  }

  // Call this method to update the view state when data changes (equivalent to
  // mutate() in SWR)
  public void updateData(List<Equipment> newData) {
    this.data = newData;
    fireTableDataChanged();
  }
}
