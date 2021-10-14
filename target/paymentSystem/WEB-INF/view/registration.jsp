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
    <title><fmt:message key="registration"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>
<body>
<div class="slider-area">
    <div class="slider-bgLogin text-center">
        <div class="container">
            <div class="row">
                <div class="slidertext">
                    <h1><fmt:message key="registration"/></h1>
                    <br>
                </div>
                <div class="table" id="registerForm">
                    <div class="localRegistrationBlock">
                        <a class="loginBtn" href="${pageContext.request.contextPath}/"><fmt:message
                                key="login"/></a>
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/registration?language=RU">RU</a>
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language/registration?language=EN">EN</a>
                    </div>

                    <form class="w3-container" align="center" method="post"
                          action="${pageContext.request.contextPath}/view/registration">
                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="text" required placeholder="<fmt:message key="login"/>"
                                       name="login">
                            </label>
                        </p>

                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="text" required placeholder="Email" name="email">
                            </label>
                        </p>

                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="password" required
                                       placeholder="<fmt:message key="password"/>"
                                       name="password">
                            </label>
                        </p>


                        <input class="btn editProfileBtn" id="loginSubmitBtn" type="submit" value="<fmt:message key="registration"/>">
                        <c:if test="${requestScope.invalidData}">
                            <div class="w3-container">
                                <fmt:message key="invalidEmailOrPass"/>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.existUser}">
                            <div class="w3-container">
                                <fmt:message key="userAlreadyExists"/>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
