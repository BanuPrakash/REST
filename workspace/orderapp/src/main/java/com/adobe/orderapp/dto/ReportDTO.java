package com.adobe.orderapp.dto;

import java.util.Date;

public record ReportDTO(Integer oid, Date orderDate, String firstName, String email) {
}
