-- Set the search path to our schema
SET search_path TO inredd_schema;

-- =====================================================
-- Populate user table
-- =====================================================
INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 1, 'Mirela Teixeira', 'Cazzolato', 'mirela@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 1);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 2, 'Alessandra Alaniz', 'Macedo', 'alessandra@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 2);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 3, 'Camila', 'Tirapelli', 'camila@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 3);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 4, 'Hugo Gaêta', 'Araujo', 'hugo@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 4);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 5, 'Pedro Bastos', 'Cruvinel', 'pedro@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 5);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 6, 'Júlio Ruiz', 'Marrara', 'julio@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 6);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 7, 'Camila Porto', 'Capel', 'camila.p@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 7);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 8, 'Gabriela Ayres de', 'Souza', 'gabriela@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 8);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 9, 'Leandro', 'Cardoso', 'leandro@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 9);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 10, 'Murilo Montanari', 'Souza', 'murilo@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 10);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 11, 'José Andery', 'Carneiro', 'jose@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 11);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 12, 'Éric Arnold dos Santos', 'Brito', 'eric@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 12);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 13, 'Thiago Alves Vieira de', 'Matos', 'thiago@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 13);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 14, 'Vinicius', 'Melo', 'vinicius@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 14);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 15, 'Michelle', 'Chang', 'michelle@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 15);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 16, 'Breno Augusto Guerra', 'Zancan', 'breno@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 16);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 17, 'Iago Fonseca Silva', 'Mota', 'iago@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 17);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 18, 'Marianna Soares Nogueira', 'Borges', 'marianna@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 18);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 19, 'Gabriela Greghi de', 'Carvalho', 'gabriela.g@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 19);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 20, 'Caio Uehara', 'Martins', 'caio@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 20);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 21, 'Marcela Amanda', 'Vieira', 'marcela@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 21);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 22, 'Gabriel Fugita', 'Barbin', 'gabriel.f@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 22);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 23, 'Bruna Neves de', 'Freitas', 'bruna@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 23);

INSERT INTO user (id_user, first_name, last_name, email, password, active, contact)
SELECT 24, 'Karen Pintado', 'Palomino', 'karen@example.com', '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji', '1', '0000'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id_user = 24);

-- =====================================================
-- (Optional) Populate Address table if address info is available.
-- Here we insert one sample address record per user if desired.
-- For this example, we leave it empty and academic.id_address will be NULL.
-- =====================================================

-- =====================================================
-- Populate academic table
-- =====================================================
-- =====================================================
-- Populate academic table
-- =====================================================
INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 1, '0000', NULL, 'Profa.', 'Institution Example', '5404143204431052',
     pg_read_binary_file('/data/firebase/memberImages/MirelaCazzolato.jpg'),
     'Graduação em Ciência da Computação. Universidade Metodista de Piracicaba, UNIMEP, Brasil.; Mestrado em Ciência da Computação. Universidade Federal de São Carlos, UFSCAR, Brasil.; Doutorado em Ciências da Computação e Matemática Computacional. Universidade de São Paulo, USP, Brasil. Período sanduíche em Karlsruher Institut für Technologie.; Pós-Doutorado. Carnegie Mellon University, CMU, Estados Unidos.; Professora Doutora na Universidade de São Paulo: Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 1);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 2, '0000', NULL, 'Profa. Dra.', '0', '2407277993285186',
     pg_read_binary_file('/data/firebase/memberImages/AlessandraAlaniz.jpeg'),
     'Graduação em Ciências da Computação pela Universidade Estadual de Londrina (1996); Mestrado e Doutorado em Ciências da Computação pela Universidade de São Paulo (USP – São Carlos); Professora Associada do Departamento de Computação e Matemática da FFCLRP da Universidade de São Paulo (USP), Campus Ribeirão Preto; Vinculada ao Programa de Pós-Graduação em Computação Aplicada; A pesquisadora atua principalmente nos seguintes temas: Extração de Informação, Sistemas de Informação Inteligentes, Engenharia de Documentos, Hipermídia, Web Semântica e Recuperação de Informação em Biociências.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 2);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 3, '0000', NULL, 'Profa. Dra.', '0', '8527991428093010',
     pg_read_binary_file('/data/firebase/memberImages/camilaTirapelli.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP (1998); Mestrado e Doutorado em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Especialização em Prótese Dentária (CFO); Especialização em Radiologia e Imaginologia Dentomaxilofacial; Especialização em Pedagogia Universitária (GAP-USP); MBA em Gestão Estratégica pela Universidade de São Paulo – FEARP-USP; Professora Associada III da Faculdade de Odontologia de Ribeirão Preto (FORP USP), docente da área de Clínica Integrada e orientadora de Mestrado e Doutorado no Programa de Pós-graduação em Reabilitação Oral da FORP USP; A pesquisadora tem trabalhado interdisciplinarmente em colaboração com pesquisadores dentro e fora da Odontologia, liderando o grupo de pesquisa InReDD (Interdisciplinary Research Group in Digital Dentistry).'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 3);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 4, '0000', NULL, 'Prof. Dr.', '0', '1652639775122637',
     pg_read_binary_file('/data/firebase/memberImages/hugoGaeta.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestrado e Doutorado em Radiologia Odontológica pela Faculdade de Odontologia de Piracicaba – UNICAMP; Foi Professor Adjunto da área de Radiologia Odontológica e Imaginologia da Faculdade de Odontologia da Universidade Federal de Alfenas (UNIFAL-MG) e do Programa de Pós-Graduação em Ciências Odontológicas (UNIFAL-MG); Professor Doutor do Departamento de Estomatologia, Saúde Coletiva e Odontologia Legal (DESCOL) da Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo (FORP-USP) e orientador nos Programas de Pós-Graduação em Odontologia Restauradora e Reabilitação Oral da Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo (FORP-USP); O pesquisador tem trabalhado interdisciplinarmente em colaboração com pesquisadores dentro e fora da Odontologia, liderando o grupo de pesquisa InReDD (Interdisciplinary Research Group in Digital Dentistry).'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 4);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 5, '0000', NULL, '', '0', '1373401174969764',
     pg_read_binary_file('/data/firebase/memberImages/pedroBastos.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestrado em Odontologia Restauradora pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Doutorado em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo- FORP/USP; Pós-Doutorando em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo- FORP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 5);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 6, '0000', NULL, '', '0', '2407277993285186',
     pg_read_binary_file('/data/firebase/memberImages/julioRuiz.png'),
     'Graduado em Medicina Dentária pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP; Mestre em Medicina Dentária com área de concentração em DTM e Dor orofacial pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP; Doutorando em Odontologia (dupla titulação) com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo, FORP-USP; Doutorando em Medicina Dentária (dupla titulação) com área de concentração em Inteligência Artificial, pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 6);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 7, '0000', NULL, '', '0', '9528069961011665',
     pg_read_binary_file('/data/firebase/memberImages/camilaPortoCapel.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo; Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 7);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 8, '0000', NULL, '', '0', '6859938738453631',
     pg_read_binary_file('/data/firebase/memberImages/gabrielaAyres.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 8);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 9, '0000', NULL, '', '0', '5790574018465089',
     pg_read_binary_file('/data/firebase/memberImages/leandroCardoso.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia de Piracicaba, FOP-UNICAMP; Mestre e Especialista em Prótese Dentária pela Faculdade de Odontologia de Piracicaba, FOP-UNICAMP; Especialista em Cirurgia Plástica Periodontal SLMandic – Campinas; Doutorando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP-USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 9);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 10, '0000', NULL, '', '0', '3051734743750193',
     pg_read_binary_file('/data/firebase/memberImages/muriloMontari.png'),
     'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 10);
  
INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 11, '0000', NULL, '', '0', '5342679611366812',
     pg_read_binary_file('/data/firebase/memberImages/pedroBastos.png'),
     'Técnico em Eletrônica com ênfase em Equipamentos Biomédicos, pela Escola Técnica de Eletrônica Francisco Moreira da Costa – ETE FMC; Bacharel em Engenharia Biomédica pelo Instituto Nacional de Telecomunicações – INATEL; Mestrando em Ciência da Computação com área de concentração em Visão Computacional, aplicação de Redes Neurais Artificiais para o processamento de Imagens Médicas, pelo Departamento de Computação e Matemática, Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – DCM, FFCLRP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 11);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 12, '0000', NULL, '', '0', '9682458931179370',
     pg_read_binary_file('/data/firebase/memberImages/ericArnold.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia da Universidade Federal da Bahia – FOUFBA; Atualização em Reabilitação Oral Estética – NÚCLEO; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 12);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 13, '0000', NULL, '', '0', '1576892464551687',
     pg_read_binary_file('/data/firebase/memberImages/thiagoAlves.png'),
     'Graduação em Análise e Desenvolvimento de Sistemas pelo Instituto Federal de Educação, Ciência e Tecnologia de São Paulo, Campus Araraquara – IFSP-ARQ; Mestrando do Programa de Pós-Graduação em Computação Aplicada com área de concentração em Visão Computacional no processamento de Imagens Médicas, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 13);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 14, '0000', NULL, '', '0', '2131728266711395',
     pg_read_binary_file('/data/firebase/memberImages/ViniciusMelo.jpg'),
     'Graduação em Ciência da Computação. Universidade de Franca, UNIFRAN, Brasil.; Mestrando do Programa de Pós-Graduação em Computação Aplicada, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 14);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 15, '0000', NULL, '', '0', '6014220679248199',
     pg_read_binary_file('/data/firebase/memberImages/michelleChang.png'),
     'Técnico em Meio Ambiente pelo Instituto Federal de Minas Gerais, Campus Governador Valadares – IFMG-GV; Graduação em Odontologia pela Universidade Federal de Juiz de Fora, Campus Avançado de Governador Valadares – UFJF- GV; Mestranda em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 15);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 16, '0000', NULL, '', '0', '2820894116965168',
     pg_read_binary_file('/data/firebase/memberImages/brenoZancan.png'),
     'Graduação em Sistemas de Informações pela Universidade Estadual do Norte do Paraná; Mestrando do Programa de Pós-Graduação em Computação Aplicada, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 16);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 17, '0000', NULL, '', '0', '2157452587942180',
     pg_read_binary_file('/data/firebase/memberImages/iagoFonseca.png'),
     'Graduação em Odontologia pela Faculdade de Odontologia da Universidade Federal da Bahia – FOUFBA; Atualização em Prótese Dentária; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 17);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 18, '0000', NULL, '', '0', '5334650938944366',
     pg_read_binary_file('/data/firebase/memberImages/marianaSoares.png'),
     'Graduada em Odontologia pelo Centro Universitário Fluminense de Campos-UNIFLU; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Doutora em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Pós-Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Professora Contratada III na Disciplina de Prótese Parcial Fixa I pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP (2023); Pesquisadora visitante no Departamento de Odontologia e Saúde Bucal da Universidade de Aarhus (2024-atual).'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 18);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 19, '0000', NULL, '', '0', '4643111511552418',
     pg_read_binary_file('/data/firebase/memberImages/gabrielaGreghi.png'),
     'Graduanda em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 19);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 20, '0000', NULL, '', '0', '4347068369827764',
     pg_read_binary_file('/data/firebase/memberImages/caioUehara.png'),
     'Graduando em Ciência da Computação pela Faculdade de Ciência e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP-USP (DCM); Aluno de Iniciação Científica do grupo InReDD.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 20);
  
INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 21, '0000', NULL, '', '0', '3790288170098950',
     pg_read_binary_file('/data/firebase/memberImages/marcelaAmanda.png'),
     'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 21);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 22, '0000', NULL, '', '0', '5884239564831518',
     pg_read_binary_file('/data/firebase/memberImages/gabrielFugita.png'),
     'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 22);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 23, '0000', NULL, '', '0', '0684260742261428',
     pg_read_binary_file('/data/firebase/memberImages/brunaNeves.png'),
     'Graduada em Odontologia pelo Centro Universitário Fluminense de Campos-UNIFLU; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Doutora em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Pós-Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Professora Contratada III na Disciplina de Prótese Parcial Fixa I pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP (2023); Pesquisadora visitante no Departamento de Odontologia e Saúde Bucal da Universidade de Aarhus (2024-atual).'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 23);

INSERT INTO academic (id_user, contact, id_address, title, institution, lattes_id, profile_picture, biography)
SELECT 24, '0000', NULL, '', '0', '2430315747607049',
     pg_read_binary_file('/data/firebase/memberImages/karenPintado.png'),
     'Graduação em Odontologia pela Universidade Andina de Cusco – Peru; Especialização em Dentística (FORP/USP); Mestrado e Doutorado em Reabilitação Oral (FORP/USP); Pós-Doutoranda em Odontologia Restauradora (FORP/USP).'
WHERE NOT EXISTS (SELECT 1 FROM academic WHERE id_user = 24);

-- user
INSERT INTO permission (id, description) values (1, 'ROLE_REGISTER_USER');
INSERT INTO permission (id, description) values (2, 'ROLE_REMOVE_USER');
INSERT INTO permission (id, description) values (3, 'ROLE_SEARCH_USER');

-- activity
INSERT INTO permission (id, description) values (4, 'ROLE_REGISTER_ACTIVITY');
INSERT INTO permission (id, description) values (5, 'ROLE_REMOVE_ACTIVITY');
INSERT INTO permission (id, description) values (6, 'ROLE_SEARCH_ACTIVITY');

-- admin
INSERT INTO user_permission (id_user, id_permission) values (3, 1);
INSERT INTO user_permission (id_user, id_permission) values (3, 2);
INSERT INTO user_permission (id_user, id_permission) values (3, 3);
INSERT INTO user_permission (id_user, id_permission) values (3, 4);
INSERT INTO user_permission (id_user, id_permission) values (3, 5);
INSERT INTO user_permission (id_user, id_permission) values (3, 6);

-- common_user
INSERT INTO user_permission (id_user, id_permission) values (1, 1);
INSERT INTO user_permission (id_user, id_permission) values (1, 3);
INSERT INTO user_permission (id_user, id_permission) values (1, 4);
INSERT INTO user_permission (id_user, id_permission) values (1, 5);
INSERT INTO user_permission (id_user, id_permission) values (1, 6);