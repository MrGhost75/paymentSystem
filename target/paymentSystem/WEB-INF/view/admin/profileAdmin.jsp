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
    <title><fmt:message key="ProfileAdmin"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">

        <div class="logoBlock2">
            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/admin/profileAdmin?language=RU">
                    RU
                </a>
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/admin/profileAdmin?language=EN">
                    EN
                </a>
            </div>
            <p><fmt:message key="profile"/></p>
            <p><fmt:message key="placeholderName"/>: ${sessionScope.user.name}</p>
            <p><fmt:message key="placeholderEmail"/>: ${sessionScope.user.email}</p>
            <c:if test="${sessionScope.user.activityStatus.equals('active')}">
                <p class="activeStatus"><fmt:message key="status"/>: <fmt:message key="activeUser"/></p>
            </c:if>
            <c:if test="${sessionScope.user.activityStatus.equals('not_active')}">
                <p class="blockedStatus"><fmt:message key="status"/>: <fmt:message key="blockedUser"/></p>
            </c:if>
            <div class="mainmenu">
                <ul>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/paymentMakingAdmin"><fmt:message
                            key="paymentMaking"/></a></li>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/paymentHistoryAdmin"><fmt:message
                            key="paymentHistory"/></a></li>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/mainPageAdmin"><fmt:message key="backToMain"/></a></li>
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
                            <h1><fmt:message key="creditCards"/></h1>
                        </div>
                    </div>
                    <div class="table" id="cardsTable">
                        <table class="table1 sortable">
                            <thead>
                            <tr>
                                <th class="sorttable_numeric">id</th>
                                <th class="sorttable_alpha"><fmt:message key="placeholderName"/></th>
                                <th class="sorttable_aplha"><fmt:message key="balance"/></th>
                                <th class="sorttable_alpha"><fmt:message key="status"/></th>
                                <th><fmt:message key="setStatus"/></th>
                                <th><fmt:message key="delete"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${creditCards}" var="item" varStatus="status">
                                <tr class="highlight">
                                    <td>${item.getId()}</td>
                                    <td>${item.getName()}</td>
                                    <td>${item.getBalance()}</td>
                                    <c:if test="${item.getActivityStatus().equals('active')}">
                                        <td>
                                            <p style="color: green"><fmt:message key="activeCard"/></p>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('not_active')}">
                                        <td>
                                            <p style="color: red"><fmt:message key="blockedCard"/></p>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('active')}">
                                        <td><a class="deleteButton"
                                               href="${pageContext.request.contextPath}/view/admin/profileAdmin?command=block&Cid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="blockCardConfirm"/>')"><fmt:message key="block"/></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('not_active')}">
                                        <td><a class="updateButton"
                                               href="${pageContext.request.contextPath}/view/admin/profileAdmin?command=unblock&Cid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="unblockCardConfirm"/>')"><fmt:message key="unblock"/></a>
                                        </td>
                                    </c:if>
                                    <td>
                                        <a class="deleteButton"
                                           href="${pageContext.request.contextPath}/view/admin/profileAdmin?command=delete&Cid=${item.getId()}"
                                           onclick="return confirm('<fmt:message key="deleteCardConfirm"/>')"><fmt:message key="delete"/></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <ul>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/createCardAdmin"><fmt:message
                            key="addCard"/></a></li>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/admin/balanceTopUpAdmin"><fmt:message
                            key="balanceTopUp"/></a></li>
                </ul>
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