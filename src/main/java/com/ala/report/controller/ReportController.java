package com.ala.report.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ala.report.dto.ReportDTO;
import com.ala.report.repository.ReportRepository;

public class ReportController {

    private final ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/report")
    public String showReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                             Model model) {

        if (startDate == null || endDate == null) {
            startDate = LocalDateTime.now().minusMonths(1);
            endDate = LocalDateTime.now();
        }

        List<ReportDTO> reportData = reportRepository.getReportData(startDate, endDate);
        model.addAttribute("reportData", reportData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "work_hours_report";
    }
}