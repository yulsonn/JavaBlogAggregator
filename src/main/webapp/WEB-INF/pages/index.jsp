<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp"%>

<h1>Latest news from Java world:</h1>

<table class="table table-bordered table-hover table-striped">
    <thead>
    <tr>
        <th>date</th>
        <th>item</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${items}" var="item">
        <tr>
            <td>
                <fmt:formatDate value="${item.publishedDate}" pattern="dd.MM.yyyy HH:mm:ss"/>
                <br/>
                <c:out value="${item.blog.name}" />
            </td>
            <td>
                <strong>
                    <a href="<c:out value="${item.link}" />" target="_blank">
                        <c:out value="${item.title}" />
                    </a>
                </strong>
                <br>
                    ${item.description}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

