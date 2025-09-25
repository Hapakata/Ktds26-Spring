<%--
    <@ <== Directive
    <@ page ..... %> <== Page Directive ( Java class Import )
    <@ taglib ...%> <== Tag library Directive
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="/css/hello-spring.css">
<script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/js/board/list.js"></script>
</head>
<body>
	<div class="wrapper">
		<h1 class="page-title">게시글 목록</h1>
		<div>검색된 게시글의 수: ${list.listCount}개</div>
		
		<table class="grid">
			<colgroup>
			   <col width="80"/>
			   <col width="*"/>
			   <col width="200"/>
			   <col width="80"/>
			   <col width="120"/>
			   <col width="120"/>
			</colgroup>
		   <thead>
		       <tr>
		           <th>번호</th>
		           <th>제목</th>
		           <th>이메일</th>
		           <th>조회수</th>
		           <th>등록일</th>
		           <th>수정일</th>
		       </tr>
		   </thead>
		   <tbody>
		      
		      <c:choose>
		          <c:when test="${not empty list.boardList}">
		               <c:forEach items="${list.boardList}" var="article">
		                   <tr>
		                       <td>${article.number}</td>
		                       <td><a href="/view/${article.id}">${article.subject}</td><a/>
		                       <td>${article.email}</td>
		                       <td>${article.viewCnt}</td>
		                       <td>${article.crtDt}</td>
		                       <td>${article.mdfyDt}</td>
		                   </tr>
		               </c:forEach>		          
		          </c:when>
		          <c:otherwise>
					   <tr>
					      <td colspan ="6" class="no-data"></td>
					   </tr>
		          </c:otherwise>
		      </c:choose>
	       </tbody>
		</table>
		
		<div class="btn-group">
		  <div class="right-align">
		    <button type="button" class="save-btn write-article">새 글 작성</button>
		  </div>
		</div>
		
    </div>
</body>
</html>