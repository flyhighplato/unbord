package unbord.event.actions

import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import java.util.UUID
import unbord.event.EventEntity

if(params['eventname'] && params['eventdesc'] && session && session['username'] && session['userID'])
{	
	EventEntity eventEntity = new EventEntity(params['eventname'], params['eventdesc'], session['userID'].toString())
	eventEntity.save()
	redirect "/"
}



