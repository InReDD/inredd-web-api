-- Sincroniza sequences com os maiores valores existentes nas tabelas

-- user.id_user
SELECT setval(pg_get_serial_sequence('"user"', 'id_user'), COALESCE(MAX(id_user), 1), true) FROM "user";

-- address.id_address
SELECT setval(pg_get_serial_sequence('address', 'id_address'), COALESCE(MAX(id_address), 1), true) FROM address;

-- groups.id_groups
SELECT setval(pg_get_serial_sequence('groups', 'id_groups'), COALESCE(MAX(id_groups), 1), true) FROM groups;

-- permission.id
SELECT setval(pg_get_serial_sequence('permission', 'id'), COALESCE(MAX(id), 1), true) FROM permission;

-- paper.id_paper
SELECT setval(pg_get_serial_sequence('paper', 'id_paper'), COALESCE(MAX(id_paper), 1), true) FROM paper;

-- patients.id_patients
SELECT setval(pg_get_serial_sequence('patients', 'id_patients'), COALESCE(MAX(id_patients), 1), true) FROM patients;

-- registers.id_registers
SELECT setval(pg_get_serial_sequence('registers', 'id_registers'), COALESCE(MAX(id_registers), 1), true) FROM registers;

-- access.id_access
SELECT setval(pg_get_serial_sequence('access', 'id_access'), COALESCE(MAX(id_access), 1), true) FROM access;
