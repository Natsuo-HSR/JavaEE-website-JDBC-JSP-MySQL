<%--
  Created by IntelliJ IDEA.
  User: Лена
  Date: 07.12.2019
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello MSFCKR</title>
</head>
<body>
    <h1>TEST</h1>
    <p>
        <%@ page import="logic.TestClass" %>
        <%=
            new TestClass().getInfo()
        %>
    </p>
</body>
</html>
