DROP TABLE IF EXISTS radiographs CASCADE;
DROP TABLE IF EXISTS specific_health_questions CASCADE;
DROP TABLE IF EXISTS anamnesis_conditions CASCADE;
DROP TABLE IF EXISTS conditions_lookup CASCADE;
DROP TABLE IF EXISTS anamnesis_forms CASCADE;
DROP TABLE IF EXISTS visits CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TYPE IF EXISTS sex_enum;

CREATE TYPE sex_enum AS ENUM ('MALE', 'FEMALE', 'OTHER');

CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    sex sex_enum,
    address TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE visits (
    id SERIAL PRIMARY KEY,
    patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    visit_date DATE NOT NULL,
    main_complaint TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE anamnesis_forms (
    id SERIAL PRIMARY KEY,
    visit_id INT NOT NULL UNIQUE REFERENCES visits(id) ON DELETE CASCADE,
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

CREATE TABLE radiographs (
    id SERIAL PRIMARY KEY,
    visit_id INT REFERENCES visits(id) ON DELETE SET NULL,
    patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    radiograph_date DATE NOT NULL,
    file_path_or_url VARCHAR(1024) NOT NULL,
    viewer_context_json JSONB,
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE conditions_lookup (
    id SERIAL PRIMARY KEY,
    condition_name VARCHAR(255) UNIQUE NOT NULL,
    category VARCHAR(100)
);

CREATE TABLE anamnesis_conditions (
    anamnesis_id INT NOT NULL REFERENCES anamnesis_forms(id) ON DELETE CASCADE,
    condition_id INT NOT NULL REFERENCES conditions_lookup(id) ON DELETE CASCADE,
    PRIMARY KEY (anamnesis_id, condition_id)
);

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

INSERT INTO conditions_lookup (condition_name, category) VALUES
    ('Drug Allergy', 'Allergy'), ('Arthritis', 'Rheumatologic'), ('Cancer', 'Oncology'),
    ('Epilepsy', 'Neurologic'), ('Dehydration', 'General'), ('Fainting', 'Neurologic'),
    ('Diabetes', 'Endocrine'), ('Diphtheria', 'Infectious'), ('Rheumatic Fever', 'Cardiovascular'),
    ('Hemorrhage', 'Hematologic'), ('Hepatitis', 'Infectious'), ('Herpes', 'Infectious'),
    ('Hypertension', 'Cardiovascular'), ('Infections', 'Infectious'), ('Sexually Transmitted Infections', 'Infectious'),
    ('Bad Breath', 'Dental'), ('Osteoporosis', 'Bone'), ('Heart Problems', 'Cardiovascular'),
    ('Neurological Problems', 'Neurologic'), ('Skin Problems', 'Dermatologic'), ('Kidney Problems', 'Renal'),
    ('Respiratory Problems', 'Respiratory'), ('Measles', 'Infectious'), ('Blood Transfusion', 'History'),
    ('Organ Transplant', 'History');

INSERT INTO patients (full_name, date_of_birth, sex, address) 
VALUES 
    ('John Smith', '1985-04-15', 'MALE', '123 Flower St, Ribeirao Preto, SP'),
    ('Mary Johnson', '1992-11-30', 'FEMALE', '789 Main Ave, Sertaozinho, SP'),
    ('Carlos Pereira', '1978-09-22', 'MALE', '456 Oak Blvd, Cravinhos, SP'),
    ('Ana Beatriz Costa', '1999-03-12', 'FEMALE', '101 Pine Way, Jardinopolis, SP');

INSERT INTO visits (patient_id, visit_date, main_complaint) 
VALUES 
    (1, '2024-05-20', 'Pain in lower right wisdom tooth.'),
    (1, '2025-06-10', 'Routine check-up and cleaning.'),
    (2, '2025-01-15', 'Interested in teeth whitening.'),
    (3, '2025-07-01', 'Broken molar and sensitivity to cold.'),
    (2, '2025-08-22', 'Follow-up on teeth whitening and general sensitivity.'),
    (4, '2025-09-05', 'Consultation for orthodontic treatment options.');

INSERT INTO anamnesis_forms (visit_id, weight_kg, height_m, systolic_bp, diastolic_bp, detailed_medical_history, psychosocial_history, is_taking_medication, is_pregnant, previous_dental_history, family_health_history) 
VALUES 
    (1, 85.5, 1.78, 120, 80, 'No significant health issues reported.', 'Non-smoker, drinks alcohol socially on weekends.', FALSE, NULL, NULL, NULL),
    (2, 86.0, 1.78, 125, 85, NULL, 'Reports stress from new job.', FALSE, NULL, 'Previous wisdom tooth pain resolved.', NULL),
    (3, 65.0, 1.65, 110, 70, NULL, NULL, FALSE, FALSE, 'Used orthodontic braces for 3 years, removed in 2015.', NULL),
    (4, 92.0, 1.85, 135, 88, 'Type 2 Diabetes, controlled with Metformin.', NULL, TRUE, NULL, 'Multiple fillings, occasional gum bleeding.', 'Father had heart problems; mother has osteoporosis.'),
    (5, 65.5, 1.65, 112, 72, 'Reports sensitivity has decreased but still present in upper left quadrant.', NULL, FALSE, FALSE, NULL, 'Mother has history of arthritis.'),
    (6, 58.0, 1.62, 105, 65, 'Asthma, uses inhaler as needed.', 'University student, reports high caffeine intake.', TRUE, FALSE, 'No major dental work.', 'No known hereditary conditions.');

INSERT INTO specific_health_questions (anamnesis_id, has_cardiovascular_issue, has_rheumatic_fever, has_joint_pain, has_excessive_bleeding, had_local_anesthesia, had_anesthesia_reaction, uses_substances, has_allergies, had_medication_related_problem, has_kidney_problems, was_hospitalized, took_penicillin, took_corticosteroid_last_12m, has_persistent_cough) 
VALUES 
    (1, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE),
    (2, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE),
    (3, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE),
    (4, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE),
    (5, FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE),
    (6, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE);

INSERT INTO anamnesis_conditions (anamnesis_id, condition_id) 
VALUES 
    (1, 13),
    (1, 1),
    (3, 22),
    (4, 7),
    (4, 17),
    (4, 18),
    (5, 2),
    (6, 22),
    (6, 1);

INSERT INTO radiographs (visit_id, patient_id, radiograph_date, file_path_or_url, notes, viewer_context_json) 
VALUES 
    (1, 1, '2024-05-21', '/radiographs/john_smith/panoramic_20240521.jpg', 'Panoramic radiograph for wisdom tooth evaluation.', '{"viewer_version": "1.2", "annotations": [{"type": "circle", "points": [450, 320], "radius": 50, "label": "Tooth 48 - Semi-impacted"}], "settings": {"brightness": 55, "contrast": 70}}'),
    (NULL, 2, '2024-10-05', '/radiographs/mary_johnson/periapical_20241005.png', 'Routine check-up from the previous year.', NULL),
    (3, 2, '2025-01-15', '/radiographs/mary_johnson/bitewing_20250115.jpg', 'Bitewing radiographs to check for cavities before whitening.', '{"settings": {"brightness": 50, "contrast": 65}}'),
    (6, 4, '2025-09-05', '/radiographs/ana_costa/cephalometric_20250905.tif', 'Lateral cephalometric radiograph for orthodontic assessment.', '{"settings": {"brightness": 50, "contrast": 50}}');