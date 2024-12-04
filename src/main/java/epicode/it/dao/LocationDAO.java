package epicode.it.dao;

import epicode.it.entity.location.Location;
import jakarta.persistence.EntityManager;

public class LocationDAO {
    private EntityManager em;

    // costruttore con EntityManager come argomento
    public LocationDAO(EntityManager em) {
        this.em = em;
    }

    // salvo la location a db
    public void saveLocation(Location location) {
        this.em.getTransaction().begin();
        this.em.persist(location);
        this.em.getTransaction().commit();
    }

    // cancello location da db tramite id
    public void deleteLocation(int id) {
        Location found = this.em.find(Location.class, id);
        this.em.getTransaction().begin();
        this.em.remove(found);
        this.em.getTransaction().commit();
    }

    // aggiorno location a db tramite location passata come argomento
    public void updateLocation(Location location) {
        Location found = this.em.find(Location.class, location.getId());
        found.setNome(location.getNome());
        found.setCitta(location.getCitta());
        this.em.getTransaction().begin();
        this.em.merge(found);
        this.em.getTransaction().commit();
    }

    // ottengo location da db tramite id
    public Location getLocationById(Long id) {
        return this.em.find(Location.class, id);
    }
}
