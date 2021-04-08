
# Status actuel

* ATTENTION : Les databases sont en gitignore. C'est donc normal de ne pas les voir.
* Pour récupérer une base il faut exécuter la commande create-database.sh avec le dataset positif (ds1.sql)

## Ce qui a été fait
* Schema SQL
* définition de dataset positif et negatif en adéquation avec les scénarii
* validation création de BDD
* tests violates ok avec nds1
* gestions réductions profils, réductions représentations, synopsis et types de spectacles implémentés
* ajout table LesCategoriesZones, LesGenres, LesPublics, LesReductionsProfilsSpectateurs
* ajout attribut eMailDoss pour le LesDossierAchats_base
* ajout preReservation pour les représentations
* ajout contenu énumérations dans les tables :

    * LesCategoriesZones : Orchestre, Balcon,Poulailler
    * LesGenres : musical,improvisation,comedie,tragedie,intrigue
    * LesReductionsProfilsSpectateurs : pleinTarif, etudiant, familleNombreuse, adherent
    * Public : tout public, enfant, adulte

* écriture et validation des views

## Ce qui a été partiellement fait


## Ce qui reste à faire


## Synthèse
* bdd fonctionnelle : table et view
* alignement ok avec toute la conception
