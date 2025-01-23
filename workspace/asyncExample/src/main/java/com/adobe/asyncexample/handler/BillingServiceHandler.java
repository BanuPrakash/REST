package com.adobe.asyncexample.handler;

import com.adobe.asyncexample.dto.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BillingServiceHandler {
    @EventListener
    @Async
    public void processBill(PatientDischargeEvent patientDischargeEvent) {
        System.out.println("Billing Service: Finalize bill for patient "
                + patientDischargeEvent.getPatientId() + " : " + patientDischargeEvent.getPatientName()
        + " executed with " + Thread.currentThread());
    }
}
