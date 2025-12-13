package brasil.factory;

import brasil.dto.QuartierDTO;
import brasil.entity.Quartier;
import brasil.entity.Zone;

public class QuartierFactory {
    public static Quartier createQuartier(QuartierDTO dto) {
        String nom = dto.getNom();
        Zone zone = dto.getZone() ;
        return new Quartier(nom, zone);

     
    }   

  


    
}
