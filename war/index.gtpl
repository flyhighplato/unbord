
<% 

import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

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
	        	Title:<input type="text" name="eventname"><br/>
	        	Description:<input type="text" name="eventdesc">
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
				Hello, <% print session.getAttribute('username')%>&nbsp;&nbsp;<a href="/account/logout">[Log out]</a><br/></br>
				<ul>
					<li>
						<a href="">Find and Manage Friends</a><br/>
					</li>
					
				</ul>
				
			<%
				}
			%>
		</div>
	</div>
	
	<% if(session?.getAttribute('userID'))
	{
	%>
	<div id="search_buttons" style="margin-top:10px">
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=recommended" style="color:black">Recommended</a>
		</span>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=friends" style="color:black">Friends</a>
		</span>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=following" style="color:black">Following</a>
		</span>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=friends" style="color:black">Maybe</a>
		</span>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=past" style="color:black">Past</a>
		</span>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center">
			<a href="index.gtpl?filter=future" style="color:black">Future</a>
		</span>
	</div>
	<%
	}
	%>
	
	<div id="activity_section">
		<% if(session?.getAttribute('userID'))
		{
		%>
		<span style="background-color:white;border:solid;padding:5px;width:100px;text-align:center;float:right">
			<a href="#dialog_newevent" style="color:black" name="modal">Add Event</a>
		</span><br/>
		<%
		}
		%>
		<ul id="activity_list">
			<% 
			for(event in datastore.prepare(new Query("event")).asIterable()) {
			%>
			<li class="activity_list_item">
				<div style="margin-top:10px">
					<span style="background-color:white;padding:5px;width:100px;text-align:center">
						<a href="#" style="color:black">Overview</a>
					</span>
					<span style="background-color:white;padding:5px;width:100px;text-align:center">
						<a href="#" style="color:black">Attendees</a>
					</span>
				</div>
				<div style="float:right;width:200px">
					Created by: <a href="/account/info/<% print event['creatorUserID']%>"><% print event['creatorUserName']%></a></br>
					What do you say? 
					<a href="/event/signup/<% print event['eventID']%>">Yeah</a> 
					<a href="/event/ignore/<% print event['eventID']%>">No</a> 
					<a href="/event/ignore/<% print event['eventID']%>">Maybe</a>
				</div>
				<h2><% print event['eventName']%></h2>
				<div style="height:100px">
					<% print event['eventDesc']%>
				</div>
			</li>
			<% } 
			%>
		</ul>
	</div>
	
	
	

</body>
</html>


