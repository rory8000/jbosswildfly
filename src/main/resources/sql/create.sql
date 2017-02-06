ALTER TABLE company ADD CONSTRAINT constraint_ruc UNIQUE (ruc);
SELECT setval('company_id_seq', (select max(id) from company));