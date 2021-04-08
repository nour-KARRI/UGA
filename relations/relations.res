//=========================================================================
//                      Relation model
//-------------------------------------------------------------------------
// This relation model is expressed in RelationScript
// See https://modelscript.rtfd.io/en/latest/scripts/relations/index.html
//=========================================================================

relation model MyTheatre
import glossary model from "../glossaries/glossaries.gls"
import class model from "../classes/classes.cl1"
import qa model from "../classes/relations.qas"

relation LesDossierAchats_base(_numeroDossier_,dateCreationDossier, eMailDoss)
    intention
        (nd,dc, ed) in LesDossierAchts_base <=>
        | chaque dossier d'achat est identifie par un numero <nd> et une date de creation<dc>,
        | l’email du spectateur <ed> pour envoyer les billet
    constraints
        dom(_numeroDossier_,) = Integer
        dom(dateCreationDossier) = DateTime
        dom(eMailDoss) = String
        key _numeroDossier_

relation LesCategoriesZones(_nomCat_,prixCat)
    intention
        (nc, pc) in LesCategoriesZones <=>
        | Chaque categorie de siège est identifiée par son nom <nc>, et un prix <pc>
    constraints
        dom(_nomCat_) = String
        dom(prixCat) = Real
        key _nomCat_

relation LesGenres(_nomGenre_)
    intention
        (ng) in LesGenres <=>
        | Chaque spactacle appartient a un genre (ng)
    constraints
        dom(_nomGenre_) = String
        key _nomGenre_

relation LesReductionsProfilsSpectateurs(_nomProfilSpectateur_,tauxReductionProfil)
    intention
        (np,tauxR) in LesReductionsProfilsSpectateurs <=>
        | Les personnes qui achetent un ticket ont un profil de réduction <np>
        | qui représente un taux de réduction <tauxR>
    examples
        (pleinTarif,1.0)
    constraint
        dom(_nomProfilSpectateurReduc_) = String
        dom(tauxReductionProfil) = Real
        key _nomProfilSpectateur_

relation LesPublics(_typePublic_ )
    intention
        (tp) in LesPublics <=>
        | le spactacle appartient a un type Public (tp)
    constraints
        dom(_typePublic_) = String
        key _typePublic_


relation LesSpectacles(_numeroSpec_, nomSpec, nomGenre, synopsis, typePublic)
      intention
          (ns, nomS, ge, sy, tp) in LesSpectacles <=>
          | un Spectacle est identifie par un numero <ns> et un nom <nomS>, un genre (ge),
          | decrit par un synopsis (sy), un type public <tp>
      examples
          (10, 'marcher sur la lune', 'drame', 'c'est l'histoire de ...')
      constraints
          dom(_numeroSpec_) = Integer
          dom(nomSpec, _nomGenre_, synopsis,typePublic) = String
          key _numeroSpec_
          LesSpectacles[nomGenre] C= LesGenres[nomGenre]
          LesSpectacles[typePublic] C= LesPublics[typePublic]


relation LesRepresentations_base(_numeroSpec,_dateRepr_,_heureRepr_,tauxReductionRepr,preReservation)
        | `Spectacle` planifié dans `MyTheatre`
    intention
        (ns,dateR,heureR,tR, pr) in LesRepresentations_base <=>
        | La `Representation` d'une `Pièce` possèdant le numéro <ns>
        | se déroule à la date <dateR> et l'heure <heureR>. Elle peut avoir une réduction de taux <tR>,
        | et une prereservation <pr>.
    examples
        (35, '28/03/2019','14h00',1.0)
    constraints
        dom(numeroSpec, preReservation) = Integer
        dom(dateRepr) = Date
        dom(heureRepr) = Time
        dom(tauxReductionRepr) = Real
        dom(preReservation) = Integer
        key numeroSpec,dateRepr
        key dateRepr,heureRepr
        LesRepresentations_base[numeroSpec] = LesSpectacles[numeroSpec]


relation LesTickets(_numeroTick_, dateAchatTick,numeroRang,numeroPlace, numeroZone,dateRepr,heureRepr,numeroSpec,profilSpectateur)
    intention
        (nt, dateA, np, dateR, heureR, ns, nd, nz, nr, np) in LesTickets <=>
        | chaque ticket est identifie par un numero de serie <nt> date de l'achat <dateA>, le profile de reduction <np>, un dossier d'achat de numero <nd>
        | avec ce ticket le spectateur peut s'asseoir a la place numero <np> qui se situe dans le rang <nr>, dans la zone <nz>,
        | pour regarder un spectacle de numero <ns> qui se déroule à la date <dateR> et l'heure <heureR>.
    examples
        (1, '24/02/2018', 'familleNombreuse','28/02/2018','20h',3, 10, 1,5,22)
    constraints
        dom(_numeroTick_) = Integer
        dom(dateAchatTick) = DateTime
		    dom(numeroRang) = Integer
		    dom(numeroPlace) = Integer
		    dom(numeroZone) = Integer
        dom(dateRepr) = Date
		    dom(heureRepr) = Time
		    dom(numeroDossier) = Integer
		    dom(numeroSpec) = Integer
        dom(profilSpectateur) = String
        key _numeroTick_
        key numeroZone,numeroRang,numeroPlace,dateRepr,heureRepr,numeroSpec
        LesTicket[numeroSpec] C= LesSpectacles[numeroSpec]
        LesTicket[profilSpectateur] C= LesReductionsProfilsSpectateurs[profilSpectateur]
        LesTicket[numeroDossier] = LesDossierAchats_base[numeroDossier]
        LesTicket[dateRepr, heureRepr] C= LesRepresentations[dateRepr, heureRepr]
        LesTicket[numeroRang, numeroPlace] C= LesPlaces[numeroRang, numeroPlace]
        LesTicket[numeroZone] C= LesZones[numeroZone]




relation LesPlaces(_numeroRang_,_numeroPlace_, _numeroZone_)
    intention
        (nr,np,nz) in LesPlaces <=>
        | Une place est identifiee par un numero de rang <nr>, et un numero de place dans le rang <np> dans la zone <nz>
    examples
        (5, 1, 3)
    constraints
        dom(_numeroRang_) = Integer
        dom(_numeroPlace_) = Integer
	    	dom(_numeroZone_) = Integer
        key _numeroRang_,_numeroPlace_,_numeroZone_
        LesPlaces[_numeroZone_] = LesZones[_numeroZone_]



relation LesZones(_numeroZone_, nomCat)
    intention
        (nz,nc,pz) in LesZones <=>
        | Une zone de numéro <nz> correspond à une catégorie <nc>
    examples
        (1, 'Balcon')
    constraints
		    dom(_numeroZone_) = Integer
        dom(nomCat) = String
        key _numeroZone_
        LesZones[nomCat] C= LesCategoriesZones[nomCat]


-------------------------------------------------------------------------------
-- Les vues
-------------------------------------------------------------------------------

view LesDossierAchats(_numeroDossier_,dateCreationDossier, eMailDoss, prixGlobalDossier)
    intention
        (nd, pg, dc, ed) in LesDossierAchats <=>
        | idem `LesDossierAchats_base` avec le calcul du prix global des places <pg>
    examples
        (1,'10/03/2019 10:22:01', 100.43)
    constraints
        dom(_numeroDossier_) = Integer
        dom(prixGlobalDossier) = Real
        dom(dateCreationDossier) = DateTime
        dom(eMailDoss) = String
        key _numeroDossier_
query LesDossierAchats(prixGlobalDossier)
    | le calcul donnant le prix global (en euros) des places achetees
    | pour chaque DossierAchat


view LesRepresentations (_numeroSpec,_dateRepr_,_heureRepr_,nbPlacesRestantesepr,tauxReductionRepr,preReservation)
    intention
        (_numeroSpec,_dateRepr_,_heureRepr_, pr, tR, preR) in LesRepresentations
        | idem `LesRepresentations_base` avec le calcul du nombre de places restantes <pr>
        |
    examples
        (35, '28/03/2019','14h00', 12)
	  constraints
        dom(numeroSpec) = Integer
        dom(dateRepr) = Date
        dom(heureRepr) = Time
        dom(nbPlacesRestantesepr) = Integer
        dom(tauxReductionRepr) = Real
        dom(preReservation) = Integer
        key numeroSpec,dateRepr
        key dateRepr,heureRepr
        LesRepresentations[numeroSpec] = LesSpectacles[numeroSpec]
query LesRepresentations(nbrPlacesRestantesRepr)
    | le calcul du nombre de places restantes
