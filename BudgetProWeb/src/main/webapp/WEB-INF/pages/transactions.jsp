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
                            <li><a href="order_details.html#"><i class="fa fa-cog"></i> Account Instellingen</a></li>
                            <li><a href="order_details.html#"><i class="fa fa-power-off"></i> Uitloggen</a></li>
                        </ul>
                    </div>
                </div>
                <h1>Transacties</h1>
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
                <div class="row">
                    <div class="col-md-8">
                        <div class="widget widget-orange">
                            <div class="widget-title">
                                <i class="fa fa-group"></i> Transacties
                            </div>
                            <div class="widget-content">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Datum</th>
                                                <th>Binnenkomend</th>
                                                <th>Uitgaand</th>
                                                <th>Vast/Variabel</th>
                                                <th>Categorie</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  var="transaction" items="${user.transactions}">
                                                <tr>
                                                    <td>${transaction.datum}</td>
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
                                                    <td>
                                                        <script>
                                                            if (${transaction.repeating} > 0) {
                                                                document.write("Herhalend");
                                                            } else {
                                                                document.write("Eenmalig");
                                                            }
                                                        </script>
                                                    </td>
                                                    <td>${transaction.category.name}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/transaction/edit/${transaction.id}" class="btn btn-iconed btn-primary btn-xs"><i class="fa fa-search"></i>Details</a>
                                                        <a href="${pageContext.request.contextPath}/transaction/delete/${transaction.id}" class="btn btn-danger btn-xs"><i class="fa fa-times"></i>Verwijderen</a>
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
                                <i class="fa fa-group"></i>${formTitle}
                            </div>
                            <div class="widget-content">
                                <form:form method="POST" modelAttribute="transaction" action="${pageContext.request.contextPath}/transaction/add">
                                    <div class="form-group">
                                        <label control-label>ID</label>
                                        <form:input path="id" type="text" placeholder="${transaction.id}" class="form-control" readonly="true"/>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Categorie</label>
                                        <form:select path="category" class="form-control">
                                            <optgroup label="Ingaand"/>
                                            <c:forEach items="${incomingCat}" var="cat">
                                                <form:option label="${cat.name}" value="${cat.id}"/>
                                            </c:forEach>
                                            <optgroup label="Uitgaand"/>
                                            <c:forEach items="${outgoingCat}" var="cat">
                                                <form:option label="${cat.name}" value="${cat.id}"/>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Inkomend</label>
                                        <form:input path="incoming" type="text" placeholder="${transaction.incoming}" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Uitgaand</label>   
                                        <form:input path="outgoing" type="text" placeholder="${transaction.outgoing}" class="form-control" style="color : #f00" />
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Omschrijving</label>
                                        <form:textarea path="description" class="form-control" placeholder="${transaction.description}"/>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Herhaling</label>
                                        <div class="radio">
                                            <form:radiobutton path="repeating" name="herhaling" value="0" label=" Nooit" />
                                        </div>
                                        <div class="radio">
                                            <form:radiobutton path="repeating" name="herhaling" value="12" label=" Elke maand" />
                                        </div>
                                        <div class="radio">
                                            <form:radiobutton path="repeating" name="herhaling" value="4" label=" Elk kwartaal" />
                                        </div>
                                        <div class="radio">
                                            <form:radiobutton path="repeating" name="herhaling" value="2" label=" Elk half jaar" />
                                        </div>
                                        <div class="radio">
                                            <form:radiobutton path="repeating" name="herhaling" value="1" label=" Elk jaar" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label control-label>Datum</label>
                                        <form:input path="datum" type="date" value="${transaction.datum}" class="form-control"/>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:button type="submit" class="btn btn-primary">Opslaan</form:button>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <a href="${pageContext.request.contextPath}/transaction/list" class="btn  btn-warning">Annuleren</a>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src='${pageContext.request.contextPath}/static/js/ad67372f4b8b70896e8a596720082ac6.js'></script>
        <script src='${pageContext.request.contextPath}/static/js/d7dfc13379a397356e42ab8bd98901a0.js'></script>
    </body>
</html>
