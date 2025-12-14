package brasil.service;
import java.util.List;

import brasil.dto.ZoneDTO;
import brasil.entity.Zone;
import brasil.factory.ZoneFactory;
import brasil.repository.IZoneRepository;

public class ZoneService {

    private final IZoneRepository zoneRepository;

    public ZoneService(
            IZoneRepository zoneRepository

    ) {
        this.zoneRepository = zoneRepository;

    }

    public void ajouterZone(ZoneDTO z) {

        Zone zone = ZoneFactory.createZone(z);

        zoneRepository.insert(zone);

        System.out.println("Zone " + zone.getNom() + " ajoutée avec succès.");

    }

    public void afficherAllZone() {
        List<Zone> zone = zoneRepository.selectAll();
        int i = 1;
        for (Zone p : zone) {

            System.out.println(i + ". " + p);
            i = i + 1;
        }
    }

}
