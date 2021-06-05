const fPass = $("#firstPassword");
const sPass = $("#secondPassword");

fPass.on("input",function () {
    checkIfPasswordsAreEquivalent();
})

sPass.on("input",function () {
    checkIfPasswordsAreEquivalent();
})

/*function showUserAgreementModal() {
    $("#userAgreement").dialog({
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    }).css({height: "75vh", overflow: "auto"});
}*/

function checkIfPasswordsAreEquivalent() {
    if(fPass.val() !== sPass.val()) {
        sPass.get(0).setCustomValidity("Podane hasła są różne");
        document.getElementById("secondPasswordHelper").dataset.error="Podane hasła są różne";
    }
    else {
        sPass.get(0).setCustomValidity("");
    }
}
