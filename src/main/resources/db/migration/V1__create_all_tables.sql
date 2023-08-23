create table if not exists patients
(
      id integer not null primary key autoincrement ,
      login varchar(255) unique not null,
      password varchar(255) not null,
      icon blob,
      deleted boolean not null
) ;

insert into patients(id, login, password, icon, deleted)
values (-1, 'patient1', 'patient1', null, false),
       ( -2, 'patient2', 'patient2', null, false),
       (-3, 'patient3', 'patient3', null, false);