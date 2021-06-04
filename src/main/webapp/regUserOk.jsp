<%@page import="entity.Autore"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  	<% Autore a = (Autore) request.getAttribute("autore"); %>

	<p> Utente registrato con successo </p>
	
	<p> Usermame: <%= a %> </p>
	
</body>
