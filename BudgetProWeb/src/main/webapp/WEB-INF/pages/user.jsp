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
                <h1>Gebruiker</h1>
            </div>
            <div class="side">
                <div class="sidebar-wrapper">
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/dashboard" data-toggle="tooltip" data-placement="right" title="Startpagina" data-original-title="Startpagina">
                                <i class="fa fa-home"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/transaction/list" data-toggle="tooltip" data-placement="right" title="Transacties" data-original-title="Transacties">
                                <i class="fa fa-map-marker"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/category/list" data-toggle="tooltip" data-placement="right" title="Categorieën" data-original-title="Categorieën">                                
                                <i class="fa fa-group"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}" data-toggle="tooltip" data-placement="right" title="" data-original-title="Startpagina">
                                <i class="fa fa-pencil"></i>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}" data-toggle="tooltip" data-placement="right" title="" data-original-title="Startpagina">
                                <i class="fa fa-flash"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main-content">
                <div class="col-md-offset-4 col-md-4">
                    <div class="widget widget-orange">
                        <div class="widget-title">
                            <i class="fa fa-group"></i>Gebruiker
                        </div>
                        <div class="widget-content">
                            <form:form method="POST" modelAttribute="user" action="${pageContext.request.contextPath}/user/">
                                <div class="form-group">
                                    <label control-label>Accountnumber</label>
                                    <form:input path="accountnumber" type="text" class="form-control" readonly="true"/>
                                </div>
                                <div class="form-group">
                                    <label control-label>Voornaam</label>
                                    <form:input path="firstname" type="text" class="form-control" required="true"/>
                                </div>
                                <div class="form-group">
                                    <label control-label>Tussenvoegsel</label>
                                    <form:input path="infix" type="text" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <label control-label>Achternaam</label>
                                    <form:input path="lastname" type="text" class="form-control" required="true"/>
                                </div>
                                <div class="form-group">
                                    <label control-label>Saldo</label>
                                    <form:input path="balance" type="number" step="0.01" class="form-control" required="true"/>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <form:button type="submit" class="btn btn-primary col-md-4 col-md-offset-4">Opslaan</form:button>
                                        </div>
                                    </div>
                                        </br>
                                        <div class="row">
                                            <div class="form-group">
                                                <a onclick="return confirm('Weet je zeker dat je je account wil verwijderen?')" href="${pageContext.request.contextPath}/user/delete/${user.accountnumber}" class="btn btn-danger col-md-4 col-md-offset-4"><i class="fa fa-times"></i>Verwijderen</a>
                                            </div>
                                        </div>
                            </form:form>
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
