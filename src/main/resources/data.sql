-- Insertar datos en la tabla 'alimento'
INSERT INTO alimento (nombre, tipo, estado, fecha_caducidad) VALUES
('Manzana', 'Perecedero', 'Cerrado', '2024-12-31'),
('Arroz', 'No perecedero', 'Cerrado', '2025-06-15'),
('Leche', 'Perecedero', 'Abierto', '2024-12-20'),
('Tomate', 'Perecedero', 'Cerrado', '2024-12-18'),
('Pollo', 'Perecedero', 'Congelado', '2025-01-15'),
('Atún enlatado', 'No perecedero', 'Cerrado', '2026-03-01'),
('Queso', 'Perecedero', 'Abierto', '2024-12-25'),
('Harina', 'No perecedero', 'Cerrado', '2025-11-10');

-- Insertar datos en la tabla 'ubicacion'
INSERT INTO ubicacion (descripcion, tipo_ubicacion, capacidad) VALUES
('Estante superior de la alacena', 'ALACENA', 50),
('Bandeja media del refrigerador', 'NEVERA', 20),
('Cajón del congelador', 'CONGELADOR', 30),
('Estante inferior de la alacena', 'ALACENA', 40),
('Puerta del refrigerador', 'NEVERA', 10);

-- Insertar datos en la tabla 'existencia'
INSERT INTO existencia (alimento_id, ubicacion_id, cantidad, fecha_entrada) VALUES
(1, 1, 10, '2024-12-01'), -- Manzana en la alacena
(2, 1, 25, '2024-11-28'), -- Arroz en la alacena
(3, 2, 5, '2024-12-10'),  -- Leche en la nevera
(4, 2, 8, '2024-12-12'),  -- Tomate en la nevera
(5, 3, 15, '2024-12-05'), -- Pollo en el congelador
(6, 1, 12, '2024-11-15'), -- Atún enlatado en la alacena
(7, 2, 6, '2024-12-15'),  -- Queso en la nevera
(8, 4, 20, '2024-11-20'), -- Harina en otro estante de la alacena
(1, 5, 4, '2024-12-11'),  -- Manzana en la puerta del refrigerador
(4, 5, 10, '2024-12-14'), -- Tomate en la puerta del refrigerador
(8, 1, 5, '2024-11-10');  -- Harina en el estante superior de la alacena

-- Insertar datos en la tabla 'usuario'
INSERT INTO usuario (nombre, email, password, rol) VALUES
('admin', 'admin@test.com', 'pestillo', 'ADMINISTRADOR'),
('Rebeca', 'rebeca@test.com', 'pestillo', 'USUARIO')