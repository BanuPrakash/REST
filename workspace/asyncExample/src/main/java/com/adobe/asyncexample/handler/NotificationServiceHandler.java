package com.adobe.asyncexample.handler;

import com.adobe.asyncexample.dto.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceHandler {
    @EventListener
    @Async
    public void notify(PatientDischargeEvent patientDischargeEvent) {
        System.out.println("Notification Service: Send Notification for patient "
                + patientDischargeEvent.getPatientId() + " : " + patientDischargeEvent.getPatientName()
        +
                " executed with : " + Thread.currentThread() );
    }
}
