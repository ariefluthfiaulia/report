package com.ala.report.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReportDTO {
    private String employeeName;
    private String projectName;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private double totalHours;

    public ReportDTO(String employeeName, String projectName, LocalDateTime timeFrom, LocalDateTime timeTo) {
        this.employeeName = employeeName;
        this.projectName = projectName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.totalHours = calculateTotalHours();
    }
    
    private double calculateTotalHours() {
        Duration duration = Duration.between(timeFrom, timeTo);
        return duration.toHours() + (duration.toMinutesPart() / 60.0);
    }
}
