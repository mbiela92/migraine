package de.biela.migraine.service;

import de.biela.migraine.model.dto.MigraineDto;

import java.util.UUID;

public interface MigraineService {
    MigraineDto getMigraineById (final UUID id);
    String updateMigraineById(final UUID id,final MigraineDto updatedMigraine);
    String createMigraineById(final UUID id, final MigraineDto createMigraine);
    String deleteMigraineById(final UUID id);
}
