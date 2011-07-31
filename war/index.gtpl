
<% 

import com.google.appengine.api.datastore.*
import unbord.event.*

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
<body id="tab1">

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
		
	<%
	}
	%>
	
	<div id="activity_section">
		<% if(session?.getAttribute('userID'))
			{
			%>
		<div>
			<ul id="tabnav">
				<li class="tab1"><a href="index.gtpl?filter=recommended">Recommended</a></li> 
				<li class="tab2"><a href="index.gtpl?filter=friends">Friends</a></li> 
				<li class="tab3"><a href="index.gtpl?filter=following">Following</a></li> 
				<li class="tab4"><a href="index.gtpl?filter=maybe">Maybe</a></li> 
				<li class="tab5"><a href="index.gtpl?filter=past">Past</a></li> 
				<li class="tab6"><a href="index.gtpl?filter=future">Future</a></li> 
			</ul>
		</div>
		<%
			}
		%>
		<div id="activity_list_section">
			<% if(session?.getAttribute('userID'))
			{
			%>
			<span id="add_activity_button">
				<a href="#dialog_newevent" style="color:black" name="modal"><span>+</span>Add Event</a>
			</span><br/>
			<%
			}
			%>
			<ul id="activity_list">
				<% 
				def allEvents = EventEntity.getAllUpcomingEvents(30)
				
				for(event in allEvents)
				{

				%>
				<li class="activity_list_item_collapsed">
					
					
					<table style="width:100%">
						<tr>
							<td style="width:85%">
								<h2><% print event.eventName %></h2>
								<p>
									<% print event.getEventDesc()%>
								</p>
							</td>
							<td align="right" valign="top">
								<p style="text-align:left">
									By <a href="/account/info/<% print event.getCreatorAccount().getKeyAsString()%>"><% print event.getCreatorAccount().getUserName()%></a>
								</p>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<a href="#">More...</a>
							</td>
						</tr>
					</table>
					
				</li>
				<%
					
				}
				 
				%>
			</ul>
		</div>
	</div>
	
	
	

</body>
</html>


