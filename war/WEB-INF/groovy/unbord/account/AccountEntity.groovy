package unbord.account

import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.security.MessageDigest
import java.util.regex.Pattern

class MalformedUserNameException extends Exception{}
class DuplicateUserNameException extends Exception{}

class MalformedEmailException extends Exception{}
class DuplicateEmailException extends Exception{}

class MalformedPasswordException extends Exception{}

public class AccountEntity {
	protected Entity accountEntity
	String email = ""
	String userName = ""
	String passwordHash = ""
	boolean isNew = false;
	
	static def validEmailPattern = ~/(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/
	
	AccountEntity(String email_in, String username_in, String password_in)
	{
		setUserName(username_in)
		setEmail(email_in)
		setPassword(password_in)
		
		accountEntity = new Entity("account")
		isNew = true;
	}
	
	public boolean setUserName(String username_in)
	{
		if(username_in)
		{
			if(isUniqueUserName(username_in, DatastoreServiceFactory.getDatastoreService()))
			{
				userName = username_in
				return true;
			}
			else
			{
				throw new DuplicateUserNameException()
			}
		}
		else
		{
			throw new MalformedUserNameException()
		}
		
		return false;
	}
	
	public boolean setPassword(String password_in)
	{
		if(password_in)
		{
			//Keep only the hash of the password
			def md = MessageDigest.getInstance("SHA-256")
			def passwordHashBytes = md.digest(password_in.toString().getBytes())
			def bigIntHash = new BigInteger(1,passwordHashBytes)
		
			passwordHash = bigIntHash.toString(16)
			return true;
		}
		else
		{
			throw new MalformedPasswordException()
		}
		
		return false;
		
	}
	
	public boolean setEmail(String email_in)
	{
		if(email_in ==~ validEmailPattern)
		{
			if(isUniqueEmail(email_in,DatastoreServiceFactory.getDatastoreService()))
			{
				email = email_in
				return true;
			}
			else
			{
				throw new DuplicateEmailException()
			}
			
		}
		else
		{
			throw new MalformedEmailException()
		}
		
		return false;
	}
	
	public Key getKey()
	{
		return accountEntity.getKey()
	}
	
	public String getKeyAsString()
	{
		return KeyFactory.keyToString(getKey())
	}
	
	void save()
	{
		accountEntity['userName']=userName
		accountEntity['email']=email
		accountEntity['passwordHash']=passwordHash
		
		accountEntity.save()
	}
	
	static public boolean isUniqueUserName(String userName, BaseDatastoreService datastore)
	{
		def accountQuery = new Query("account")
		accountQuery.addFilter('username',Query.FilterOperator.EQUAL,userName)
		
		return datastore.prepare(accountQuery).asList(withLimit(1)).size() == 0	
	}
	
	static public boolean isUniqueEmail(String email, BaseDatastoreService datastore)
	{
		def emailQuery = new Query("account")
		emailQuery.addFilter('email',Query.FilterOperator.EQUAL,email)
		
		return datastore.prepare(emailQuery).asList(withLimit(1)).size() == 0
	}
	
	static public boolean isValidAccountEmail(String email)
	{
		return email ==~ validEmailPattern
	}
	
	
}
