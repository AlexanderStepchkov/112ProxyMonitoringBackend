package ru.vtb.monitoring.vtb112.dto.api.viewmodels.response;

public class VmErrorResponse {
    private String errorMessage;
    private String requestCode;

    public VmErrorResponse(String errorMessage, String requestCode) {
        this.errorMessage = errorMessage;
        this.requestCode = requestCode;
    }

    public VmErrorResponse(){}

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}
