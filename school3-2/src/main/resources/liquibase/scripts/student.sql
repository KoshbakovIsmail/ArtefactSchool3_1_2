-- liquibase formatted sql

-- changeset koshbakov:1
-- Check if the index exists before creating it
-- liquibase precondition sqlCheck:
-- SELECT count(*) FROM pg_indexes WHERE indexname = 'student' AND tablename = 'student';
-- changeset-end


