package unbord.account.actions

import unbord.account.AccountEntity
import unbord.session.UnbordSession

def validEmailPattern = ~/(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/

if(params.password == params.verifypassword)
{
	try{
		AccountEntity accountEntity = new AccountEntity(params.email,params.username,params.password)
		//System.out.println("hashedPassword:" + accountEntity.getPasswordHash())
		accountEntity.save();
		
		UnbordSession.updateSessionAccount(session,request,accountEntity)
		
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

	
