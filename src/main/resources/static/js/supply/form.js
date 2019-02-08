function addUnit() {
    var $tr = $(".ptu:last");
    var $clone = $tr.clone();
    var newId = document.getElementsByClassName('ptu').length;
    $clone.find(':text').val('');
    $clone.find('input').val('');
    $clone.find('.confirm').css('onclick', 'persistUnit(' + newId + ')');
    $tr.after($clone);
}

function persistUnit(rowId) {
    console.log(rowId);
    $.post("/supplies/create/addPtu/" + rowId)
        .done(function (data) {
            alert("Data Loaded: " + data);
        });
}

function deleteUnit(rowId) {
    console.log("Deleting " + rowId);
}

