<%--
  Created by IntelliJ IDEA.
  User: kubreg
  Date: 05.03.2016
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}"/>
    <input type="text" name="description" value="${meal.description}"/>
    <br>
    <input type="datetime-local" name="dateTime" value="${meal.dateTime}}"/>
    <br>
    <input type="number" name="calories" value="${meal.calories}"/>
    <br>
    <input type="submit" value="Save"/>
</form>
</body>
</html>
