<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kubreg
  Date: 05.03.2016
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal List</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
        .green{color: lightseagreen}
        .red{color: red}
    </style>
</head>
<body>
<h2>Meal List</h2>
<c:if test="${not empty mealList}">
    <table>
        <thead>
        <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Calories</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealList}" var="meal">
            <tr class="${meal.exceed ? 'green' : 'red'}">
                <th><c:out value="${meal.description}" /></th>
                <th><c:out value="${meal.dateTime}" /></th>
                <th><c:out value="${meal.calories}"/></th>
                <th><a href="meals?action=update&id=${meal.id}">Edit</a>  <a href="meals?action=delete&id=${meal.id}">Delete</a></th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
