package yourproject.view;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import yourproject.model.Reservation;

public class ReservationTableModel extends AbstractTableModel {
  private final String[] columnNames = { "ID", "User ID", "Equipment ID", "Start Date", "Type" };
  protected List<Reservation> reservations;

  public ReservationTableModel(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  @Override
  public int getRowCount() {
    return reservations.size();
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
    Reservation res = reservations.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> res.getId();
      case 1 -> res.getUserId();
      case 2 -> res.getEquipmentId();
      case 3 -> res.getStartDate();
      case 4 -> res.getReservationType();
      default -> null;
    };
  }

  public Reservation getReservationAt(int rowIndex) {
    return reservations.get(rowIndex);
  }

  public void updateData(List<Reservation> newReservations) {
    this.reservations = newReservations;
    fireTableDataChanged();
  }
}
