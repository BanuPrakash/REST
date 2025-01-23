package com.adobe.asyncexample.dto;

public class PatientDischargeRequest {
    private String patientId;
    private String patientName;

    public PatientDischargeRequest() {
    }

    public PatientDischargeRequest(String patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
