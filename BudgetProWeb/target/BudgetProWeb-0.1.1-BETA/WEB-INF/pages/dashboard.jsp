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
                    ['Binnenkomend', ${incoming}],
                    ['Uitgaand', ${outgoing}]
                ]);
                var options = {
                    backgroundColor: 'transparent',
                    colors: ['#04b404', '#ff0000'],
                    pieSliceText: 'percentage',
                    is3D: true,
                    fontSize: 16,
                    legend: {position: 'bottom'}
                };

                var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
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
                <h1>Dashboard</h1>
                </br>
                ${project.version}
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
                    </ul>
                </div>
            </div>
            <div class="main-content">
                <div class="row">
                    <div class="col-md-7">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i>Recente Transacties
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 225px;">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Datum</th>
                                                <th>Binnenkomend</th>
                                                <th>Uitgaand</th>
                                                <th>Vast/Variabel</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="transaction" items="${transactionList}">
                                                <tr>
                                                    <td>${transaction.datum}</td>
                                                    <td>
                                                        <script>
                                                            document.write(${transaction.incoming}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td style="color: #f00">
                                                        <script>
                                                            document.write(${transaction.outgoing}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td>
                                                        <script>
                                                            if (${transaction.repeating} !== 0) {
                                                                document.write("Herhalend");
                                                            } else {
                                                                document.write("Eenmalig");
                                                            }
                                                        </script>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i>Statistieken
                            </div>
                            <div class="widget-content">
                                <div id="piechart_3d" style="height: 225px;"</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div>
                    <a href="${pageContext.request.contextPath}/transaction/list" class="btn btn-action btn-lg btn-info"><i class="fa fa-euro"></i> Transacties</a>
                    <a href="${pageContext.request.contextPath}/categorie/list" class="btn btn-action btn-lg btn-info"><i class="fa fa fa-list"></i> Categorieën</a>
                    <a href="${pageContext.request.contextPath}/mortgage/list" class="btn btn-action btn-lg btn-info"><i class="fa fa-money"></i> Hypotheken</a>
                </div>
            </div>
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
            <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
            <script src='${pageContext.request.contextPath}/static/js/ad67372f4b8b70896e8a596720082ac6.js'></script>
            <script src='${pageContext.request.contextPath}/static/js/d7dfc13379a397356e42ab8bd98901a0.js'></script>
    </body>
</html>
