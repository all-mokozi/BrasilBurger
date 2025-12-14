package brasil.repository;
import java.util.List;

import brasil.entity.Zone;

public interface IZoneRepository {

     public void  insert( Zone b);
     public List<Zone> selectAll();
     public Zone selectZoneById(int id );
    
}
