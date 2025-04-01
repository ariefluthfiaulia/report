package com.ala.report.service.impl;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ala.report.dto.ReportDTO;
import com.ala.report.repository.TimeRecordRepository;
import com.ala.report.service.ReportService;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    
    private final TimeRecordRepository timeRecordRepository;

    public ReportServiceImpl(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }

    @Override
    public Page<ReportDTO> getReportData(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return timeRecordRepository.getReportData(startDate, endDate, pageable);
    }

    @Override
    public Page<ReportDTO> getReportDataForEmployee(String employeeName, LocalDateTime startDate, 
                                                   LocalDateTime endDate, Pageable pageable) {
        return timeRecordRepository.getReportDataForEmployee(employeeName, startDate, endDate, pageable);
    }
} 