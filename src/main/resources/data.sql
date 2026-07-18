ALTER SEQUENCE authors_id_seq RESTART WITH 6;
ALTER SEQUENCE genres_id_seq RESTART WITH 6;
ALTER SEQUENCE books_id_seq RESTART WITH 6;
ALTER SEQUENCE collections_id_seq RESTART WITH 6;
ALTER SEQUENCE users_id_seq RESTART WITH 6;
ALTER SEQUENCE loan_activities_id_seq RESTART WITH 6;

INSERT INTO genres (id, name, description, created_date, edited_date) VALUES
                                                                          (1, 'Roman', 'Nederlandse romans en verhalen', NOW(), NOW()),
                                                                          (2, 'Thriller', 'Spannende thrillers', NOW(), NOW()),
                                                                          (3, 'Fantasy', 'Fantasy en avontuur', NOW(), NOW()),
                                                                          (4, 'Historisch', 'Historische romans', NOW(), NOW()),
                                                                          (5, 'Jeugd', 'Kinderboeken en jeugdliteratuur', NOW(), NOW());

-- ✅ Auteurs toevoegen
INSERT INTO authors (id, name, biography, created_date, edited_date) VALUES
                                                                         (1, 'Harry Mulisch', 'Nederlands schrijver en dichter (1927-2010). Bekend voor zijn oorlogsromans.', NOW(), NOW()),
                                                                         (2, 'Cees Nooteboom', 'Nederlands schrijver en dichter (1933-heden). Auteur van internationale bestsellers.', NOW(), NOW()),
                                                                         (3, 'Arnon Grunberg', 'Hedendaagse Nederlands schrijver (1971-heden). Schrijft thrillers en romans.', NOW(), NOW()),
                                                                         (4, 'A.C. Baantjer', 'Nederlands schrijver van misdaaddrama''s (1923-1999). Beroemd om zijn Baantjer serie.', NOW(), NOW()),
                                                                         (5, 'Annie M.G. Schmidt', 'Nederlands kinderboeken schrijfster (1911-1995). Schepper van Jip en Janneke.', NOW(), NOW());

-- ✅ Collections toevoegen
INSERT INTO collections (id, name, description, created_date, edited_date) VALUES
                                                                               (1, 'Nederlandse Klassiekers', 'De beste Nederlandse litteratuur klassiekers', NOW(), NOW()),
                                                                               (2, 'Spannende Thrillers', 'Hartverscheurende Nederlandse thrillers', NOW(), NOW()),
                                                                               (3, 'Kinderboeken', 'Favoriete kinderboeken voor alle leeftijden', NOW(), NOW());

-- ✅ Boeken toevoegen
INSERT INTO books (id, title, release_year, description, number_of_copies, genre_id, collection_id, created_date, edited_date) VALUES
                                                                                                                                   (1, 'De Aanslag', 1982, 'Een meesterwerk over het verzet in Amsterdam tijdens WWII. Een intens verhaal over vriendschap en moraal.', 5, 4, 1, NOW(), NOW()),
                                                                                                                                   (2, 'Rituelen', 1980, 'Een mysterieuze thriller over twee vrienden in het Nederlandse boslandschap. Vol onduidelijke wendingen.', 3, 2, 2, NOW(), NOW()),
                                                                                                                                   (3, 'Blauwe Nacht', 1988, 'Een intrigerende roman over liefde en verlies in Amsterdam. Een literair meesterwerk vol diepgang.', 4, 1, 1, NOW(), NOW()),
                                                                                                                                   (4, 'Baantjer: De Cock en de moord op het Rembrandtplein', 1975, 'Spannend misdaaddrama uit de beroemde Baantjer serie. Onderzoek door inspecteur De Cock.', 6, 2, 2, NOW(), NOW()),
                                                                                                                                   (5, 'Jip en Janneke', 1953, 'Klassieken voor kinderen vol avontuur en vriendschap. Originele verhalen van Annie M.G. Schmidt.', 10, 5, 3, NOW(), NOW());

-- ✅ Boeken koppelen aan auteurs (via join table book_author)
INSERT INTO book_author (book_id, author_id) VALUES
                                                 (1, 1),  -- De Aanslag - Harry Mulisch
                                                 (2, 2),  -- Rituelen - Cees Nooteboom
                                                 (3, 3),  -- Blauwe Nacht - Arnon Grunberg
                                                 (4, 4),  -- Baantjer - A.C. Baantjer
                                                 (5, 5);  -- Jip en Janneke - Annie M.G. Schmidt

-- ✅ Users toevoegen
INSERT INTO users (id, name, email, phone_number, dob, collection_id, created_date, edited_date) VALUES
                                                                                                     (1, 'Jan de Vries', 'jan@example.com', '0612345678', '1985-03-15', NULL, NOW(), NOW()),
                                                                                                     (2, 'Maria Jansen', 'maria@example.com', '0687654321', '1990-07-22', NULL, NOW(), NOW()),
                                                                                                     (3, 'Peter Smit', 'peter@example.com', '0633445566', '1978-11-08', NULL, NOW(), NOW());

-- ✅ Loan Activities toevoegen
INSERT INTO loan_activities (id, book_id, user_id, loan_date, return_date, created_date, edited_date) VALUES
                                                                                                          (1, 1, 1, '2024-07-10 10:00:00', NULL, NOW(), NOW()),
                                                                                                          (2, 2, 2, '2024-07-12 14:30:00', '2024-07-18 15:00:00', NOW(), NOW()),
                                                                                                          (3, 5, 3, '2024-07-15 09:15:00', NULL, NOW(), NOW()),
                                                                                                          (4, 3, 1, '2024-07-16 11:00:00', NULL, NOW(), NOW()),
                                                                                                          (5, 4, 2, '2024-07-17 16:45:00', NULL, NOW(), NOW());