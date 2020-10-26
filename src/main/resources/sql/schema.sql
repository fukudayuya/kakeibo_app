create table users(
userid serial primary key,
name text,
email text,
password text
);

create table savings(
savingsid serial primary key,
userid int,
targetSavings int,
nowSavings int,
totalSavings int,
savingStatus int
);

create table spendgenre(
spendgenreid serial primary key,
spendgenrename text
);

create table spend(
spendid serial primary key,
spendgenreid int,
title text,
userid int,
spenddate date,
contents text,
price int
);

create table incomegenre(
incomegenreid serial primary key,
incomegenrename text
);

create table income(
incomeid serial primary key,
incomegenreid int,
title text,
userid int,
incomedate date,
contents text,
price int
);

create table confirm(
confirmid serial primary key,
userid int,
confirmDate date,
confirmStatus int
);

create table autoinput(
autoinputid serial primary key,
inputsetid int,
incomegenreid int,
title text,
userid int,
contents text,
price int
);

create table autospend(
autospendid serial primary key,
inputsetid int,
spendgenreid int,
title text,
userid int,
contents text,
price int
);