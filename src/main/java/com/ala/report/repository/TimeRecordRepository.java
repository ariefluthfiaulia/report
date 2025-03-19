package com.ala.report.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ala.report.dto.ReportDTO;
import com.ala.report.model.TimeRecord;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {

    @Query(value = 
        "SELECT e.name AS employeeName, p.name AS projectName, " +
        "CAST(SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600.0) AS double precision) AS totalHours " +
        "FROM time_record t " +
        "JOIN employee e ON t.employee_id = e.id " +
        "JOIN project p ON t.project_id = p.id " +
        "WHERE t.time_from BETWEEN :startDate AND :endDate " +
        "GROUP BY e.name, p.name " +
        "ORDER BY e.name, p.name", nativeQuery = true)
    Page<ReportDTO> getReportData(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate,
                                 Pageable pageable);

    @Query(value = 
        "SELECT e.name AS employeeName, p.name AS projectName, " +
        "CAST(SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600.0) AS double precision) AS totalHours " +
        "FROM time_record t " +
        "JOIN employee e ON t.employee_id = e.id " +
        "JOIN project p ON t.project_id = p.id " +
        "WHERE e.name = :employeeName AND t.time_from BETWEEN :startDate AND :endDate " +
        "GROUP BY e.name, p.name " +
        "ORDER BY e.name, p.name", nativeQuery = true) 
    Page<ReportDTO> getReportDataForEmployee(@Param("employeeName") String employeeName,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate,
                                           Pageable pageable);
}