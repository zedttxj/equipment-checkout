package yourproject.model;

import java.util.List;

public interface ItemRepository {
  List<Equipment> findAll();

  List<Equipment> findAvailable();

  Equipment findById(int id);

  Equipment save(Equipment item); // insert if id == 0, else update

  void deleteById(int id);

  List<Reservation> findAllReservations();

  List<Reservation> findActiveReservations();

  List<Reservation> findPastReservations();

  void addReservation(Reservation reservation);
}
