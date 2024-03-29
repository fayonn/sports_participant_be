package com.example.sports_participant_be_main.services;

import com.example.sports_participant_be_main.models.GymBrand;
import com.example.sports_participant_be_main.models.Location;
import com.example.sports_participant_be_main.models.LocationRoom;
import com.example.sports_participant_be_main.repositories.LocationRepo;
import com.example.sports_participant_be_main.repositories.LocationRoomRepo;
import com.example.sports_participant_be_main.utils.exceptions.gym_brand.GymBrandNotFoundException;
import com.example.sports_participant_be_main.utils.exceptions.location.LocationIsAlreadyExistsException;
import com.example.sports_participant_be_main.utils.exceptions.location.LocationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class LocationService {
    private final LocationRepo locationRepo;
    private final LocationRoomRepo locationRoomRepo;
    private final GymBrandService gymBrandService;

    public Location saveLocation(Location location, UUID gymBrandId, Set<UUID> roomIds) {
        GymBrand gymBrand = gymBrandService.findById(gymBrandId).orElseThrow(() -> {
            throw new GymBrandNotFoundException(gymBrandId);
        });

        Optional<Location> l = locationRepo.findLocationByStreetAndStreetNumber(
                location.getStreet(),
                location.getStreetNumber()
        );

        if (l.isPresent())
            throw new LocationIsAlreadyExistsException(location.getStreet(), location.getStreetNumber());

        location.setRooms(this.getAllLocationRoomsById(roomIds));
        location.setGymBrand(gymBrand);
        return this.locationRepo.save(location);
    }

    public Set<Location> getLocationsByIds(Set<UUID> locationIds) {
        Set<Location> locations = this.locationRepo.findLocationsByIdIn(locationIds);

        if (locations.size() != locationIds.size()){
            log.warn("The count of location ids and the count of locations not equal.");
        }

        return locations;
    }

    public Optional<Location> findLocationById(UUID id) {
        return this.locationRepo.findById(id);
    }

    public LocationRoom saveLocationRoom(LocationRoom locationRoom, UUID locationId) {
        Location location = this.findLocationById(locationId).orElseThrow(() -> {
            throw new LocationNotFoundException(locationId);
        });

        locationRoom.setLocation(location);
        locationRoom.setStatus(LocationRoom.Status.ACTIVE);
        return this.locationRoomRepo.save(locationRoom);
    }

    public Optional<LocationRoom> findLocationRoomById(UUID id) {
        return this.locationRoomRepo.findById(id);
    }

    public Set<LocationRoom> getAllLocationRoomsById(Collection<UUID> ids) {
        Set<LocationRoom> rooms = this.locationRoomRepo.getAllByIdIn(ids);

        if (rooms.size() != ids.size())
            log.warn("The count of location ids and the count of locations not equal.");

        return rooms;
    }

    public Set<Location> getAllLocationByGymBrandId(UUID gymBrandId) {
        GymBrand gymBrand = gymBrandService.findById(gymBrandId).orElseThrow(() -> {
            throw new GymBrandNotFoundException(gymBrandId);
        });

        return this.locationRepo.getAllByGymBrandId(gymBrand.getId());
    }

    public Set<LocationRoom> getAllLocationRoomsByLocationId(UUID locationId) {
        Location location = this.findLocationById(locationId).orElseThrow(() -> {
            throw new LocationNotFoundException(locationId);
        });

        return this.locationRoomRepo.getAllByLocationId(location.getId());
    }

    public void deleteLocation(UUID id) {
        this.locationRepo.deleteById(id);
    }

    public void deleteLocationRoom(UUID id) {
        this.locationRoomRepo.deleteById(id);
    }
}
