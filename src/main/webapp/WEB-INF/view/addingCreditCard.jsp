<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<html>
<meta charset="utf-8">

<head>
    <title><fmt:message key="addCard"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>
<body>
<div class="slider-area">
    <div class="slider-bgLogin text-center">
        <div class="container">
            <div class="row">
                <div class="slidertext">
                    <h1><fmt:message key="addCard"/></h1>
                    <br>
                </div>
                <div class="table" id="registerForm">
                    <div class="localRegistrationBlock">

                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/addingCreditCard?language=RU">RU</a>
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/addingCreditCard?language=EN">EN</a>
                    </div>

                    <form class="w3-container" align="center" method="post"
                          action="${pageContext.request.contextPath}/view/addingCreditCard">
                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="text" required placeholder="<fmt:message key="placeholderName"/>"
                                       name="cardName">
                            </label>
                        </p>

                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="password" required
                                       placeholder="<fmt:message key="pincode"/>"
                                       name="pincode">
                            </label>
                        </p>


                        <input class="btn editProfileBtn" id="loginSubmitBtn" type="submit" value="<fmt:message key="accept"/>">

                        <c:if test="${requestScope.invalidPin}">
                            <div class="w3-container">
                                <fmt:message key="invalidPin"/>
                            </div>
                        </c:if>

                        <c:if test="${requestScope.existCard}">
                            <div class="w3-container">
                                <fmt:message key="existCard"/>
                            </div>
                        </c:if>

                    </form>
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
    </div>
</div>
</body>
</html>