INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 1, 'https://doi.org/10.1000/xyz123', '2022-06-15', 'Deep Learning for Medical Imaging', ARRAY['J. Doe', 'A. Smith'], '10.1000/xyz123', ARRAY['deep learning', 'medical', 'AI']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 1);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 2, 'https://doi.org/10.1000/abc456', '2023-01-10', 'Neural Networks in Production', ARRAY['L. Costa; M. Chen'], '10.1000/abc456', ARRAY['neural networks', 'production']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 2);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 3, 'https://doi.org/10.1000/qwe789', '2021-03-22', 'Data Mining Techniques', ARRAY['S. Kumar; R. Lee'], '10.1000/qwe789', ARRAY['data mining', 'algorithms']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 3);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 4, 'https://doi.org/10.1000/asd987', '2024-02-05', 'Bayesian Inference Applications', ARRAY['T. Silva; B. Chan'], '10.1000/asd987', ARRAY['bayesian', 'statistics']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 4);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 5, 'https://doi.org/10.1000/zxc321', '2020-09-30', 'Computer Vision with Transformers', ARRAY['M. Zhang; E. Torres'], '10.1000/zxc321', ARRAY['computer vision', 'transformers']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 5);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 6,
       'https://doi.org/10.1007/s00784-022-04438-5',
       NULL,
       'Two decades of research on CBCT imaging in DMFR – an appraisal of scientific evidence',
       ARRAY['Hugo Gaêta-Araujo; André Ferreira Leite; Karla de Faria Vasconcelos and Reinhilde Jacobs'],
       '10.1007/s00784-022-04438-5',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 6);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 7,
       'https://doi.org/10.1007/s10266-020-00511-1',
       NULL,
       'Digital photography vs. clinical assessment of resin composite restorations',
       ARRAY['Cecília Vilela Vasconcelos Barros de Almeida', 'Karen Pintado-Palomino',  'João Henrique Parise Fortes',  'Raphael Jurca Gonçalves da Motta', 'Bruna Neves de Freitas', 'Wilson Matsumoto', 'Maria Tereza Moura de Oliveira Cavalcanti', 'Josué Alves & Camila Tirapelli'],
       '10.1007/s10266-020-00511-1',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 7);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 8,
       'https://doi.org/10.1016/j.jdent.2021.103757',
       NULL,
       'Clinical decision-making in anterior resin composite restorations: a multicenter evaluation',
       ARRAY['Bruna Neves de Freitas', 'Karen Pintado-Palomino', 'Cecília V. V. Barros de Almeida', 'Pedro Bastos Cruvinel', 'Aline Evangelista Souza-Gabriel', 'Silmara Aparecida Milori Corona', 'Saulo Geraldeli', 'Brigitte Grosgogeat', 'Jean-François Roulet', 'Camila Tirapelli'],
       '10.1016/j.jdent.2021.103757',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 8);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 9,
       'https://doi.org/10.1016/j.jdent.2022.104387',
       NULL,
       'Comparison of intraoral scanning and CBCT to generate digital and 3D-printed casts by fused deposition modeling and digital light processing',
       ARRAY['Bruna Neves de Freitas', 'Lucas Moreira Mendonça', 'Pedro Bastos Cruvinel', 'Tito José de Lacerda', 'Fernando Gonçalves Junqueira Leite', ' Christiano Oliveira-Santos', 'Camila Tirapelli'],
       '10.1016/j.jdent.2022.104387',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 9);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 10,
       'https://doi.org/10.1016/j.oooo.2023.12.006',
       NULL,
       'Development of a dental digital data set for research in artificial intelligence: The importance of labeling performed by radiologists',
       ARRAY['Eliana Dantas Costa', 'Hugo Gaêta-Araujo', 'José Andery Carneiro', 'Breno Augusto Guerra Zancan', 'José Augusto Baranauskas', 'Alessandra Alaniz Macedo', 'Camila Tirapelli'],
       '10.1016/j.oooo.2023.12.006',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 10);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 11,
       'https://doi.org/10.1259/dmfr.20200145',
       NULL,
       'Cone beam computed tomography in dentomaxillofacial radiology: a two-decade overview',
       ARRAY['Hugo Gaêta-Araujo', 'Tamara Alzoubi', 'Karla de Faria Vasconcelos', 'Kaan Orhan', 'Ruben Pauwels', 'Jan W Casselman and Reinhilde Jacobs'],
       '10.1259/dmfr.20200145',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 11);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 12,
       'https://doi.org/10.1259/dmfr.20200367',
       NULL,
       'Automatic exposure compensation of digital radiographic technologies does not affect alveolar bone-level measurement',
       ARRAY['Nicolly Oliveira-Santos', 'Hugo Gaêta-Araujo', 'Débora Costa Ruiz', 'Eduarda Helena Leandro Nascimento', 'Wilson Gustavo Cral', 'Christiano Oliveira-Santos', 'Francisco Carlos Groppo'],
       '10.1259/dmfr.20200367',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 12);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 13,
       'https://doi.org/10.1259/dmfr.20200412',
       NULL,
       'Can diagnostic changes caused by cone beam computed tomography alter the clinical decision in impacted lower third molar treatment plan?',
       ARRAY['Lucas Moreira Mendonça', 'Hugo Gaêta-Araujo', 'Pedro Bastos Cruvinel', 'Ingrid Wenzel Tosin', 'Marcelo Rodrigues Azenha', 'Emanuela Prado Ferraz', 'Christiano Oliveira-Santos', 'Camila Tirapelli'],
       '10.1259/dmfr.20200412',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 13);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 14,
       'https://doi.org/10.15517/ijds.2024.59184',
       NULL,
       'Potential of Artificial Intelligence to Generate Health Research Reports of Decayed, Missed and Restored Teeth',
       ARRAY['Eliana Dantas Costa', 'José Andery Carneiro', 'Breno Augusto Guerra Zancan', 'Hugo Gaêta-Araujo', 'Christiano Oliveira-Santos', ' Alessandra Alaniz Macedo', 'Camila Tirapelli'],
       '10.15517/ijds.2024.59184',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 14);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 15,
       'https://doi.org/10.34119/bjhrv7n2-156',
       NULL,
       'Deep learning to support the detection and classification of teeth, dental caries and restorations: a review',
       ARRAY['José Andery Carneiro', 'Breno Augusto Guerra Zancan', 'Camila Tirapelli', 'Alessandra Alaniz Macedo'],
       '10.34119/bjhrv7n2-156',
       ARRAY[]::text[]
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 15);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 16,
       'https://doi.org/10.5753/sbcas_estendido.2024.2258',
       NULL,
       'Dental Second Look AI: Ferramenta multipropósito para análise de imagens panorâmicas odontológicas. Accepted in Trilha de Ferramentas e Aplicações do Simpósio Brasileiro de Computação Aplicada à Saúde (FA@SBCAS), junho 2024.',
       ARRAY['Uehara, C.', 'Tirapelli, C.', 'Macedo, A. A.'],
       '10.5753/sbcas_estendido.2024.2258',
       ARRAY['Tool and Aplication','Artificial intelligence','Image','Prize']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 16);
