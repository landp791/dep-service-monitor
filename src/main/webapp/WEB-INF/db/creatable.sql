create table service(serviceId varchar(42), serviceName varchar(100), serviceUrl varchar(200), constraint pk_service primary key (serviceId));

create table owner(umAccount varchar(50), name varchar(20), mail varchar(60), constraint pk_owner primary key (umAccount));
       
create table serviceOwner(serviceId varchar(42), umAccount varchar(50), constraint pk_service_owner primary key (serviceId, umAccount)); 