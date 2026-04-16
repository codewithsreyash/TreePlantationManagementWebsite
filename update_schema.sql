-- 1. Rename the existing 'species' column to 'name' to match the updated entity.
-- This keeps any of your existing tree data intact!
ALTER TABLE tree_species CHANGE COLUMN species name VARCHAR(255) NOT NULL;

-- 2. Add the new scientific_name column.
-- It can safely remain nullable for any previously existing records.
ALTER TABLE tree_species ADD COLUMN scientific_name VARCHAR(255);
