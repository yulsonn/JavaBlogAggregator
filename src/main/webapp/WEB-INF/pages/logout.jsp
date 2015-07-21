<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp"%>

<!-- csrt for log out-->
<form action="/logout" method="post" id="logoutForm">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<script>
  window.onload = function formSubmit() {
    document.getElementById("logoutForm").submit();
  }
</script>
