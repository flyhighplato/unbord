
<% 
if(session == null) {session = request.getSession(true)} 
%>
<html>
<head>
    <title>Unbord</title>
    
    <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
    <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
    
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/js/modal.js"></script>
</head>
<body>

	<div id="boxes">
	    <div id="dialog_newuser" class="window">
	        <a href="#" class="close">Close</a>
	        <form style="width:300px;text-align:right;margin-top:50px" action="/account/create" method="get">
	        	Email:<input type="text" name="email"><br/>
	        	Username:<input type="text" name="username"><br/>
	        	Password:<input type="text" name="password"><br/>
	        	Re-enter Password:<input type="text" name="verifypassword"><br/>
	        	<input type="submit" value="Create Account &gt;&gt;">
	        </form>
	    </div>
	    <div id="dialog_newevent" class="window">
	        <a href="#" class="close">Close</a>
	        <form style="width:300px;text-align:right;margin-top:50px" action="/event/create" method="get">
	        	Event Name:<input type="text" name="eventname"><br/>
	        	
	        	<input type="submit" value="Create Event &gt;&gt;">
	        </form>
	    </div>
	  	<div id="mask"></div>
	</div>
	
	<div id="banner">
		<h1>unbord</h1>
		<div id="dashboard">
			<% 	if (session?.getAttribute('username')==null)
				{
			%>
			<div style="width:200px;text-align:center">
				<a href="#dialog_newuser" name="modal">Create Account</a>
				<br/>
				or
			</div>
			<div style="width:200px;text-align:right">
				<form action="/account/login" method="get">
					<span>Username:</span> <input type="text" name="username"></input>
					<span>Password:</span> <input type="text" name="password"></input>
					<input type="submit" value="Log In"/>
					
				</form>
			</div>
			<%	}
				else
				{
			%>
				Hello, <% print session.getAttribute('username')%></br>
				<a href="/account/logout">Log out</a>
			<%
				}
			%>
		</div>
	</div>
	
	<div id="searchButtons">
	<a href="#dialog_newevent" name="modal">Add Event</a>
	</div>
	
	<div id="activity_section">
		<ul id="activity_list">
			<% 30.times {%>
			<li class="activity_list_item">Test item</li>
			<% } %>
		</ul>
	</div>
	
	
	

</body>
</html>


