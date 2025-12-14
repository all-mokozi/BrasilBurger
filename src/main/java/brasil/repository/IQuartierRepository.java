package brasil.repository;
import java.util.List;

import brasil.entity.Quartier;
import brasil.entity.Zone;

public interface IQuartierRepository {

     public void  insert(Quartier b, Zone z);
     public List<Quartier> selectAllQuartierByZone(int zoneId);
     public List<Quartier> selectAll();
    
}
