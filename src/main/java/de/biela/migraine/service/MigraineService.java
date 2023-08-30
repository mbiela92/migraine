package de.biela.migraine.service;

import de.biela.migraine.model.Migraine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MigraineService extends JpaRepository<Migraine, UUID> {

}
