package Monitoring.Monitoring.services.workers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import Monitoring.Monitoring.config.AppConfig;
import Monitoring.Monitoring.db.models.PushToken;
import Monitoring.Monitoring.db.models.VtbIncidents;
import Monitoring.Monitoring.db.repositories.interfaces.PushTokenRepository;
import Monitoring.Monitoring.db.repositories.interfaces.VtbIncidentsRepository;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class NotificationsSender {
    @Autowired
    AppConfig appConfig;

    @Autowired
    private VtbIncidentsRepository vtbIncidentsRepository;

    @Autowired
    private PushTokenRepository pushTokenRepository;

    @Scheduled(fixedRateString = "${notificationsender.scheduler.fixedrate}")
    public void sendPushNotifications() {
        List<VtbIncidents> incidents = vtbIncidentsRepository.getTimeFilteredNonSentVtbIncidents(appConfig.getLastDaysToProcess());
        RestTemplate restTemplate = new RestTemplate();
        try {
            // TODO отправлять все аварии в одном вызове?
            incidents.forEach(i -> sendNotificationsForIncident(restTemplate, i));
            var ids = incidents.stream().map(VtbIncidents::getId).collect(toSet());
            vtbIncidentsRepository.markAsNotificationSent(ids);
        }
        catch (Exception e) {
            log.error("Ошибка при передаче инцидента на сервис отправки уведомлений.", e);
        }
    }

    private void sendNotificationsForIncident(RestTemplate restTemplate, VtbIncidents i) {
        Map<String, Object> request = Map.of(
                "accident", buildAccidentInfo(i),
                "targets", buildTargets(pushTokenRepository.findAll()));

        // TODO добавить обработку результата. Возможно проставлять отметку об отправке уведомления для устройства.
        ResponseEntity<Void> result = restTemplate.postForEntity(appConfig.getPusherUrl(), request, Void.class);
    }

    @NotNull
    private Map<String, Serializable> buildAccidentInfo(VtbIncidents i) {
        return Map.of(
                "id", i.getId(),
                "header", "Авария " + i.getId(),
                "description", "Обнаружена новая авария, подключитесь к устранению.",
                "name", "Инцидент " + i.getId());
    }

    @NotNull
    private List<Map<String, String>> buildTargets(List<PushToken> allTokens) {
        return allTokens
                .stream()
                .map(t -> Map.of("token", t.getToken(), "osName", t.getPlatform()))
                .collect(toList());
    }
}