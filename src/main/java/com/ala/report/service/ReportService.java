package com.ala.report.service;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ala.report.dto.ReportDTO;

public interface ReportService {
    Page<ReportDTO> getReportData(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<ReportDTO> getReportDataForEmployee(String employeeName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
} 