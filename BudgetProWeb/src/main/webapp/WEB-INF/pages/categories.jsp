<%-- 
    Document   : categories
    Created on : 9-dec-2015, 15:53:58
    Author     : Dave van Rijn, Student 500714558, Klas IS202
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head meta charset="utf-8">
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
                    <h1>Categorieën</h1>
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
                    <div class="col-md-8">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Categorieën
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive" style="height: 395px; overflow: auto;">
                                    <table id="catTable" class="table">
                                        <thead>
                                            <tr>
                                                <th>Naam</th>
                                                <th>Ingaand/Uitgaand</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="category" items="${user.categories}">
                                                <tr id="row${category.id}">
                                                    <td>${category.name}</td>
                                                    <td>
                                                        <script>
                                                            if (${category.incoming}) {
                                                                document.write("Ingaand");
                                                            } else {
                                                                document.write("Uitgaand");
                                                            }
                                                        </script>
                                                    </td>
                                                    <td>
                                                        <button onclick="detailCat(${category.id})" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-pencil"></i>Bewerken</button>
                                                        <button onclick="deleteCat(${category.id})" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Details
                            </div>
                            <div class="widget-content">
                                <form method="POST" action="JavaScript:addCat()" >
                                    <div class="form-group">
                                        <label control-label>ID</label>
                                        <input id="id" type="text" class="form-control" readonly="true" value="0">
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Naam</label>
                                        <input id="name" type="text" class="form-control" placeholder="Naam">
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Ingaand/Uitgaand</label>
                                        <div class="radio">
                                            <input id="incoming" type="radio" name="inOut" value="1"> Ingaand
                                        </div>
                                        <div class="radio">
                                            <input id="outgoing" type="radio" name="inOut" value="0"> Uitgaand
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>Opslaan</button>
                                    </div>
                                    <div class="form-group">
                                        <a onclick="resetForm()" class="btn  btn-danger"><i class="fa fa-times"></i>Annuleren</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src='${pageContext.request.contextPath}/static/assets/js/Categories.js'></script>
        <script src='${pageContext.request.contextPath}/static/js/ad67372f4b8b70896e8a596720082ac6.js'></script>
        <script src='${pageContext.request.contextPath}/static/js/d7dfc13379a397356e42ab8bd98901a0.js'></script>
        <script type="text/javascript">
                                            $(document).ready(function () {
                                                document.getElementById("outgoing").checked = true;
                                            });
        </script>
    </body>
</html>
