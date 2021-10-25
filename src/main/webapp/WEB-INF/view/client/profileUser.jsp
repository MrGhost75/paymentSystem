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
    <title><fmt:message key="ProfileUser"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">

        <div class="logoBlockProfile">

            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/client/profileUser?language=RU">
                    RU
                </a>
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/client/profileUser?language=EN">
                    EN
                </a>
            </div>

            <p style="font-size: 16px; margin-left: 50px"><fmt:message key="profile"/></p>
            <p style="font-size: 16px; margin-left: 50px"> <fmt:message key="placeholderName"/>: <c:out value="${sessionScope.user.name}"/><p>
            <p style="font-size: 16px; margin-left: 50px"><fmt:message key="placeholderEmail"/>: ${sessionScope.user.email}</p>
            <c:if test="${sessionScope.user.activityStatus.equals('active')}">
                <p style="font-size: 16px; margin-left: 50px; color: #5ffc03"><fmt:message key="status"/>: <fmt:message key="activeUser"/></p>
            </c:if>
            <c:if test="${sessionScope.user.activityStatus.equals('not_active')}">
                <p style="font-size: 16px; margin-left: 50px; color: #ff0005"><fmt:message key="status"/>: <fmt:message key="blockedUser"/></p>
            </c:if>

            <div class="mainmenu">
                <ul>
                    <c:if test="${sessionScope.user.activityStatus.equals('active')}">
                        <li><a class="scroll-animite btn"
                            href="${pageContext.request.contextPath}/view/paymentMaking"><fmt:message
                            key="paymentMaking"/></a></li>
                    </c:if>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/paymentHistory"><fmt:message
                            key="paymentHistory"/></a></li>
                    <li><a class="scroll-animite btn"
                           href="${pageContext.request.contextPath}/view/client/mainPageUser"><fmt:message
                            key="backToMain"/></a></li>
                </ul>
            </div>
        </div>


    </header>
    <div class="slider-area">
        <div class="slider-bg text-center">
            <div class="container">
                <div class="row">
                    <c:if test="${sessionScope.user.activityStatus.equals('not_active')}">
                        <div class="col-lg-12">
                            <div class="slidertext">
                                <h1 style="color: #bf1b1b"><fmt:message key="youAreBlocked"/></h1>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.user.activityStatus.equals('active')}">
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
                                <th class="sorttable_numeric"><fmt:message key="balance"/></th>
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
                                               href="${pageContext.request.contextPath}/view/client/profileUser?command=block&Cid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="blockCardConfirm"/>')"><fmt:message key="block"/></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.getActivityStatus().equals('not_active')}">
                                        <td><a class="updateButton"
                                               href="${pageContext.request.contextPath}/view/client/profileUser?command=unblock&Cid=${item.getId()}"
                                               onclick="return confirm('<fmt:message key="unblockCardConfirm"/>')"><fmt:message key="unblock"/></a>
                                        </td>
                                    </c:if>
                                    <td>
                                        <a class="deleteButton"
                                           href="${pageContext.request.contextPath}/view/client/profileUser?command=delete&Cid=${item.getId()}"
                                           onclick="return confirm('<fmt:message key="deleteCardConfirm"/>')"><fmt:message key="delete"/></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    </c:if>
                </div>
                <c:if test="${sessionScope.user.activityStatus.equals('active')}">
                    <div class="mainmenu" style="margin: unset; text-align: center">
                        <ul>
                            <li><a class="scroll-animite btn"
                                   href="${pageContext.request.contextPath}/view/addingCreditCard"><fmt:message
                                    key="addCard"/></a></li>
                            <li><a class="scroll-animite btn"
                                   href="${pageContext.request.contextPath}/view/balanceReplenishment"><fmt:message
                                    key="balanceTopUp"/></a></li>
                        </ul>
                    </div>
                </c:if>
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