package unbord.account.actions
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.security.MessageDigest
import unbord.account.AccountEntity
import unbord.session.UnbordSession

if(params["username"] && params["password"])
{
	AccountEntity accountEntity = AccountEntity.getByUsernameAndPassword(params["username"],params["password"])
	
	if(accountEntity)
	{
		UnbordSession.updateSessionAccount(session,request,accountEntity)
	}
}
redirect "/"

