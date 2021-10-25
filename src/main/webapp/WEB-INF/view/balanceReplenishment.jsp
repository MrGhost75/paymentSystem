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
    <title><fmt:message key="balanceTopUp"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>

<body style="zoom: 80%">
<div id="home"></div>
<div class="ournet-inter-area">
    <header id="header" class="header-area">
        <div class="logoBlockProfile">
            <div style="font-size: 16px; text-align: end;">
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/balanceReplenishment?language=RU">
                    RU
                </a>
                <a class="loginBtn"
                   href="${pageContext.request.contextPath}/view/language/balanceReplenishment?language=EN">
                    EN
                </a>
            </div>
            <p id="pageLogo" style="font-size: 26px; text-align: center; margin-bottom: 20px"><fmt:message key="balanceTopUp"/></p>
        </div>
    </header>
    <div class="slider-area">
        <div class="slider-bg text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="slidertext">
                            <h1><fmt:message key="availableCreditCards"/></h1>
                        </div>
                    </div>
                    <div class="table" id="cardsTable">
                        <table class="table1 sortable">
                            <thead>
                            <tr>
                                <th class="sorttable_numeric">id</th>
                                <th class="sorttable_alpha"><fmt:message key="placeholderName"/></th>
                                <th class="sorttable_numeric"><fmt:message key="balance"/></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${creditCards}" var="item" varStatus="status">
                                <tr class="highlight">
                                    <td>${item.getId()}</td>
                                    <td>${item.getName()}</td>
                                    <td>${item.getBalance()}</td>
                                    <c:if test="${item.equals(pickedCard)}">
                                        <td><a class="deleteButton"
                                               href="${pageContext.request.contextPath}/view/balanceReplenishment?command=unpick&Cid=${item.getId()}"><fmt:message key="unpickCard"/></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${!item.equals(pickedCard)}">
                                        <td><a class="updateButton"
                                               href="${pageContext.request.contextPath}/view/balanceReplenishment?command=pick&Cid=${item.getId()}"><fmt:message key="chooseCard"/></a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="mainmenu" style="margin: unset; text-align: center">
                    <c:if test="${pickedCard != null}">
                        <form class="w3-container" align="center" method="post"
                              action="${pageContext.request.contextPath}/view/balanceReplenishment?command=pick&Cid=${pickedCard.getId()}">

                            <p>
                                <label>
                                    <input class="profileEditorFields loginField" type="password" required
                                           placeholder="<fmt:message key="pincode"/>"
                                           name="pincode">
                                </label>
                            </p>

                            <p>
                                <label>
                                    <input class="profileEditorFields loginField" type="text" required
                                           placeholder="<fmt:message key="amount"/>"
                                           name="paymentAmount">
                                </label>
                            </p>


                            <input class="btn editProfileBtn" id="loginSubmitBtn" type="submit" value="<fmt:message key="topUp"/>">

                            <c:if test="${requestScope.wrongPin}">
                                <div class="w3-container">
                                    <fmt:message key="wrongPin"/>
                                </div>
                            </c:if>

                            <c:if test="${requestScope.invalidData}">
                                <div class="w3-container">
                                    <fmt:message key="invalidAmount"/>
                                </div>
                            </c:if>

                            <c:if test="${requestScope.negativeAmount}">
                                <div class="w3-container">
                                    <fmt:message key="negativeAmount"/>
                                </div>
                            </c:if>

                        </form>
                    </c:if>
                    <c:if test="${sessionScope.user.role.equals('admin')}">
                        <li><a class="scroll-animite logOutBtn"
                               href="${pageContext.request.contextPath}/view/admin/profileAdmin"><fmt:message key="cancel"/></a></li>
                    </c:if>
                    <c:if test="${sessionScope.user.role.equals('user')}">
                        <li><a class="scroll-animite logOutBtn"
                               href="${pageContext.request.contextPath}/view/client/profileUser"><fmt:message key="cancel"/></a></li>
                    </c:if>
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