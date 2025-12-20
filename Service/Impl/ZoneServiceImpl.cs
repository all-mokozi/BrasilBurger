using Models;
using Repository;
using Service;

namespace Impl
{
    public class ZoneServiceImpl : IZoneService
    {
       private readonly IZoneRepository _repo;
        public ZoneServiceImpl(IZoneRepository repo)
        {
            _repo = repo;
        }
        public IEnumerable<Zone> GetAllZones()
        {
           return  _repo.SelectAllZone();
        }

    }
}