
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Лена
  Date: 09.12.2019
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean isCookie = false;
    String name = null;
    Cookie[] cookies = request.getCookies();
    String cookieName = "user";
    Cookie cookie = null;
    if(cookies !=null) {
        for(Cookie c: cookies) {
            if(cookieName.equals(c.getName())) {
                cookie = c;
                name = cookie.getValue();
                //System.out.println("NAME = " + name);
                if (name != "") {
                    //System.out.println("Name: " + cookie.getValue());
                    isCookie = true;
                    break;
                }
            }
        }
    }
    //System.out.println(isCookie);
%>
<html>
<head>
    <title></title>
    <style>
        <%@include file='css/info.css' %>
    </style>
    <script>
        function postToUrl(path, method) {
            method = method || "post"; // Устанавливаем метод отправки.

            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", path);
            /*for(var key in params) {
                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", key);
                hiddenField.setAttribute("value", params[key]);

                form.appendChild(hiddenField);
            }*/

            document.body.appendChild(form);
            form.submit();
        }
    </script>
    <script>
        function OnSubmitForm() {
            if(document.pressed == 'Registration') {
                document.myform.action ="/regcustomer";
            } else if(document.pressed == 'Sign Up') {
                document.myform.action ="/processcustomer";
            }
            return true;
        }
    </script>
</head>
<body>
<header>
    <div id="logo" onclick="slowScroll('#top')">
        <span>Buildings.com</span>

        <c:choose>
            <c:when test="<%=isCookie%>">
                <form method="post">
                    <input type="submit" id="butt" value="Выйти" onclick="form.action='${pageContext.request.contextPath}/clear'">
                    <input type="hidden" name="url" value="/">
                    <text>Здравствуйте <%=name%> &nbsp;</text>
                </form>
            </c:when>
            <c:otherwise>
                <form method="post">
                    <input type="submit" id="butt" value="Sign Up" onclick="form.action='${pageContext.request.contextPath}/processcustomer'">
                    <input type="submit" id="butt" value="Registration" onclick="form.action='${pageContext.request.contextPath}/regcustomer'">
                    <input type="password" name="password" id="password" placeholder="password here" value="${password}" size="10">
                    <input type="hidden" name="url" value="http://localhost:8080/JavaEETes_war_exploded/">
                    <input type="text" name="login" id="login" placeholder="login here" value="${login}" size="7">
                </form>
            </c:otherwise>
        </c:choose>


    </div>
</header>



<div id="top">
    <div id="text">
    <h3>Подробнее о: <%=request.getAttribute("appname")%></h3>
    </div>
    <h5>План</h5>
    <img src="data:image/png;base64,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" width="300px", height="320px" align="center">
    <h4>Фото</h4>
    <img src="data:image/jpeg;base64,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" width="300px", height="220px" align="center">


</div>

</body>
</html>
