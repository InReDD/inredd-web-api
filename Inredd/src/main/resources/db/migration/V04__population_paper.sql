INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 1, 'https://doi.org/10.1000/xyz123', '2022-06-15', 'Deep Learning for Medical Imaging', 'J. Doe; A. Smith', '10.1000/xyz123', ARRAY['deep learning', 'medical', 'AI']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 1);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 2, 'https://doi.org/10.1000/abc456', '2023-01-10', 'Neural Networks in Production', 'L. Costa; M. Chen', '10.1000/abc456', ARRAY['neural networks', 'production']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 2);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 3, 'https://doi.org/10.1000/qwe789', '2021-03-22', 'Data Mining Techniques', 'S. Kumar; R. Lee', '10.1000/qwe789', ARRAY['data mining', 'algorithms']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 3);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 4, 'https://doi.org/10.1000/asd987', '2024-02-05', 'Bayesian Inference Applications', 'T. Silva; B. Chan', '10.1000/asd987', ARRAY['bayesian', 'statistics']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 4);

INSERT INTO paper (id_paper, url_doi, publish_date, title, authors, doi, tags)
SELECT 5, 'https://doi.org/10.1000/zxc321', '2020-09-30', 'Computer Vision with Transformers', 'M. Zhang; E. Torres', '10.1000/zxc321', ARRAY['computer vision', 'transformers']
WHERE NOT EXISTS (SELECT 1 FROM paper WHERE id_paper = 5);

INSERT INTO paper_has_user (id_paper, id_user)
SELECT 1, 2 WHERE NOT EXISTS (SELECT 1 FROM paper_has_user WHERE id_paper = 1 AND id_user = 2);

INSERT INTO paper_has_user (id_paper, id_user)
SELECT 2, 3 WHERE NOT EXISTS (SELECT 1 FROM paper_has_user WHERE id_paper = 2 AND id_user = 3);

INSERT INTO paper_has_user (id_paper, id_user)
SELECT 3, 4 WHERE NOT EXISTS (SELECT 1 FROM paper_has_user WHERE id_paper = 3 AND id_user = 4);

INSERT INTO paper_has_user (id_paper, id_user)
SELECT 4, 5 WHERE NOT EXISTS (SELECT 1 FROM paper_has_user WHERE id_paper = 4 AND id_user = 5);

INSERT INTO paper_has_user (id_paper, id_user)
SELECT 5, 2 WHERE NOT EXISTS (SELECT 1 FROM paper_has_user WHERE id_paper = 5 AND id_user = 2);
