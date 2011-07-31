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
	
	static protected String entityTypeName = "account"
	static protected String emailFieldName = "email"
	static protected String userNameFieldName = "userName"
	static protected String passwordHashFieldName = "passwordHash"
	
	static protected datastore = DatastoreServiceFactory.getDatastoreService()
	
	static def validEmailPattern = ~/(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/
	
	AccountEntity(String email_in, String username_in, String password_in)
	{
		setUserName(username_in)
		setEmail(email_in)
		setPassword(password_in)
		
		accountEntity = new Entity(entityTypeName)
	}
	
	protected AccountEntity(Entity accountEntity_in)
	{
		userName = accountEntity_in[userNameFieldName]
		email = accountEntity_in[emailFieldName]
		passwordHash = accountEntity_in[passwordHashFieldName]
		accountEntity = accountEntity_in
	}
	
	public boolean setUserName(String username_in)
	{
		if(username_in)
		{
			if(isUniqueUserName(username_in))
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
			passwordHash = hashPassword(password_in)
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
			if(isUniqueEmail(email_in))
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
		accountEntity[userNameFieldName]=userName
		accountEntity[emailFieldName]=email
		accountEntity[passwordHashFieldName]=passwordHash
		
		accountEntity.save()
	}
	
	static public boolean isUniqueUserName(String userName)
	{
		def accountQuery = new Query(entityTypeName)
		accountQuery.addFilter(userNameFieldName,Query.FilterOperator.EQUAL,userName)
		
		return datastore.prepare(accountQuery).asList(withLimit(1)).size() == 0	
	}
	
	static public boolean isUniqueEmail(String email)
	{
		def emailQuery = new Query(entityTypeName)
		emailQuery.addFilter(emailFieldName,Query.FilterOperator.EQUAL,email)
		
		return datastore.prepare(emailQuery).asList(withLimit(1)).size() == 0
	}
	
	static public boolean isValidAccountEmail(String email)
	{
		return email ==~ validEmailPattern
	}
	
	static public String hashPassword(String password_in)
	{
		def md = MessageDigest.getInstance("SHA-256")
		def passwordHashBytes = md.digest(password_in.toString().getBytes())
		def bigIntHash = new BigInteger(1,passwordHashBytes)
		return bigIntHash.toString(16)
	}
	
	static public AccountEntity getByUsernameAndPassword(String username_in, String password_in)
	{
		def accountQuery = new Query(entityTypeName)
		accountQuery.addFilter(userNameFieldName,Query.FilterOperator.EQUAL, username_in)
		accountQuery.addFilter(passwordHashFieldName,Query.FilterOperator.EQUAL,hashPassword(password_in))
		
		def entities = datastore.prepare(accountQuery).asList(withLimit(1))
		
		if(entities.size()>0)
		{
			return new AccountEntity(entities.getAt(0))
		}
		else
		{
			return null;
		}
	}
	
	static public AccountEntity getByKey(Key entityKey_in)
	{
		def accountQuery = new Query(entityTypeName)
		accountQuery.addFilter(Entity.KEY_RESERVED_PROPERTY,Query.FilterOperator.EQUAL, entityKey_in)
		
		def entities = datastore.prepare(accountQuery).asList(withLimit(1))
		
		if(entities.size()>0)
		{
			return new AccountEntity(entities.getAt(0))
		}
		else
		{
			return null;
		}
	}
	
	static public AccountEntity getByKey(String entityKey_in)
	{
		return getByKey(KeyFactory.stringToKey(entityKey_in))
	}
	
}
