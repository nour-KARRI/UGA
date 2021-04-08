--=========================================================================
-- CyberCinemas
--=========================================================================
-- Based on the course of M.C. Fauvet
---------------------------------------------------------------------------

---------------------------------------------------------------------------
-- Database schema
---------------------------------------------------------------------------


CREATE TABLE LesDossiersAchats_base(
    numeroDossier INTEGER PRIMARY KEY AUTOINCREMENT,
	dateCreationDossier DATETIME,
    eMailDoss VARCHAR(100)
);


CREATE TABLE LesCategoriesZones(
    nomCat VARCHAR(100) PRIMARY KEY,
    prixCat REAL
);


CREATE TABLE LesZones(
    numeroZone INTEGER PRIMARY KEY AUTOINCREMENT,
    nomCat VARCHAR(100) NOT NULL,

    CONSTRAINT fk_nomCat
		    FOREIGN KEY (nomCat) REFERENCES LesCategoriesZones(nomCat)
);


CREATE TABLE LesPlaces(
    numeroRang INTEGER,
    numeroPlace INTEGER,
    numeroZone INTEGER,

    CONSTRAINT PK
        PRIMARY KEY (numeroRang,numeroPlace,numeroZone),
    CONSTRAINT FK_lesPlaces_Zones
        FOREIGN KEY (numeroZone) REFERENCES LesZones(numeroZone)
);


CREATE TABLE LesGenres(
    nomGenre VARCHAR(100) PRIMARY KEY
);



CREATE TABLE LesPublics (
    typePublic VARCHAR(100) PRIMARY KEY
);


CREATE TABLE LesSpectacles(
    numeroSpec INTEGER PRIMARY KEY AUTOINCREMENT,
    nomSpec VARCHAR(100),
    nomGenre  VARCHAR(100),
    synopsis VARCHAR(5000),
    typePublic VARCHAR(100),

    CONSTRAINT fk_spec_genre
	    	FOREIGN KEY(nomGenre) REFERENCES LesGenres(nomGenre)
    CONSTRAINT fk_spec_typePublic
        FOREIGN KEY(typePublic) REFERENCES LesPublics(typePublic)
);


CREATE TABLE LesReductionsProfilsSpectateurs(
    profilSpectateur VARCHAR(100) PRIMARY KEY,
    tauxReductionProfil REAL,

    CONSTRAINT Dom_tauxReductionProfil
        CHECK (tauxReductionProfil >= 0 AND tauxReductionProfil <= 1)
);


CREATE TABLE LesRepresentations_base(
    numeroSpec INTEGER,
    dateRepr DATE,
    heureRepr TIME,
    tauxReductionRepr REAL,
    preReservation INTEGER NOT NULL,

    CONSTRAINT PK
        PRIMARY KEY (dateRepr,heureRepr),
    CONSTRAINT uk_lesRep UNIQUE (numeroSpec,dateRepr)
    CONSTRAINT FK_numeroSpec
        FOREIGN KEY (numeroSpec) REFERENCES LesSpectacles(numeroSpec)
    CONSTRAINT Dom_tauxReductionRepr
        CHECK (tauxReductionRepr >= 0 AND tauxReductionRepr <= 1)
    CONSTRAINT Dom_preReservation
        CHECK (preReservation in(0,1))
);

CREATE TABLE LesTickets(
    numeroTick INTEGER PRIMARY KEY AUTOINCREMENT,
    dateAchatTick DATETIME,
    numeroRang INTEGER NOT NULL,
    numeroPlace INTEGER NOT NULL,
    numeroZone INTEGER NOT NULL,
    dateRepr DATE NOT NULL,
    heureRepr TIME NOT NULL,
    numeroDossier INTEGER NOT NULL,
    numeroSpec INTEGER NOT NULL,
    profilSpectateur VARCHAR(100) NOT NULL,

    CONSTRAINT FK_numeroRang_numeroPlace
        FOREIGN KEY (numeroRang,numeroPlace,numeroZone) REFERENCES LesPlaces(numeroRang,numeroPlace,numeroZone),
    CONSTRAINT FK_dateRepr_heureRepr
        FOREIGN KEY (dateRepr,heureRepr) REFERENCES LesRepresentations_base(dateRepr,heureRepr),
    CONSTRAINT FK_numeroDossier
        FOREIGN KEY (numeroDossier) REFERENCES LesDossiersAchats_base(numeroDossier)
    CONSTRAINT FK_numeroSpec
        FOREIGN KEY (numeroSpec) REFERENCES LesSpectacles(numeroSpec)
    CONSTRAINT UK_places_Repr UNIQUE (numeroRang,numeroPlace,numeroZone,heureRepr,dateRepr,numeroSpec),
    CONSTRAINT FK_profilSpectateur
        FOREIGN KEY (profilSpectateur) REFERENCES LesReductionsProfilsSpectateurs(profilSpectateur)
);


CREATE view IF NOT EXISTS LesDossiersAchats
as
SELECT numeroDossier,dateCreationDossier, eMailDoss, sum(prixTicket) as Prix_total_dossier
FROM(
SELECT  T.numeroDossier as numeroDossier,
        DA.dateCreationDossier as dateCreationDossier,
        eMailDoss,
        C.prixCat*(1-tauxReductionProfil)*(1-tauxReductionRepr) as prixTicket
FROM ((LesZones Z JOIN LesTickets T ON Z.numeroZone=T.numeroZone)
JOIN LesDossiersAchats_base DA ON (DA.numeroDossier=T.numeroDossier)
JOIN lesRepresentations_base R ON (R.dateRepr = T.dateRepr AND R.heureRepr = T.heureRepr)
JOIN LesReductionsProfilsSpectateurs L ON L.profilSpectateur=T.profilSpectateur
JOIN LesCategoriesZones C on C.nomCat=Z.nomCat))
GROUP BY numeroDossier, dateCreationDossier, eMailDoss
ORDER BY numeroDossier;


CREATE  view IF NOT EXISTS LesRepresentations
as
SELECT T.numeroSpec, T.dateRepr, T.heureRepr, ((select count(*) from LesPlaces)-count(numeroTick)) as nbPlacesRestantes, R.tauxReductionRepr, R.preReservation
FROM LesTickets T JOIN LesRepresentations_base R ON R.heureRepr = T.heureRepr AND R.dateRepr = T.dateRepr
GROUP BY T.numeroSpec,T.dateRepr,T.heureRepr, R.tauxReductionRepr,R.preReservation
UNION
SELECT numeroSpec,dateRepr,heureRepr,(select count(*) from LesPlaces) as nbPlacesRestantes, tauxReductionRepr,preReservation
FROM LesRepresentations_base
WHERE numeroSpec
NOT IN (SELECT numeroSpec FROM LesTickets)
UNION
SELECT numeroSpec,dateRepr,heureRepr,(select count(*) from LesPlaces) as nbPlacesRestantes, tauxReductionRepr,preReservation
FROM LesRepresentations_base
WHERE dateRepr
NOT IN (SELECT dateRepr FROM LesTickets);
