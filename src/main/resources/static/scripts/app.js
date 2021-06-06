const credentialsSearch = document.getElementById("credentials-search");
const credentialsListElements = document.querySelectorAll("#credentials-list .collection li");
const numOfResults = document.getElementById("num-of-results");



function filter_credentials(e) {
    console.log("changed");
    var searchInput = e.target.value.toLocaleLowerCase();
    var numOfResults = 0;
    for (var i = 0; i < credentialsListElements.length; i++) {
        if ((credentialsListElements[i].innerText.toLocaleLowerCase().includes(searchInput))) {
            credentialsListElements[i].classList.remove("hide");
            numOfResults++;
        } else
            credentialsListElements[i].classList.add("hide");
    }
    document.getElementById("num-of-results").innerHTML = numOfResults + " wyników";
}


function togglePasswordVisibility() {
    var passwordInput = document.querySelector("#password.toggleMe");
    if (event.target.checked)
        passwordInput.type = "text";
    else
        passwordInput.type = "password";
}
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('#mobile-menu');
    var instances = M.Sidenav.init(elems, {edge: 'right', draggable: true});
});

document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.modal');
    var instances = M.Modal.init(elems);
});
/*
sidenav password list
document.addEventListener('DOMContentLoaded', function() {

    if(!(window.innerWidth>600)) {
        document.getElementById("credentials").classList.add("sidenav");
        document.getElementById("credentialSideTrigger").classList.remove("hidden");
        var elems = document.querySelectorAll('#credentials');
        var instances = M.Sidenav.init(elems);
    }
});
*/

