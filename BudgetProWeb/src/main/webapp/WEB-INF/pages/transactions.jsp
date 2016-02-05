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
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src='${pageContext.request.contextPath}/static/assets/js/Transactions.js'></script>
        <script src='${pageContext.request.contextPath}/static/js/ad67372f4b8b70896e8a596720082ac6.js'></script>
        <script src='${pageContext.request.contextPath}/static/js/d7dfc13379a397356e42ab8bd98901a0.js'></script>
        <script type="text/javascript">
            $(document).ready(function () {
                document.getElementById("noRep").checked = true;
            });
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
                    <h1>Transacties</h1>
                </div>
                <div class="col-md-2">
                    <h1 id="balance">€
                        <script>
                            document.write(${user.balance}.toFixed(2))
                        </script>
                    </h1>
                </div>
                <div class="col-md-3">
                    <h4 id="lastDate">Laatste transactie: ${lastDate}</h4>
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
                    <div class="col-md-8">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Transacties
                                <div class="form-switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" onclick="changeTable(this)" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" checked>
                                        <label class="onoffswitch-label" for="myonoffswitch">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 395px; overflow: auto;">
                                    <table id="tranTable" class="table">
                                        <thead>
                                            <tr>
                                                <th>Datum</th>
                                                <th>Binnenkomend</th>
                                                <th>Uitgaand</th>
                                                <th>Categorie</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="transaction" items="${transactionList}">
                                                <tr id="row${transaction.id}" data-val="${transaction.datum}">
                                                    <td id="date${transaction.id}">${transaction.datum}</td>
                                                    <td>
                                                        <script>
                                                            document.write(${transaction.incoming}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td style="color : #f00">
                                                        <script>
                                                            document.write(${transaction.outgoing}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td>${transaction.category.name}</td>
                                                    <td>
                                                        <button onclick="toForm(${transaction.id})" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</button>
                                                        <button onclick="deleteT(${transaction.id})" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="col-md-offset-5">
                                        <a id="more" href="" class="btn btn-success">Meer laden...</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Herhalende Transacties
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 395px; overflow: auto;">
                                    <table id="tranRepTable" class="table">
                                        <thead>
                                            <tr>
                                                <th>Datum</th>
                                                <th>Binnenkomend</th>
                                                <th>Uitgaand</th>
                                                <th>Categorie</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="transaction" items="${repeatingList}">
                                                <tr id="row${transaction.id}" data-val="${transaction.datum}">
                                                    <td id="date${transaction.id}">${transaction.datum}</td>
                                                    <td>
                                                        <script>
                                                            document.write(${transaction.incoming}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td style="color : #f00">
                                                        <script>
                                                            document.write(${transaction.outgoing}.toFixed(2));
                                                        </script>
                                                    </td>
                                                    <td>${transaction.category.name}</td>
                                                    <td>
                                                        <button onclick="toForm(${transaction.id})" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</button>
                                                        <button onclick="deleteT(${transaction.id})" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="col-md-offset-5">
                                        <a id="moreRep" href="" class="btn btn-success">Meer laden...</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Details
                            </div>
                            <div id="form" class="widget-content">
                                <form method="POST" action="JavaScript:addT()">
                                    <div class="form-group">
                                        <label contro-label>ID</label>
                                        <input id="id" type="text" class="form-control" readonly="true" value="0">
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Categorie</label>
                                        <select id="category" class="form-control">
                                            <optgroup label="Uitgaand">
                                                <c:forEach items="${outgoingCat}" var="cat">
                                                    <option value="${cat.id}">${cat.name}</option>
                                                </c:forEach>
                                            </optgroup>
                                            <optgroup label="Ingaand">
                                                <c:forEach items="${incomingCat}" var="cat">
                                                    <option value="${cat.id}">${cat.name}</option>
                                                </c:forEach>
                                            </optgroup>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Ingaand</label>
                                        <input id="incoming" type="number" step="0.01" min="0.00" class="form-control" placeholder="0.00">
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Uitgaand</label>
                                        <input id="outgoing" type="number" step="0.01" min="0.00" class="form-control" style="color: #f00" placeholder="0.00">
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Omschrijving</label>
                                        <textarea id="description" class="form-control" placeholder="Omschrijving"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Herhaling</label>
                                        <div class="radio">
                                            <input id="noRep" type="radio" name="herhaling" value="0"> Eenmalig
                                        </div>
                                        <div class="radio">
                                            <input id="rep12" type="radio" name="herhaling" value="12"> Elke maand
                                        </div>
                                        <div class="radio">
                                            <input id="rep4" type="radio" name="herhaling" value="4"> Elk kwartaal
                                        </div>
                                        <div class="radio">
                                            <input id="rep2" type="radio" name="herhaling" value="2"> Elk half jaar
                                        </div>
                                        <div class="radio">
                                            <input id="rep1" type="radio" name="herhaling" value="1"> Elk jaar
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Datum</label>
                                        <input id="date" type="date" class="form-control" value="${date}">
                                    </div>
                                    <div class="form-group">
                                        <button id="sub" title="" type="submit" class="btn btn-primary"><i class="fa fa-save"></i>Opslaan</button>
                                    </div>
                                    <div class="form-group">
                                        <a onclick="resetForm()" class="btn btn-danger"><i class="fa fa-times"></i>Annuleren</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>
