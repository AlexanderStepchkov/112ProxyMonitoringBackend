package ru.vtb.monitoring.vtb112.db.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.monitoring.vtb112.db.models.SmDefMeasurementApi;

@Repository
public interface SmDefMeasurementApiRepository extends JpaRepository<SmDefMeasurementApi, Integer> {
}
