function genetatePass(){
    var passwordArr           = [];
    var charsLower='abcdefghijklmnopqrstuvwxyz';
    var charsUpper='ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var charsNumbers='0123456789';
    var charsSpecial='!"#$%&\'()*+,-./:;<=>?@[]^_`{|}~';

    var chars="";

    if(document.getElementById('nums').checked)
        chars+=charsNumbers;
    if(document.getElementById('letters').checked)
        chars+=charsUpper+charsLower;
    if(document.getElementById('special').checked)
        chars+=charsSpecial;
    if(chars.length>0) {
        document.getElementById("copyPass").classList.remove("disabled");
        var passwordLen = parseInt(document.getElementById("genLength").value);
        var randomChar = '';
        var typeClass = '';
        for (var i = 0; i < passwordLen; i++) {
            var randomChar = '';
            var randomClass = '';
            randomChar = (chars.charAt(Math.floor(Math.random() *
                chars.length)));
            if (isNumber(randomChar))
                randomClass = "number blue-text";
            else if (isLetter(randomChar))
                randomClass = "letter green-text";
            else
                randomClass = "special red-text";
            passwordArr.push('<span class="' + randomClass + ' lighten-4">' + randomChar + '</span>');
        }
        var password = passwordArr.join('');

        document.getElementById("generatedPassword").innerHTML = password;
    }
    else
    {
        document.getElementById("generatedPassword").innerHTML = '&nbsp;';
        document.getElementById("copyPass").classList.add("disabled");
        M.toast({html: 'Wybierz dozwolone znaki', classes: 'red white-text'})
    }
}

function isNumber(input) {
    var filter = /^[0-9]+$/;
    if (input.match(filter))
        return true;
    return false;
}
function isLetter(input) {
    var filter = /^[a-zA-Z]+$/;
    if (input.match(filter))
        return true;
    return false;
}

function copyPass() {
    var passElem = document.getElementById("generatedPassword");
    var range = document.createRange();
    range.selectNode(passElem);
    window.getSelection().removeAllRanges();
    window.getSelection().addRange(range);
    document.execCommand("copy");
    window.getSelection().removeAllRanges();
    M.toast({html: 'Skopiowano', classes: 'green white-text'})
}


function usePass(){
    document.getElementById("password").value=document.getElementById("generatedPassword").innerText;
    document.getElementById("password").focus();
    M.Modal.getInstance(document.getElementById("generatorModal")).close();
}