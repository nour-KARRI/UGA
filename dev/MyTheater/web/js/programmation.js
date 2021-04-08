console.log("DANS LES SCRIPT");
let dernierBoutonSynopsis = -1; // mémorise le numéro du dernier bouton pour lequel on a demandé un synopis
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth() + 1;
var yyyy = today.getFullYear();
if (dd < 10) {
    dd = '0' + dd;
}
if (mm < 10) {
    mm = '0' + mm;
}
/**
 * Date du jour
 */

today = yyyy + '-' + mm + '-' + dd;

/**
 * Initialisation des sélecteurs de dates
 */

document.getElementById("dateUnique").setAttribute("min", today);
document.getElementById("datePlage1").setAttribute("min", today);
document.getElementById("datePlage2").setAttribute("min", today);


/**
 * Détecte un changement dans le champ "dateUnique" 
 * pour réinitialiser les champs de la plage de dates
 */

document.getElementById("dateUnique").addEventListener('input', function ()
{
    document.getElementById("datePlage2").setAttribute("min", today);
    let valeurDateUnique = document.getElementById("dateUnique").value;
    if (valeurDateUnique !== "") {
        document.getElementById("datePlage1").value = "";
        document.getElementById("datePlage2").value = "";
    }
}, false);

/**
 * Détecte un changement dans le champ1 de la plage de dates pour réinitiliaser le champ dateUnique
 * Modifie également la valeur minimale sélectionnable dans le champ2 de la plage de dates
 */
document.getElementById("datePlage1").addEventListener('input', function ()
{
	document.getElementById("datePlage2").setAttribute("min", document.getElementById("datePlage1").value);
    let valeurDatePlage1 = document.getElementById("datePlage1").value;
    if (valeurDatePlage1 !== "") {
        document.getElementById("dateUnique").value = "";
    }
}, false);

/**
 * Détecte un changement  dans le champ2 de la plage de dates pour réinitialiser le champ dateUnique
 */

document.getElementById("datePlage2").addEventListener('input', function ()
{
    let valeurDatePlage2 = document.getElementById("datePlage2").value;
    if (valeurDatePlage2 !== "") {
        document.getElementById("dateUnique").value = "";
    }
}, false);

function Synopsis(numeroSpec,numeroBouton,nomSpec,genreSpec) {
    
    if ($('#synopsis').is(":visible") && numeroBouton === dernierBoutonSynopsis) {
        $('#synopsis').hide();
    } else {
        dernierBoutonSynopsis = numeroBouton;
        document.getElementById("titre").innerHTML = nomSpec;
        document.getElementById("genreSpec").innerHTML = genreSpec;
        $.ajax(
                {
                    url: 'synopsis',
                    type: 'GET',
                    data: {
                        idSpec: numeroSpec
                    },
                    success: function (data) {
                        console.log("AJAX " + data);
                        document.getElementById("synopsi").innerHTML = data;
                        
                        $('#synopsis').show();
                    }
                }
        );

    }
}

/**
 * Réinitiliase tous les filtres
 */

function effacer(){
    document.getElementById("dateUnique").setAttribute("min", today);
    document.getElementById("dateUnique").value = "";
    document.getElementById("datePlage1").setAttribute("min", today);
    document.getElementById("datePlage1").value = "";
    document.getElementById("datePlage2").setAttribute("min", today);
    document.getElementById("datePlage2").value = "";
    document.getElementById("genre").value = "Choisir...";
    document.getElementById("public").value = "Choisir...";
}




