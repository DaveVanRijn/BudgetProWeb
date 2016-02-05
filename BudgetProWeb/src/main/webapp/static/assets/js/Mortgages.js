function addC() {
    var id = $("#id").val();
    var name = $("#name").val();
    var kind;
    if (document.getElementById("aflVrij").checked) {
        kind = "Aflossingsvrij";
    } else if (document.getElementById("annu").checked) {
        kind = "Annuiteit";
    } else if (document.getElementById("lin").checked) {
        kind = "Lineair";
    } else if (document.getElementById("spaar").checked) {
        kind = "Spaar";
    }
    var description = $("#description").val();
    var redemption = $("#redemption").val();
    var residual = $("#resid").val();
    var interest = $("#interest").val();
    var annu = $("#annuïty").val();

    var json = {"id": id, "name": name, "kind": kind, "description": description,
        "redemption": redemption, "residual": residual, "interest": interest, "annu": annu};

    $.ajax({
        url: "../mortgage/add",
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(json),
        complete: function (data) {
            var retJSON = JSON.parse(data.responseText);
            if (retJSON.isNew) {
                addRow(retJSON);
            } else {
                editRow(retJSON);
            }
        }
    });
}

function addRow(json) {
    var id = parseInt(json.id);
    var name = json.name;
    var kind = json.kind;
    var residual = json.residual;
    var interest = json.interest;

    var table = document.getElementById("cats").tBodies[0];
    var row = table.insertRow(-1);
    var cellName = row.insertCell(0);
    var cellKind = row.insertCell(1);
    var cellResidual = row.insertCell(2);
    var cellInterest = row.insertCell(3);
    var cellButtons = row.insertCell(4);

    row.id = "row" + id;
    cellName.innerHTML = name;
    cellName.id = "mort" + id;
    cellKind.innerHTML = kind;
    cellResidual.innerHTML = residual.toFixed(2);
    cellInterest.innerHTML = interest.toFixed(2);
    cellButtons.innerHTML = '<button onclick="toForm(' + id + ')" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</button> ' +
            '<button onclick="deleteC(' + id + ')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>';
    resetForm();
    $("div").animate({scrollTop: 1}, 1000);
}

function editRow(json) {
    var id = parseInt(json.id);
    var name = json.name;
    var kind = json.kind;
    var residual = json.residual;
    var interest = json.interest;

    var cells = $("#row" + id).find("td");
    cells[0].innerHTML = name;
    cells[1].innerHTML = kind;
    cells[2].innerHTML = residual.toFixed(2);
    cells[3].innerHTML = interest.toFixed(2);
    resetForm();
    $("div").animate({scrollTop: 1}, 1000);
}

function deleteC(id) {
    if (confirmDelete()) {
        $.ajax({
            url: "../mortgage/delete",
            type: "GET",
            data: {"id": id}
        });
        $("#mort" + id).closest("tr").remove();
        resetForm();
    }
}

function confirmDelete() {
    return confirm("Weet je zeker dat je deze hypotheek wil verwijderen?");
}

function resetForm() {
    $("#id").val("0");
    $("#name").val("");
    document.getElementById("annu").checked = false;
    document.getElementById("lin").checked = false;
    document.getElementById("spaar").checked = false;
    document.getElementById("aflVrij").checked = true;
    $("#description").val("");
    $("#redemption").val("");
    $("#resid").val("");
    $("#interest").val("");
    $("#annuïty").val("");
}

function toForm(id) {
    $.ajax({
        url: "../mortgage/details",
        type: "GET",
        data: {"id": id},
        complete: function (data) {
            populateForm(data.responseText);
        }
    });
}

function populateForm(data) {
    var json = JSON.parse(data);
    var id = parseInt(json.id);
    var name = json.name;
    var kind = json.kind;
    var description = json.description;
    var redemption = json.redemption.toFixed(2);
    var residual = json.residual.toFixed(2);
    var interest = json.interest.toFixed(2);
    var annuïty = json.annuïty.toFixed(2);

    $("#id").val(id);
    $("#name").val(name);
    $("#description").val(description);
    $("#redemption").val(redemption);
    $("#resid").val(residual);
    $("#interest").val(interest);
    $("#annuïty").val(annuïty);

    switch (kind) {
        case "Aflossingsvrij":
            document.getElementById("aflVrij").checked = true;
            break;
        case "Annuiteit":
            document.getElementById("annu").checked = true;
            break;
        case "Lineair":
            document.getElementById("lin").checked = true;
            break;
        case "Spaar":
            document.getElementById("spaar").checked = true;
            break;
    }
}