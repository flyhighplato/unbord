package unbord.session
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import unbord.account.AccountEntity


class UnbordSession {
	static void updateSessionAccount(HttpSession session, HttpServletRequest request, AccountEntity accountEntity)
	{
		if(session==null)
		{
			session = request.getSession(true)
		}
		
		if(accountEntity)
		{
			session.setMaxInactiveInterval(1209600)
			session.setAttribute('userID',accountEntity.getKeyAsString())
			session.setAttribute('username',accountEntity.getUserName())
		}
		else
		{
			session.setAttribute('userID',null)
			session.setAttribute('username',null)
		}
	}
}
