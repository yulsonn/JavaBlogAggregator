<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp"%>

<style>
  .form-signin {
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
  }
  .form-signin .form-signin-heading,
  .form-signin  {
    margin-bottom: 10px;
  }
  .form-signin  {
    font-weight: normal;
  }
  .form-signin .form-control {
    position: relative;
    height: auto;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="text"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
</style>

<sec:authorize access="isAuthenticated()">
  <h3>You are already logged in as: <c:out value="${pageContext.request.userPrincipal.name}" /></h3>
</sec:authorize>

<c:if test="${param.error eq true}">
  <div class="alert alert-danger" role="alert">
    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
    <span class="sr-only">Error:</span>
    Invalid username or password
  </div>
</c:if>

<sec:authorize access="!isAuthenticated()">
  <form Class="form-signin" role = "form" action='<spring:url value="/login"/>' method="post">
    <h2 class="form-signin-heading">Please sign in</h2>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <label for="name" class="sr-only">Name</label>
      <input type="text" id="name" name="username" class="form-control" placeholder="Name" required autofocus>
    <label for="password" class="sr-only">Password</label>
      <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  </form>
</sec:authorize>