package com.adobe.asyncexample.api;

import com.adobe.asyncexample.dto.PatientDischargeRequest;
import com.adobe.asyncexample.service.PatientDischargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/discharge")
public class PatientController {
    @Autowired
    private PatientDischargeService service;

    @PostMapping()
    public  String discharge(@RequestBody PatientDischargeRequest request) {
        service.discharge(request.getPatientId(), request.getPatientName());
        return "Patient Discharge done !!!";
    }
}
