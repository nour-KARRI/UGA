--=========================================================================
-- MyTheatre
--=========================================================================

---------------------------------------------------------------------------
--  This negative dataset tests all constraints defined in the schema
---------------------------------------------------------------------------

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

--@ violates LesCategoriesZones.PK
INSERT INTO LesCategoriesZones VALUES ('Poulailler',15);


---------------------------------------------------------------------------
-- LesPublics
---------------------------------------------------------------------------
INSERT INTO LesPublics VALUES ('Adultes');
INSERT INTO LesPublics VALUES ('Tout public');
INSERT INTO LesPublics VALUES ('Enfants');

--@ violates LesPublics.PK
INSERT INTO LesPublics VALUES ('Enfants');


---------------------------------------------------------------------------
-- LesReductionsProfilsSpectateurs
---------------------------------------------------------------------------
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Plein tarif',0);
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Etudiant',0.2);

--@ violates LesReductionsProfilsSpectateurs.CK.tauxReductionProfil
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Famille nombreuse',2);
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Adherent',0.4);

--@ violates LesReductionsProfilsSpectateurs.PK
INSERT INTO LesReductionsProfilsSpectateurs VALUES ('Adherent',0.4);


---------------------------------------------------------------------------
-- LesGenres
---------------------------------------------------------------------------
INSERT INTO LesGenres VALUES ('Musical');
INSERT INTO LesGenres VALUES ('Improvisation');
INSERT INTO LesGenres VALUES ('Tragedie');
INSERT INTO LesGenres VALUES ('Intrigue');

--@ violates LesGenres.PK
INSERT INTO LesGenres VALUES ('Intrigue');


---------------------------------------------------------------------------
-- LesSpectacles
---------------------------------------------------------------------------
-- le numero est en AUTO_INCREMENT
-- pas besoin de tester la PK car on est en AUTOINCREMENT
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Romeo et Juliette','Tragedie','Une femme aime un homme, un homme aime une femme, c est beau','Adultes');
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Antigone','Tragedie','Antigone est la fille d Œdipe et de Jocaste, souverains de Thèbes','Adultes');

--@ violates LesSpectacles.fk_spec_typePublic
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Hamlet','Intrigue','Le roi du Danemark, père dHamlet, est mort récemment','Gamins');
--@ violates LesSpectacles.fk_spec_genre
INSERT INTO LesSpectacles (nomSpec,nomGenre,synopsis,typePublic) VALUES ('Asterix ET Obelix','Animation','Obelix n est pas GROS','Adultes');

---------------------------------------------------------------------------
-- LesZones
---------------------------------------------------------------------------
-- le numero est en AUTO_INCREMENT
-- pas besoin de tester la PK car on est en AUTOINCREMENT
INSERT INTO LesZones (nomCat) VALUES ('Poulailler');
--@ violates LesZones.fk_nomCat
INSERT INTO LesZones (nomCat) VALUES ('toto');
INSERT INTO LesZones (nomCat) VALUES ('Balcon');
INSERT INTO LesZones (nomCat) VALUES ('Balcon');
INSERT INTO LesZones (nomCat) VALUES ('Orchestre');
INSERT INTO LesZones (nomCat) VALUES ('Orchestre');



---------------------------------------------------------------------------
-- LesPlaces
---------------------------------------------------------------------------

INSERT INTO LesPlaces VALUES(1,2,1);
INSERT INTO LesPlaces VALUES(2,3,1);
INSERT INTO LesPlaces VALUES(2,4,1);

INSERT INTO LesPlaces VALUES(1,1,2);
INSERT INTO LesPlaces VALUES(1,2,2);
INSERT INTO LesPlaces VALUES(1,3,2);


INSERT INTO LesPlaces VALUES(1,1,1);
--@ violates LesPlaces.PK
INSERT INTO LesPlaces VALUES(1,1,1);

--@ violates LesPlaces.FK_lesPlaces_Zones
INSERT INTO LesPlaces VALUES(1,2,8);


---------------------------------------------------------------------------
-- LesRepresentations_base
---------------------------------------------------------------------------

INSERT INTO LesRepresentations_base VALUES (1,'20/03/2019','15h00',0,1);
INSERT INTO LesRepresentations_base VALUES (1,'21/03/2019','20h00',0,1);
--@ violates LesRepresentations_base.PK
INSERT INTO LesRepresentations_base VALUES (2,'20/03/2019','15h00',0,1);

--@ violates LesRepresentations_base.FK_numeroSpec
INSERT INTO LesRepresentations_base VALUES (4,'29/03/2019','22h00',0,1);

--@ violates LesRepresentations_base.ck.preReservation
INSERT INTO LesRepresentations_base VALUES (2,'20/03/2019','22h00',0,2);
INSERT INTO LesRepresentations_base VALUES (2,'21/03/2019','16h00',0,1);
--@ violates LesRepresentations_base.uk_lesRep
INSERT INTO LesRepresentations_base VALUES (3,'21/03/2019','16h00',0,1);

--@ violates LesRepresentations_base.ck.tauxReductionRepr
INSERT INTO LesRepresentations_base VALUES (3,'22/03/2019','21h00',2,1);

---------------------------------------------------------------------------
-- LesDossierAchats_base
---------------------------------------------------------------------------

-- le numero est en AUTO_INCREMENT
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('07-03-2019 10:00:00','mail1@domain.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('08-03-2019 11:00:00','mail2@domain.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('08-03-2019 12:00:00','mail3@domain.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('09-03-2019 10:00:00','mail4@domain.com');
INSERT INTO LesDossiersAchats_base (dateCreationDossier,eMailDoss) VALUES ('10-03-2019 17:00:00','mail5@domain.com');

---------------------------------------------------------------------------
-- LesTickets
---------------------------------------------------------------------------


INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-15-02 15:00:00',1,1,1,'20/03/2019','15h00',1,1,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-15-02 15:00:00',1,2,1,'21/03/2019','20h00',1,1,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-14-02 14:00:00',1,3,2,'20/03/2019','15h00',3,1,'Plein tarif');
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-15-02 10:00:00',2,3,1,'20/03/2019','22h00',2,2,'Plein tarif');

--@ violates LesTickets.FK_profilSpectateur
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-16-02 10:00:00',2,4,1,'21/03/2019','16h00',2,2,'Unijambiste');

--@ violates LesTickets.UK_places
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-15-02 15:00:00',1,2,1,'21/03/2019','20h00',1,1,'Plein tarif');

--@ violates LesTickets.FK_numeroRang_numeroPlace
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-14-02 14:00:00',1,99,1,'20/03/2019','15h00',3,1,'Plein tarif');

--@ violates LesTickets.FK_dateRepr_heureRepr
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-15-02 15:00:00',1,2,1,'21/03/2019','19h00',1,1,'Plein tarif');

--@ violates LesTickets.FK_numeroDossier
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-16-02 10:00:00',2,3,1,'21/03/2019','16h00',6,1,'Plein tarif');

--@ violates LesTickets.AttributNOTNULL
INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur) VALUES ('2019-16-02 10:00:00',NULL,4,2,'21/03/2019','16h00',1,1,'pleinTarif');
