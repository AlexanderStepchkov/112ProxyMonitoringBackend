package Monitoring.Monitoring.db.repositories.implementations;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Monitoring.Monitoring.db.models.VtbIncidents;
import Monitoring.Monitoring.db.repositories.interfaces.VtbIncidentsRepository;

@Component
public class VtbIncidentsRepositoryImpl implements VtbIncidentsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VtbIncidents> getAllVtbIncidents() {
        Query vtbIncidentsQuery = entityManager.createQuery("select a from VtbIncidents a", VtbIncidents.class);
        List<VtbIncidents> vtbIncidents = vtbIncidentsQuery.getResultList();
        return vtbIncidents;
    }

    @Override
    public List<VtbIncidents> getTimeFilteredNonSentVtbIncidents(long daysDiff) {

        String qryString = """
                select i 
                  from VtbIncidents i 
                 where i.factBeginAt >= :twoDaysBackFromNow
                   and i.notificationSent <> true
                """;

        TypedQuery<VtbIncidents> vtbIncidentsQuery = entityManager
                .createQuery(qryString, VtbIncidents.class)
                .setParameter("twoDaysBackFromNow", ZonedDateTime.now().minusDays(daysDiff));

        return vtbIncidentsQuery.getResultList();
    }

    @Override
    @Transactional
    public void markAsNotificationSent(Set<Integer> incidentsIds) {
        entityManager.createQuery("update VtbIncidents set notificationSent = true where id in (:ids)")
                     .setParameter("ids", incidentsIds)
                     .executeUpdate();
    }

    @Override
    public List<VtbIncidents> getTimeFilteredVtbIncidents(long daysDiff) {
        Clock cl = Clock.systemUTC();
        ZonedDateTime dt = ZonedDateTime.now(cl).minusDays(daysDiff);

        Query vtbIncidentsQuery = entityManager.createQuery(String.format(
                "select incident from VtbIncidents incident where incident.factBeginAt >= timestamp '%d'",
                dt), VtbIncidents.class);
        List<VtbIncidents> vtbIncidents = vtbIncidentsQuery.getResultList();
        return vtbIncidents;
    }

    @Override
    public void putVtbIncidents(List<VtbIncidents> vtbIncidents) {
        entityManager.getTransaction().begin();
        for(VtbIncidents vtbIncident : vtbIncidents){
            entityManager.persist(vtbIncident);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public VtbIncidents getVtbIncident(int id) {
        String query =  String.format("select a from VtbIncidents a where a.id=%s;", id);
        VtbIncidents vtbIncident = entityManager.createQuery(query, VtbIncidents.class)
                .getSingleResult();
        return  vtbIncident;
    }

    @Override
    public List<VtbIncidents> getVtbIncidents(List<String> incidentIds){

        String idsString = String.join(", ", incidentIds);

        String query = String.format("select a from VtbIncidents a where a.incident_id in %s;", idsString);

        List<VtbIncidents> vtbIncidents = entityManager.createQuery(query, VtbIncidents.class)
                                                       .getResultList();

        return vtbIncidents;
    }
}
