package com.ala.report.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ala.report.dto.ReportDTO;
import com.ala.report.repository.TimeRecordRepository;

@Controller
public class ReportController {

    private final TimeRecordRepository reportRepository;

    public ReportController(TimeRecordRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/report")
    public String showReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {

        if (startDate == null || endDate == null) {
            startDate = LocalDateTime.now().minusMonths(1);
            endDate = LocalDateTime.now();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Page<ReportDTO> reportPage;
        if (isAdmin) {
            reportPage = reportRepository.getReportData(startDate, endDate, pageable);
        } else {
            reportPage = reportRepository.getReportDataForEmployee(username, startDate, endDate, pageable);
        }

        model.addAttribute("reportPage", reportPage);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "work_hours_report";
    }
}