#ifndef ITEM_REPOSITORY_H
#define ITEM_REPOSITORY_H

#include <vector>
#include "Equipment.h"
#include "Reservation.h"

class ItemRepository {
public:
    virtual ~ItemRepository() = default;

    virtual std::vector<Equipment> findAll() = 0;
    virtual std::vector<Equipment> findAvailable() = 0;
    virtual Equipment* findById(int id) = 0;
    virtual Equipment save(Equipment item) = 0;
    virtual void deleteById(int id) = 0;

    virtual std::vector<Reservation> findAllReservations() = 0;
    virtual std::vector<Reservation> findActiveReservations() = 0;
    virtual std::vector<Reservation> findPastReservations() = 0;
    virtual void addReservation(const Reservation& reservation) = 0;
};

#endif // ITEM_REPOSITORY_H
