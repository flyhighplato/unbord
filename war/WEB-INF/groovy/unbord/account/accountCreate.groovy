package unbord.account
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.security.MessageDigest
import java.util.regex.Pattern
import javax.servlet.http.*

def validEmailPattern = ~/(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/

//Avoid hitting the datastore for invalid email addresses
if(params.email ==~ validEmailPattern)
{
	def accountQuery = new Query("account")
	accountQuery.addFilter('username',Query.FilterOperator.EQUAL,params.username)
	
	def emailQuery = new Query("account")
	emailQuery.addFilter('email',Query.FilterOperator.EQUAL,params.email)
	
	def isUniqueUsername = datastore.prepare(accountQuery).asList(withLimit(1)).size() == 0
	def isUniqueEmail = datastore.prepare(emailQuery).asList(withLimit(1)).size() == 0
	
	def passwordVerifies = params.password == params.verifypassword
	
	if(isUniqueUsername && isUniqueEmail && passwordVerifies)
	{
		//Generate the new entity to represent this user
		Entity accountEntity = new Entity("account")
		accountEntity << params.subMap(['username', 'email'])
		
		//UUID will represent the user's ID
		def uuid = UUID.randomUUID()
		accountEntity['userID']=uuid.toString()
		
		//Keep only the hash of the password (standard security measure)
		def md = MessageDigest.getInstance("SHA-256")
		def passwordHashBytes = md.digest(params['password'].toString().getBytes())
		def bigIntHash = new BigInteger(1,passwordHashBytes)
	
		accountEntity['passwordHash'] = bigIntHash.toString(16)
		
		accountEntity.save()
		
		if(session==null)
		{
			session = request.getSession(true)
			session.setMaxInactiveInterval(1209600)
		}
			
		session.setAttribute('userID',uuid)
		session.setAttribute('username',params.username)
		
		redirect '/'

	}
	else if(!passwordVerifies)
	{
		println "Passwords don't match"
	}
	else if(!isUniqueUsername)
	{
		def entities = datastore.prepare(accountQuery).asList(withLimit(10))
		
		for(entity in entities)
		{
			println "Entity:" + entity.getProperties()
		}
	}
	else if(!isUniqueEmail)
	{
		def entities = datastore.prepare(emailQuery).asList(withLimit(10))
		
		for(entity in entities)
		{
			println "Entity:" + entity.getProperties()
		}
	}
}
else
{
	println "Invalid email"
}


	
