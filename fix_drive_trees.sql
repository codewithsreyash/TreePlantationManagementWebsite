-- 1. Drop the composite Primary Key constraint generated automatically by Hibernate.
-- This allows multiple rows of the exact same (drive_id, tree_id) tuple to exist for our quantities.
ALTER TABLE drive_trees DROP PRIMARY KEY;

-- 2. (Highly Recommended) Add a generic surrogate ID primary key.
-- A table without a primary key is terrible for performance in MySQL InnoDB.
-- The generic 'id' column will keep each quantity row perfectly unique even if drive_id and tree_id overlap.
ALTER TABLE drive_trees ADD COLUMN id BIGINT AUTO_INCREMENT PRIMARY KEY FIRST;
