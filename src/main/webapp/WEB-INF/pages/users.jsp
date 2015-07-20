<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp"%>

<table class="table table-bordered table-hover table-striped">
  <thead>
    <tr>
      <th>user name</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${users}" var="user">
      <tr>
        <td>
          <a href='<spring:url value="/users/${user.id}"/>'>
            ${user.name}
          </a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
