const credentialsSearch = document.getElementById("credentials-search");
const credentialsListElements = document.querySelectorAll("#credentials-list .collection li");
const numOfResults = document.getElementById("num-of-results");

credentialsSearch.addEventListener("input", filter_credentials);

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
document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.modal');
    var instances = M.Modal.init(elems);
});