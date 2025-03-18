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
public interface ReportRepository extends JpaRepository<TimeRecord, Long>  {
    @Query("SELECT new com.ala.report.dto.ReportDTO(e.name, p.name, " +
           "SUM(EXTRACT(EPOCH FROM (t.timeTo - t.timeFrom)) / 3600)) " +
           "FROM TimeRecord t " +
           "JOIN t.employee e " +
           "JOIN t.project p " +
           "WHERE t.timeFrom BETWEEN :startDate AND :endDate " +
           "GROUP BY e.name, p.name " +
           "ORDER BY e.name, p.name")
    List<ReportDTO> getReportData(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);
}
