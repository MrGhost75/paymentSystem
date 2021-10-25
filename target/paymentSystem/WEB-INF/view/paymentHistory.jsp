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
    <title><fmt:message key="paymentHistory"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">

        <div class="logoBlockProfile">
            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/paymentHistory?language=RU">
                    RU
                </a>
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/paymentHistory?language=EN">
                    EN
                </a>
            </div>
            <p style="font-size: 16px; margin-left: 50px;"> <fmt:message key="placeholderName"/>: ${sessionScope.user.name}</p>
            <p style="font-size: 16px; margin-left: 50px;"><fmt:message key="placeholderEmail"/>: ${sessionScope.user.email}</p>
            <div class="mainmenu">
                <ul>
                    <c:if test="${sessionScope.user.role.equals('admin')}">
                        <li><a class="scroll-animite btn"
                               href="${pageContext.request.contextPath}/view/admin/profileAdmin"><fmt:message key="goBack"/></a></li>
                    </c:if>
                    <c:if test="${sessionScope.user.role.equals('user')}">
                        <li><a class="scroll-animite btn"
                               href="${pageContext.request.contextPath}/view/admin/profileUser"><fmt:message key="goBack"/></a></li>
                    </c:if>

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
                            <h1><fmt:message key="paymentHistory"/></h1>
                        </div>
                    </div>
                    <div class="table" id="paymentsTable">
                        <table class="table1 sortable">
                            <thead>
                            <tr>
                                <th class="sorttable_alpha"><fmt:message key="amount"/></th>
                                <th class="sorttable_alpha"><fmt:message key="date"/></th>
                                <th class="sorttable_alpha"><fmt:message key="description"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${payments}" var="item" varStatus="status">
                                <tr class="highlight">
                                    <td>${item.getAmount()}</td>
                                    <td>${item.getDate()}</td>
                                    <td>${item.getDescription()}</td>
                            </c:forEach>
                            </tbody>
                        </table>

                        <form class="w3-container" align="center" method="post"
                              action="${pageContext.request.contextPath}/view/paymentHistory">

                            <p>
                                <label>
                                    <input class="profileEditorFields loginField" type="text" required
                                           placeholder="<fmt:message key="beginDate"/>"
                                           name="beginDate">
                                </label>
                            </p>

                            <p>
                                <label>
                                    <input class="profileEditorFields loginField" type="text" required
                                           placeholder="<fmt:message key="endDate"/>"
                                           name="endDate">
                                </label>
                            </p>

                            <input class="btn editProfileBtn" id="loginSubmitBtn" type="submit" value="<fmt:message key="sortDates"/>">

                            <c:if test="${requestScope.invalidDate}">
                                <div class="w3-container">
                                    <fmt:message key="invalidDate"/>
                                </div>
                            </c:if>

                            <c:if test="${requestScope.wrongDate}">
                                <div class="w3-container">
                                    <fmt:message key="wrongDate"/>
                                </div>
                            </c:if>

                        </form>

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
