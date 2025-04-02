-- 1. Backup data
CREATE TABLE time_record_backup AS TABLE time_record;

-- 2. Drop index lama
DROP INDEX IF EXISTS idx_time_record_time_range;
DROP INDEX IF EXISTS idx_time_record_employee_time;
DROP INDEX IF EXISTS idx_time_record_project_id;
DROP INDEX IF EXISTS idx_time_record_employee_id;
DROP INDEX IF EXISTS idx_time_record_time_from;

-- 3. Rename tabel lama
ALTER TABLE time_record RENAME TO time_record_old;

-- 4. Buat tabel baru dengan partitioning
CREATE TABLE time_record (
    id INT8 NOT NULL,
    employee_id INT8 NOT NULL,
    project_id INT8 NOT NULL,
    time_from TIMESTAMP NOT NULL,
    time_to TIMESTAMP NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
) PARTITION BY RANGE (time_from);

-- 5. Buat partisi per tahun
CREATE TABLE time_record_2023 PARTITION OF time_record 
    FOR VALUES FROM ('2023-01-01 00:00:00') TO ('2024-01-01 00:00:00');
CREATE TABLE time_record_2024 PARTITION OF time_record 
    FOR VALUES FROM ('2024-01-01 00:00:00') TO ('2025-01-01 00:00:00');
CREATE TABLE time_record_2025 PARTITION OF time_record 
    FOR VALUES FROM ('2025-01-01 00:00:00') TO ('2026-01-01 00:00:00');

-- 6. Migrasi data
INSERT INTO time_record (id, employee_id, project_id, time_from, time_to)
SELECT id, employee_id, project_id, time_from, time_to FROM time_record_backup;

-- 7. Buat index per partisi
CREATE INDEX idx_time_record_2023_employee_time ON time_record_2023 (employee_id, time_from);
CREATE INDEX idx_time_record_2023_project ON time_record_2023 (project_id);

CREATE INDEX idx_time_record_2024_employee_time ON time_record_2024 (employee_id, time_from);
CREATE INDEX idx_time_record_2024_project ON time_record_2024 (project_id);

CREATE INDEX idx_time_record_2025_employee_time ON time_record_2025 (employee_id, time_from);
CREATE INDEX idx_time_record_2025_project ON time_record_2025 (project_id);

-- 8. Cleanup
DROP TABLE time_record_old;
DROP TABLE time_record_backup;