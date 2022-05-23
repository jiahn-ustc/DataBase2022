create table client(
    client_id varchar(18) primary key,
    name varchar(32) not null,
    tel varchar(11) ,
    address varchar(32) ,
    contact_name varchar(32) ,
    contact_tel varchar(11) ,
    contact_email varchar(32) ,
    contact varchar(16) 
);
create table bank(
    bank_name varchar(16) primary key,
    city varchar(16) not null,
    amount float not null
);
create table employee(
    employee_id varchar(18) primary key,
    name varchar(32) not null,
    bank_name varchar(16) not null,
    tel varchar(11) ,
    address varchar(32) ,
    constraint FK_BName foreign key (bank_name)
        REFERENCES bank (bank_name)
);
create table department(
    department_id varchar(8) primary key,
    name varchar(16),
    type varchar(16)
);
create table manage_department(
    bank_name varchar(16),
    department_id varchar(8),
    manager_id varchar(18) not null,
    primary key (bank_name, department_id),
    constraint FK_BName10 foreign key (bank_name)
        REFERENCES bank(bank_name),
    constraint FK_MID foreign key (manager_id)
        REFERENCES employee(employee_id)
);
create table employee_work(
    employee_id varchar(18) primary key,
    department_id varchar(8) not null,
    start_time date not null,
    constraint FK_DID foreign key (department_id)
        REFERENCES department (department_id)
);
create table account(
    account_id varchar(8) primary key,
    account_type varchar(16)
);

create table saving_account(
    account_id varchar(8) primary key,
    bank_name varchar(16) not null,
    client_id varchar(18) not null,
    balance float not null default 0,
    start_time datetime not null,
    rate float,
    currency_type varchar(16),
    last_time datetime,
    constraint FK_AID foreign key (account_id)
        REFERENCES account(account_id),
    constraint FK_BName1 foreign key (bank_name)
        REFERENCES bank(bank_name),
    constraint FK_CID foreign key (client_id)
        REFERENCES client(client_id)
);
create table check_account(
    account_id varchar(8) primary key,
    bank_name varchar(16) not null,
    client_id varchar(18) not null,
    balance float not null default 0,
    start_time datetime not null,
    overdraft float default 0,
    last_time datetime,
    constraint FK_AID1 foreign key (account_id)
        REFERENCES account(account_id),
    constraint FK_BName2 foreign key (bank_name)
        REFERENCES bank(bank_name),
    constraint FK_CID1 foreign key (client_id)
        REFERENCES client(client_id)
);
create table loan(
    loan_id varchar(8) primary key,
    bank_name varchar(16) ,
    amount float not null,
    constraint FK_BName3 foreign key (bank_name)
        REFERENCES bank(bank_name)
);

create table client_get_loan(
    client_id varchar(18) ,
    bank_name varchar(16) ,
    loan_id varchar(8) ,
    primary key (client_id, loan_id),
    constraint FK_CID2 foreign key (client_id)
        REFERENCES client(client_id),
    constraint FK_BName4 foreign key (bank_name)
        REFERENCES bank(bank_name),
    constraint FK_LID foreign key (loan_id)
        REFERENCES loan(loan_id)
);

create table loan_pay(
    bank_name varchar(16) ,
    loan_id varchar(8) ,
    pay_code varchar(16) ,
    primary key (loan_id, pay_code),
    pay_date datetime not null,
    pay_amount float not null,
    constraint FK_BName5 foreign key (bank_name)
        REFERENCES bank(bank_name),
    constraint FK_LID1 foreign key (loan_id)
        REFERENCES loan(loan_id)
);



create table employee_client(
    client_id varchar(18) ,
    employee_id varchar(18) ,
    type varchar(16) ,
    primary key (client_id, employee_id),
    constraint FK_CID3 foreign key (client_id)
        REFERENCES client(client_id),
    constraint FK_EID foreign key (employee_id)
        REFERENCES employee(employee_id)
);


