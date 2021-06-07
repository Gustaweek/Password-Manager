document.addEventListener('DOMContentLoaded', function() {
var newPasswords = document.getElementById("newPasswords");
var collapsibleNewPasswords = M.Collapsible.init(newPasswords, {accordion:false, onOpenStart:openingPasswords,onCloseStart:closingPasswords});
});
function openingPasswords(){
    document.getElementById("changePassword").checked=true;
    document.getElementById("newPassword").required = true;
    document.getElementById("newPasswordSecond").required = true;
}
function closingPasswords(){
    document.getElementById("changePassword").removeAttribute("checked");
    document.getElementById("newPassword").removeAttribute("required");
    document.getElementById("newPasswordSecond").removeAttribute("required");

}