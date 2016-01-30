<%-- 
    Document   : actionList
    Created on : 1-okt-2015, 12:12:01
    Author     : Dave van Rijn, Student 500714558, Klas IS202
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head meta charset="utf-8">
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript">
            google.load("visualization", "1", {packages: ["corechart"]});
            google.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ['Soort', 'Bedrag'],
                    ['Ingaand', ${totalIn}],
                    ['Uitgaand', ${totalOut}]
                ]);
                var options = {
                    backgroundColor: 'transparent',
                    colors: ['#04b404', '#ff0000'],
                    pieSliceText: 'percentage',
                    is3D: true,
                    fontSize: 16,
                    legend: {position: 'bottom'}
                };

                var chart = new google.visualization.PieChart(document.getElementById('pieCommon'));
                chart.draw(data, options);

                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Naam');
                data.addColumn('number', 'Bedrag');
                var inName = [${inName}];
                var inValue = [${inValue}];

                var row = new Array(inName.length);

                for (var i = 0; i < inName.length; i++) {
                    row[i] = [inName[i], inValue[i]];
                }
                data.addRows(row);
                var options = {
                    backgroundColor: 'transparent',
                    pieSliceText: 'percentage',
                    is3D: true,
                    fontSize: 16,
                    legend: {position: 'none'}
                };

                var chart = new google.visualization.PieChart(document.getElementById('pieIncoming'));
                chart.draw(data, options);

                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Naam');
                data.addColumn('number', 'Bedrag');
                var outName = [${outName}];
                var outValue = [${outValue}];
                var row = new Array(outName.length);

                for (var i = 0; i < outName.length; i++) {
                    row[i] = [outName[i], outValue[i]];
                }
                data.addRows(row);
                var options = {
                    backgroundColor: 'transparent',
                    pieSliceText: 'percentage',
                    is3D: true,
                    fontSize: 16,
                    legend: {position: 'none'}
                };

                var chart = new google.visualization.PieChart(document.getElementById('pieOutgoing'));
                chart.draw(data, options);
            }
        </script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel='stylesheet' href='${pageContext.request.contextPath}/static/css/style.css'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/static/assets/favicon.ico" rel="shortcut icon">
        <link href="${pageContext.request.contextPath}/static/assets/apple-touch-icon.png" rel="apple-touch-icon">
        <title>BudgetPro</title>
    </head>
    <body class="glossed">
        <div class="all-wrapper fixed-header left-menu hide-sub-menu">
            <div class="page-header">
                <div class="header-links hidden-xs">
                    <div class="dropdown">
                        <a href="order_details.html#" class="header-link clearfix" data-toggle="dropdown">
                            <div class="user-name-w">
                                ${user.firstname} ${user.infix} ${user.lastname}<i class="fa fa-caret-down"></i>
                            </div>
                        </a>
                        <ul class="dropdown-menu dropdown-inbar" style="right: 0; left: auto;">
                            <li><a href="${pageContext.request.contextPath}/user/"><i class="fa fa-cog"></i> Account Instellingen</a></li>
                            <li><a href="${pageContext.request.contextPath}/user/logout"><i class="fa fa-power-off"></i> Uitloggen</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-5">
                    <h1>Statistieken</h1>
                </div>
                <div class="col-md-2">
                    <h1>€
                        <script>
                            document.write(${user.balance}.toFixed(2))
                        </script>
                    </h1>
                </div>
                <div class="col-md-3">
                    <h4>Laatste transactie: ${lastDate}</h4>
                </div>
            </div>
            <div class="side">
                <div class="sidebar-wrapper">
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/dashboard" data-toggle="tooltip" data-placement="right" title="" data-original-title="Startpagina">
                                <i class="fa fa-home"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/transaction/list" data-toggle="tooltip" data-placement="right" title="Transacties" data-original-title="Transacties">
                                <i class="fa fa-euro"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/category/list" data-toggle="tooltip" data-placement="right" title="Categorieën" data-original-title="Categorieën">
                                <i class="fa fa-list"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/mortgage/list" data-toggle="tooltip" data-placement="right" title="Hypotheken" data-original-title="Hypotheken">
                                <i class="fa fa-money"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/statistics/page" data-toggle="tooltip" data-placement="right" title="Statistieken" data-original-title="Statistieken">
                                <i class="fa fa-tachometer"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main-content">
                <div class="row">
                    <div class="col-md-6">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-table"></i> Ingaande Categorieën
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 200px; overflow: auto;">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Naam</th>
                                                <th>Bedrag</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="entry" items="${incoming}">
                                                <tr>
                                                    <td>${entry.cat.name}</td>
                                                    <td>
                                                        <script>
                                                            document.write(${entry.amount}.toFixed(2));
                                                        </script>
                                                    </td><td>${transaction.category.name}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/statistics/details/${entry.cat.id}" class="btn btn-iconed btn-primary btn-sm"><i class="fa fa-search"></i>Details</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-table"></i> Uitgaande Categorieën
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 200px; overflow: auto;">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Naam</th>
                                                <th>Bedrag</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="entry" items="${outgoing}">
                                                <tr>
                                                    <td>${entry.cat.name}</td>
                                                    <td>
                                                        <script>
                                                            document.write(${entry.amount}.toFixed(2));
                                                        </script>
                                                    </td><td>${transaction.category.name}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/statistics/details/${entry.cat.id}" class="btn btn-iconed btn-primary btn-sm"><i class="fa fa-search"></i>Details</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-pie-chart"></i> Ingaande Categorieën
                            </div>
                            <div class="widget-content">
                                <div id="pieIncoming" style="height: 350px;"</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="widget widget-orange">
                        <div class="widget-title">
                            <i class="fa fa-pie-chart"></i> Alle Categorieën
                        </div>
                        <div class="widget-content">
                            <div id="pieCommon" style="height: 350px;"</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="widget widget-orange">
                    <div class="widget-title">
                        <i class="fa fa-pie-chart"></i> Uitgaande Categorieën
                    </div>
                    <div class="widget-content">
                        <div id="pieOutgoing" style="height: 350px;"</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="widget widget-orange">
        <div class="widget-title">
            <i class="fa fa-table"></i> Categorie ${category.name}
            <script>
                if (${category.incoming}) {
                    document.write("Ingaand");
                } else {
                    document.write("Uitgaand");
                }
            </script>
        </div>
        <div class="widget-content">
            <div class="table-responsive" style="height: 300px; overflow: auto;">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Beschrijving</th>
                            <th>Bedrag</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach  var="transaction" items="${transactions}">
                            <tr>
                                <td>${transaction.description}</td>
                                <td>
                                    <script>
                                        if (${category.incoming}) {
                                            document.write(${transaction.incoming}.toFixed(2));
                                        } else {
                                            document.write(${transaction.outgoing}.toFixed(2));
                                        }
                                    </script>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src='${pageContext.request.contextPath}/static/js/ad67372f4b8b70896e8a596720082ac6.js'></script>
<script src='${pageContext.request.contextPath}/static/js/d7dfc13379a397356e42ab8bd98901a0.js'></script>
</body>
</html>
