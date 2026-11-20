package yourproject.view;

import java.util.List;
import yourproject.model.Reservation;

public class PastReservationTableModel extends ReservationTableModel {
  private final String[] columnNames = { "ID", "User ID", "Equipment ID", "Start Date", "End Date", "Type" };

  public PastReservationTableModel(List<Reservation> reservations) {
    super(reservations);
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
      case 4 -> res.getEndDate(); // Added End Date for past reservations
      case 5 -> res.getReservationType();
      default -> null;
    };
  }
}
