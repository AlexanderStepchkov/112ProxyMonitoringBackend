package Monitoring.Monitoring.dto.viewmodels.response;

import Monitoring.Monitoring.dto.viewmodels.enums.BlMetricsStatus;

import java.time.ZonedDateTime;

public class VmMetricInfoResponse {
    private long value;
    private long delta;
    private double deltaPercent;
    private BlMetricsStatus deltaStatus;
    private ZonedDateTime date;

    public VmMetricInfoResponse(long value, long delta, double deltaPercent, BlMetricsStatus deltaStatus, ZonedDateTime date) {
        this.value = value;
        this.delta = delta;
        this.deltaPercent = deltaPercent;
        this.deltaStatus = deltaStatus;
        this.date = date;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public double getDeltaPercent() {
        return deltaPercent;
    }

    public void setDeltaPercent(double deltaPercent) {
        this.deltaPercent = deltaPercent;
    }

    public BlMetricsStatus getDeltaStatus() {
        return deltaStatus;
    }

    public void setDeltaStatus(BlMetricsStatus deltaStatus) {
        this.deltaStatus = deltaStatus;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}