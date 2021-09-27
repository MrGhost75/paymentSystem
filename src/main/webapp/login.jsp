<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="authorizationTitle"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" media="screen">
</head>
<body>

<div class="slider-area">
    <div class="slider-bgLogin text-center">
        <div class="container">
            <div class="row">
                <div class="slidertext">
                    <h3><fmt:message key="authorizationTitle"/></h3>
                </div>
                <div class="table">
                    <div class="localRegistrationBlock">
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/registration"><fmt:message
                                key="registration"/></a>
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language//?language=RU">
                            <%--<img src="image/ru.png"/>--%>
                            RU
                        </a>
                        <a class="loginBtn" href="${pageContext.request.contextPath}/view/language//?language=EN">
                            <%--<img src="${pageContext.request.contextPath}image/en.png"/>--%>
                            EN
                        </a>
                    </div>
                    <form class="w3-container" align="center" method="post"
                          action="${pageContext.request.contextPath}/view/login">
                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="text" required
                                       placeholder="<fmt:message key="login"/>"
                                       name="login">
                            </label>
                        </p>

                        <p>
                            <label>
                                <input class="profileEditorFields loginField" type="password" required
                                       placeholder="<fmt:message key="password"/>"
                                       name="password">
                            </label>
                        </p>

                        <div>
                            <input class="btn editProfileBtn" id="loginSubmitBtn" type="submit"
                                   value="<fmt:message key="enter"/>">
                        </div>

                        <c:if test="${requestScope.notFound}">
                            <div class="w3-container">
                                <fmt:message key="invalidData"/>
                            </div>
                        </c:if>

                        <c:if test="${requestScope.wrongData}">
                            <div class="w3-container">
                                <fmt:message key="incorrectData"/>
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