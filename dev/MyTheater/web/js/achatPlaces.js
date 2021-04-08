/* 
 * Copyright (C) 2018 genoud
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

$(document).ready(function () {

    let seatNumber = 1; // numéro utilisé pour associer un label aux sièges
    let seatId = 1; // numero utilisé pour associer un id aux sièges
    let ancien = 1;
    let numTicket = 1;
    let $detailCategorie = $('#detail-categories');
    let $nbPlaces = $('#nbplaces');
    let $prixTotal = $('#prixtotal');
    let sc = $('#seat-map').seatCharts({
        map: [
            '__A[3_1_1,1]A[3_1_2,2]A[3_1_3,3]A[3_1_4,4]A[3_1_5,5]__A[3_1_6,6]A[3_1_7,7]A[3_1_8,8]A[3_1_9,9]A[3_1_10,10]__',
            '__A[3_2_1,1]A[3_2_2,2]A[3_2_3,3]A[3_2_4,4]A[3_2_5,5]__A[3_2_6,6]A[3_2_7,7]A[3_2_8,8]A[3_2_9,9]A[3_2_10,10]__',
            '__B[2_1_1,1]B[2_1_2,2]B[2_1_3,3]B[2_1_4,4]B[2_1_5,5]__B[2_1_6,6]B[2_1_7,7]B[2_1_8,8]B[2_1_9,9]B[2_1_10,10]__',
            '__B[2_2_1,1]B[2_2_2,2]B[2_2_3,3]B[2_2_4,4]B[2_2_5,5]__B[2_2_6,6]B[2_2_7,7]B[2_2_8,8]B[2_2_9,9]B[2_2_10,10]__',
            '__C[1_1_1,1]C[1_1_2,2]C[1_1_3,3]C[1_1_4,4]C[1_1_5,5]__C[1_1_6,6]C[1_1_7,7]C[1_1_8,8]C[1_1_9,9]C[1_1_10,10]__',
            '__C[1_2_1,1]C[1_2_2,2]C[1_2_3,3]C[1_2_4,4]C[1_2_5,5]__C[1_2_6,6]C[1_2_7,7]C[1_2_8,8]C[1_2_9,9]C[1_2_10,10]__'
        ],
        seats: {
            A: {
                classes: 'Orchestre', // votre classe CSS spécifique
                category: 'A',
                price: 25
            },
            B: {
                classes: 'Balcon', // votre classe CSS spécifique
                category: 'B',
                price: 20
            },
            C: {
                classes: 'Poulailler', // votre classe CSS spécifique
                category: 'C',
                price: 15
            }
        },
        naming: {
            top: false,
            getLabel: function (character, row, column) {

                if (row === ancien) {
                    return seatNumber++;
                } else {
                    seatNumber = 1;
                    ancien = row;
                    return seatNumber++;
                }
            },
            getId: function (character, row, column) {
                return seatId++;
            },
            rows: ['R1', 'R2', 'R1', 'R2', 'R1', 'R2']
        },
        legend: {
            node: $('#legend'),
            items: [
                ['A', 'available', 'Orchestre'],
                ['B', 'available', 'Balcon'],
                ['C', 'available', 'Poulailler'],
                [, 'unavailable', 'Place non disponible']
            ]
        },
        click: function () {
            if (this.status() === 'available') {
                /*
                 * Une place disponible a été sélectionnée
                 * Mise à jour du nombre de places et du prix total
                 *
                 * Attention la fonction .find  ne prend pas en compte 
                 * la place qui vient d'être selectionnée, car la liste des
                 * places sélectionnées ne sera modifiée qu'après le retour de cette fonction.
                 * C'est pourquoi il est nécessaire d'ajouter 1 au nombre de places et le prix
                 * de la place sélectionnée au prix calculé.
                 */
                $nbPlaces.text(sc.find('selected').length + 1);
                $prixTotal.text(calculerPrixTotal(sc) + this.data().price);
                $("#places").append("<tr" + "  " + "class=\"ticket\"" + " " + "tarif=\"P\"" + "id=\"" + this.node().attr("id") + "_\">"
                        + "<td>" + numTicket + "</td>"
                        + "<td>" + DetailsId(this.node().attr("id"))[0] + "</td>"
                        + "<td>" + DetailsId(this.node().attr("id"))[1] + "</td>"
                        + "<td>" + DetailsId(this.node().attr("id"))[2] + "</td>"
                        + "<td><select class=\"tarif\">"
                        + "<option value=\"T\">Tarif Plein</option>"
                        + "<option value=\"E\">Etudiant (20%)</option>"
                        + "<option value=\"F\">Famille  (30%)</option>"
                        + "<option value=\"A\">Adherent (40%)</option>"
                        + "</select>"                       
                        + "</td>" 
                        + "</tr>");
                numTicket++;
                $("#achatBtn").prop("disabled", false);
                return 'selected';
            } else if (this.status() === 'selected') {
                // la place est désélectionnée

                document.getElementById(this.node().attr("id") + "_").remove();
                numTicket--;
                let nbPlaceSelectees = sc.find('selected').length - 1;
//                  

                $nbPlaces.text(nbPlaceSelectees);
                if (nbPlaceSelectees === 0) {
                    $("#achatBtn").prop("disabled", true);
                    $prixTotal.text(0);
                } else {
                    $prixTotal.text(calculerPrixTotal(sc) - this.data().price);
                }
                return 'available';
            } else if (this.status() === 'unavailable') {
                // la place a déjà été achetée.
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });

    $("#achatBtn").click(function () {
        acheter();
    });

    $("#SauveBtn").click(function () {
        sauvegarderBillets();
    });




    majPlanSalle();
    setInterval(majPlanSalle, 3000); // le plan de salle est mis à jour toutes les 10 secondes

    /**
     * met à jour le status des places. Cette mise à jour est effectuée par un 
     * appel ajax au service d'url placesNonDisponibles.
     * La réponse de ce service est un objet JSON contenant un tableau bookings
     * @returns {undefined}
     */
    function majPlanSalle() {
        $.ajax({
            type: 'post',
            url: 'placesNonDisponibles',
            dataType: 'json',
            success: function (reponse) {
                // iteration sur toutes les places vendues contenue dans le tableau bookings
                // de l'objet reponse
                $.each(reponse.placesVendues, function (index, placeVendue) {
                    //mettre à jour le status de l'objet Seat correspondant à la place vendue
                    sc.status(placeVendue.placeId, 'unavailable'); // le premier paramètre 
                    // de status est l'identifiant de la place (siège) pour laquellle on souhaite
                    // modifier le status. Ce paramètre est un chaîne, placeVendue.placeID est 
                    // de type number (entier), ''+ placeVendue.placeId permet de le convertir
                    // en chaîne de caractères (on aurait aussi pu utiliser placeVendue.placeId.toString())
                });
                let nbPlaceSelectees = sc.find('selected').length;
                $('#nbplaces').text(nbPlaceSelectees);
                if (nbPlaceSelectees === 0) {
                    $("#achatBtn").prop("disabled", true);
                    $prixTotal.text(0);
                } else {
                    $("#achatBtn").prop("disabled", false);
                    $prixTotal.text(calculerPrixTotal(sc));
                }
            }
        });
    }
});
function majPanier(sc) {
    let nbPlaceSelectees = sc.find('selected').length;
    $('#nbplaces').text(nbPlaceSelectees);
    if (nbPlaceSelectees === 0) {
        $("#achatBtn").prop("disabled", true);
        $prixTotal.text(0);
    } else {
        $("#achatBtn").prop("disabled", false);
        $prixTotal.text(calculerPrixTotal(sc));
    }
}


function calculerPrixTotal(sc) {
    let total = 0;
    //retrouver toutes les places sélectionnées et sommer leur prix
    sc.find('selected').each(function () {
        total += this.data().price;
    });
    return total;
}


function DetailsId(id) {
    let arrayId = Array.from(id);
    let i = 0;
    let zone = "";
    while (arrayId[i] !== '_') {
        zone = zone + arrayId[i];
        i++;
    }
    i++;
    let rang = "";
    while (arrayId[i] !== '_') {
        rang = rang + arrayId[i];
        i++;
    }
    i++;
    let place = "";
    while (i < arrayId.length) {
        place = place + arrayId[i];
        i++;
    }

    return [zone, rang, place];
}

function acheter() {
    let params = "";
    let premier = true;
    let tiks = document.getElementsByClassName("ticket");
    Array.prototype.forEach.call(tiks, function (tik) {
        let ticket_Id = tik.getAttribute("id");
        let ticket_Red = tik.lastChild.firstElementChild.value;

        params = params + "&place=";

        params = params + ticket_Id + ticket_Red;

    });
    location.replace("acheterPlaces?Flag=1" + params);
}

function sauvegarderBillets() {
    let params = "";
    let premier = true;
    let tiks = document.getElementsByClassName("ticket");
    Array.prototype.forEach.call(tiks, function (tik) {
        let ticket_Id = tik.getAttribute("id");
        let ticket_Red = tik.lastChild.firstElementChild.value;

        params = params + "&place=";

        params = params + ticket_Id + ticket_Red;

    });
    location.replace("acheterPlaces?Flag=2" + params);
}



function VerifMail()
{
    let a = document.getElementById("Mail").value;
    let mailValide = false;
    for (let j = 0; j < (a.length); j++) {
        if (a.charAt(j) === '@') {
            if (j < (a.length - 4)) {
                for (let k = j; k < (a.length - 2); k++) {
                    if (a.charAt(k) === '.')
                        mailValide = true;
                }
            }
        }
    }

    if (mailValide === false) {
        alert("Veuillez saisir une adresse email valide.");
        return mailValide;
    }
}
