
DROP DATABASE IF EXISTS db_pfr;
CREATE DATABASE IF NOT EXISTS `db_pfr`;
USE `db_pfr`;

CREATE TABLE `location`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `zip_code` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `is_archived`  BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `classroom`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `capacity` SMALLINT NOT NULL,
    `location_id` SMALLINT UNSIGNED NOT NULL,
    `is_bookable` TINYINT(1) NOT NULL,
    `is_archived` TINYINT(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    FOREIGN KEY(`location_id`) REFERENCES `location`(`id`)
);

CREATE TABLE `slot`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `week_day` VARCHAR(255) NOT NULL,
    `daytime` VARCHAR(255) NOT NULL,
    `is_bookable` TINYINT(1) NOT NULL,
    `is_archived`  BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `promo`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `students_number` TINYINT NOT NULL,
    `is_active` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `role`(
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(255) NOT NULL,
    `lastname` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role_id` TINYINT UNSIGNED NOT NULL,
    `is_active` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`role_id`) REFERENCES `role`(`id`)
);

CREATE TABLE `trainer_promo`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `promo_id` SMALLINT UNSIGNED NOT NULL,
    `user_id` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY(`promo_id`) REFERENCES `promo`(`id`)
);

CREATE TABLE `event_type`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `is_exceptional_closure` TINYINT(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);

CREATE TABLE `event`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `speaker_firstname` VARCHAR(255) NULL,
    `speaker_lastname` VARCHAR(255) NULL,
    `speaker_email` VARCHAR(255) NULL,
    `speaker_phone_number` VARCHAR(255) NULL,
    `description` TEXT NULL,
    `event_type_id` SMALLINT UNSIGNED NOT NULL,
    `participants_number` SMALLINT NULL,
    `promo_id` SMALLINT UNSIGNED NULL,
    `is_archived` BOOLEAN DEFAULT 0 NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`promo_id`) REFERENCES `promo`(`id`),
    FOREIGN KEY(`event_type_id`) REFERENCES `event_type`(`id`)
);

CREATE TABLE `booking`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `booking_date` DATE NOT NULL,
    `classroom_id` SMALLINT UNSIGNED DEFAULT NULL,
    `slot_id` SMALLINT UNSIGNED NOT NULL,
    `event_id` SMALLINT UNSIGNED NOT NULL,
    `user_id` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`classroom_id`) REFERENCES `classroom`(`id`),
    FOREIGN KEY(`slot_id`) REFERENCES `slot`(`id`),
    FOREIGN KEY(`event_id`) REFERENCES `event`(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `conflict`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `comment` TEXT NULL,
    `created_at` DATETIME NOT NULL,
    `booking_id` INT UNSIGNED NOT NULL,
    `user_id` SMALLINT UNSIGNED NOT NULL,
    `event_id` SMALLINT UNSIGNED NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`booking_id`) REFERENCES `booking`(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY(`event_id`) REFERENCES `event`(`id`)
);

-- Insertion des rôles 'Formateur', 'Admin' et 'Super Admin'
INSERT INTO role (name) VALUES ('ROLE_FORMATEUR');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_SUPERADMIN');

-- Insertion de plusieurs évènements
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Hackathon', false);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Cours', false);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Travaux', true);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Jour ferié', true);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Conférence', false);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Congé', true);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Réunion', false);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Jury', false);
INSERT INTO event_type (name, is_exceptional_closure) VALUES ('Entretien', false);

-- Insertion des emplacements 'Tours Mame', 'Tours' et 'Orléans'
INSERT INTO location (name, address, zip_code, city)
VALUES ('Tours Mame', '49 Bd Preuilly', '37000', 'Tours');
INSERT INTO location (name, address, zip_code, city)
VALUES ('Tours', '32 Av. Marcel Dassault', '37000', 'Tours');
INSERT INTO location (name, address, zip_code, city)
VALUES ('Orléans', '122-124 Rue du Faubourg Bannier', '45000', 'Orléans');

-- Insertion de la plage horaire 'lundi' en 'réservation possible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('lundi', 'matin', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('lundi', 'après-midi', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('lundi', 'soir', true);

-- Insertion de la plage horaire 'mardi' en 'réservation possible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mardi', 'matin', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mardi', 'après-midi', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mardi', 'soir', true);

-- Insertion de la plage horaire 'mercredi' en 'réservation possible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mercredi', 'matin', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mercredi', 'après-midi', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('mercredi', 'soir', true);

-- Insertion de la plage horaire 'jeudi' en 'réservation possible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('jeudi', 'matin', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('jeudi', 'après-midi', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('jeudi', 'soir', true);

-- Insertion de la plage horaire 'vendredi' en 'réservation possible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('vendredi', 'matin', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('vendredi', 'après-midi', true);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('vendredi', 'soir', true);

-- Insertion de la plage horaire 'samedi' en 'réservation impossible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('samedi', 'matin', false);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('samedi', 'après-midi', false);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('samedi', 'soir', false);

-- Insertion de la plage horaire 'dimanche' en 'réservation impossible'
INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('dimanche', 'matin', false);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('dimanche', 'après-midi', false);

INSERT INTO slot (week_day, daytime, is_bookable)
VALUES ('dimanche', 'soir', false);