<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="UTF-8">
<title>NewIndex</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="index.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link
	href="https://fonts.googleapis.com/css?family=Montserrat+Alternates&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid my1">
		<h1 id="x">Ti prendo per la gola</h1>
		<h5>Corsi di cucina per neofiti</h5>
	</div>
	<div class="container-fluid my2">
		<div class="row">
			<div class="col-sm-2 col-md-2 col-lg-2 col-xl-2" id="corsi">
				<b>I nostri corsi</b>
			</div>
			<div class="col-sm-2 col-md-2 col-lg-2 col-xl-2" id="scuola">
				<b>La scuola</b>
			</div>
			<div class="col-sm-2 col-md-2 col-lg-2 col-xl-2" id="contatti">
				<b>Contatti</b>
			</div>
			<div class="col-sm-4 col-md-4 col-lg-4 col-xl-4" id="news">
				<b>News</b>
			</div>
			<div class="col-sm-2 col-md-2 col-lg-2 col-xl-2" id="accedi">
				<b>Accedi</b>
			</div>
		</div>
	</div>

	<div class="container-fluid my2">
		<div class="row">
			<div class="col" id="registrati">
				<form action="regUtente">
					<table>

						<tr height="50" align="center">
							<th colspan="3" valign="middle">REGISTRAZIONE UTENTE</th>
						</tr>

						<tr height="50">
							<td width="20%">Username</td>
							<td width="40%"><input type="text" name="idUtente"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='idUtente'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach>
						</tr>
						<tr height="50">
							<td width="20%">Password</td>
							<td width="40%"><input type="text" name="password"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='password'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach>
						</tr>
						<tr height="50">
							<td width="20%">Nome</td>
							<td width="40%"><input type="text" name="nome"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='nome'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach>
						</tr>
						<tr height="50">
							<td width="20%">Cognome</td>
							<td width="40%"><input type="text" name="cognome"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='cognome'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach>
						</tr>
						<tr height="50">
							<td width="20%">Giorno di nascita</td>
							<td width="40%"><select name="giorno">
									<c:forEach begin="1" end="31" var="i">
										<option value="${i}">${i}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr height="50">
							<td width="20%">Mese di nascita</td>
							<td width="40%"><select name="mese">
									<c:forEach begin="1" end="12" var="i">
										<option value="${i}">${i}</option>
									</c:forEach>
							</select></td>
						</tr>


						<tr height="50">
							<td width="20%">Anno di nascita</td>
							<td width="40%"><input type="text" name="anno"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='anno'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach></td>
						</tr>
						<tr height="50">
							<td width="20%">EMail</td>
							<td width="40%"><input type="text" name="email"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='email'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach></td>
						</tr>

						<tr height="50">
							<td width="20%">Telefono</td>
							<td width="40%"><input type="text" name="telefono"></td>
							<td width="40%"><c:forEach items="${lista}" var="errore">
									<c:if test="${errore.campoValidato=='telefono'}"> ${errore.descrizioneErrore}</c:if>
								</c:forEach></td>
						</tr>
						<tr height="50">
							<th colspan="3" valign="middle"><input type="submit"
								value="registra"><br></th>
						</tr>
					</table>
				</form>
			</div>
			<div class="col">ALTRE COSE</div>
		</div>
	</div>


</body>
</html>