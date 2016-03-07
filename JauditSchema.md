# Introduction #

These are some automatically generated schemas which reads the JPA information from the Java objects within jaudit-vo.

# PostgreSQL #

```

    alter table business_audit_events 
        drop constraint FK4B57881CAD45DAD7;

    alter table consumption_events 
        drop constraint FK58D4B63DAD45DAD7;

    alter table life_cycle_audit_events 
        drop constraint FKB5B11AD9AD45DAD7;

    alter table membership_change_audit_events 
        drop constraint FKE7B0E843AD45DAD7;

    alter table property_value_changes 
        drop constraint FK61FFF10BFF0BCAB9;

    alter table transaction_records 
        drop constraint FK631DD9E151A4AA57;

    drop table business_audit_events;

    drop table consumption_events;

    drop table life_cycle_audit_events;

    drop table membership_change_audit_events;

    drop table property_value_changes;

    drop table session_records;

    drop table transaction_records;

    create table business_audit_events (
        id varchar(255) not null,
        ts timestamp not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        business_class varchar(255) not null,
        business_action varchar(1024),
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table consumption_events (
        id varchar(255) not null,
        ts timestamp not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        amount_consumed float8 not null,
        scale int4,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table life_cycle_audit_events (
        id varchar(255) not null,
        ts timestamp not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        life_cycle_event_type varchar(255) not null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table membership_change_audit_events (
        id varchar(255) not null,
        ts timestamp not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        membership_change_event_type varchar(255) not null,
        membership_group_subject_id varchar(255) not null,
        membership_group_subject_type varchar(255),
        membership_group_subject_discriminator varchar(255),
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table property_value_changes (
        id varchar(255) not null,
        property_type varchar(255) not null,
        new_value_specified bool,
        old_value_specified bool,
        new_value varchar(1024),
        old_value varchar(1024),
        property_name varchar(255) not null,
        life_cycle_audit_event varchar(255) not null,
        primary key (id)
    );

    create table session_records (
        id varchar(255) not null,
        system_subject_id varchar(255),
        system_subject_type varchar(255),
        system_subject_discriminator varchar(255),
        ended_ts timestamp,
        subjectId varchar(255),
        responsible_subject_type varchar(255),
        responsible_subject_discriminator varchar(255),
        responsible_address varchar(255),
        responsible_agent varchar(255),
        started_ts timestamp,
        system_address varchar(255),
        session_id varchar(255),
        primary key (id)
    );

    create table transaction_records (
        id varchar(255) not null,
        ended_ts timestamp,
        started_ts timestamp,
        transaction_id varchar(128) not null,
        session_record varchar(255) not null,
        primary key (id)
    );

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


# SQLServer #
```

    alter table business_audit_events 
        drop constraint FK4B57881CAD45DAD7;

    alter table consumption_events 
        drop constraint FK58D4B63DAD45DAD7;

    alter table life_cycle_audit_events 
        drop constraint FKB5B11AD9AD45DAD7;

    alter table membership_change_audit_events 
        drop constraint FKE7B0E843AD45DAD7;

    alter table property_value_changes 
        drop constraint FK61FFF10BFF0BCAB9;

    alter table transaction_records 
        drop constraint FK631DD9E151A4AA57;

    drop table business_audit_events;

    drop table consumption_events;

    drop table life_cycle_audit_events;

    drop table membership_change_audit_events;

    drop table property_value_changes;

    drop table session_records;

    drop table transaction_records;

    create table business_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255) null,
        target_subject_type varchar(255) null,
        target_subject_discriminator varchar(255) null,
        description varchar(255) null,
        business_class varchar(255) not null,
        business_action varchar(1024) null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table consumption_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255) null,
        target_subject_type varchar(255) null,
        target_subject_discriminator varchar(255) null,
        description varchar(255) null,
        amount_consumed double precision not null,
        scale int null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table life_cycle_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255) null,
        target_subject_type varchar(255) null,
        target_subject_discriminator varchar(255) null,
        description varchar(255) null,
        life_cycle_event_type varchar(255) not null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table membership_change_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255) null,
        target_subject_type varchar(255) null,
        target_subject_discriminator varchar(255) null,
        description varchar(255) null,
        membership_change_event_type varchar(255) not null,
        membership_group_subject_id varchar(255) not null,
        membership_group_subject_type varchar(255) null,
        membership_group_subject_discriminator varchar(255) null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table property_value_changes (
        id varchar(255) not null,
        property_type varchar(255) not null,
        new_value_specified tinyint null,
        old_value_specified tinyint null,
        new_value varchar(1024) null,
        old_value varchar(1024) null,
        property_name varchar(255) not null,
        life_cycle_audit_event varchar(255) not null,
        primary key (id)
    );

    create table session_records (
        id varchar(255) not null,
        system_subject_id varchar(255) null,
        system_subject_type varchar(255) null,
        system_subject_discriminator varchar(255) null,
        ended_ts datetime null,
        subjectId varchar(255) null,
        responsible_subject_type varchar(255) null,
        responsible_subject_discriminator varchar(255) null,
        responsible_address varchar(255) null,
        responsible_agent varchar(255) null,
        started_ts datetime null,
        system_address varchar(255) null,
        session_id varchar(255) null,
        primary key (id)
    );

    create table transaction_records (
        id varchar(255) not null,
        ended_ts datetime null,
        started_ts datetime null,
        transaction_id varchar(128) not null,
        session_record varchar(255) not null,
        primary key (id)
    );

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


# MySQL 5 Dialect #
```

    alter table business_audit_events 
        drop 
        foreign key FK4B57881CAD45DAD7;

    alter table consumption_events 
        drop 
        foreign key FK58D4B63DAD45DAD7;

    alter table life_cycle_audit_events 
        drop 
        foreign key FKB5B11AD9AD45DAD7;

    alter table membership_change_audit_events 
        drop 
        foreign key FKE7B0E843AD45DAD7;

    alter table property_value_changes 
        drop 
        foreign key FK61FFF10BFF0BCAB9;

    alter table transaction_records 
        drop 
        foreign key FK631DD9E151A4AA57;

    drop table if exists business_audit_events;

    drop table if exists consumption_events;

    drop table if exists life_cycle_audit_events;

    drop table if exists membership_change_audit_events;

    drop table if exists property_value_changes;

    drop table if exists session_records;

    drop table if exists transaction_records;

    create table business_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        business_class varchar(255) not null,
        business_action varchar(1024),
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table consumption_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        amount_consumed double precision not null,
        scale integer,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table life_cycle_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        life_cycle_event_type varchar(255) not null,
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table membership_change_audit_events (
        id varchar(255) not null,
        ts datetime not null,
        target_subject_id varchar(255),
        target_subject_type varchar(255),
        target_subject_discriminator varchar(255),
        description varchar(255),
        membership_change_event_type varchar(255) not null,
        membership_group_subject_id varchar(255) not null,
        membership_group_subject_type varchar(255),
        membership_group_subject_discriminator varchar(255),
        transaction_record varchar(255) not null,
        primary key (id)
    );

    create table property_value_changes (
        id varchar(255) not null,
        property_type varchar(255) not null,
        new_value_specified bit,
        old_value_specified bit,
        new_value varchar(1024),
        old_value varchar(1024),
        property_name varchar(255) not null,
        life_cycle_audit_event varchar(255) not null,
        primary key (id)
    );

    create table session_records (
        id varchar(255) not null,
        system_subject_id varchar(255),
        system_subject_type varchar(255),
        system_subject_discriminator varchar(255),
        ended_ts datetime,
        subjectId varchar(255),
        responsible_subject_type varchar(255),
        responsible_subject_discriminator varchar(255),
        responsible_address varchar(255),
        responsible_agent varchar(255),
        started_ts datetime,
        system_address varchar(255),
        session_id varchar(255),
        primary key (id)
    );

    create table transaction_records (
        id varchar(255) not null,
        ended_ts datetime,
        started_ts datetime,
        transaction_id varchar(128) not null,
        session_record varchar(255) not null,
        primary key (id)
    );

    alter table business_audit_events 
        add index FK4B57881CAD45DAD7 (transaction_record), 
        add constraint FK4B57881CAD45DAD7 
        foreign key (transaction_record) 
        references transaction_records (id);

    alter table consumption_events 
        add index FK58D4B63DAD45DAD7 (transaction_record), 
        add constraint FK58D4B63DAD45DAD7 
        foreign key (transaction_record) 
        references transaction_records (id);

    alter table life_cycle_audit_events 
        add index FKB5B11AD9AD45DAD7 (transaction_record), 
        add constraint FKB5B11AD9AD45DAD7 
        foreign key (transaction_record) 
        references transaction_records (id);

    alter table membership_change_audit_events 
        add index FKE7B0E843AD45DAD7 (transaction_record), 
        add constraint FKE7B0E843AD45DAD7 
        foreign key (transaction_record) 
        references transaction_records (id);

    alter table property_value_changes 
        add index FK61FFF10BFF0BCAB9 (life_cycle_audit_event), 
        add constraint FK61FFF10BFF0BCAB9 
        foreign key (life_cycle_audit_event) 
        references life_cycle_audit_events (id);

    alter table transaction_records 
        add index FK631DD9E151A4AA57 (session_record), 
        add constraint FK631DD9E151A4AA57 
        foreign key (session_record) 
        references session_records (id);

```