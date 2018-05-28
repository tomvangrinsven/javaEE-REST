/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     28-3-2018 23:21:42                           */
/*==============================================================*/


drop table if exists PROVIDER;

drop table if exists SUBSCRIBER;

drop table if exists SUBSCRIPTION;

drop table if exists SUBSCRIPTIONLENGTH;

drop table if exists SUBSCRIPTIONSTATUS;

drop table if exists SERVICE;

drop table if exists SHAREDSUBSCRIPTION;

drop table if exists PRICES;

drop table if exists TOKEN;

drop table if exists USER;

create table PROVIDER
(
   Name varchar(256) not null,
   primary key (Name)
);

create table SUBSCRIBER
(
   ID                   int not null auto_increment,
   NAME                 varchar(256) not null,
   EMAIL                varchar(256) not null,
   primary key (ID)
);

create table SUBSCRIPTION
(
   ID                   int not null auto_increment,
   SUBSCRIBER		int not null,
   STATUS               varchar(256) not null,
   SERVICE               int not null,
   LENGTH		varchar(255) not null,
   STARTDATE           date not null,
   ENDDATE            date,
   DOUBLED           boolean not null,
   primary key (ID)
);

create table SUBSCRIPTIONLENGTH
(
   Length               varchar(255) not null,
   primary key (Length)
);

create table SUBSCRIPTIONSTATUS
(
   STATUS               varchar(256) not null,
   primary key (STATUS)
);

create table SERVICE
(
   NAME                 varchar(256) not null,
   SHAREABLE             int not null,
   DOUBLEABLE        boolean not null,
   PROVIDER            varchar(256) not null,
   ID                   int not null auto_increment,
   primary key (ID)
);

create table SHAREDSUBSCRIPTION
(
   SUBSCRIBERID            int not null,
   SUBSCRIPTIONID         int not null,
   primary key (SUBSCRIBERID, SUBSCRIPTIONID)
);

create table PRICES
(
   SERVICE               int not null,
   LENGTH               varchar(255) not null,
   PRICE                double not null,
   primary key (SERVICE, LENGTH)
);

create table TOKEN
(
   TOKEN                varchar(19) not null,
   ID                   int not null,
   EXPIRES              datetime not null,
   primary key (TOKEN)
);

create table USER
(
   USERNAME             varchar(256) not null,
   ID                   int not null,
   PASSWORD             varchar(256) not null,
   primary key (USERNAME)
);

alter table SUBSCRIPTION add constraint FK_subscriberinsubscription foreign key (SUBSCRIBER)
      references SUBSCRIBER (ID) on delete restrict on update restrict;

alter table SUBSCRIPTION add constraint FK_serviceinsubscription foreign key (SERVICE)
      references SERVICE (ID) on delete restrict on update restrict;

alter table SUBSCRIPTION add constraint FK_lengthofsubscription foreign key (LENGTH)
      references SUBSCRIPTIONLENGTH (LENGTH) on delete restrict on update restrict;

alter table SUBSCRIPTION add constraint FK_statusinsubscription foreign key (STATUS)
      references SUBSCRIPTIONSTATUS (STATUS) on delete restrict on update restrict;

alter table SERVICE add constraint FK_providerinservice foreign key (PROVIDER)
      references PROVIDER (NAME) on delete restrict on update restrict;

alter table SHAREDSUBSCRIPTION add constraint FK_subscription foreign key (SUBSCRIPTIONID)
      references SUBSCRIPTION (ID) on delete restrict on update restrict;

alter table SHAREDSUBSCRIPTION add constraint FK_user foreign key (SUBSCRIBERID)
      references SUBSCRIBER (ID) on delete restrict on update restrict;

alter table PRICES add constraint FK_priceforlength foreign key (LENGTH)
      references SUBSCRIPTIONLENGTH (LENGTH) on delete restrict on update restrict;

alter table PRICES add constraint FK_priceinservice foreign key (SERVICE)
      references SERVICE (ID) on delete restrict on update restrict;

alter table TOKEN add constraint FK_tokenofsubscriber foreign key (ID)
      references SUBSCRIBER (ID) on delete restrict on update restrict;

alter table USER add constraint FK_userinsubscriber foreign key (ID)
      references SUBSCRIBER (ID) on delete restrict on update restrict;


insert into SUBSCRIBER (name, email) values ('Tom van Grinsven', 'zegik@lekker.niet');
insert into SUBSCRIBER (name, email) values ('Meron', 'Meron.Brouwer@han.nl');
insert into SUBSCRIBER (name, email) values ('Dennis', 'Dennis.Breuker@han.nl');
insert into SUBSCRIBER (name, email) values ('Michel', 'Michel.Portier@han.nl');
insert into User values ('zegik@lekker.niet', 1, 'pwd');

insert into PROVIDER values ('h2 is awesome');

insert into SUBSCRIPTIONLENGTH values ('maand'),('jaar'),('half jaar');

insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('H2 telefonie', 0, false, 'h2 is awesome');
insert into PRICES values (1, 'maand', 5.00),(1, 'half jaar', 25.00),(1, 'jaar', 45.00);
insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('In-memory', 0, true, 'h2 is awesome');
insert into PRICES values (2, 'maand', 10.00),(2, 'half jaar', 50.00),(2, 'jaar', 90.00);
insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('Glasvezel-internet (download 500Mbps)', 0, true, 'h2 is awesome');
insert into PRICES values (3, 'maand', 40.00),(3, 'half jaar', 200.00),(3, 'jaar', 360.00);
insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('Kabel-internet (download 300Mbps)', 0, false, 'h2 is awesome');
insert into PRICES values (4, 'maand', 30.00),(4, 'half jaar', 150.00),(4, 'jaar', 270.00);
insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('Eredivisie Live 1,2,3,4 en 5', 2, false, 'h2 is awesome');
insert into PRICES values (5, 'maand', 10.00),(5, 'half jaar', 50.00),(5, 'jaar', 90.00);
insert into SERVICE (NAME, SHAREABLE, DOUBLEABLE, PROVIDER) values ('HBO Plus', 2, false, 'h2 is awesome');
insert into PRICES values (6, 'maand', 15.00),(6, 'half jaar', 75.00),(6, 'jaar', 135.00);

insert into SUBSCRIPTIONSTATUS values ('proef'),('actief'),('opgezegd');

insert into SUBSCRIPTION (SUBSCRIBER, STATUS, SERVICE, LENGTH, STARTDATE, DOUBLED) values
  (1, 'actief', 1, 'maand', '2018-01-27', false),
  (1, 'actief', 2, 'maand', '2018-01-27', false),
  (1, 'actief', 4, 'maand', '2018-01-27', false),
  (2, 'actief', 5, 'maand', '2018-01-27', false);

insert into SHAREDSUBSCRIPTION values (1, 4);
INSERT INTO token VALUES('token', 1, current_date);


