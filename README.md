# moneytransfer

### Introduction

This a project for Circle Interview Process. In this project, I have create a rest API for supporting the transfer amount functionality. The API allows to transfer amount from one account to another account, Say from Account A to Account B.

### Data Models

This project consists of two data tables. First is the Accounts table and the Second is the transactions table. 

- Accounts Table : This table is the main data table for the project. It consists of all the cutomer records. The schema for this table is

```
  id        TEXT UNIQUE NOT NULL,
  balance   DECIMAL(18,2) NOT NULL DEFAULT 0,
  CONSTRAINT pk_accounts_id PRIMARY KEY (id)
```
Here the id represents the cutomers Account id, balance represents current balance in the Account and id acts as the primary key.

Following are the enteries currently in the Accounts table

```
  id      balance
  '1'     100
  '2'     120
  '3'     700
  '4'     1000
```


- Transaction Table: I have created this table to keep records of the transactions taking place between the customers. Following is the schema for the table.

```
  id  SERIAL ,
  fromAccountId  TEXT NOT NULL,
  toAccountId    TEXT NOT NULL,
  amountToTransfer   int NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
```
Here the id is the transaction id, fromAccountId represents the account that initiates the transaction, toAccountId represents the account to which money is being transferred and amountToTransfer is the amount that was transfered between the accounts. This transaction table is updated only for the successful transactions.

### Usecases Covered 

Transaction between the two accounts will be unsuccessful in the following Usecases :

- If both the fromAccountId and toAccountId entered are same
- If one of the accounts entered is incorrect, i.e its not present in the Accounts table
- If the amountToTransfer entered is less than equal to zero
- If balance in the account with fromAccountId has zero or insufficient balance

If we meet any of the above mentioned usecases then the transaction wont be successful otherwise it will be successful.

### TestCases Demo

The test cases below show all the usecases discussed above

- Unsuccessful case, when the accounts entered are same:

Request: 

```
{
  "fromAccountId": "1",
  "toAccountId": "1",
  "amountToTransfer": 10.0
}
```

Response:

```
{
  "errorCode": 400,
  "errorMessage": "Cannot proceed, transactions happening between same accounts"
}
```

- Unsuccessful case, when one of the accounts entered is incorrect :

Request: 

```
{
  "fromAccountId": "1",
  "toAccountId": "20",
  "amountToTransfer": 10.0
}
```

Response:

```
{
  "errorCode": 400,
  "errorMessage": "Account with id: 20 is Invalid"
}
```

- Unsuccessful case, If the amountToTransfer entered is less than equal to zero :

Request: 

```
{
  "fromAccountId": "1",
  "toAccountId": "2",
  "amountToTransfer": 0.0
}
```

Response:

```
{
  "errorCode": 400,
  "errorMessage": "Amount being transferred is not applicable"
}
```

- Unsuccessful case, If balance in the account with fromAccountId has zero or insufficient balance :

Here fromAccountId has balance of 100

Request: 

```
{
  "fromAccountId": "1",
  "toAccountId": "2",
  "amountToTransfer": 500.0
}
```

Response:

```
{
  "errorCode": 400,
  "errorMessage": "Account with id: 1 has Insufficient Balance"
}
```

- Successful transaction from account 1 to 2, Account has balance of 1

Request: 

```
{
  "fromAccountId": "1",
  "toAccountId": "2",
  "amountToTransfer": 10.0
}
```

Response:

```
{
  "transactionId": 1,
  "balance": 90
}
```

### Starting the Application

- Import the project as a maven project in the editor of your choice. I have used Intellij.
- Then do mvn clean build to build the project.
- Run the project. If the project doesnt run, you will have to edit the run configuration in your editor by selecting the application configuration and setting the Proagram Arguments as "server configuration.yml"
- Once the server starts, you can headover to your browser or play with the API endpoint in Postman
- To run the tests, you need to start the server by running the Server Application
- 1. To access all the accounts in the accounts table,
     http://localhost:8080/api/accounts
  2. To access an account by its account id,
     http://localhost:8080/api/accounts/1
  3. To make use of the transfer API, the endpoint is
     http://localhost:8080/api/transfer
     
     You can access the endpoint either doing a curl command or using Postman.
     
     If using Postman, set the header propeties as the following
     Content-Type -> application/json
     
     Then in body, you can pass the body as following,
     
     ```
      {
          "fromAccountId": "1",
          "toAccountId": "2",
          "amountToTransfer": 10.0
      }
    ```
   
### Application Walkthrough

![alt tag](https://github.com/anmolkhanna93/moneytransfer/blob/master/demo.gif)

### Technologies and Frameworks used

- The code is written java and have used dropwizard.io framwork for structuring my project.
- PostgressSql is used, which is running in the Docker container.
- For doing the testing, I have used Rest-Assured API, which provides easy to use methods to do the testing of the Rest API.
- Postman, to test and play with the endpoints
