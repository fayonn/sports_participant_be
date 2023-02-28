package com.example.sports_participant_be_main.services;

import com.example.sports_participant_be_main.models.Location;
import com.example.sports_participant_be_main.models.Role;
import com.example.sports_participant_be_main.models.Schedule;
import com.example.sports_participant_be_main.repositories.RoleRepo;
import com.example.sports_participant_be_main.repositories.ScheduleRepo;
import com.example.sports_participant_be_main.utils.exceptions.location.LocationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepo roleRepo;

    public Role save(Role role) {
        this.roleRepo.findByName(role.getName()).ifPresent((item) -> {
            throw new RuntimeException("Role with name - " + role.getName() + " already exists");
        });

        role.setName(role.getName().toUpperCase());
        return this.roleRepo.save(role);
    }

    public Optional<Role> findByName(String name) {
        return this.roleRepo.findByName(name);
    }
}
