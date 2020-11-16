package Monitoring.Monitoring.db.repositories.implementations;

import Monitoring.Monitoring.db.models.Updates;
import Monitoring.Monitoring.db.repositories.interfaces.UpdatesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


public class UpdatesRepositoryImpl implements UpdatesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Updates getUpdateEntityByServiceName(String serviceName) {
        String qryString = """
                select upd
                  from Updates upd 
                 upd.service_name = :serviceName
                """;
        TypedQuery<Updates> vtbIncidentsQuery = entityManager.createQuery(qryString, Updates.class)
                .setParameter("serviceName", serviceName);
        Updates update = vtbIncidentsQuery.getSingleResult();

        return update;
    }

    @Override
    public void putUpdate(Updates update){
        entityManager.merge(update);
    }
}