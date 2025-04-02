package com.ala.report.service.impl;

import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.ala.report.dto.ReportDTO;
import com.ala.report.repository.TimeRecordRepository;
import com.ala.report.service.ReportService;
import com.ala.report.exception.ReportException;

@Service
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService {
    
    private final TimeRecordRepository timeRecordRepository;

    public ReportServiceImpl(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> getReportData(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        validateDateRange(startDate, endDate);
        
        try {
            log.info("Getting report data for start date: {}, end date: {}, pageable: {}", startDate, endDate, pageable);
            return timeRecordRepository.getReportData(startDate, endDate, pageable);
        } catch (DataAccessException e) {
            log.error("Database error when getting report data", e);
            throw new ReportException("Failed to retrieve data from database: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> getReportDataForEmployee(String employeeName, LocalDateTime startDate, 
                                                   LocalDateTime endDate, Pageable pageable) {
        validateDateRange(startDate, endDate);
        
        try {
            log.info("Getting report data for employee: {}, start date: {}, end date: {}, pageable: {}", employeeName, startDate, endDate, pageable);
            return timeRecordRepository.getReportDataForEmployee(employeeName, startDate, endDate, pageable);
        } catch (DataAccessException e) {
            log.error("Database error when getting employee report data", e);
            throw new ReportException("Failed to retrieve data from database: " + e.getMessage());
        }
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ReportException("Start date must be before end date");
        }
        if (startDate.isAfter(LocalDateTime.now())) {
            throw new ReportException("Start date cannot be in the future");
        }
    }
} 