create database breakfast;

use breakfast;

create TABLE `category`(
	categoryID int primary key auto_increment,
	`category` varchar(255)
);

create TABLE `admin`(
	adminID int primary KEY auto_increment,
	adminName varchar(255) NOT NULL,	
	email varchar(255) NOT NULL,
	password_hash varchar(40) NOT NULL,
	phone varchar(20) DEFAULT ''
);
admin
create TABLE `customer`(
	customerID int primary key auto_increment,
	fullName varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password_hash varchar(40) NOT NULL,
	address varchar(255) DEFAULT '',
	phone varchar(20) DEFAULT ''
)
insert INTO `customer`(fullName,email,password_hash)
values ('Duc Hoa','hoa@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab'),
('Xuan Chinh','chinh@gmail.com','356a192b7913b04c54574d18c28d46e6395428ab')

create TABLE `product`(
	productID int auto_increment primary key,
	productName varchar(255) NOT NULL,
	categoryID int DEFAULT NULL,
	description varchar(255) NOT NULL,
	stock int NOT NULL,
	image varchar(255) NOT NULL,
	price float NOT NULL,
	FOREIGN KEY (categoryID) REFERENCES `category`(categoryID)
)

create TABLE `order`(
	orderID int primary key auto_increment,
	customerId int NOT NULL,
	`date` date NOT NULL,
	status int not null,
	FOREIGN KEY (status) REFERENCES status(ID),
	FOREIGN KEY (customerId) REFERENCES `customer`(customerId)
)

create table orderDetail(
	orderID int not null,
	productID int not null,
	quantity int not null,
	price FLOAT,
	foreign key (orderID) REFERENCES `order`(orderID),
	foreign key (productID) REFERENCES `product`(productID)
)admin

create table status(
	ID int primary key auto_increment,
	status varchar(20) not null
)