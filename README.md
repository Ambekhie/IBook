# IBook
Online Multi-threaded Book Store - JAVA - Application.

## Order Processing System

A well designed database for the central management of a book store, to be used for online management and procurement.
The main task is to discriminate between two types of users: managers and customers and add some functionalities to each type.

## Three-Tier Architecture 

The Application consists mainly of three separate softwares 
1. client view (workstation)
2. server side (business logic)
3. Model (database) 
 
A TCP connection is established between clients and server side using socket programming.
The server side establishes a connection with MYSQL database using JDBC

## Why using Three-Tier Architecture ?

1. Keeps critical data away from users (kept at server side).
2. Application supports multiple users (Multi-threaded)
3. Application is scalable, maintainable and extensible. 


## About Database relations

For each book in the store, the system keeps the book’s ISBN number, title, author(s), publisher, publication
year, selling price, and category. The book’s category can be one of the following: "Science", "Art", "Religion",
"History" and "Geography". The system keeps track of the names of publishers, their addresses and
telephone number. Information about book orders is also maintained.

## About Users 

Only previously registered users can login to the system. New customers are able to sign up for a new customer
account by providing the necessary information: user name, password, last name, first name, e-mail address,
phone number, and shipping address. A registered customer can do the following activities.
1. Edit his personal information including his password
2. Search for books by any of the book’s attributes.
3. Add books to a shopping cart
4. Manage his shopping cart : 
    - View the items in the cart
    - View the individual and total prices of the books in the cart
    - Remove items from the cart
5. Checkout a shopping cart :
    - The customer is then required to provide a credit card number and its expiry date. This transaction is
    completed successfully if the credit card information is appropriate.
    - The book’s quantities in the store are updated according to this transaction.
6. Logout of the system
    - Doing this will remove all the items in the current cart.

## Managers :
Manager can do the same operations that a normal customer can do plus the following :

1. Add new books
2. Modify existing books
3. Place orders for books
4. Confirm orders
5. Promote registered customers to have managers credentials
6. View the following reports on sales
    - The total sales for books in the previous month
    - The top 5 customers who purchase the most purchase amount in descending order for the last three
    months
    - The top 10 selling books for the last three months
 
## About system load

- Number of books in the database: 1,000,000
- Average Number of authors: 500,000
- Number of inquires to the database( using the book ISBN, title or category) per day: 150,000
- Number of customer purchases per day: 50,000
- Average number of book orders generated per day :10
