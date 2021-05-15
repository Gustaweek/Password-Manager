function showUserAgreementModal() {
    $("#userAgreement").dialog({
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    }).css({height: "75vh", overflow: "auto"});
}