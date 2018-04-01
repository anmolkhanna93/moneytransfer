package com.interview.dao;

import com.interview.model.Account;
import com.interview.model.Transactions;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

public class ServiceDAOTest {

    Set<Account> accountSet;
    Account account1,account2,account3;
    Transactions sameAccounts;
    Transactions successfullTransactions;
    Transactions insufficientBalanceTransactions;
    Transactions verifyInvalidAccountTransactions;

    private ServiceDAO dao;


    @Before
    public void setUp() throws Exception {

        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api/";

        dao = new ServiceDAO();
        accountSet = new HashSet<>();
        account1 = new Account("1",100.0);
        account2 = new Account("2",120.0);
        account3 = new Account("3",700.0);
        accountSet.add(account1);
        accountSet.add(account2);
        accountSet.add(account3);
        sameAccounts = new Transactions("1","1",10.0);
        successfullTransactions = new Transactions("1","2",10.0);
        insufficientBalanceTransactions = new Transactions("2","3",1000.0);
        verifyInvalidAccountTransactions = new Transactions("6","1",100.0);


    }

    @Test
    public void makeSurePingApiEndPointIsUp() {
        given().when().get("http://localhost:8080/api/ping").then().statusCode(200);
    }

    @Test
    public void makeSureGETApiEndPointIsUp(){
        given().when().get("http://localhost:8080/api/accounts").then().statusCode(200);
    }

    @Test
    public void makeSureGetByIdApiEndPointIsUp(){
        given().when().get("http://localhost:8080/api/accounts/1").then().statusCode(200);
    }

    @Test
    public void makeSureTransferApiEndPointIsUp(){
        given().when().get("http://localhost:8080/api/transfer").then().statusCode(405);
    }

    @Test
    public void verifyAccountId() {
        given().when().get("/accounts").then()
                .body(containsString("1"));
    }

    @Test
    public void verifyTransferEndPointStatus() {

        given().contentType("application/json")
                .body(successfullTransactions)
                .when().post("/transfer").then()
                .statusCode(200);
    }

    @Test
    public void verifySuccefullTransaction() {

        given().contentType("application/json")
                .body(successfullTransactions)
                .when().post("/transfer").then()
                .body("transactionId",equalTo(2))
                .body("balance", equalTo(80.0f));
    }

    @Test
    public void verifySameAccounts() {

        given().contentType("application/json")
                .body(sameAccounts)
                .when().post("/transfer").then()
                .body("errorMessage",equalTo("Cannot proceed, transactions happening between same accounts"));
    }

    @Test
    public void verifyInvalidAccounts() {

        given().contentType("application/json")
                .body(verifyInvalidAccountTransactions)
                .when().post("/transfer").then()
                .body("errorMessage",equalTo("Account with id: 6 is Invalid"));
    }

    @Test
    public void verifyZeroAmountTransaction() {

        given().contentType("application/json")
                .body(new Transactions("1","2",0.0))
                .when().post("/transfer").then()
                .body("errorMessage",equalTo("Amount being transferred is not applicable"));
    }

    @Test
    public void verifyNegativeAmountTransaction() {

        given().contentType("application/json")
                .body(new Transactions("1","2",-2.0))
                .when().post("/transfer").then()
                .body("errorMessage",equalTo("Amount being transferred is not applicable"));
    }

    @Test
    public void verifyInsufficientBalanceTransaction() {

        given().contentType("application/json")
                .body(insufficientBalanceTransactions)
                .when().post("/transfer").then()
                .body("errorMessage",equalTo("Account with id: 2 has Insufficient Balance"));
    }
}