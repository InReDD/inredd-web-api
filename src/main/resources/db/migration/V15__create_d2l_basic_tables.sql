DROP TABLE IF EXISTS radiographs CASCADE;
DROP TABLE IF EXISTS specific_health_questions CASCADE;
DROP TABLE IF EXISTS anamnesis_conditions CASCADE;
DROP TABLE IF EXISTS conditions_lookup CASCADE;
DROP TABLE IF EXISTS anamnesis_forms CASCADE;
DROP TABLE IF EXISTS visits CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TYPE IF EXISTS sex_enum;
DROP TYPE IF EXISTS respondent_enum;

-- #############################################################################
-- ## SEÇÃO 1: DEFINIÇÃO DA ESTRUTURA DO BANCO DE DADOS (DDL)                 ##
-- #############################################################################

-- Definição dos tipos de dados personalizados (ENUMs)
CREATE TYPE sex_enum AS ENUM ('Masculino', 'Feminino', 'Outro');
CREATE TYPE respondent_enum AS ENUM ('Própria pessoa', 'Pais', 'Responsável');

-- Tabela 1: PATIENTS - Armazena dados demográficos básicos.
CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    sex sex_enum,
    address TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
COMMENT ON TABLE patients IS 'Tabela principal para armazenar dados demográficos dos pacientes.';

-- Tabela 2: VISITS - Nova tabela central para cada encontro do paciente.
CREATE TABLE visits (
    id SERIAL PRIMARY KEY,
    patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    visit_date DATE NOT NULL,
    main_complaint TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
COMMENT ON TABLE visits IS 'Tabela central que representa cada visita ou encontro de um paciente.';
COMMENT ON COLUMN visits.main_complaint IS 'Queixa principal ou motivo da visita.';

-- Tabela 3: ANAMNESIS_FORMS - Questionário detalhado, agora ligado a uma visita.
CREATE TABLE anamnesis_forms (
    id SERIAL PRIMARY KEY,
    -- Relação 1-para-1 com a visita. Cada visita tem no máximo um formulário de anamnese.
    visit_id INT NOT NULL UNIQUE REFERENCES visits(id) ON DELETE CASCADE,
    respondent respondent_enum,
    weight_kg DECIMAL(5, 2),
    height_m DECIMAL(3, 2),
    systolic_bp INT,
    diastolic_bp INT,
    is_pregnant BOOLEAN,
    had_recent_fever BOOLEAN,
    is_under_medical_treatment BOOLEAN,
    is_taking_medication BOOLEAN,
    detailed_medical_history TEXT,
    family_health_history TEXT,
    previous_dental_history TEXT,
    psychosocial_history TEXT,
    additional_info_for_dentist TEXT,
    special_needs_during_treatment TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
COMMENT ON TABLE anamnesis_forms IS 'Armazena o questionário de anamnese detalhado para uma visita específica.';
COMMENT ON COLUMN anamnesis_forms.visit_id IS 'Referência à visita correspondente (FK, UNIQUE).';

-- Tabela 4: RADIOGRAPHS - Exames radiográficos, agora ligados a uma visita.
CREATE TABLE radiographs (
    id SERIAL PRIMARY KEY,
    -- Uma visita pode ter múltiplos exames radiográficos.
    visit_id INT REFERENCES visits(id) ON DELETE SET NULL,
    patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    radiograph_date DATE NOT NULL,
    file_path_or_url VARCHAR(1024) NOT NULL,
    viewer_context_json JSONB,
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
COMMENT ON TABLE radiographs IS 'Armazena informações sobre as radiografias, associadas a uma visita.';
COMMENT ON COLUMN radiographs.visit_id IS 'Referência à visita em que a radiografia foi solicitada/analisada.';

-- Tabela 5: CONDITIONS_LOOKUP - Tabela de consulta para condições médicas.
CREATE TABLE conditions_lookup (
    id SERIAL PRIMARY KEY,
    condition_name VARCHAR(255) UNIQUE NOT NULL,
    category VARCHAR(100)
);
INSERT INTO conditions_lookup (condition_name, category) VALUES
    ('Alergia a Medicamentos', 'Alergia'), ('Artrite', 'Reumatológica'), ('Câncer', 'Oncologia'),
    ('Epilepsia', 'Neurológica'), ('Desidratação', 'Geral'), ('Desmaios', 'Neurológica'),
    ('Diabetes', 'Endócrina'), ('Difteria', 'Infecciosa'), ('Febre Reumática', 'Cardiovascular'),
    ('Hemorragia', 'Hematológica'), ('Hepatite', 'Infecciosa'), ('Herpes', 'Infecciosa'),
    ('Hipertensão', 'Cardiovascular'), ('Infecções', 'Infecciosa'), ('Infecções Sexualmente Transmissíveis', 'Infecciosa'),
    ('Mau-hálito', 'Odontológica'), ('Osteoporose', 'Óssea'), ('Problemas Cardíacos', 'Cardiovascular'),
    ('Problemas Neurológicos', 'Neurológica'), ('Problemas de Pele', 'Dermatológica'), ('Problemas Renais', 'Renal'),
    ('Problemas Respiratórios', 'Respiratória'), ('Sarampo', 'Infecciosa'), ('Transfusão de Sangue', 'Histórico'),
    ('Transplante de Órgãos', 'Histórico');

-- Tabela 6: ANAMNESIS_CONDITIONS - Tabela de junção para condições de um formulário.
CREATE TABLE anamnesis_conditions (
    anamnesis_id INT NOT NULL REFERENCES anamnesis_forms(id) ON DELETE CASCADE,
    condition_id INT NOT NULL REFERENCES conditions_lookup(id) ON DELETE CASCADE,
    PRIMARY KEY (anamnesis_id, condition_id)
);

-- Tabela 7: SPECIFIC_HEALTH_QUESTIONS - Respostas Sim/Não para um formulário.
CREATE TABLE specific_health_questions (
    anamnesis_id INT PRIMARY KEY REFERENCES anamnesis_forms(id) ON DELETE CASCADE,
    has_cardiovascular_issue BOOLEAN, has_rheumatic_fever BOOLEAN, has_joint_pain BOOLEAN,
    has_chest_pain BOOLEAN, has_fatigue_on_exertion BOOLEAN, has_ankle_edema BOOLEAN,
    has_recent_weight_loss BOOLEAN, had_hepatitis BOOLEAN, has_kidney_problems BOOLEAN,
    has_gastric_problems BOOLEAN, has_dizziness_fainting BOOLEAN, has_epilepsy BOOLEAN,
    was_hospitalized BOOLEAN, has_persistent_cough BOOLEAN, had_local_anesthesia BOOLEAN,
    had_anesthesia_reaction BOOLEAN, had_general_anesthesia BOOLEAN, has_excessive_bleeding BOOLEAN,
    bleeding_control_method TEXT, had_dental_treatment_complication BOOLEAN, took_penicillin BOOLEAN,
    took_corticosteroid_last_12m BOOLEAN, has_allergies BOOLEAN, had_medication_related_problem BOOLEAN,
    uses_substances BOOLEAN, had_oral_white_spots BOOLEAN, white_spots_treatment TEXT,
    has_recurrent_aphthous_ulcers BOOLEAN, had_hiv_test BOOLEAN, has_insensitive_body_area BOOLEAN
);

-- #############################################################################
-- ## SEÇÃO 2: INSERÇÃO DE DADOS DE EXEMPLO (DML) - FORMATO MULTILINHA        ##
-- #############################################################################

-- PACIENTE 1: João da Silva
INSERT INTO patients (full_name, date_of_birth, sex, address) 
VALUES 
    ('João da Silva', '1985-04-15', 'Masculino', 'Rua das Flores, 123, Ribeirão Preto, SP');

-- VISITA 1 PARA João da Silva (Paciente ID: 1, Visita ID: 1, Anamnese ID: 1)
INSERT INTO visits (patient_id, visit_date, main_complaint) 
VALUES 
    (1, '2024-05-20', 'Dor no dente do siso inferior direito.');

INSERT INTO anamnesis_forms (visit_id, respondent, weight_kg, height_m, systolic_bp, diastolic_bp, detailed_medical_history, psychosocial_history) 
VALUES 
    (1, 'Própria pessoa', 85.5, 1.78, 120, 80, 'Nenhum problema de saúde significativo relatado.', 'Não fumante, consome álcool socialmente aos finais de semana.');

INSERT INTO anamnesis_conditions (anamnesis_id, condition_id) 
VALUES 
    (1, 13), -- Hipertensão
    (1, 1);  -- Alergia

INSERT INTO specific_health_questions (anamnesis_id, has_cardiovascular_issue, has_rheumatic_fever, has_joint_pain, has_excessive_bleeding, had_local_anesthesia, had_anesthesia_reaction, uses_substances) 
VALUES 
    (1, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE);

-- RADIOGRAFIA para a VISITA 1 de João da Silva
INSERT INTO radiographs (visit_id, patient_id, radiograph_date, file_path_or_url, notes, viewer_context_json) 
VALUES 
    (1, 1, '2024-05-21', '/radiographs/joao_silva/panoramic_20240521.jpg', 'Radiografia panorâmica para avaliação do siso.', '{"viewer_version": "1.2", "annotations": [{"type": "circle", "points": [450, 320], "radius": 50, "label": "Dente 48 - Semi-incluso"}], "settings": {"brightness": 55, "contrast": 70}}');

-- VISITA 2 PARA João da Silva (Paciente ID: 1, Visita ID: 2, Anamnese ID: 2)
INSERT INTO visits (patient_id, visit_date, main_complaint) 
VALUES 
    (1, '2024-06-10', 'Consulta de rotina e limpeza.');

INSERT INTO anamnesis_forms (visit_id, respondent, systolic_bp, diastolic_bp, additional_info_for_dentist) 
VALUES 
    (2, 'Própria pessoa', 125, 85, 'A dor no siso melhorou após o medicamento prescrito.');

INSERT INTO specific_health_questions (anamnesis_id, has_cardiovascular_issue, has_rheumatic_fever, has_joint_pain, has_excessive_bleeding, had_local_anesthesia, had_anesthesia_reaction, uses_substances) 
VALUES 
    (2, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE);

-- PACIENTE 2: Maria Oliveira
INSERT INTO patients (full_name, date_of_birth, sex, address) 
VALUES 
    ('Maria Oliveira', '1992-11-30', 'Feminino', 'Avenida Principal, 789, Sertãozinho, SP');

-- Radiografia antiga para Maria, sem associação a uma visita específica no novo sistema.
INSERT INTO radiographs (visit_id, patient_id, radiograph_date, file_path_or_url, notes) 
VALUES 
    (NULL, 2, '2023-10-05', '/radiographs/maria_oliveira/periapical_20231005.png', 'Check-up de rotina do ano anterior.');

-- VISITA 1 PARA Maria Oliveira (Paciente ID: 2, Visita ID: 3, Anamnese ID: 3)
INSERT INTO visits (patient_id, visit_date, main_complaint) 
VALUES 
    (2, '2025-01-15', 'Gostaria de fazer clareamento dental.');

INSERT INTO anamnesis_forms (visit_id, respondent, weight_kg, height_m, is_pregnant, previous_dental_history) 
VALUES 
    (3, 'Própria pessoa', 65.0, 1.65, FALSE, 'Já usei aparelho ortodôntico por 3 anos, removido em 2015.');

INSERT INTO anamnesis_conditions (anamnesis_id, condition_id) 
VALUES 
    (3, 22); -- Problema Respiratório

INSERT INTO specific_health_questions (anamnesis_id, has_cardiovascular_issue, has_rheumatic_fever, has_allergies, had_medication_related_problem, had_local_anesthesia, uses_substances) 
VALUES 
    (3, FALSE, FALSE, TRUE, TRUE, TRUE, FALSE);

-- RADIOGRAFIA para a VISITA 1 de Maria Oliveira
INSERT INTO radiographs (visit_id, patient_id, radiograph_date, file_path_or_url, notes, viewer_context_json) 
VALUES 
    (3, 2, '2025-01-15', '/radiographs/maria_oliveira/bitewing_20250115.jpg', 'Radiografias interproximais para checar cáries antes do clareamento.', '{"settings": {"brightness": 50, "contrast": 65}}');