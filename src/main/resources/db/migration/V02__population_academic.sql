
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
       '$2a$10$Ot4XGuyPP7r82nN3WXA0bOL1Qk9gShKDlVuPoyp89HoFnHcwO4Tji',
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
      'Graduação em Ciência da Computação pela UNIMEP; ...'),
  (2 , NULL, 'Profa. Dra.' ,'0','2407277993285186',
      'Graduação em Ciência da Computação (UEL/1996); ...'),
  (3 , NULL, 'Profa. Dra.' ,'0','8527991428093010',
      'Graduação, Mestrado e Doutorado na FORP-USP; ...'),
  (4 , NULL, 'Prof. Dr.'   ,'0','1652639775122637',
      'Graduação FORP-USP; ...'),
  (5 , NULL, ''            ,'0','1373401174969764',
      'Graduação, Mestrado, Doutorado e Pós-Doutorado na FORP-USP ...'),
  (6 , NULL, ''            ,'0','2407277993285186',
      'Graduado e Mestre pela FMDUP; ...'),
  (7 , NULL, ''            ,'0','9528069961011665',
      'Graduação, Mestrado e Doutorado (em andamento) ...'),
  (8 , NULL, ''            ,'0','6859938738453631',
      'Graduação e Mestrado em Reabilitação Oral ...'),
  (9 , NULL, ''            ,'0','5790574018465089',
      'Graduação e Mestrado na FOP-UNICAMP; ...'),
  (10, NULL, ''            ,'0','3051734743750193',
      'Graduando em Odontologia (FORP-USP); ...'),
  (11, NULL, ''            ,'0','5342679611366812',
      'Técnico em Eletrônica; Eng. Biomédica; ...'),
  (12, NULL, ''            ,'0','9682458931179370',
      'Graduação em Odontologia (UFBA); ...'),
  (13, NULL, ''            ,'0','1576892464551687',
      'Graduação em ADS (IFSP-ARQ); ...'),
  (14, NULL, ''            ,'0','2131728266711395',
      'Graduação em Ciência da Computação (UNIFRAN); ...'),
  (15, NULL, ''            ,'0','6014220679248199',
      'Técnico em Meio Ambiente; ...'),
  (16, NULL, ''            ,'0','2820894116965169',
      'Graduação em Sistemas de Informação (UENP); ...'),
  (17, NULL, ''            ,'0','2157452587942180',
      'Graduação em Odontologia (UFBA); ...'),
  (18, NULL, ''            ,'0','5334650938944366',
      'Graduação UNIFLU; Mestre e Doutora ...'),
  (19, NULL, ''            ,'0','4643111511552418',
      'Graduanda em Odontologia (FORP-USP); ...'),
  (20, NULL, ''            ,'0','4347068369827764',
      'Graduando em Ciência da Computação (FFCLRP-USP); ...'),
  (21, NULL, ''            ,'0','3790288170098950',
      'Graduanda em Odontologia (FORP-USP); ...'),
  (22, NULL, ''            ,'0','5884239564831518',
      'Graduando em Odontologia (FORP-USP); ...'),
  (23, NULL, ''            ,'0','0684260742261428',
      'Graduação UNIFLU; Mestre e Doutora ...'),
  (24, NULL, ''            ,'0','2430315747607049',
      'Graduação em Odontologia (Cusco); Especialista em Dentística; ...');

/* ========================================================================
   3. Permissões
   ======================================================================== */
INSERT INTO permission (id, description) VALUES
  (1, 'ROLE_REGISTER_USER'   ),
  (2, 'ROLE_REMOVE_USER'     ),
  (3, 'ROLE_SEARCH_USER'     ),
  (4, 'ROLE_REGISTER_TERMS'),
  (5, 'ROLE_REGISTER_GROUP'),
  (6, 'ROLE_REMOVE_GROUP' ),
  (7, 'ROLE_SEARCH_GROUP' ),
  (8, 'ROLE_REGISTER_PAPER'),
  (9, 'ROLE_REMOVE_PAPER' ),
  (10, 'ROLE_SEARCH_PAPER' );

/* ========================================================================
   4. Permissões por usuário
   ======================================================================== */
INSERT INTO user_permission (id_user_permission, id_permission) VALUES
  -- admin (id_user = 3)
  (3,1),(3,2),(3,3),(3,4),(3,5),(3,6),
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