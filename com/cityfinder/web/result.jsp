<%@ page import="java.util.*" %>

<html>
<body>
<h1 align="cemter">City Search Resutls</h1>
<p>
<%
    String[] citysInfo = (String[]) request.getAttribute("citysInfo");

    for (String s : citysInfo)
    {
        out.print("<br>" + s);
    }
%>
</body>
</html>