package de.biela.migraine.service.impl;

import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.Migraine;
import de.biela.migraine.repository.MigraineRepository;
import de.biela.migraine.service.MigraineService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MigraineServiceImpl implements MigraineService {

    private final MigraineRepository migraineRepository;

    public MigraineServiceImpl(MigraineRepository migraineRepository) {
        this.migraineRepository = migraineRepository;
    }

    @Override
    public MigraineDto getMigraineById(final UUID id) {
            Optional <Migraine> migraine = migraineRepository.findById(id);
            return migraine.map(mig -> new MigraineDto(mig.getId(),mig.getDate(),mig.getDescription(),mig.getPainSeverity(),mig.getCreationTimestamp(),mig.getModificationTimestamp(),null)).orElse(null);

    }
    @Override
    public String updateMigraineById(final UUID id, final MigraineDto updatedMigraine){
        try {
            Optional<Migraine> existingMigraine = migraineRepository.findById(id);
            if (existingMigraine.isPresent()) {
                Migraine tempMigraine = existingMigraine.get();
                if (updatedMigraine.date() != null) {
                    tempMigraine.setDate(updatedMigraine.date());
                    tempMigraine.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedMigraine.description() != null) {
                    tempMigraine.setDescription(updatedMigraine.description());
                    tempMigraine.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedMigraine.painSeverity() != null) {
                    tempMigraine.setPainSeverity(updatedMigraine.painSeverity());
                    tempMigraine.setModificationTimestamp(LocalDateTime.now());
                }
                migraineRepository.save(tempMigraine);
                return "Migräne wurde aktualisiert";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für die Migräne update.");
        }
        return "Update ist fehlgeschlagen";
    }
    @Override
    public Migraine createMigraineById(UUID id, MigraineDto createMigraine) {
        try {
            Migraine migraine = new Migraine(createMigraine.date(),createMigraine.description(),createMigraine.painSeverity(), createMigraine.creationTimestamp(),createMigraine.modificationTimestamp());
            migraine.setId(createMigraine.id());
            return migraineRepository.save(migraine);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für die Erstellung von Migräne.");
        }

    }
    @Override
    public String deleteMigraineById(UUID id) {

            migraineRepository.deleteById(id);
            return "Migräne wurde gelöscht";
    }


}
