using Data;
using Models;
using Repository;

namespace Impl
{
    public class ZoneRepositoryImpl : IZoneRepository
    {
        private readonly BrasilBurgerDbContext _context;
        public ZoneRepositoryImpl( BrasilBurgerDbContext context)
        {
            _context=context;
            
        }
                public IEnumerable<Zone> SelectAllZone()
        {
            return _context.Zones.
            OrderBy(z=>z.Nom).
            ToList();
            
        }

    }
}