package Monitoring.Monitoring.services.workers;

import Monitoring.Monitoring.db.models.Metrics;
import Monitoring.Monitoring.db.models.SmDefMeasurementApi;
import Monitoring.Monitoring.db.models.SmRawdataMeasApi;
import Monitoring.Monitoring.db.models.Updates;
import Monitoring.Monitoring.db.repositories.interfaces.MetricsRepository;
import Monitoring.Monitoring.db.repositories.interfaces.SmDefMeasurementApiRepository;
import Monitoring.Monitoring.db.repositories.interfaces.SmRawdataMeasApiRepository;
import Monitoring.Monitoring.db.repositories.interfaces.UpdatesRepository;
import Monitoring.Monitoring.db.vertica.models.SmDefMeasurementVertica;
import Monitoring.Monitoring.db.vertica.models.SmRawdataMeasVertica;
import Monitoring.Monitoring.db.vertica.repositories.interfaces.SmDefMeasurementVerticaRepository;
import Monitoring.Monitoring.db.vertica.repositories.interfaces.SmRawdataMeasVerticaRepository;
import Monitoring.Monitoring.mappers.VerticaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VerticaWorker {
    private SmDefMeasurementApiRepository smDefMeasurementApiRepository;
    private SmDefMeasurementVerticaRepository smDefMeasurementVerticaRepository;
    private SmRawdataMeasApiRepository smRawdataMeasApiRepository;
    private SmRawdataMeasVerticaRepository smRawdataMeasVerticaRepository;
    private MetricsRepository metricsRepository;
    private UpdatesRepository updatesRepository;
    private final String verticaServiceName = "VerticaSmRawData";
    private VerticaMapper verticaMapper;

    @Autowired
    public VerticaWorker(SmDefMeasurementApiRepository smDefMeasurementApiRepository, SmDefMeasurementVerticaRepository smDefMeasurementVerticaRepository, SmRawdataMeasApiRepository smRawdataMeasApiRepository, SmRawdataMeasVerticaRepository smRawdataMeasVerticaRepository, MetricsRepository metricsRepository, UpdatesRepository updatesRepository, VerticaMapper verticaMapper) {
        this.smDefMeasurementApiRepository = smDefMeasurementApiRepository;
        this.smDefMeasurementVerticaRepository = smDefMeasurementVerticaRepository;
        this.smRawdataMeasApiRepository = smRawdataMeasApiRepository;
        this.smRawdataMeasVerticaRepository = smRawdataMeasVerticaRepository;
        this.metricsRepository = metricsRepository;
        this.updatesRepository = updatesRepository;
        this.verticaMapper = verticaMapper;
    }

    @Scheduled(fixedRate = 900000)
    public void takeSmDefMeasurementVertica() {
        try {
            List<Metrics> metrics = metricsRepository.findAll()
                    .stream()
                    .filter(m -> !m.isMerged())
                    .collect(Collectors.toList());
            if (metrics.size() != 0) {
                    List<SmDefMeasurementVertica> smDefMeasurementVerticaList =
                            smDefMeasurementVerticaRepository.getSmDefMeasurements(metrics);

                    List<SmDefMeasurementApi> smDefMeasurementApiList =
                            smDefMeasurementVerticaList
                                    .stream()
                                    .map(verticaMapper::mapToSmDefMeasurementApi)
                                    .collect(Collectors.toList());

                    smDefMeasurementApiRepository.saveAll(smDefMeasurementApiList);

                    for (Metrics metric : metrics) {
                        metric.setMerged(true);
                    }
                    metricsRepository.saveAll(metrics);
                }
            } catch(SQLException sqlException){
                log.error("Произошла ошибка при попытке выгрузки данных из Vertic-и из таблицы SmDefMeasurements", sqlException);
        }
    }

    @Scheduled(fixedRate = 900000)
    public void takeSmRawdataMeasVertica() {
        try {
            Updates update = updatesRepository.getUpdateEntityByServiceName(verticaServiceName);
            List<SmRawdataMeasVertica> smRawdataMeasVerticaList =
                    smRawdataMeasVerticaRepository.getSmRawdataMeasVertica(update);
            List<SmRawdataMeasApi> smRawdataMeasApiList =
                    smRawdataMeasVerticaList
                            .stream()
                            .map(verticaMapper::mapToSmRawdataMeasApi)
                            .collect(Collectors.toList());
            smRawdataMeasApiRepository.saveAll(smRawdataMeasApiList);

            ZonedDateTime updatedAt = smRawdataMeasVerticaList.stream()
                    .max(Comparator.comparing(SmRawdataMeasVertica::getTimeStamp))
                    .get()
                    .getTimeStamp();

            update.setUpdateTime(updatedAt);
            updatesRepository.putUpdate(update);
        } catch (SQLException sqlException){
            log.error("Произошла ошибка при попытке выгрузки данных из Vertic-и из таблицы SmRawdataMeas", sqlException);
        }
    }
}
