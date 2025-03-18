<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Work Hours Report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Work Hours Report</h1>
        <form method="get" action="/report">
            <div class="form-group">
                <label for="startDate">Start Date</label>
                <input type="datetime-local" class="form-control" id="startDate" name="startDate" value="${startDate}">
            </div>
            <div class="form-group">
                <label for="endDate">End Date</label>
                <input type="datetime-local" class="form-control" id="endDate" name="endDate" value="${endDate}">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <table class="table table-bordered mt-4">
            <thead>
                <tr>
                    <th>Employee Name</th>
                    <th>Project Name</th>
                    <th>Total Hours</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${reportData}">
                    <tr>
                        <td>${entry.employeeName}</td>
                        <td>${entry.projectName}</td>
                        <td>${entry.totalHours}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>