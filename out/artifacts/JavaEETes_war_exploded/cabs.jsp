<%--
  Created by IntelliJ IDEA.
  User: Лена
  Date: 21.12.2019
  Time: 1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    boolean isCookie = false;
    String name = "";
    Cookie[] cookies = request.getCookies();
    String cookieName = "user";
    Cookie cookie = null;
    if(cookies !=null) {
        for(Cookie c: cookies) {
            if(cookieName.equals(c.getName())) {
                cookie = c;
                name = cookie.getValue();
                if (name != "") {
                    //System.out.println("Name: " + cookie.getValue());
                    isCookie = true;
                }
                break;
            }
        }
    }

    //System.out.println(isCookie);
    String id = "not";
    String cookieName1 = "id";
    Cookie cookie1 = null;
    if(cookies !=null) {
        for(Cookie c: cookies) {
            if(cookieName1.equals(c.getName())) {
                cookie1 = c;
                id = cookie1.getValue();
                if (id != "") {
                    System.out.println("ID: " + cookie1.getValue());
                    //isCookie = true;
                }
                break;
            }
        }
    }

    boolean isAdmin = false;
    String role = "user";
    String cookieName2 = "role";
    Cookie cookie2 = null;
    if(cookies !=null) {
        for(Cookie c: cookies) {
            if(cookieName2.equals(c.getName())) {
                cookie2 = c;
                role = cookie2.getValue();
                if (role != "") {
                    System.out.println("ROLE: " + cookie2.getValue());
                    if (role.equals("admin")) isAdmin = true;
                }
                break;
            }
        }
    }
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title></title>
    <style>
        <%@include file='css/catalog.css' %>
    </style>
    <script>
        function postToUrl(path, params, method) {
            method = method || "post"; // Устанавливаем метод отправки.

            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", path);
            for(var key in params) {
                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", key);
                hiddenField.setAttribute("value", params[key]);

                form.appendChild(hiddenField);
            }

            document.body.appendChild(form);
            form.submit();
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

                    <input type="hidden" name="url" value="/appartslist">
                    <c:choose>
                        <c:when test="<%=isAdmin%>">
                            <a href="#" onclick="postToUrl('cabs',{'adminName':'<%=name%>'},'POST');">личный кабинет &nbsp;</a>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                    <text>Здравствуйте <%=name%> &nbsp;</text>

                </form>
            </c:when>
        </c:choose>


    </div>
</header>

<br><br><br>
<br>
<table>
    <tr>
        <th>Логин</th>
        <th>Недвижимость</th>
    </tr>
    <c:forEach var="admin" items="${admins}">
    <tr>
        <td><c:out value="${admin.clientName}"/></td>
        <td><c:out value="${admin.appName}"/></td>
    </tr>
    </c:forEach>
</table>

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
