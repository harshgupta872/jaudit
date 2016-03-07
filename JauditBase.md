# Introduction #

The base project is checked into subversion within the jaudit folder.  It has no runtime dependencies except for the 1.5 JRE.


# Details #

## The Hierarchy and Flow ##

There is a minimum required set of information that you should consider when auditing within your application and a hierarchy to prevent too much duplication of information among that information.  Most applications have the following flow of interactions with (in) the system:

  1. Entity authenticates to the system.
  1. Entity is awarded a session with the system.
  1. Entity executes one or more pieces of work within the context of the session.
  1. Entity closes the session/session is closed by the system.

In order to audit those interactions the following abstractions are defined.

### Common Concepts ###

**Subject**

You will find references to subjects or audit subjects through out the code and schema.  A subject is simply a uniquely identifiable entity within a system.  Subject examples include:
  * A User
  * A Piece of Hardware
  * An Account
  * A Web Service
  * A Group
A subject has three simple fields:
  * Id : A globally unique identifier.
  * Type : The type of this subject, like in the examples above.
  * Discriminator : A grouping of subjects most often to discriminate overall groupings of subjects across types.  In SalesForce, this would be an organization.  In an online bill pay system, this would be a bank id or utility company.

**GUID**
Globally Unique ID.  At the minimum a guid should be a unique identifier across all entities/records/logs within a system.

**Time Stamps**
All Time Stamps should be stored/displayed in the UTC timezone.

### Session Record ###

```
org.opensaas.jaudit.SessionRecord
```

This is a record of the session parameters itself and a top level "container" of all of the transactions that execute within its context.  This record should be created when an entity authenticates to the system.

TODO: Document internal processes.

TODO: Insert a link to the javadoc/external site.


**Example DDL (Postgresql Variant)**
```
    create table session_records (
        id varchar(255) not null, -- GUID
        session_id varchar(255), -- Session Implementation specific id for this instance of the session.
        system_subject_discriminator varchar(255), -- The system subject is the system where the session was created/managed.
        system_subject_type varchar(255),
        system_subject_id varchar(255),
        ended_ts timestamp, -- When the session was ended.
        responsible_subject_discriminator varchar(255), -- The responsible subject is the user or entity which authenticated to the system
        responsible_subject_type varchar(255),
        responsible_subject_id varchar(255) not null,
        responsible_address varchar(255), -- From where the responsible entity is working with the system
        responsible_agent varchar(255), -- The client UserAgent string that the responsible entity is using.
        started_ts timestamp,
        system_address varchar(255), -- When this session was started.
        primary key (id) 
    );

    comment on table session_records is 'Top level record of a session within the system.';

```

### Transaction Record ###

```
org.opensaas.jaudit.TransactionRecord
```

A record of a complete set of work or "transaction" completed within a system within the context of a Session.  There can be 0..**Transaction Records associated with a Session Record.**

**Example DDL (Postgresql Variant)**
```
    create table transaction_records (
        id varchar(255) not null, -- GUID
        ended_ts timestamp, -- When the transaction ended
        started_ts timestamp, -- When the transaction started
        transaction_id varchar(128) not null, -- The transaction system's unique identifier for this transaction
        session_record varchar(255) not null,  -- The Session Record this transaction is associated with.
        primary key (id)
    );

    comment on table transaction_records is 'Record of a transaction within a Session.';

```

### Life Cycle Audit Event ###

```
org.opensaas.jaudit.LifeCycleAuditEvent
org.opensaas.jaudit.LifeCycleAuditEventType
org.opensaas.jaudit.PropertyValueChange
```

This is a record of a change made to a target entity like a property value change or state change.  Optionally a collection of Property Value Changes (PVC) can be associated with a life cycle audit event to create a more granular record of what properties were changed and to what values on the target subject.

Things that can happen as part of a life cycle (and the values that might show up in the life\_cycle\_event\_type column:
  * CREATE : Entity created.  Optionally associated PVCs will have the new values for all properties.
  * UPDATE : Entity properties updated.  Optionally associated PVCs will have the new values and old values for all properties changed.
  * DELETE : Entity deleted.
  * STATE\_CHANGE : Entity changed from a significant state to another.  For example: enabled to disabled.

Note that group membership changes are treated separately underneath the Membership Change Audit Event.

Property Value Change property type reserved values include:
  * string
  * double
  * boolean
  * datetime
  * timezone
  * locale
  * reference
To use your own type, you should use a name space such as:
`com.yahoo.maps.GpsCoordinates`


**Example DDL (Postgresql Variant)**
```
    create table life_cycle_audit_events (
        id varchar(255) not null, -- GUID
        ts timestamp not null, -- When this event occurred
        target_subject_discriminator varchar(255),  -- The target subject is the entity that experienced the life cycle event
        target_subject_type varchar(255),
        target_subject_id varchar(255),
        description varchar(255),
        life_cycle_event_type varchar(255) not null, -- The type of the event. See LifeCycleAuditEventType for types.
        transaction_record varchar(255) not null, -- A reference to the associated transaction record
        primary key (id)
    );
    comment on table life_cycle_audit_events is 'Record of a life cycle event that happens to a target entity.';

    create table property_value_changes (
        id varchar(255) not null, -- GUID
        property_type varchar(255) not null, -- Property type such as String.
        new_value_specified bool, -- Is a new value being recorded or should it be ignored
        old_value_specified bool,  -- Is an old value being recorded or should it be ingored
        new_value varchar(1024),  -- The new value
        old_value varchar(1024), -- The old value
        property_name varchar(255) not null, -- The name of the property
        life_cycle_audit_event varchar(255) not null, -- Reference to the life cycle audit event
        primary key (id)
    );
    comment on table property_value_changes is 'A record of a change in a property value.';

```

_Implementation Note_ : You should probably not record password or other security sensitive values within a property value change unencrypted.

### Consumption Events ###

```
org.opensaas.jaudit.ConsumptionAuditEvent
```

A record of a resource being consumed in total or in part.

**Example DDL (Postgresql Variant)**
```
    create table consumption_events (
        id varchar(255) not null, -- GUID
        ts timestamp not null, -- When this event occurred
        target_subject_discriminator varchar(255), -- The item being consumed
        target_subject_type varchar(255),
        target_subject_id varchar(255),
        description varchar(255),
        amount_consumed float8 not null, -- Amount consumed
        scale int4, -- The scale to use on the amount
        transaction varchar(255) not null, -- Associated transaction
        primary key (id)
    );
```

### Membership Change Audit Events ###

```
org.opensaas.jaudit.MembershipChangeAuditEvent
```

A record of the membership of a group being updated.  The two types of changes:
  * ADDED
  * REMOVED

**Example DDL (Postgresql Variant)**
```
 create table membership_change_audit_events (
        id varchar(255) not null, -- GUID
        ts timestamp not null, -- When the event happened
        target_subject_discriminator varchar(255), -- the entity being ADDED or REMOVED from a grroup
        target_subject_type varchar(255),
        target_subject_id varchar(255),
        description varchar(255),
        membership_change_event_type varchar(255) not null, -- ADDED OR REMOVED
        membership_group_subject_discriminator varchar(255), -- The group being ADDED to or REMOVED from.
        membership_group_subject_type varchar(255),
        membership_group_subject_id varchar(255) not null,
        transaction varchar(255) not null, -- Associated transaction
        primary key (id)
    );
```

### Business Audit Events ###
```
org.opensaas.jaudit.BusinessAuditEvent
```

A Business event is a generic record of an important business level event such as:
  * Store opening
  * New Feature Released
  * Marketing campaign started
  * IPO
  * Website unavailable
  * New CEO hired

**Example DDL (Postgresql Variant)**
```
    create table business_audit_events (
        id varchar(255) not null, -- GUID
        ts timestamp not null, -- When this event occurred.
        target_subject_discriminator varchar(255),
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        description varchar(255),
        business_class varchar(255) not null,
        business_action varchar(1024),
        transaction_record varchar(255) not null,
        primary key (id)
    );
```



## Optional Foreign Key Constraints ##

```
    alter table business_audit_events 
        add constraint FK4B57881CAD45DAD7 
        foreign key (transaction_record) 
        references transaction_records;

    alter table consumption_events 
        add constraint FK58D4B63DAD45DAD7 
        foreign key (transaction_record) 
        references transaction_records;

    alter table life_cycle_audit_events 
        add constraint FKB5B11AD9AD45DAD7 
        foreign key (transaction_record) 
        references transaction_records;

    alter table membership_change_audit_events 
        add constraint FKE7B0E843AD45DAD7 
        foreign key (transaction_record) 
        references transaction_records;

    alter table property_value_changes 
        add constraint FK61FFF10BFF0BCAB9 
        foreign key (life_cycle_audit_event) 
        references life_cycle_audit_events;

    alter table transaction_records 
        add constraint FK631DD9E151A4AA57 
        foreign key (session_record) 
        references session_records;
```