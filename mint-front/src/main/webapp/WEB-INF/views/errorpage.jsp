<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>System Error Page</title>
</head>
<body>
	<pre>
		<h1> System Error !!! </h1>
		<br>
		<code>
			ErrorCd : ${errorCd}
			ErrorMsg: ${errorMsg}
			<br>
			ErrorDetail : 
			${errorDetail}
		</code>
	</pre>
</body>
</html>