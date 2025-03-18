package com.ala.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDTO {
    private String employeeName;
    private String projectName;
    private double totalHours;
}
