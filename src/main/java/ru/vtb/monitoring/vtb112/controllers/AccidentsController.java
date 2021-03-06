package ru.vtb.monitoring.vtb112.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.monitoring.vtb112.db.models.AffectedSystem;
import ru.vtb.monitoring.vtb112.db.repositories.interfaces.IncidentRepository;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.converters.IncidentStatusConverter;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.request.VmAccidentsRequest;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.response.*;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.submodels.VmManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api/v1.0/accidents")
public class AccidentsController {

    @Autowired
    IncidentRepository incidentDAO; //todo move to @Service layer

    @PostMapping(consumes = "application/json;charset=UTF-8",
                 produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<VmAccidentResponse>> getAccidents(@RequestBody VmAccidentsRequest request) {

        var paging = PageRequest.of(request.getPage(), request.getLimit());
        var supportedCategories = Arrays.asList(1,2);
        return new ResponseEntity<>(incidentDAO.allByCriteria(supportedCategories,
                                                              request.getAffectedSystems(),
                                                              request.getStartDate(),
                                                              request.getKeyword(),
                                                              paging)
                .stream()
                .map(incident ->
                        new VmAccidentResponse(
                                incident.getId().toString(),
                                incident.getIncidentId(),
                                incident.getCategory() == null ? 0 : incident.getCategory(),
                                IncidentStatusConverter.convertToStatus(
                                        incident.getStatus(),
                                        incident.getFactEndAt(),
                                        incident.getExpiredAt()
                                ),
                                IncidentStatusConverter.convertToStatusType(incident.getStatus()),
                                incident.getDescription(),
                                incident.getAffectedSystems()
                                        .stream()
                                        .map(AffectedSystem::getName)
                                        .collect(Collectors.toList()),
                                incident.getIdentedAt()
                        )
                )
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/new", produces = "application/json;charset=UTF-8")
    public ResponseEntity<VmNewAccidentResponse> getNewAccident() {
        return new ResponseEntity<>(incidentDAO.findMaxByCreatedDate()
                .map(incident ->
                        new VmNewAccidentResponse(
                                incident.getId().toString(),
                                incident.getIncidentId()
                        )
                )
                .orElse(null), HttpStatus.OK);

    }

    @GetMapping(value = "/info", produces = "application/json;charset=UTF-8")
    public ResponseEntity<VmAccidentInfoResponse> getAccidentInfo(@RequestParam Integer id) {
        return new ResponseEntity<>(incidentDAO.findById(id)
                .map(incident ->
                    new VmAccidentInfoResponse(
                            incident.getId().toString(),
                            incident.getIncidentId(),
                            incident.getCategory() == null ? 0 : incident.getCategory(),
                            IncidentStatusConverter.convertToStatus(
                                    incident.getStatus(),
                                    incident.getFactEndAt(),
                                    incident.getExpiredAt()
                            ),
                            IncidentStatusConverter.convertToStatusType(incident.getStatus()),
                            incident.getDescription(),
                            incident.getImpact(),
                            incident.getFailurePoint(),
                            "М-Банк",
                            incident.getAffectedSystems()
                                    .stream()
                                    .map(AffectedSystem::getName)
                                    .collect(Collectors.toList()),
                            incident.getCreatedAt(),
                            incident.getIdentedAt(),
                            incident.getExpiredAt(),
                            "https://bankvtb.webex.com/meet/xxx",
                            "https://t.me/vtb"
                    )
                )
                .orElse(null), HttpStatus.OK);
    }

    @GetMapping(value = "/workers", produces = "application/json;charset=UTF-8")
    public ResponseEntity<VmAccidentWorkersResponse> getWorkers(@RequestParam Integer id) {
        return new ResponseEntity<>(incidentDAO.findById(id)
                .map(incident ->
                        new VmAccidentWorkersResponse(
                                new VmManager(incident.getSpecialistId(), null, null),
                                Collections.emptyList()
                        )
                )
                .orElse(null), HttpStatus.OK);
    }

    @GetMapping(value = "/descriptions", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<VmAccidentDescriptionResponse>> getAccidentDescriptions(@RequestParam Integer id) {
        return new ResponseEntity<>(incidentDAO.findById(id)
                .map(incident -> Collections.singletonList(
                        new VmAccidentDescriptionResponse(
                                incident.getDescription(),
                                null
                        ))
                )
                .orElse(null), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<VmAccidentHistoryResponse> getHistory(@RequestParam String id) {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

}