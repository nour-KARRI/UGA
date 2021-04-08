--=========================================================================
-- -- MyTheatre
--=========================================================================

---------------------------------------------------------------------------
-- Prolog for sqlite
---------------------------------------------------------------------------

PRAGMA foreign_keys = ON;

---------------------------------------------------------------------------
-- LesCategoriesZones
---------------------------------------------------------------------------
INSERT INTO LesCategoriesZones VALUES ('Orchestre',25);
INSERT INTO LesCategoriesZones VALUES ('Balcon',20);
INSERT INTO LesCategoriesZones VALUES ('Poulailler',15);

---------------------------------------------------------------------------
-- LesGenres
---------------------------------------------------------------------------
INSERT INTO LesGenres VALUES ('Musical');
INSERT INTO LesGenres VALUES ('Improvisation');
INSERT INTO LesGenres VALUES ('Comedie');
INSERT INTO LesGenres VALUES ('Tragedie');
INSERT INTO LesGenres VALUES ('Intrigue');

---------------------------------------------------------------------------
-- LesPublics
---------------------------------------------------------------------------
INSERT INTO LesPublics VALUES ('Adultes');
INSERT INTO LesPublics VALUES ('Tout public');
INSERT INTO LesPublics VALUES ('Enfants');


---------------------------------------------------------------------------
-- LesReductionsProfilsSpectateurs
---------------------------------------------------------------------------
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Plein tarif',0);
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Etudiant',0.2);
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Famille nombreuse',0.3);
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Adherent',0.4);


---------------------------------------------------------------------------
-- LesSpectacles
---------------------------------------------------------------------------

-- le numero est en AUTO_INCREMENT
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Romeo et Juliette','Tragedie','Romeo and Juliet is a tragedy written by William Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately reconcile their feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays Romeo and Juliet is a tragedy written by William Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately reconcile their feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays ','Adultes');
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Antigone','Tragedie','synopsis d Antigone : Romeo and Juliet is a tragedy written by William Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately reconcile their feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays Romeo and Juliet is a tragedy written by William Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately reconcile their feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays ','Adultes');
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Hamlet','Intrigue','synopsis d Hamlet: Romeo and Juliet is a tragedy written by William Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately reconcile their feuding families. It was among Shakespeare s most popular plays during his lifetime and along with Hamlet, is one of his most frequently performed plays','Adultes');


---------------------------------------------------------------------------
-- LesZones
---------------------------------------------------------------------------
-- le numero est en AUTO_INCREMENT
INSERT INTO LesZones (nomCat) VALUES ('Poulailler');

INSERT INTO LesZones (nomCat) VALUES ('Balcon');

INSERT INTO LesZones (nomCat) VALUES ('Orchestre');




---------------------------------------------------------------------------
-- LesPlaces
---------------------------------------------------------------------------
INSERT INTO LesPlaces VALUES(1,1,1);
INSERT INTO LesPlaces VALUES(1,2,1);
INSERT INTO LesPlaces VALUES(1,3,1);
INSERT INTO LesPlaces VALUES(1,4,1);
INSERT INTO LesPlaces VALUES(1,5,1);
INSERT INTO LesPlaces VALUES(1,6,1);
INSERT INTO LesPlaces VALUES(1,7,1);
INSERT INTO LesPlaces VALUES(1,8,1);
INSERT INTO LesPlaces VALUES(1,9,1);
INSERT INTO LesPlaces VALUES(1,10,1);



INSERT INTO LesPlaces VALUES(1,1,2);
INSERT INTO LesPlaces VALUES(1,2,2);
INSERT INTO LesPlaces VALUES(1,3,2);
INSERT INTO LesPlaces VALUES(1,4,2);
INSERT INTO LesPlaces VALUES(1,5,2);
INSERT INTO LesPlaces VALUES(1,6,2);
INSERT INTO LesPlaces VALUES(1,7,2);
INSERT INTO LesPlaces VALUES(1,8,2);
INSERT INTO LesPlaces VALUES(1,9,2);
INSERT INTO LesPlaces VALUES(1,10,2);



INSERT INTO LesPlaces VALUES(1,1,3);
INSERT INTO LesPlaces VALUES(1,2,3);
INSERT INTO LesPlaces VALUES(1,3,3);
INSERT INTO LesPlaces VALUES(1,4,3);
INSERT INTO LesPlaces VALUES(1,5,3);
INSERT INTO LesPlaces VALUES(1,6,3);
INSERT INTO LesPlaces VALUES(1,7,3);
INSERT INTO LesPlaces VALUES(1,8,3);
INSERT INTO LesPlaces VALUES(1,9,3);
INSERT INTO LesPlaces VALUES(1,10,3);


INSERT INTO LesPlaces VALUES(2,1,1);
INSERT INTO LesPlaces VALUES(2,2,1);
INSERT INTO LesPlaces VALUES(2,3,1);
INSERT INTO LesPlaces VALUES(2,4,1);
INSERT INTO LesPlaces VALUES(2,5,1);
INSERT INTO LesPlaces VALUES(2,6,1);
INSERT INTO LesPlaces VALUES(2,7,1);
INSERT INTO LesPlaces VALUES(2,8,1);
INSERT INTO LesPlaces VALUES(2,9,1);
INSERT INTO LesPlaces VALUES(2,10,1);



INSERT INTO LesPlaces VALUES(2,1,2);
INSERT INTO LesPlaces VALUES(2,2,2);
INSERT INTO LesPlaces VALUES(2,3,2);
INSERT INTO LesPlaces VALUES(2,4,2);
INSERT INTO LesPlaces VALUES(2,5,2);
INSERT INTO LesPlaces VALUES(2,6,2);
INSERT INTO LesPlaces VALUES(2,7,2);
INSERT INTO LesPlaces VALUES(2,8,2);
INSERT INTO LesPlaces VALUES(2,9,2);
INSERT INTO LesPlaces VALUES(2,10,2);



INSERT INTO LesPlaces VALUES(2,1,3);
INSERT INTO LesPlaces VALUES(2,2,3);
INSERT INTO LesPlaces VALUES(2,3,3);
INSERT INTO LesPlaces VALUES(2,4,3);
INSERT INTO LesPlaces VALUES(2,5,3);
INSERT INTO LesPlaces VALUES(2,6,3);
INSERT INTO LesPlaces VALUES(2,7,3);
INSERT INTO LesPlaces VALUES(2,8,3);
INSERT INTO LesPlaces VALUES(2,9,3);
INSERT INTO LesPlaces VALUES(2,10,3);



---------------------------------------------------------------------------
-- LesRepresentations_base
---------------------------------------------------------------------------
INSERT INTO LesRepresentations_base VALUES (1,'2019/02/20','15h00',0,1);
INSERT INTO LesRepresentations_base VALUES (1,'2019/03/20','15h00',0,1);
INSERT INTO LesRepresentations_base VALUES (1,'2019/03/21','20h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/03/20','22h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/03/21','16h00',0,1);
INSERT INTO LesRepresentations_base VALUES (3,'2019/03/22','21h00',0,1);
INSERT INTO LesRepresentations_base VALUES (3,'2019/04/20','15h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/05/20','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/05/21','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/05/23','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/04/20','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (2,'2019/04/26','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (3,'2019/04/13','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (1,'2019/04/14','18h00',0,1);
INSERT INTO LesRepresentations_base VALUES (3,'2019/04/16','18h00',0,1);


---------------------------------------------------------------------------
-- LesDossierAchats_base
---------------------------------------------------------------------------
-- le numero est en AUTO_INCREMENT
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('2019-03-07 10:00:00','mail1@gp04.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('2019-03-08 11:00:00','mail2@gp04.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('2019-03-08 12:00:00','mail3@gp04.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('2019-03-09 10:00:00','mail4@gp04.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('2019-03-10 17:00:00','mail5@gp04.com');


---------------------------------------------------------------------------
-- LesTickets
---------------------------------------------------------------------------
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-02-15 15:00:00',1,1,1,'2019/03/20','15h00',1,1,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-02-15 15:00:00',1,2,1,'2019/03/21','20h00',1,1,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-02-14 14:00:00',1,1,1,'2019/03/20','22h00',3,2,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-02-15 10:00:00',1,9,1,'2019/03/20','22h00',2,2,'Etudiant');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-02-16 10:00:00',1,8,1,'2019/03/21','16h00',2,2,'Famille nombreuse');
