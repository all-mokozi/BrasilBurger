using Models;

namespace Service
{
    public interface IZoneService
    {
        public IEnumerable<Zone>GetAllZones();
    }
}