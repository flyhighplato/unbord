package unbord.account.actions

if(session == null)
{
	session = request.getSession(false)
}
	
if(session!=null)
{
	session.invalidate()
}

redirect '/'
