package com.ala.report.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ala.report.dto.ReportDTO;
import com.ala.report.model.TimeRecord;

@Repository
public interface ReportRepository extends JpaRepository<TimeRecord, Long> {

    @Query(value = 
        "SELECT e.name AS employeeName, p.name AS projectName, " +
        "CAST(SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600.0) AS double precision) AS totalHours " +
        "FROM time_record t " +
        "JOIN employee e ON t.employee_id = e.id " +
        "JOIN project p ON t.project_id = p.id " +
        "WHERE t.time_from BETWEEN :startDate AND :endDate " +
        "GROUP BY e.name, p.name " +
        "ORDER BY e.name, p.name", nativeQuery = true)
    List<ReportDTO> getReportData(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    @Query(value = 
        "SELECT e.name AS employeeName, p.name AS projectName, " +
        "CAST(SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600.0) AS double precision) AS totalHours " +
        "FROM time_record t " +
        "JOIN employee e ON t.employee_id = e.id " +
        "JOIN project p ON t.project_id = p.id " +
        "WHERE e.name = :employeeName AND t.time_from BETWEEN :startDate AND :endDate " +
        "GROUP BY e.name, p.name " +
        "ORDER BY e.name, p.name", nativeQuery = true) 
    List<ReportDTO> getReportDataForEmployee(@Param("employeeName") String employeeName,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
}