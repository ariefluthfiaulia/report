CREATE INDEX idx_time_record_time_range ON time_record (time_from, time_to);
CREATE INDEX idx_project_name ON project (name);
CREATE INDEX idx_time_record_employee_time ON time_record (employee_id, time_from); 

DROP INDEX IF EXISTS idx_time_record_employee_id;
DROP INDEX IF EXISTS idx_time_record_time_from;
