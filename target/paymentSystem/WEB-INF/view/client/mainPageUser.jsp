<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.language ? param.language : pageContext.request.locale}"
       scope="application"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="java">
<meta charset="utf-8">

<head>
    <title><fmt:message key="PSUser1"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">

        <div class="logoBlock">
            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/client/mainPageUser?language=RU">
                    RU
                </a>
                <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/client/mainPageUser?language=EN">
                    EN
                </a>
            </div>
            <p id="pageLogo">PaymentSystemUser</p>
            <div class="mainmenu">
                <ul class="topChange">
                    <li><a class="btn"
                           href="${pageContext.request.contextPath}/view/client/profileUser"><fmt:message
                            key="profile"/></a></li>

                    <li><a class="logOutBtn" href="${pageContext.request.contextPath}/view/logout"><fmt:message
                            key="logout"/></a></li>
                </ul>
            </div>
        </div>
        <div id="time"><span id="datetime"></span></div>
        <script src="${pageContext.request.contextPath}/styles/js/time.js"></script>

    </header>
</div>
</body>
</html>