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
        <%@include file='css/main.css' %>
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
    <h1>Сайт недвижимости</h1>
    <h3>Пам пам пам</h3>
    <br>
    <c:choose>
        <c:when test="<%=isCookie%>">
            <a href="#" onclick="postToUrl('appartslist','POST');">Перейти в каталог</a>
        </c:when>
        <c:otherwise>
            <h3>Чтобы продолжить войдите или зарегестрируйтесь</h3>
        </c:otherwise>
    </c:choose>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    function slowScroll(id) {
        $('html, body').animate({
            scrollTop: $(id).offset().top
        }, 500);
    }

    $(document).on("scroll", function () {
        if($(window).scrollTop() === 0)
            $("header").removeClass("fixed");
        else
            $("header").attr("class", "fixed");
    });
</script>
</body>
</html>
