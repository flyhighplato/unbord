package unbord.account
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.security.MessageDigest

if(params["username"] && params["password"])
{
	def accountQuery = new Query("account")
	accountQuery.addFilter('username',Query.FilterOperator.EQUAL,params.username)
	
	def md = MessageDigest.getInstance("SHA-256")
	def passwordHashBytes = md.digest(params['password'].toString().getBytes())
	def bigIntHash = new BigInteger(1,passwordHashBytes)

	accountQuery.addFilter('passwordHash',Query.FilterOperator.EQUAL,bigIntHash.toString(16))
	
	def entities = datastore.prepare(accountQuery).asList(withLimit(1))
	
	if(entities.size()>0)
	{
		Entity userEntity = entities.getAt(0);
		if(session==null)
		{
			session = request.getSession(true)
			session.setMaxInactiveInterval(1209600)
		}
			
		session.setAttribute('userID',userEntity['userID'])
		session.setAttribute('username',userEntity['username'])
	}
	
}

redirect "/"
