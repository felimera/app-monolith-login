
DROP TABLE IF EXISTS public.tbl_user CASCADE;
DROP TABLE IF EXISTS public.cat_rol CASCADE;

-- Crecion del catalogo de roles
CREATE TABLE IF NOT EXISTS public.cat_rol
(
    ro_id INT8 GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ro_code TEXT NOT NULL UNIQUE,
    ro_name TEXT NOT NULL,
    ro_description TEXT NULL,
    ro_valid BOOLEAN DEFAULT TRUE,
    ro_visible BOOLEAN DEFAULT TRUE
);
-- Creacion de los registro para catalo de Roles.
INSERT INTO cat_rol (ro_name,ro_code,ro_description, ro_visible) VALUES
('Administrador','ADMIN',null,false);
INSERT INTO cat_rol (ro_name,ro_code,ro_description) VALUES
('Director/a','DIRECT', 'Cumple funciones de semi-administrativas.'),
('Cliente','CLIENT', 'Usurio final del sistema.');

-- Creacion tabla USER
CREATE TABLE IF NOT EXISTS public.tbl_user
(
    us_id INT8 GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    us_username TEXT NOT NULL,
    us_password TEXT NOT NULL,
    us_first_name TEXT NOT NULL,
    us_last_name TEXT NOT NULL,
    us_email TEXT NOT NULL UNIQUE,
    us_phone_1 TEXT NULL,
    us_phone_2 TEXT NULL,
    us_ro_id INT8 REFERENCES cat_rol(ro_id) NOT NULL,
    us_registration_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    us_modification_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE public.tbl_user ADD CONSTRAINT check_phone_1_numeric CHECK (us_phone_1 IS NULL OR us_phone_1 ~ '^\+?[0-9]+$');
ALTER TABLE public.tbl_user ADD CONSTRAINT check_phone_2_numeric CHECK (us_phone_2 IS NULL OR us_phone_2 ~ '^\+?[0-9]+$');

-- Creación del primer usuario del sistema.
-- Password test123
INSERT INTO tbl_user(us_username,us_password,us_first_name,us_last_name,us_email,us_ro_id)
values
('admin','$2a$10$b2VxzyvEdwxrWodUXkg46uULCzS543ffDT102RxF5IB7qn0SCTJbO','admin','admin','admin@test.com',1),
('test','$2a$10$b2VxzyvEdwxrWodUXkg46uULCzS543ffDT102RxF5IB7qn0SCTJbO','test','test','test@test.com',2),
('test2','$2a$10$b2VxzyvEdwxrWodUXkg46uULCzS543ffDT102RxF5IB7qn0SCTJbO','test2','test2','test2@test2.com',3);

