package com.adobe.asyncexample.dto;

import org.springframework.context.ApplicationEvent;

public class PatientDischargeEvent extends ApplicationEvent {
    String patientId;
    String patientName;
    public PatientDischargeEvent(Object source, String patientId, String patientName) {
        super(source);
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
