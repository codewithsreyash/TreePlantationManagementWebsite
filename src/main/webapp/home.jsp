<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tree Plantation Public Home</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 60%; margin-bottom: 20px;}
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background-color: #f4f4f4; }
        .admin-link { display: inline-block; padding: 10px 15px; background: #28a745; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>Welcome to the Public Tree Plantation Drive Portal</h1>
    
    <h2>Upcoming Plantation Drives</h2>
    <c:if test="${empty activeDrives}">
        <p>No active drives at the moment.</p>
    </c:if>
    <c:if test="${not empty activeDrives}">
        <table>
            <tr>
                <th>ID</th>
                <th>Location</th>
                <th>Date</th>
            </tr>
            <c:forEach var="drive" items="${activeDrives}">
                <tr>
                    <td>${drive.id}</td>
                    <td>${drive.location}</td>
                    <td>${drive.driveDate}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br/>
    <a class="admin-link" href="admin.xhtml">Go to Coordinator Dashboard (JSF Backend)</a>
</body>
</html>
