function addT() {
    var id = $("#id").val();
    var category = $("#category").val();
    var incoming = $("#incoming").val();
    var outgoing = $("#outgoing").val();
    var description = $("#description").val();
    var date = $("#date").val();
    var repeating;

    if (document.getElementById('noRep').checked) {
        repeating = 0;
    } else if (document.getElementById('rep12').checked) {
        repeating = 12;
    } else if (document.getElementById('rep4').checked) {
        repeating = 4;
    } else if (document.getElementById('rep2').checked) {
        repeating = 2;
    } else if (document.getElementById('rep1').checked) {
        repeating = 1;
    }

    var jsonText = {"id": id, "category": category, "incoming": incoming,
        "outgoing": outgoing, "description": description, "repeating": repeating,
        "date": date};

    $.ajax({
        url: "../transaction/add",
        contentType: 'application/json',
        data: JSON.stringify(jsonText),
        type: "POST",
        complete: function (response) {
            var json = JSON.parse(response.responseText);
            if (json.add === true) {
                addRow(json);
            } else {
                editRow(json);
            }
        }
    });

}

function addRow(json) {
    var id = parseInt(json.id);
    var date = json.date;
    var incoming = json.incoming;
    var outgoing = json.outgoing;
    var category = json.category;
    var repeating = json.repeating;
    var table;

    if (repeating === true) {
        table = document.getElementById("tranRepTable");
    } else {
        table = document.getElementById("tranTable");
    }
    if (repeating === true || (document.getElementById("myonoffswitch").checked && repeating === false)) {
        var row = table.insertRow(1);
        row.id = "row" + id;
        var cellDate = row.insertCell(0);
        var cellIncoming = row.insertCell(1);
        var cellOutgoing = row.insertCell(2);
        var cellCategory = row.insertCell(3);
        var cellButtons = row.insertCell(4);

        cellDate.innerHTML = date;
        cellDate.id = "date" + id;
        cellIncoming.innerHTML = parseFloat(incoming).toFixed(2);
        cellOutgoing.innerHTML = parseFloat(outgoing).toFixed(2);
        cellOutgoing.style = "color : #f00";
        cellCategory.innerHTML = category;
        cellButtons.innerHTML = '<button onclick="toForm(' + id + ')" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</button> ' +
                '<button onclick="deleteT(' + id + ')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>';
        setBalance(parseFloat(json.balance));
        sortTable();
    }
    resetForm();
    $("div").animate({scrollTop: 1}, 1000);
}

function editRow(json) {
    var id = json.id;
    var date = json.date;
    var incoming = json.incoming;
    var outgoing = json.outgoing;
    var category = json.category;
    var repeating = json.repeating;

    if (repeating === true || (document.getElementById("myonoffswitch").checked && repeating === false)) {
        var cells = $('#row' + id).find("td");
        cells[0].innerHTML = date;
        cells[1].innerHTML = incoming.toFixed(2);
        cells[2].innerHTML = outgoing.toFixed(2);
        cells[3].innerHTML = category;
        setBalance(parseFloat(json.balance));
        sortTable();
    }
    resetForm();
    $("div").animate({scrollTop: 1}, 1000);
}

function sortTable() {
    var tbl = document.getElementById("tranTable").tBodies[0];
    var store = [];
    for (var i = 0, len = tbl.rows.length; i < len; i++) {
        var row = tbl.rows[i];
        var date = new Date(row.cells[0].textContent || row.cells[0].innerText);
        if (!isNaN(date))
            store.push([date, row]);
    }
    store.sort(function (x, y) {
        return y[0] - x[0];
    });
    for (var i = 0, len = store.length; i < len; i++) {
        tbl.appendChild(store[i][1]);
    }
    var fullDate = store[0][0];
    var day = fullDate.getDate();
    var month = fullDate.getMonth() + 1;
    var year = fullDate.getFullYear();

    var topDate = day + "-" + month + "-" + year;
    store = null;
    document.getElementById("lastDate").innerHTML = "Laatste transactie: " + topDate;
}


function deleteT(id) {
    if (confirmDelete()) {
        $.ajax({
            url: "../transaction/delete",
            data: {"id": id},
            type: "GET",
            complete: function (balance) {
                setBalance(parseFloat(balance.responseText));
            }
        });
        $("#date" + id).closest("tr").remove();
        resetForm();
        if (document.getElementById("myonoffswitch").checked) {
            sortTable();
        }
    }
}

function setBalance(balance) {
    $("#balance").html("€ " + balance.toFixed(2));
}

function confirmDelete() {
    return confirm("Weet je zeker dat je deze transactie wil verwijderen?");
}

function resetForm() {
    $("#incoming").val("");
    $("#outgoing").val("");
    $("#description").val("");
    $("#id").val("0");
    document.getElementById("rep12").checked = false;
    document.getElementById("rep4").checked = false;
    document.getElementById("rep2").checked = false;
    document.getElementById("rep1").checked = false;
    document.getElementById("noRep").checked = true;
}

function populateForm(data) {
    var json = JSON.parse(data);
    $("#id").val(json.id);
    $("#category").val(json.category.toString());
    $("#incoming").val(parseFloat(json.incoming).toFixed(2));
    $("#outgoing").val(parseFloat(json.outgoing).toFixed(2));
    $("#description").val(json.description);
    var repeating = json.repeating;
    if (repeating === 0 || repeating === -1) {
        document.getElementById("noRep").checked = true;
    } else if (repeating === 12) {
        document.getElementById("rep12").checked = true;
    } else if (repeating === 4) {
        document.getElementById("rep4").checked = true;
    } else if (repeating === 2) {
        document.getElementById("rep2").checked = true;
    } else if (repeating === 1) {
        document.getElementById("rep1").checked = true;
    }
    $("#date").val(json.date);
    var button = document.getElementById("sub");
    if (!document.getElementById("myonoffswitch").checked && repeating === -1) {
        button.disabled = true;
        button.title = "Herhalende transacties kunnen alleen via de herhalende transacties tabel gewijzigd worden";
    } else {
        button.disabled = false;
        button.title = "";
    }
}

function toForm(id) {
    $.ajax({
        url: "../transaction/edit",
        data: {"id": id},
        type: "GET",
        complete: function (data) {
            populateForm(data.responseText);
        }
    });
}

function changeTable(cb) {
    var eenmalig = cb.checked;
    $.ajax({
        url: "../transaction/changeList",
        type: "GET",
        data: {"eenmalig": eenmalig},
        complete: function (data) {
            setTable(data.responseText);
        }
    });
}

function setTable(data) {
    var json = JSON.parse(data);
    var table = document.getElementById("tranTable").tBodies[0];
    table.innerHTML = "";

    json.forEach(function (obj) {
        var id = obj.id;
        var row = table.insertRow(-1);
        row.id = "row" + id;
        var cellDate = row.insertCell(0);
        var cellIncoming = row.insertCell(1);
        var cellOutgoing = row.insertCell(2);
        var cellCategory = row.insertCell(3);
        var cellButtons = row.insertCell(4);

        cellDate.innerHTML = obj.date;
        cellDate.id = "date" + id;
        cellIncoming.innerHTML = obj.incoming.toFixed(2);
        cellOutgoing.innerHTML = obj.outgoing.toFixed(2);
        cellOutgoing.style = "color: #f00";
        cellCategory.innerHTML = obj.category;
        cellButtons.innerHTML = '<button onclick="toForm(' + id + ')" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</button> ' +
                '<button onclick="deleteT(' + id + ')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>';

    });
}