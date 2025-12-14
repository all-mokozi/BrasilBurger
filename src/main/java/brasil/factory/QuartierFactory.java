package brasil.factory;

import brasil.dto.QuartierDTO;
import brasil.entity.Quartier;
import brasil.entity.Zone;

public class QuartierFactory {
    public static Quartier createQuartier(QuartierDTO dto) {
        String nom = dto.getNom();
        Zone zoneId = dto.getZone() ;
        return new Quartier(nom, zoneId);

     
    }   

  


    
}
