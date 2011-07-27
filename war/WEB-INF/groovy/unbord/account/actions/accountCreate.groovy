package unbord.account.actions

import unbord.account.AccountEntity

def validEmailPattern = ~/(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/

if(params.password == params.verifypassword)
{
	try{
		AccountEntity accountEntity = new AccountEntity(params.email,params.username,params.password)
		accountEntity.save();
		
		if(session==null)
		{
			session = request.getSession(true)
			session.setMaxInactiveInterval(1209600)
		}
		
		
		session.setAttribute('userID',accountEntity.getKeyAsString())
		session.setAttribute('username',accountEntity.getUserName())
		
		redirect '/'
	}
	catch(Exception e)
	{
		System.out.println("Couldn't create account:" + e)
	}
}
else
{
	System.out.println("Passwords don't match")
}

	
