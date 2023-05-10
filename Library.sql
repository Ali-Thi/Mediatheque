drop table members cascade;
drop table documents cascade;
drop table type_document cascade;

SET TIMEZONE='Europe/Paris';

create table members(
    id serial PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    birthday date
);

create table documents(
    id serial PRIMARY KEY,
    type_doc int,
    title varchar(100),
    for_adult BOOLEAN,
    id_borrower int,
    id_reserver int
);

create table type_document(
    id serial PRIMARY KEY,
    type_doc varchar(50)
);

alter table members 
    add constraint CK_BIRTHDAY check(birthday <= NOW()),
    add constraint U_NAME_BIRTHDAY unique(first_name, last_name, birthday);

alter table documents 
    add constraint FK_TYPE foreign key (type_doc) references type_document(id), 
    add constraint FK_ID_BORROWER foreign key (id_borrower) references members(id), 
    add constraint FK_ID_RESERVER foreign key (id_reserver) references members(id),
    add constraint U_TYPE_TITLE_FORADULT unique(type_doc, title, for_adult),
    add constraint CK_ID_BORROWER check(id_borrower = id_reserver or id_borrower is null),
    add constraint CK_ID_SERVER check(id_borrower = id_reserver or id_reserver is null);

alter table type_document add constraint U_TYPE unique(type_doc);


CREATE OR REPLACE FUNCTION NAME_TO_UPPER() RETURNS TRIGGER
AS $$
    BEGIN
        NEW.first_name = UPPER(NEW.first_name);
        NEW.last_name = UPPER(NEW.last_name);
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER upper_name BEFORE INSERT OR UPDATE ON members
    FOR EACH ROW EXECUTE FUNCTION NAME_TO_UPPER(id);


insert into type_document(type_doc) values('DVD');

insert into members(first_name, last_name, birthday) values('Tony', 'Yaka', '2003-09-03');
insert into members(first_name, last_name, birthday) values('Patricia', 'Finner', '1996-04-12');
insert into members(first_name, last_name, birthday) values('Kevin', 'Lirayeu', '2017-08-07');

insert into documents(type_doc, title, for_adult) values(1, 'Les jours sombres', false);
insert into documents(type_doc, title, for_adult) values(1, 'Poliedrum', false);
insert into documents(type_doc, title, for_adult) values(1, 'Kamakin', true);