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
    <title><fmt:message key="PSAdmin1"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">

        <div class="logoBlock">
            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/admin/mainPageAdmin?language=RU">
                    RU
                </a>
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/admin/mainPageAdmin?language=EN">
                    EN
                </a>
            </div>
            <p id="pageLogo">PaymentSystemAdmin</p>
            <div class="mainmenu">
                <ul>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/profileAdmin"><fmt:message
                            key="profile"/></a></li>
                    <li><a class="scroll-animite logOutBtn"
                           href="${pageContext.request.contextPath}/view/logout"><fmt:message key="logout"/></a></li>
                </ul>
            </div>
        </div>

    </header>
    <div class="slider-area">
        <div class="slider-bg text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="slidertext">
                            <h1><fmt:message key="clients"/></h1>
                            <br>
                            <p><fmt:message key="clientsPageInfo"/></p>
                        </div>
                    </div>
                    <div class="table" id="clientsTable">
                        <table class="table1 sortable">
                            <thead>
                            <tr>
                                <th class="sorttable_alpha"><fmt:message key="placeholderName"/></th>
                                <th class="sorttable_alpha"><fmt:message key="placeholderEmail"/></th>
                                <th class="sorttable_alpha"><fmt:message key="status"/></th>
                                <th><fmt:message key="setStatus"/></th>
                                <th><fmt:message key="delete"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${users}" var="item" varStatus="status">
                                <tr class="highlight">
                                    <td>${item.getName()}</td>
                                    <td>${item.getEmail()}</td>
                                    <c:if test="${item.getActivityStatus().equals('active')}">
                                        <td>
                                            <p style="color: green"><fmt:message key="activeUser"/></p>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('not_active')}">
                                        <td>
                                            <p style="color: red"><fmt:message key="blockedUser"/></p>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('active')}">
                                        <td><a class="deleteButton"
                                               href="${pageContext.request.contextPath}/view/admin/mainPageAdmin?command=block&Uid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="blockClientConfirm"/>')"><fmt:message key="block"/></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('not_active')}">
                                        <td><a class="updateButton"
                                               href="${pageContext.request.contextPath}/view/admin/mainPageAdmin?command=unblock&Uid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="unblockClientConfirm"/>')"><fmt:message key="unblock"/></a>
                                        </td>
                                    </c:if>
                                    <td>
                                        <a class="deleteButton"
                                           href="${pageContext.request.contextPath}/view/admin/mainPageAdmin?command=delete&Uid=${item.getId()}"
                                           onclick="return confirm('<fmt:message key="deleteClientConfirm"/>')"><fmt:message key="delete"/></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="time"><span id="datetime"></span></div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/styles/js/time.js"></script>
<script src="${pageContext.request.contextPath}/styles/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/styles/js/rowLight.js"></script>
<script src="${pageContext.request.contextPath}/styles/js/sorttable.js"></script>


</body>
</html>