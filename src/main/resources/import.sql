
-- Add data to adoption_conditions table
INSERT INTO adoption_conditions (garden, id, details, mqs) VALUES (true, 111, 'gli piacciono i bambini', 70);
INSERT INTO adoption_conditions (garden, id, details, mqs) VALUES (false, 222, 'molto timido, ma coccolone', 60);
INSERT INTO adoption_conditions (garden, id, details, mqs) VALUES (true, 333, 'gli piace fare lunghe passeggiate', 90);

-- Add data to animal table
INSERT INTO animal (id, conditions_id, description, name, species, url_image) VALUES (123, 111, 'pelo corto, labrador', 'Cracco', 'cane', '/images/labrador.jpg');
INSERT INTO animal (id, conditions_id, description, name, species, url_image) VALUES (456, 222, 'pelo corto, tabby', 'Geronimo', 'gatto', 'https://cdn.freecodecamp.org/curriculum/cat-photo-app/relaxing-cat.jpg');
INSERT INTO animal (id, conditions_id, description, name, species, url_image) VALUES (789, 333, 'pelo lungo, pastore tedesco', 'Asso', 'cane', 'https://upload.wikimedia.org/wikipedia/commons/d/d9/Collage_of_Nine_Dogs.jpg');

