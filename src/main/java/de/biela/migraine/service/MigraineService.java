package de.biela.migraine.service;

import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.Migraine;

import java.util.UUID;

public interface MigraineService {
    MigraineDto getMigraineById (final UUID id);
    String updateMigraineById(final UUID id,final MigraineDto updatedMigraine);
    Migraine createMigraineById(final UUID id, final MigraineDto createMigraine);
    String deleteMigraineById(final UUID id);
}
