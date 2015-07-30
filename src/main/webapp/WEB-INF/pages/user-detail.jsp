<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp"%>

<h1>${user.name}</h1>

<br /><br />

<script type="text/javascript">
  $(document).ready(function() {
    $('.nav-tabs a:first').tab('show'); //Show first tab
    /*show modal window when delete blog*/
    $(".triggerRemove").click(function(e) {
      e.preventDefault();
      $("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
      $("#modalRemove").modal();
    })
  });
</script>

<!-- Nav tabs -->
<ul class="nav nav-tabs">
  <c:forEach items="${user.blogs}" var="blog">
    <li><a href="#blog_${blog.id}" data-toggle="tab">${blog.name}</a></li>
  </c:forEach>
</ul>

<div class="tab-content">
  <c:forEach items="${user.blogs}" var="blog">
    <div class="tab-pane" id="blog_${blog.id}">
      <h1><c:out value="${blog.name}" /></h1>
      <p>
      <a href="<spring:url value="/blog/remove/${blog.id}.html"/>" class="btn btn-danger triggerRemove">remove</a>
      <p>
      <p><c:out value="${blog.url}" /></p>

      <table class="table table-bordered table-hover table-striped">
        <thead>
        <tr>
          <th>date</th>
          <th>item</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${blog.items}" var="item">
        <tr>
          <td>
            <fmt:formatDate value="${item.publishedDate}" pattern="dd.MM.yyyy HH:mm:ss"/>
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
    </div>
  </c:forEach>
</div>

<!-- Modal -->
<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="modalRemoveLabel">Remove blog</h4>
      </div>
      <div class="modal-body">
        Really remove?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <a href="" class="btn btn-danger removeBtn">Remove</a>
      </div>
    </div>
  </div>
</div>