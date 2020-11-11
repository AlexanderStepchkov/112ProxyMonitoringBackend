package Monitoring.Monitoring.db.vertica.models;

import java.time.ZonedDateTime;

public class SmDefMeasurementVertica {
    private Integer sessionId;

    private Integer measurementId;

    private Integer schedId;

    private Integer categoryId;

    private Integer monitorId;

    private Integer targetId;

    private String msname;

    private String msid;

    private String userRemark;

    private String connectionData;

    private Integer dmConnectionId;

    private Integer active;

    private Character ciId;

    private Character etiId;

    private String integrationName;

    private String profileId;

    private ZonedDateTime modifiedDate;

    private ZonedDateTime creationDate;

    private boolean isDeleted;

    public SmDefMeasurementVertica(Integer sessionId, Integer measurementId, Integer schedId, Integer categoryId, Integer monitorId, Integer targetId, String msname, String msid, String userRemark, String connectionData, Integer dmConnectionId, Integer active, Character ciId, Character etiId, String integrationName, String profileId, ZonedDateTime modifiedDate, ZonedDateTime creationDate, boolean isDeleted) {
        this.sessionId = sessionId;
        this.measurementId = measurementId;
        this.schedId = schedId;
        this.categoryId = categoryId;
        this.monitorId = monitorId;
        this.targetId = targetId;
        this.msname = msname;
        this.msid = msid;
        this.userRemark = userRemark;
        this.connectionData = connectionData;
        this.dmConnectionId = dmConnectionId;
        this.active = active;
        this.ciId = ciId;
        this.etiId = etiId;
        this.integrationName = integrationName;
        this.profileId = profileId;
        this.modifiedDate = modifiedDate;
        this.creationDate = creationDate;
        this.isDeleted = isDeleted;
    }

    public SmDefMeasurementVertica(){}

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    public Integer getSchedId() {
        return schedId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getMsname() {
        return msname;
    }

    public void setMsname(String msname) {
        this.msname = msname;
    }

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getConnectionData() {
        return connectionData;
    }

    public void setConnectionData(String connectionData) {
        this.connectionData = connectionData;
    }

    public Integer getDmConnectionId() {
        return dmConnectionId;
    }

    public void setDmConnectionId(Integer dmConnectionId) {
        this.dmConnectionId = dmConnectionId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Character getCiId() {
        return ciId;
    }

    public void setCiId(Character ciId) {
        this.ciId = ciId;
    }

    public Character getEtiId() {
        return etiId;
    }

    public void setEtiId(Character etiId) {
        this.etiId = etiId;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
