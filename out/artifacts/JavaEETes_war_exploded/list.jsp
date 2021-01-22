<%--
  Created by IntelliJ IDEA.
  User: Лена
  Date: 14.12.2019
  Time: 0:22
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
            <c:otherwise>
                <form method="post">
                    <input type="submit" id="butt" value="Sign Up" onclick="form.action='${pageContext.request.contextPath}/processcustomer'">
                    <input type="submit" id="butt" value="Registration" onclick="form.action='${pageContext.request.contextPath}/regcustomer'">
                    <input type="password" name="password" id="password" placeholder="password here" value="${password}" size="10">
                    <input type="text" name="login" id="login" placeholder="login here" value="${login}" size="7">
                    <input type="hidden" name="url" value="http://localhost:8080/JavaEETes_war_exploded/list.jsp">
                </form>
            </c:otherwise>
        </c:choose>


    </div>
</header>

<br><br><br><br><br><br><br>
<div id="mainpanel">
    <div id="filter">
        <form method="post">
            <text>Тип: </text>
        <select name="type">
            <option>Тип</option>
            <option>Дом</option>
            <option>Студия</option>
            <option>1 ком. квартира</option>
            <option>2-х ком. квартира</option>
            <option>3-х ком. квартира</option>
            <option>4-х ком. квартира</option>
        </select>
            <text>Цена(от-до): </text>
            <input type="text" name="costfrom" id="costfrom" placeholder="0" value="${login}" size="4">
            <input type="text" name="costto" id="costto" placeholder="9999999" value="${login}" size="4">

            <text>Площадь(от-до): </text>
            <input type="text" name="squarefrom" id="squarefrom" placeholder="0" value="${login}" size="3">
            <input type="text" name="squareto" id="squareto" placeholder="999" value="${login}" size="3">
            &nbsp;&nbsp;
            <input type="submit" id="butt" value="Показать" onclick="form.action='${pageContext.request.contextPath}/appartslist'">
            <input type="hidden" name="filter" value="true">
        </form>
    </div>


    <div id="prod-list">

            <c:forEach var="appart" items="${apparts}">
            <div class="product-wrap">
                <div id="product">
                    <div class="product-item">
                        <img src="<c:out value="${appart.URL}"/>"/>
                        <div class="product-buttons">
                            <a href="#" class="button"
                               onclick="postToUrl('appartslink',{'id':'<c:out value="${appart.id}"/>', 'uid':'<%=id%>'},'POST');">Купить</a>
                        </div>
                    </div>
                    <div class="product-title">
                        <a href="#" onclick="postToUrl('appartsinfo',{'id':'<c:out value="${appart.id}"/>', 'uid':'<%=id%>'},'POST');">
                            <c:out value="${appart.name}"/><br>
                            Тип: <c:out value="${appart.type}"/><br>
                            Площадь: <c:out value="${appart.square}"/> м2<br>
                            Цена: <c:out value="${appart.cost}"/> $
                        </a>
                    </div>
                </div>
            </div>
            </c:forEach>
    </div>
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
