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

    @Query("SELECT new com.ala.report.dto.ReportDTO(e.name, p.name, t.timeFrom, t.timeTo) " +
           "FROM TimeRecord t " +
           "JOIN t.employee e " +
           "JOIN t.project p " +
           "WHERE t.timeFrom BETWEEN :startDate AND :endDate")
    List<ReportDTO> getReportData(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);


    @Query("SELECT new com.ala.report.dto.ReportDTO(e.name, p.name, t.timeFrom, t.timeTo) " +
           "FROM TimeRecord t " +
           "JOIN t.employee e " +
           "JOIN t.project p " +
           "WHERE e.name = :employeeName AND t.timeFrom BETWEEN :startDate AND :endDate")
    List<ReportDTO> getReportDataForEmployee(@Param("employeeName") String employeeName,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    }
