package unbord.event

import com.google.appengine.api.datastore.*

import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import unbord.account.AccountEntity

class EventEntity {
	protected Entity eventEntity
	
	String eventName = ""
	String eventDesc = ""
	AccountEntity creatorAccount = null
	
	static protected String entityTypeName = "event"
	protected String eventNameFieldName = "eventName"
	protected String eventDescFieldName = "eventDesc"
	protected String eventCreatorIDFieldName = "creatorID"
	
	static protected datastore = DatastoreServiceFactory.getDatastoreService()
	
	EventEntity(String eventName_in, String eventDesc_in, String creatorID_in)
	{
		eventEntity = new Entity(entityTypeName)
		setEventName(eventName_in)
		setEventDescription(eventDesc_in)
		setCreator(creatorID_in)	
	}
	
	protected EventEntity(Entity eventEntity_in)
	{
		eventEntity = eventEntity_in
		eventName = eventEntity_in[eventNameFieldName]
		eventDesc = eventEntity_in[eventDescFieldName]
	}
	
	protected static getByEntity(Entity eventEntity_in)
	{
		return new EventEntity(eventEntity_in)
	}
	
	void save()
	{
		eventEntity[eventNameFieldName] = eventName
		eventEntity[eventDescFieldName] = eventDesc
		eventEntity[eventCreatorIDFieldName] = creatorAccount.getKey()
		
		eventEntity.save()
	}
	
	public boolean setEventName(String eventName_in)
	{
		if(eventName_in)
		{
			eventName = eventName_in
			return true;
		}
		
		return false;
	}
	
	public boolean setEventDescription(String eventDesc_in)
	{
		if(eventDesc_in)
		{
			eventDesc = eventDesc_in
			return true;
		}
		
		return false;
	}
	
	public AccountEntity getCreatorAccount()
	{
		if(eventEntity && !creatorAccount)
		{
			creatorAccount = AccountEntity.getByKey(eventEntity[eventCreatorIDFieldName]) 
		}
		
		return creatorAccount
	}
	
	public boolean setCreator(String creatorID_in)
	{
		if(creatorID_in)
		{
			creatorAccount = AccountEntity.getByKey(creatorID_in)
			
			if(creatorAccount)
			{
				return true;
			}
		}
		
		return false;
	}
	
	static public getAllUpcomingEvents(int maxSize)
	{
		def eventLazyList = datastore.prepare(new Query(entityTypeName)).asList(withLimit(maxSize))
		
		def eventList = []
		for(event in eventLazyList)
		{
			eventList.push(new EventEntity(event))
		}

		return eventList
	}
	
	public Key getKey()
	{
		return eventEntity.getKey()
	}
	
	public String getKeyAsString()
	{
		return KeyFactory.keyToString(getKey())
	}
}
