package com.adobe.asyncexample.service;

import com.adobe.asyncexample.dto.PatientDischargeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PatientDischargeService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public  void discharge(String patientId, String patientName) {
        eventPublisher.publishEvent(new PatientDischargeEvent(this, patientId, patientName));
    }
}
