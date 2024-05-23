-- liquibase formatted sql

-- changeset koshbakov:1
-- Check if the index exists before creating it
-- liquibase precondition sqlCheck:
-- SELECT count(*) FROM pg_indexes WHERE indexname = 'student' AND tablename = 'student';
-- changeset-end

-- changeset koshbakov:1 runOnChange:true
 create index student ON student (name);

--changeset koshbakov:2
create index faculty_name_color_index ON faculty (name, color);