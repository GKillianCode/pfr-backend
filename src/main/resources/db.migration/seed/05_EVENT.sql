INSERT INTO event(
    name,
    speaker_firstname,
    speaker_lastname,
    speaker_email,
    speaker_phone_number,
    description,
    event_type_id,
    participants_number,
    promo_id
)
VALUES(
    'Hackathon TechDays',
    'John',
    'Doe',
    'johndoe@email.com',
    '0123456789',
    'Developpez votre projet en equipe et relevez des defis techniques lors de notre Hackathon.',
    1,
    18,
    5
),(
    'Cours de Developpement Web',
    'Sophie',
    'Martin',
    'sophiemartin@email.com',
    '0123456789',
    'Apprenez les bases du developpement web avec les dernieres technologies.',
    2,
    30,
    3
),(
    'Travaux Pratiques - Reseaux',
    'David',
    'Gonzalez',
    'davidgonzalez@email.com',
    '0123456789',
    'Exercez-vous sur les concepts de reseaux informatiques avec des exercices pratiques.',
    3,
    20,
    7
),(
    'Jour ferie : Fete du Travail',
    NULL,
    NULL,
    NULL,
    NULL,
    'Le bureau sera ferme en raison de la fete du travail.',
    4,
    NULL,
    NULL
),(
    'Conference IA et Big Data',
    'Sophie',
    'Dupont',
    'sophiedupont@email.com',
    '0123456789',
    'Decouvrez les dernieres tendances en matiere d\'IA et de Big Data.',
    5,
    100,
    NULL
),(
    'Conge d\'ete',
    NULL,
    NULL,
    NULL,
    NULL,
    'Le bureau sera ferme en raison des conges d\'ete.',
    6,
    NULL,
    NULL
),(
    'Reunion equipe developpement',
    'Alexandre',
    'Meyer',
    'alexandremeyer@email.com',
    '0123456789',
    'Reunion de l\'equipe de developpement pour discuter des projets en cours.',
    7,
    10,
    6
),(
    'Jury de Soutenance Projets',
    'Caroline',
    'Dubois',
    'carolinedubois@email.com',
    '0123456789',
    'Jury de soutenance des projets de fin d\'annee pour les etudiants en informatique.',
    8,
    5,
    1
),(
    'Entretien de Recrutement',
    'Marc',
    'Lefebvre',
    'marclefebvre@email.com',
    '0123456789',
    'Entretien de recrutement pour un poste de developpeur web.',
    9,
    1,
    4
),(
    'Atelier DevOps',
    'Thomas',
    'Leroy',
    'thomasleroy@email.com',
    '0123456789',
    'Decouvrez les bonnes pratiques de DevOps et de CI/CD.',
    1,
    20,
    9
),(
    'Cours de Securite Informatique',
    'Julien',
    'Girard',
    'juliengirard@email.com',
    '0123456789',
    'Apprenez les bases de la securite informatique pour proteger vos donnees et votre infrastructure.',
    2,
    30,
    3
),(
    'Travaux Pratiques - Bases de Donnees',
    'Antoine',
    'Riviere',
    'antoineriviere@email.com',
    '0123456789',
    'Exercez-vous sur les concepts',
    2,
    20,
    1);
