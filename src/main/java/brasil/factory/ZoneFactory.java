package brasil.factory;

import brasil.dto.ZoneDTO;
import brasil.entity.Zone;

public class ZoneFactory {
    public static Zone createZone(ZoneDTO dto) {
        String nom = dto.getNom();
        double prixLivraison = dto.getPrixLivraison();
        return new Zone(nom, prixLivraison);  
    }   

  


    
}
