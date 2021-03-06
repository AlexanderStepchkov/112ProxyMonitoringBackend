package ru.vtb.monitoring.vtb112.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.response.VmVersion;

@RestController
@RequestMapping("/api/v1.0")
public class VersionController {

    @GetMapping(value = "/checkVersion", produces = "application/json;charset=UTF-8")
    public ResponseEntity<VmVersion> getVersion(@RequestParam String version) {
        return new ResponseEntity<>(new VmVersion("normal"), HttpStatus.OK);
    }
}
