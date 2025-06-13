CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* ========================================================================
   1. Usuários
   ======================================================================== */
INSERT INTO "user" (
  id_user,
  first_name,
  last_name,
  email,
  public_picture,
  contact,
  password,
  active
) VALUES
  (1,  'Mirela Teixeira',              'Cazzolato',  'mirela@example.com',
       pg_read_binary_file('/data/firebase/memberImages/MirelaCazzolato.jpg'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (2,  'Alessandra Alaniz',             'Macedo',     'alessandra@example.com',
       pg_read_binary_file('/data/firebase/memberImages/AlessandraAlaniz.jpeg'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (3,  'Camila',                        'Tirapelli',  'camila@example.com',
       pg_read_binary_file('/data/firebase/memberImages/camilaTirapelli.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (4,  'Hugo Gaêta',                    'Araujo',     'hugo@example.com',
       pg_read_binary_file('/data/firebase/memberImages/hugoGaeta.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (5,  'Pedro Bastos',                  'Cruvinel',   'pedro@example.com',
       pg_read_binary_file('/data/firebase/memberImages/pedroBastos.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (6,  'Júlio Ruiz',                    'Marrara',    'julio@example.com',
       pg_read_binary_file('/data/firebase/memberImages/julioRuiz.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (7,  'Camila Porto',                  'Capel',      'camila.p@example.com',
       pg_read_binary_file('/data/firebase/memberImages/camilaPortoCapel.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (8,  'Gabriela Ayres de',             'Souza',      'gabriela@example.com',
       pg_read_binary_file('/data/firebase/memberImages/gabrielaAyres.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (9,  'Leandro',                       'Cardoso',    'leandro@example.com',
       pg_read_binary_file('/data/firebase/memberImages/leandroCardoso.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (10, 'Murilo Montanari',              'Souza',      'murilo@example.com',
       pg_read_binary_file('/data/firebase/memberImages/muriloMontari.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (11, 'José Andery',                   'Carneiro',   'jose@example.com',
       pg_read_binary_file('/data/firebase/memberImages/pedroBastos.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (12, 'Éric Arnold dos Santos',        'Brito',      'eric@example.com',
       pg_read_binary_file('/data/firebase/memberImages/ericArnold.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (13, 'Thiago Alves Vieira de',        'Matos',      'thiago@example.com',
       pg_read_binary_file('/data/firebase/memberImages/thiagoAlves.png'),
       '+516987654321',
       crypt('t123456', gen_salt('bf', 10)),
       true
  ),
  (14, 'Vinicius',                      'Melo',       'vinicius@example.com',
       pg_read_binary_file('/data/firebase/memberImages/ViniciusMelo.jpg'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (15, 'Michelle',                      'Chang',      'michelle@example.com',
       pg_read_binary_file('/data/firebase/memberImages/michelleChang.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (16, 'Breno Augusto Guerra',          'Zancan',     'breno@example.com',
       pg_read_binary_file('/data/firebase/memberImages/brenoZancan.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (17, 'Iago Fonseca Silva',            'Mota',       'iago@example.com',
       pg_read_binary_file('/data/firebase/memberImages/iagoFonseca.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (18, 'Marianna Soares Nogueira',      'Borges',     'marianna@example.com',
       pg_read_binary_file('/data/firebase/memberImages/marianaSoares.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (19, 'Gabriela Greghi de',            'Carvalho',   'gabriela.g@example.com',
       pg_read_binary_file('/data/firebase/memberImages/gabrielaGreghi.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (20, 'Caio Uehara',                   'Martins',    'caio@example.com',
       pg_read_binary_file('/data/firebase/memberImages/caioUehara.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (21, 'Marcela Amanda',                'Vieira',     'marcela@example.com',
       pg_read_binary_file('/data/firebase/memberImages/marcelaAmanda.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (22, 'Gabriel Fugita',                'Barbin',     'gabriel.f@example.com',
       pg_read_binary_file('/data/firebase/memberImages/gabrielFugita.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (23, 'Bruna Neves de',                'Freitas',    'bruna@example.com',
       pg_read_binary_file('/data/firebase/memberImages/brunaNeves.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  ),
  (24, 'Karen Pintado',                 'Palomino',   'karen@example.com',
       pg_read_binary_file('/data/firebase/memberImages/karenPintado.png'),
       '+516987654321',
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
       true
  );



/* ========================================================================
   2. Acadêmicos
   ======================================================================== */
INSERT INTO academic
    (id_user, id_address, title, institution, lattes_id, abstract)
VALUES
  (1 , NULL, 'Profa.'      ,'Institution Example','5404143204431052',
      'Graduação em Ciência da Computação. Universidade Metodista de Piracicaba, UNIMEP, Brasil.; Mestrado em Ciência da Computação. Universidade Federal de São Carlos, UFSCAR, Brasil.; Doutorado em Ciências da Computação e Matemática Computacional. Universidade de São Paulo, USP, Brasil. Período sanduíche em Karlsruher Institut für Technologie.; Pós-Doutorado. Carnegie Mellon University, CMU, Estados Unidos.; Professora Doutora na Universidade de São Paulo: Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto.'),
  (2 , NULL, 'Profa. Dra.' ,'0','2407277993285186',
      'Graduação em Ciências da Computação pela Universidade Estadual de Londrina (1996); Mestrado e Doutorado em Ciências da Computação pela Universidade de São Paulo (USP – São Carlos); Professora Associada do Departamento de Computação e Matemática da FFCLRP da Universidade de São Paulo (USP), Campus Ribeirão Preto; Vinculada ao Programa de Pós-Graduação em Computação Aplicada; A pesquisadora atua principalmente nos seguintes temas: Extração de Informação, Sistemas de Informação Inteligentes, Engenharia de Documentos, Hipermídia, Web Semântica e Recuperação de Informação em Biociências.'),
  (3 , NULL, 'Profa. Dra.' ,'0','8527991428093010',
      'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP (1998); Mestrado e Doutorado em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Especialização em Prótese Dentária (CFO); Especialização em Radiologia e Imaginologia Dentomaxilofacial; Especialização em Pedagogia Universitária (GAP-USP); MBA em Gestão Estratégica pela Universidade de São Paulo – FEARP-USP; Professora Associada III da Faculdade de Odontologia de Ribeirão Preto (FORP USP), docente da área de Clínica Integrada e orientadora de Mestrado e Doutorado no Programa de Pós-graduação em Reabilitação Oral da FORP USP; A pesquisadora tem trabalhado interdisciplinarmente em colaboração com pesquisadores dentro e fora da Odontologia, liderando o grupo de pesquisa InReDD (Interdisciplinary Research Group in Digital Dentistry).'),
  (4 , NULL, 'Prof. Dr.'   ,'0','1652639775122637',
      'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestrado e Doutorado em Radiologia Odontológica pela Faculdade de Odontologia de Piracicaba – UNICAMP; Foi Professor Adjunto da área de Radiologia Odontológica e Imaginologia da Faculdade de Odontologia da Universidade Federal de Alfenas (UNIFAL-MG) e do Programa de Pós-Graduação em Ciências Odontológicas (UNIFAL-MG); Professor Doutor do Departamento de Estomatologia, Saúde Coletiva e Odontologia Legal (DESCOL) da Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo (FORP-USP) e orientador nos Programas de Pós-Graduação em Odontologia Restauradora e Reabilitação Oral da Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo (FORP-USP); O pesquisador tem trabalhado interdisciplinarmente em colaboração com pesquisadores dentro e fora da Odontologia, liderando o grupo de pesquisa InReDD (Interdisciplinary Research Group in Digital Dentistry).'),
  (5 , NULL, ''            ,'0','1373401174969764',
      'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestrado em Odontologia Restauradora pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Doutorado em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo- FORP/USP; Pós-Doutorando em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo- FORP/USP.'),
  (6 , NULL, ''            ,'0','2407277993285186',
      'Graduado em Medicina Dentária pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP; Mestre em Medicina Dentária com área de concentração em DTM e Dor orofacial pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP; Doutorando em Odontologia (dupla titulação) com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo, FORP-USP; Doutorando em Medicina Dentária (dupla titulação) com área de concentração em Inteligência Artificial, pela Faculdade de Medicina Dentária da Universidade do Porto, FMDUP.'),
  (7 , NULL, ''            ,'0','9528069961011665',
      'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo; Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo.'),
  (8 , NULL, ''            ,'0','6859938738453631',
      'Graduação em Odontologia pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo– FORP/USP; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo.'),
  (9 , NULL, ''            ,'0','5790574018465089',
      'Graduação em Odontologia pela Faculdade de Odontologia de Piracicaba, FOP-UNICAMP; Mestre e Especialista em Prótese Dentária pela Faculdade de Odontologia de Piracicaba, FOP-UNICAMP; Especialista em Cirurgia Plástica Periodontal SLMandic – Campinas; Doutorando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP-USP.'),
  (10, NULL, ''            ,'0','3051734743750193',
      'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'),
  (11, NULL, ''            ,'0','5342679611366812',
      'Técnico em Eletrônica com ênfase em Equipamentos Biomédicos, pela Escola Técnica de Eletrônica Francisco Moreira da Costa – ETE FMC; Bacharel em Engenharia Biomédica pelo Instituto Nacional de Telecomunicações – INATEL; Mestrando em Ciência da Computação com área de concentração em Visão Computacional, aplicação de Redes Neurais Artificiais para o processamento de Imagens Médicas, pelo Departamento de Computação e Matemática, Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – DCM, FFCLRP/USP.'),
  (12, NULL, ''            ,'0','9682458931179370',
      'Graduação em Odontologia pela Faculdade de Odontologia da Universidade Federal da Bahia – FOUFBA; Atualização em Reabilitação Oral Estética – NÚCLEO; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'),
  (13, NULL, ''            ,'0','1576892464551687',
      'Graduação em Análise e Desenvolvimento de Sistemas pelo Instituto Federal de Educação, Ciência e Tecnologia de São Paulo, Campus Araraquara – IFSP-ARQ; Mestrando do Programa de Pós-Graduação em Computação Aplicada com área de concentração em Visão Computacional no processamento de Imagens Médicas, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'),
  (14, NULL, ''            ,'0','2131728266711395',
      'Graduação em Ciência da Computação. Universidade de Franca, UNIFRAN, Brasil.; Mestrando do Programa de Pós-Graduação em Computação Aplicada, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'),
  (15, NULL, ''            ,'0','6014220679248199',
      'Técnico em Meio Ambiente pelo Instituto Federal de Minas Gerais, Campus Governador Valadares – IFMG-GV; Graduação em Odontologia pela Universidade Federal de Juiz de Fora, Campus Avançado de Governador Valadares – UFJF- GV; Mestranda em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'),
  (16, NULL, ''            ,'0','2820894116965169',
      'Graduação em Sistemas de Informações pela Universidade Estadual do Norte do Paraná; Mestrando do Programa de Pós-Graduação em Computação Aplicada, pertencente ao Departamento de Computação e Matemática da Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP/USP.'),
  (17, NULL, ''            ,'0','2157452587942180',
      'Graduação em Odontologia pela Faculdade de Odontologia da Universidade Federal da Bahia – FOUFBA; Atualização em Prótese Dentária; Mestrando em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto, Universidade de São Paulo – FORP/USP.'),
  (18, NULL, ''            ,'0','5334650938944366',
      'Graduada em Odontologia pelo Centro Universitário Fluminense de Campos-UNIFLU; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Doutora em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Pós-Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Professora Contratada III na Disciplina de Prótese Parcial Fixa I pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP (2023); Pesquisadora visitante no Departamento de Odontologia e Saúde Bucal da Universidade de Aarhus (2024-atual).'),
  (19, NULL, ''            ,'0','4643111511552418',
      'Graduanda em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'),
  (20, NULL, ''            ,'0','4347068369827764',
      'Graduando em Ciência da Computação pela Faculdade de Ciência e Letras de Ribeirão Preto da Universidade de São Paulo – FFCLRP-USP (DCM); Aluno de Iniciação Científica do grupo InReDD.'),
  (21, NULL, ''            ,'0','3790288170098950',
      'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'),
  (22, NULL, ''            ,'0','5884239564831518',
      'Graduando em Odontologia pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Aluno de Iniciação Científica do grupo InReDD.'),
  (23, NULL, ''            ,'0','0684260742261428',
      'Graduada em Odontologia pelo Centro Universitário Fluminense de Campos-UNIFLU; Mestre em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Doutora em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Pós-Doutoranda em Odontologia com área de concentração em Reabilitação Oral pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP; Professora Contratada III na Disciplina de Prótese Parcial Fixa I pela Faculdade de Odontologia de Ribeirão Preto da Universidade de São Paulo – FORP/USP (2023); Pesquisadora visitante no Departamento de Odontologia e Saúde Bucal da Universidade de Aarhus (2024-atual).'),
  (24, NULL, ''            ,'0','2430315747607049',
      'Graduação em Odontologia pela Universidade Andina de Cusco – Peru; Especialização em Dentística (FORP/USP); Mestrado e Doutorado em Reabilitação Oral (FORP/USP); Pós-Doutoranda em Odontologia Restauradora (FORP/USP).');

/* ========================================================================
   3. Permissões
   ======================================================================== */
INSERT INTO permission (id, description) VALUES
-- Insere todas as roles em permission (user + group + geral)
  /* GROUP PERMISSIONS*/
  /* MEMBERS = 4*/
  (1, 'ROLE_LIST_MEMBER'),
  (2, 'ROLE_INVITE_MEMBER'),
  (3, 'ROLE_DELETE_MEMBER'),
  (4, 'ROLE_EDIT_MEMBER'),
  /* GROUPS = 4*/
  (5, 'ROLE_LIST_GROUP'),
  (6, 'ROLE_CREATE_GROUP'),
  (7, 'ROLE_DELETE_GROUP'),
  (8, 'ROLE_EDIT_GROUP'),
  /* LIBRARY = 2*/
  (9, 'ROLE_MODERATE_PAPER'),
  (10, 'ROLE_UPLOAD_PAPER'),
  /* SOLUTIONS = 5*/
  (11, 'ROLE_SOLUTION_VIEW_OPEN_DATA_DASHBOARD'),
  (12, 'ROLE_SOLUTION_VIEW_D2L_DASHBOARD'),
  (13, 'ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS'),
  (14, 'ROLE_SOLUTION_LIST_USER'),
  (15, 'ROLE_SOLUTION_DELETE_USER'),
  /* SETTINGS = 2*/
  (16, 'ROLE_EDIT_ACCEPT_TERMS'),
  (17, 'ROLE_EDIT_PRIVACY_POLICY'),
  /* GENERAL PERMISSIONS*/
  (18, 'ROLE_REGISTER_TERMS_AND_POLICY'),
  /* USERS = 3*/
  (19, 'ROLE_SEARCH_USER'),
  (20, 'ROLE_REGISTER_USER'),
  (21, 'ROLE_REMOVE_USER')
  ON CONFLICT (id) DO UPDATE
  SET description = EXCLUDED.description;

/* ========================================================================
   4. Permissões por usuário
   ======================================================================== */
INSERT INTO user_permission (id_user_permission, id_permission) VALUES
  -- admin (id_user = 13)
  (13,1),(13,2),(13,3),(13,4),(13,5),(13,6),(13,7),(13,8),(13,9),(13,10),
  (13,11),(13,12),(13,13),(13,14),(13,15),(13,16),(13,17),(13,18), (13,19), (13,20), (13,21),
  -- common user (id_user = 1)
  (1,1),(1,3),(1,4),(1,5),(1,6);

/* ========================================================================
   5. Grupos
   ======================================================================== */
INSERT INTO groups (id_groups, name, description) VALUES
  (1,'Coordinating Professors','InReDD group'),
  (2,'Postdoctoral'          ,'InReDD group'),
  (3,'Doctorate'             ,'InReDD group'),
  (4,'Master''s'             ,'InReDD group'),
  (5,'Scientific Initiation' ,'InReDD group'),
  (6,'Collaborating Researchers','InReDD group');

/* ========================================================================
   6. Usuários × Grupos
   ======================================================================== */
INSERT INTO groups_has_user (id_groups, id_user) VALUES
  (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10);