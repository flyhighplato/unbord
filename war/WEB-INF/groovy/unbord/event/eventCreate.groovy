package unbord.event

import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.util.UUID

if(params['eventname'] && params['eventdesc'] && session && session['username'] && session['userID'])
{
	Entity eventEntity = new Entity("event")

	
	eventEntity['eventID']=UUID.randomUUID().toString()
	eventEntity['eventName']=params['eventname']
	eventEntity['eventDesc']=params['eventdesc']
	eventEntity['creatorUserID']=session['userID'].toString()
	eventEntity['creatorUserName']=session['username'].toString()
	
	eventEntity.save()
	
	redirect "/"
}



