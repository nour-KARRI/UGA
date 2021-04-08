--=========================================================================
--                          Object Model
---------------------------------------------------------------------------
-- This object model is expressed in ObjectScript.
-- See https://modelscript.readthedocs.io/en/latest/scripts/objects1/index.html
-- ObjectScript1 is an annotated version of USE OCL.
-- Annotations are in comments and start with
--    "@" for statement annotations
--    "|" for documentation annotations
--=========================================================================
--
--@ object model O1
--@ import glossary model from "../../glossaries/glossaries.gls"
--@ import class model from "../../classes/classes.cl1"

--| Le theatre propose differents profils de reduction

    ! create reductionProfilPleinTarif : ReductionProfilSpectateur
    ! reductionProfilPleinTarif._nomProfilSpectateur_ := 'Plein tarif'
    ! reductionProfilPleinTarif.tauxReductionProfil := 0

    ! create reductionProfilEtudiant : ReductionProfilSpectateur
    ! reductionProfilEtudiant._nomProfilSpectateur_ := 'Etudiant'
    ! reductionProfilEtudiant.tauxReductionProfil := 0.2

    ! create reductionProfilFamilleNombreuse : ReductionProfilSpectateur
    ! reductionProfilFamilleNombreuse._nomProfilSpectateur_ := 'Famille nombreuse'
    ! reductionProfilFamilleNombreuse.tauxReductionProfil := 0.3

    ! create reductionProfilAdherent : ReductionProfilSpectateur
    ! reductionProfilAdherent._nomProfilSpectateur_ := 'Adhérent'
    ! reductionProfilAdherent.tauxReductionProfil := 0.25


--| Il y a un spectacle humoristique sur la vie en milieu hospitalier

    ! create comedie : Genre
    ! comedie._nomGenre_ := 'Comédie'

    ! create docteurParkinson : Spectacle
    ! docteurParkinson._numeroSpectacle_ := 6
    ! docteurParkinson.nomSpectacle := 'Le docteur Parkinson'
    ! docteurParkinson.synopsis := 'L histoire d un chrirugien qui aurait du etre DJ'


    ! create publicAdulte : Public
    ! publicAdulte._typePublic_ := 'Adulte'
    ! create toutPublic : Public
    ! toutPublic._typePublic_ := 'Tout public'


    ! insert(docteurParkinson,toutPublic) into EstDestine
    ! insert(docteurParkinson,comedie) into CorrespondAu

--| En tarif plein, le coût est de 15€ par personne pour un placement dans le poulailler, 20€ par personne pour un placement dans la catégorie balcon et 25€ par
--| personne pour un placement orchestre.

    ! create poulailler : CategorieZone
    ! poulailler._nomCategorieZone_ := 'Poulailler'
    ! poulailler.prixCategorie := 15
    ! create orchestre : CategorieZone
    ! orchestre._nomCategorieZone_ := 'Orchestre'
    ! orchestre.prixCategorie := 25
    ! create balcon : CategorieZone
    ! balcon._nomCategorieZone_ := 'Balcon'
    ! balcon.prixCategorie := 20

--| La représentation le lundi soir à 20% de réduction par rapport à celle du dimanche soir.

    ! create docteurParkinson1 : Representation
    ! docteurParkinson1._date_ := '22/04/2019'
    ! docteurParkinson1._heure_ := '20h00'
  	! docteurParkinson1.tauxReduction := 0.2
    ! docteurParkinson1.preReservation := 1

    ! create docteurParkinson2 : Representation
    ! docteurParkinson2._date_ := '28/04/2019'
    ! docteurParkinson2._heure_ := '20h00'
  	! docteurParkinson2.tauxReduction := 0
    ! docteurParkinson2.preReservation := 1

    ! insert(docteurParkinson,docteurParkinson1) into EstProgrammeeLe
    ! insert(docteurParkinson,docteurParkinson2) into EstProgrammeeLe
