package com.ala.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class ReportDTO {
    private String employeeName;
    private String projectName;
    private double totalHours;
}
