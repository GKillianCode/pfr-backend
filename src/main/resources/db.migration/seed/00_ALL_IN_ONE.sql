INSERT INTO `user` (firstname, lastname, email, password, role_id, is_active)
VALUES
    ("John", "Doe", "johndoe@gmail.com", "root", 1, 1),
    ("Jane", "Doe", "janedoe@gmail.com", "root", 1, 1),
    ("John", "Smith", "js@gmail.com", "pass", 1, 1),
    ("Jason", "Jensen", "jj@gmail.com", "12345", 1, 1),
    ("Jenny", "Jensen", "jenjen@gmail.com", "password", 1, 1);

INSERT INTO `classroom` (`id`, `name`, `capacity`, `location_id`, `is_bookable`)
VALUES
    (NULL, 'Salle 1', '15', '1', '1'),
    (NULL, 'Salle 2', '20', '1', '1'),
    (NULL, 'Salle 3', '22', '2', '1'),
    (NULL, 'Salle 4', '24', '2', '1'),
    (NULL, 'Salle 5', '22', '3', '0');

INSERT INTO promo (name, students_number, is_active)
VALUES
    ('CDA', 13, 1),
    ('TLOG', 9, 1),
    ('CDUI', 20, 1),
    ('DATA_ALT', 14, 1),
    ('CDA_2_2022', 13, 1),
    ('TLOG_1', 16, 1),
    ('CDUI_3_2023', 18, 1),
    ('DATA_ALT_2_2022', 28, 1),
    ('CDA_1_2021', 6, 0),
    ('TLOG_2_2023', 12, 0);

INSERT INTO `trainer_promo` (`id`, `promo_id`, `user_id`)
VALUES
    (NULL, '1', '1'),
    (NULL, '9', '1'),
    (NULL, '3', '4'),
    (NULL, '4', '3'),
    (NULL, '1', '5');

INSERT INTO `event` (`id`, `name`, `speaker_firstname`, `speaker_lastname`, `speaker_email`, `speaker_phone_number`, `description`, `event_type_id`, `participants_number`, `promo_id`, `is_archived`) VALUES
(1, 'Hackathon CDA', 'John', 'Doe', 'johndoe@gmail.com', '123456789', 'Un hackathon pour les étudiants de la promo CDA', 1, 14, 1, 0),
(2, 'Cours TLOG', NULL, NULL, NULL, NULL, 'Cours de Technologies du Web pour la promo TLOG', 2, NULL, 2, 0),
(3, 'Travaux CDUI', NULL, NULL, NULL, NULL, 'Travaux pratiques pour les étudiants de la promo CDUI', 3, NULL, 3, 0),
(4, 'Jour férié', NULL, NULL, NULL, NULL, 'Jour férié, pas de cours', 4, NULL, NULL, 0),
(5, 'Conférence', 'Jane', 'Doe', 'janedoe@gmail.com', '987654321', 'Conférence sur l\'intelligence artificielle', 5, 24, NULL, 0),
(6, 'Congé', NULL, NULL, NULL, NULL, 'Congé pour tous', 6, NULL, NULL, 0),
(7, 'Réunion', NULL, NULL, NULL, NULL, 'Réunion de l\'équipe pédagogique', 7, 12, NULL, 0),
(8, 'Jury', 'Jason', 'Jensen', 'jj@gmail.com', '555555555', 'Jury de fin de formation', 8, NULL, 10, 0),
(9, 'Entretien', 'Jenny', 'Jensen', 'jenjen@gmail.com', '111111111', 'Entretien individuel avec les étudiants', 9, 2, 5, 0),
(10, 'Cours de développement web', 'Thomas', 'Lefebvre', 'thomas.lefebvre@example.com', '1234567890', 'Introduction au développement web et aux technologies front-end', 2, NULL, 3, 0),
(11, 'Cours de gestion de projet informatique', 'Sophie', 'Martin', 'sophie.martin@example.com', '9876543210', 'Méthodologies et bonnes pratiques de gestion de projet informatique', 2, 18, NULL, 0),
(12, 'Cours de data science', 'Alexandre', 'Dupuis', 'alexandre.dupuis@example.com', '1234567890', 'Introduction à l\'analyse de données et aux techniques de data science', 2, NULL, 4, 0),
(13, 'Cours de développement mobile', 'Laura', 'Moreau', 'laura.moreau@example.com', '9876543210', 'Création d\'applications mobiles pour iOS et Android', 2, 18, NULL, 0),
(14, 'Cours de sécurité informatique', 'Antoine', 'Gagnon', 'antoine.gagnon@example.com', '1234567890', 'Principes de base de la sécurité informatique et des bonnes pratiques de protection des données', 2, 20, NULL, 0),
(15, 'Cours de bases de données', 'Marie', 'Dubois', 'marie.dubois@example.com', '9876543210', 'Conception, modélisation et gestion de bases de données relationnelles', 2, 13, 5, 0),
(16, 'Cours de développement d\'applications Java', 'Nicolas', 'Lefevre', 'nicolas.lefevre@example.com', '1234567890', 'Création d\'applications Java avec les frameworks Spring et Hibernate', 2, 20, NULL, 0),
(17, 'Cours de web design', 'Emma', 'Girard', 'emma.girard@example.com', '9876543210', 'Principes de conception graphique et création d\'interfaces web attrayantes', 2, NULL, 6, 0),
(18, 'Cours de programmation en C++', 'Pierre', 'Martin', 'pierre.martin@example.com', '1234567890', 'Apprentissage du langage C++ et développement d\'applications', 2, 15, NULL, 0),
(19, 'Cours d\'intelligence artificielle', 'Julie', 'Roy', 'julie.roy@example.com', '9876543210', 'Introduction à l\'intelligence artificielle et aux algorithmes d\'apprentissage automatique', 2, 22, NULL, 0);


INSERT INTO `booking` (`id`, `booking_date`, `classroom_id`, `slot_id`, `event_id`, `user_id`) VALUES
(1, '2023-05-15', 1, 1, 1, 1),
(2, '2023-05-15', 1, 2, 1, 1),
(3, '2023-05-15', 1, 3, 1, 1),
(4, '2023-05-16', 1, 4, 1, 1),
(5, '2023-05-16', 1, 5, 1, 1),
(6, '2023-05-16', 1, 4, 18, 2),
(7, '2023-05-17', 1, 8, 18, 2),
(8, '2023-05-19', 1, 14, 15, 3),
(9, '2023-05-15', 4, 1, 5, 3),
(10, '2023-05-16', 4, 4, 5, 3),
(11, '2023-05-17', 4, 8, 19, 5),
(12, '2023-05-15', 2, 1, 2, 1),
(13, '2023-05-15', 2, 2, 2, 1),
(14, '2023-05-15', 3, 1, 3, 2),
(15, '2023-05-15', 3, 2, 3, 2),
(16, '2023-05-15', 4, 1, 10, 1),
(17, '2023-05-15', 4, 2, 10, 1),
(18, '2023-05-15', 5, 1, 12, 3),
(19, '2023-05-15', 5, 2, 12, 3),
(20, '2023-05-16', 2, 4, 9, 4),
(21, '2023-05-16', 2, 5, 9, 4),
(22, '2023-05-16', 3, 4, 16, 2),
(23, '2023-05-16', 3, 5, 16, 2),
(24, '2023-05-16', 4, 4, 15, 1),
(25, '2023-05-16', 4, 5, 15, 1),
(26, '2023-05-16', 5, 4, 14, 3),
(27, '2023-05-16', 5, 5, 14, 3),
(28, '2023-05-17', 2, 7, 12, 2),
(29, '2023-05-17', 2, 8, 12, 2),
(30, '2023-05-17', 2, 9, 11, 4),
(31, '2023-05-17', 3, 7, 11, 4),
(32, '2023-05-17', 3, 8, 19, 1),
(33, '2023-05-17', 3, 9, 19, 1);

INSERT INTO `conflict` (`id`, `comment`, `created_at`, `booking_id`, `user_id`, `event_id`) VALUES
(1, 'Cours imprévu', '2023-05-01 14:45:00', 1, 1, 13),
(2, 'Cours imprévu', '2023-05-01 14:45:00', 2, 1, 13),
(3, 'Besoin de plus de place', '2023-05-07 16:30:00', 4, 2, 17),
(4, 'Besoin de plus de place', '2023-05-07 16:30:00', 5, 2, 17),
(5, 'Probleme de materiel dans l\'autre salle', '2023-05-10 09:30:00', 22, 3, 8),
(6, 'Probleme de materiel dans l\'autre salle', '2023-05-10 09:30:00', 23, 3, 8);
