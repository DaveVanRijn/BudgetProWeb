function details(id, incoming) {
    $.ajax({
        url: "../statistics/category",
        data: {"id": id},
        type: "GET",
        complete: function (data) {
            setDetails(data.responseText, incoming);
            toBottom();
        }
    });
}

function setDetails(data, incoming) { 
    var json = JSON.parse(data);
    var table = document.getElementById("statTable").tBodies[0];
    table.innerHTML = "";

    json.forEach(function (obj) {
        var row = table.insertRow(-1);
        var cellDesc = row.insertCell(0);
        var cellNumb = row.insertCell(1);

        cellDesc.innerHTML = obj.description;
        cellNumb.innerHTML = obj.number.toFixed(2);
        if (!incoming) {
            cellNumb.style = "color: #f00;";
        }
    });
}

function toBottom() {
    $(".main-content").animate({ scrollTop : $(document).height()} , 1000);
}

