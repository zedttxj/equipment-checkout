package yourproject.view;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import yourproject.model.Equipment;

public class ItemTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Name", "Description"};
    private List<Equipment> items;

    public ItemTableModel(List<Equipment> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Equipment eq = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> eq.getId();
            case 1 -> eq.getName();
            case 2 -> eq.getDescription();
            default -> null;
        };
    }

    public void updateData(List<Equipment> newItems) {
        this.items = newItems;
        fireTableDataChanged();
    }
}
