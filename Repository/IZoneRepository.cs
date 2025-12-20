using Models;

namespace Repository
{
    public interface IZoneRepository
    {
        public IEnumerable<Zone> SelectAllZone();
    }
}