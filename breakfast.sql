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
	phone varchar(20) DEFAULT ''
)
insert into [admin](adminName,email,password_hash)
values ('Duc Hoa','hoa@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab'),
('Xuan Chinh','chinh@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab')

create table customer(
	customerID int primary key identity(1,1),
	fullName varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password_hash varchar(40) NOT NULL,
	[address] varchar(255) DEFAULT '',
	phone varchar(20) DEFAULT ''
)
insert into customer(fullName,email,password_hash)
values ('Duc Hoa','hoa@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab'),
('Xuan Chinh','chinh@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab')

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
	orderID int primary key identity(1,1),
	customerId int NOT NULL,
	date date NOT NULL,
	[status] int not null,
	FOREIGN KEY ([status]) REFERENCES [status](ID),
	FOREIGN KEY (customerId) REFERENCES customer(customerId)
)

create table orderDetail(
	orderID int not null,
	productID int not null,
	quantity int not null,
	price float
	foreign key (orderID) references [order](orderID),
	foreign key (productID) references product(productID)
)

create table [status](
	ID int primary key identity(1,1),
	[status] varchar(20) not null
)

INSERT INTO status([status])
VALUES('Pending'),
('Confirmed'),
('Cancelled'),
('Enabled'),
('Disable')
