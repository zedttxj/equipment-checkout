#ifndef IN_MEMORY_ITEM_REPOSITORY_H
#define IN_MEMORY_ITEM_REPOSITORY_H

#include "ItemRepository.h"
#include <vector>
#include <algorithm>

class InMemoryItemRepository : public ItemRepository {
private:
    std::vector<Equipment> items;
    int nextId = 1;
    std::vector<Reservation> reservations;

public:
    std::vector<Reservation> findAllReservations() override {
        return reservations;
    }

    void addReservation(const Reservation& reservation) override {
        reservations.push_back(reservation);
    }

    std::vector<Equipment> findAll() override {
        return items; // Defensive copy by value
    }

    std::vector<Equipment> findAvailable() override {
        std::vector<int> activeReservedIds;
        for (const auto& r : reservations) {
            if (!r.has_end_date_impl_check()) { // Handled via optional check below
            }
        }

        // Collecting IDs with active reservations (endDate has no value)
        for (const auto& r : reservations) {
            if (!r.getEndDate().has_value()) {
                activeReservedIds.push_back(r.getEquipmentId());
            }
        }

        std::vector<Equipment> availableItems;
        for (const auto& item : items) {
            auto it = std::find(activeReservedIds.begin(), activeReservedIds.end(), item.getId());
            if (it == activeReservedIds.end()) {
                availableItems.push_back(item);
            }
        }

        return availableItems;
    }

    std::vector<Reservation> findActiveReservations() override {
        std::vector<Reservation> active;
        for (const auto& r : reservations) {
            if (!r.getEndDate().has_value()) {
                active.push_back(r);
            }
        }
        return active;
    }

    std::vector<Reservation> findPastReservations() override {
        std::vector<Reservation> past;
        for (const auto& r : reservations) {
            if (r.getEndDate().has_value()) {
                past.push_back(r);
            }
        }
        return past;
    }

    Equipment* findById(int id) override {
        auto it = std::find_if(items.begin(), items.end(), [id](const Equipment& i) {
            return i.getId() == id;
        });
        if (it != items.end()) {
            return &(*it);
        }
        return nullptr;
    }

    Equipment save(Equipment item) override {
        if (item.getId() == 0) {
            item.setId(nextId++);
            items.push_back(item);
        } else {
            deleteById(item.getId());
            items.push_back(item);
        }
        return item;
    }

    void deleteById(int id) override {
        items.erase(
            std::remove_if(items.begin(), items.end(), [id](const Equipment& i) {
                return i.getId() == id;
            }),
            items.end()
        );
    }
};

#endif // IN_MEMORY_ITEM_REPOSITORY_H
