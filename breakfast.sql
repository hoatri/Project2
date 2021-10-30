create database breakfast;

use breakfast;

create table category(
	categoryID int primary key identity(1,1),
	category varchar(255)
)

create table [admin](
	adminID int identity(1,1) primary key,
	adminName varchar(255) NOT NULL,	
	email varchar(255) NOT NULL,
	password_hash varchar(40) NOT NULL,
	phone varchar(20) not null
)

create table customer(
	customerID int primary key identity(1,1),
	fullName varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password_hash varchar(40) NOT NULL,
	[address] varchar(255) DEFAULT '',
	phone varchar(20) DEFAULT ''
)

create table product(
	productID int identity(1,1) primary key,
	productName varchar(255) NOT NULL,
	categoryID int DEFAULT NULL,
	[description] varchar(255) NOT NULL,
	stock int NOT NULL,
	[image] varchar(255) NOT NULL,
	price float NOT NULL,
	FOREIGN KEY (categoryID) REFERENCES category(categoryID)
)

create table [order](
	orderId int primary key identity(1,1),
	customerId int NOT NULL,
	date date NOT NULL,

	FOREIGN KEY (customerId) REFERENCES customer(customerId)
)

drop table product
drop table category
insert into 