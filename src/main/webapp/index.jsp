<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <script type="text/javascript" charset="utf8" src="scripts/jquery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="styles/bootstrap.min.css">
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="kk" ${language == 'kk' ? 'selected' : ''}>Қазақ</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
</body>
</html>