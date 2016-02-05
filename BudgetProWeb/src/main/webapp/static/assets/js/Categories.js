function deleteCat(id) {
    if (confirmDelete()) {
        $.ajax({
            url: "../category/delete",
            type: "GET",
            data: {"id": id},
            complete: function () {

            }
        });
        var row = document.getElementById("row" + id);
        row.parentNode.removeChild(row);
        resetForm();
    }
}

function confirmDelete() {
    return confirm("Weet je zeker dat je deze categorie wil verwijderen?");
}

function addCat() {
    var id = $("#id").val();
    var name = $("#name").val();
    var incoming;

    if (document.getElementById("incoming").checked) {
        incoming = true;
    } else {
        incoming = false;
    }
    console.log(incoming);

    var jsontext = {"id": id, "name": name, "incoming": incoming};

    $.ajax({
        url: "../category/add",
        contentType: 'application/json',
        type: "POST",
        data: JSON.stringify(jsontext),
        complete: function (data) {
            if (data.responseText !== "") {
                console.log(data);
                var json = JSON.parse(data.responseText);
                if (json.isNew === true) {
                    addRow(json);
                } else {
                    editRow(json);
                }
            }
        }
    });
}

function addRow(json) {
    var id = parseInt(json.id);
    var name = json.name;
    var incoming = json.incoming;

    var table = document.getElementById("catTable");
    var row = table.insertRow(-1);
    row.id =  "row" + id;
    var cellName = row.insertCell(0);
    var cellIncoming = row.insertCell(1);
    var cellButtons = row.insertCell(2);

    cellName.innerHTML = name;
    if (incoming) {
        cellIncoming.innerHTML = "Ingaand";
    } else {
        cellIncoming.innerHTML = "Uitgaand";
    }
    cellButtons.innerHTML = '<button onclick="detailCat(' + id + ')" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-pencil"></i>Bewerken</button> '
            + '<button onclick="deleteCat(' + id + ')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>';

    resetForm();
}

function editRow(json) {
    var id = parseInt(json.id);
    var name = json.name;
    var incoming = json.incoming;

    var cells = $('#row' + id).find("td");
    cells[0].innerHTML = name;
    if (incoming) {
        cells[1].innerHTML = "Ingaand";
    } else {
        cells[1].innerHTML = "Uitgaand";
    }

    resetForm();
}

function detailCat(id) {
    $.ajax({
        url: "../category/details",
        type: "GET",
        data: {"id": id},
        complete: function (data) {
            setDetails(data.responseText);
        }
    });
}

function setDetails(data) {
    var json = JSON.parse(data);

    $("#id").val(json.id);
    $("#name").val(json.name);
    var inc = document.getElementById("incoming");
    var out = document.getElementById("outgoing");

    inc.checked = json.incoming;
    out.checked = !json.incoming;
}

function resetForm() {
    $("#id").val("0");
    $("#name").val("");
    $("#incoming").attr("checked", false);
    $("#outgoing").attr("checked", true);
}
