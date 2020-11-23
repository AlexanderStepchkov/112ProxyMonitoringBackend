package Monitoring.Monitoring.services.workers;

import Monitoring.Monitoring.config.AppConfig;
import Monitoring.Monitoring.db.models.Unavailabilities;
import Monitoring.Monitoring.db.repositories.interfaces.UnavailabilitiesRepository;
import Monitoring.Monitoring.db.repositories.interfaces.UpdatesRepository;
import Monitoring.Monitoring.dto.services.viewmodels.response.mainmodels.VmSmUnavailability;
import Monitoring.Monitoring.dto.services.viewmodels.response.modelwrappers.VmUnavailabilityWrapper;
import Monitoring.Monitoring.services.workers.BaseSmWorker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmUnavalabilityWorker extends BaseSmWorker<VmSmUnavailability, VmUnavailabilityWrapper, Unavailabilities> {
    @Autowired
    private SmUnavalabilityWorker(
            AppConfig appConfig,
            UnavailabilitiesRepository unavailabilitiesRepository,
            UpdatesRepository updatesRepository)
    {
        super(appConfig,
                unavailabilitiesRepository,
                new ModelMapper(),
                updatesRepository,
                VmUnavailabilityWrapper.class,
                Unavailabilities.class,
                "Unavalabilities",
                appConfig.getSmUnavailabilityUrl());
    }

    @Scheduled(fixedRateString = "${sm.methods.incident.fixedrate}")
    public void loadUnavailabilitiesFromSm() {
        process();
    }
}
