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
    visit_id INT NOT NULL UNIQUE REFERENCES visits(id) ON DELETE CASCADE,
    patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    viewer_context_json JSONB, 
    image_data BYTEA NOT NULL,
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

-- Example data insertion
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



INSERT INTO radiographs (visit_id, patient_id, image_data, notes, viewer_context_json) 
VALUES 
    -- (1, 1, decode('89504E470D0A1A0A0000000D4948445200000100000001000806000000', 'hex'), 'Panoramic radiograph for wisdom tooth evaluation.', '{"viewer_version": "1.2", "annotations": [{"type": "circle", "points": [450, 320], "radius": 50, "label": "Tooth 48 - Semi-impacted"}], "settings": {"brightness": 55, "contrast": 70}}'),
    -- (2, 1, decode('89504E470D0A1A0A0000000D4948445200000100000001000806000000', 'hex'), 'Routine check-up radiograph.', '{"settings": {"brightness": 50, "contrast": 65}}'),
    -- (3, 2, decode('89504E470D0A1A0A0000000D4948445200000100000001000806000000', 'hex'), 'Bitewing radiographs to check for cavities before whitening.', '{"settings": {"brightness": 50, "contrast": 65}}'),
    -- (4, 4, decode('89504E470D0A1A0A0000000D4948445200000100000001000806000000', 'hex'), 'Lateral cephalometric radiograph for orthodontic assessment.', '{"settings": {"brightness": 50, "contrast": 50}}');
    (1, 1, pg_read_binary_file('/srv/pg_imports/images/29-F-68.jpg'), 'Panoramic radiograph for wisdom tooth evaluation.', '{"viewer_version": "1.2", "annotations": [{"type": "circle", "points": [450, 320], "radius": 50, "label": "Tooth 48 - Semi-impacted"}], "settings": {"brightness": 55, "contrast": 70}}'),
    (2, 1, pg_read_binary_file('/srv/pg_imports/images/242-F-25.jpg'), 'Routine check-up radiograph.', '{"settings": {"brightness": 50, "contrast": 65}}'),
    (3, 2, pg_read_binary_file('/srv/pg_imports/images/607-F-38.jpg'), 'Bitewing radiographs to check for cavities before whitening.', '{"settings": {"brightness": 50, "contrast": 65}}'),
    (4, 4, pg_read_binary_file('/srv/pg_imports/images/1386-F-51.jpg'), 'Lateral cephalometric radiograph for orthodontic assessment.', '{"settings": {"brightness": 50, "contrast": 50}}');

INSERT INTO anamnesis_forms (visit_id, weight_kg, height_m, systolic_bp, diastolic_bp, is_pregnant, had_recent_fever, is_under_medical_treatment, is_taking_medication, detailed_medical_history, family_health_history, previous_dental_history, psychosocial_history, additional_info_for_dentist, special_needs_during_treatment)
VALUES
    (1, 70.5, 1.75, 120, 80, FALSE, FALSE, FALSE, FALSE, 'No significant medical history.', 'No family history of major illnesses.', 'No prior dental issues.', 'No psychosocial concerns.', 'None.', 'None.'),
    (2, 65.0, 1.68, 110, 70, FALSE, FALSE, FALSE, FALSE, 'No significant medical history.', 'No family history of major illnesses.', 'No prior dental issues.', 'No psychosocial concerns.', 'None.', 'None.');

INSERT INTO specific_health_questions (anamnesis_id, has_cardiovascular_issue, has_rheumatic_fever, has_joint_pain, has_excessive_bleeding, had_local_anesthesia, had_anesthesia_reaction, uses_substances, has_allergies, had_medication_related_problem, has_kidney_problems, was_hospitalized, took_penicillin, took_corticosteroid_last_12m, has_persistent_cough) 
VALUES 
    (1, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE),
    (2, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE);