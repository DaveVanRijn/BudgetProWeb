<!DOCTYPE html>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">



        <link rel='stylesheet' href='${pageContext.request.contextPath}/static/css/style.css'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

        <link href="${pageContext.request.contextPath}/static/assets/favicon.ico" rel="shortcut icon">
        <link href="${pageContext.request.contextPath}/static/assets/apple-touch-icon.png" rel="apple-touch-icon">

        <title>BudgetPro</title>
    </head>

    <body class="glossed">
        <div class="all-wrapper no-menu-wrapper light-bg">
            <div class="login-logo-w">
                <a href="/" class="logo">
                    <i class="fa fa-money"></i>
                </a>
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="widget widget-orange">
                        <div class="widget-title">
                            <h3 class="text-center"><i class="fa fa-lock"></i>Registreren</h3>
                        </div>
                        <div class="widget-content">
                            <form:form method="POST" modelAttribute="user" action="${pageContext.request.contextPath}/register" class="form-horizontal bottom-margin">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Rekeningnummer</label>
                                    <div class="col-md-8">
                                        <form:input path="accountnumber" type="text" placeholder="Rekeningnummer" class="form-control" required="true" pattern="[0-9]*"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Voornaam</label>
                                    <div class="col-md-8">
                                        <form:input path="firstname" type="text" placeholder="Voornaam" class="form-control" required="true" pattern="[a-zA-Z]*"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Tussenvoegsel</label>
                                    <div class="col-md-8">
                                        <form:input path="infix" type="text" placeholder="Tussenvoegsel" class="form-control" pattern="[a-z]*"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Achternaam</label>
                                    <div class="col-md-8">
                                        <form:input path="lastname" type="text" placeholder="Achternaam" class="form-control" required="true" pattern="[a-zA-Z]*"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Huidig budget</label>
                                    <div class="col-md-8">
                                        <form:input path="balance" type="text" placeholder="Huidig budget" class="form-control" required="true" pattern="\d+(\.\d{2})?"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <form:button type="submit" class="btn btn-primary btn-rounded btn-iconed">Registreren</form:button>
                                        </div>
                                    </div>
                                    <h5 style="color: red"><i class="fa"></i>${message}</h5>
                                </form:form>
                            <div class="no-account-yet">
                                Heb je al een account? <a href="${pageContext.request.contextPath}/">Log in!</a>
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