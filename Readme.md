
java + Spring Boot/Spring security +JPA+ redis + Microservice POC

Use API gateway and Discovery server, use any database as per you convenience(Mysql, cassendra or any other)

Maintain logging of entry exit through Spring AOP.

You have to create 4 microservice Service(client) 1 Service as Discovery Server and register all your microservice as client to server,

Enable Spring boot admin as well)

1. Register Service



Customer registration

(Take Data from Customer and store in DB(like customer personal info, address info etc)

2)Login Service

Customer Login(through Facbook/Google etc and through Biometric(facial and fingerprint)

verify Customer from DB( by username and Password and create one session and that will be available

throughout customer Journey)

Implement JWT token on success login.

**Seperate DB for each Microservice

3)Product Service (without Login Customer will not able to do below operation)

Validate JWT token in each below request and only User in ADMIN role will able to do below

operation

Add product

delete product(soft delete).

update product price
Excelude this API from Security

any user can access below service

get product by name(cache the frequent used data in cache(redis)

get product by category(cache the frequent used data in cache(redis)

get product by price order asc and Desc.

4. Cart Service(without Login Customer will not able to do below operation)



Only authorized USER will able to do below Operation Protect Your API for un-authorized access(by using auth token in request header)

1. Maintain customer specific Cart


2. Add product to Cart(through voice command as well)


3. Delete product from cart(through voice command as well)


4. junit case



