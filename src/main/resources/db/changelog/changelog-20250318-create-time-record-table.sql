DROP TABLE IF EXISTS time_record;

CREATE TABLE time_record (
 id INT8 PRIMARY KEY,
 employee_id INT8 NOT NULL,
 project_id INT8 NOT NULL,
 time_from TIMESTAMP NOT NULL,
 time_to TIMESTAMP NOT NULL,
 CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
 CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);