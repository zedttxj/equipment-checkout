package yourproject.model;

import java.util.List;

public interface ItemRepository {
  List<Equipment> findAll();

  Equipment findById(int id);

  Equipment save(Equipment item); // insert if id == 0, else update

  void deleteById(int id);

  List<Reservation> findAllReservations();

  void addReservation(Reservation reservation);
}
