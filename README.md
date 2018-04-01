# moneytransfer

### Introduction

This a project for Circle Interview Process. In this project, I have create a rest API for supporting the transfer amount functionality. The API allows to transfer amount from one account to another account, Say from Account A to Account B.

### Data Models

This project consists of two data tables. First is the Accounts table and the Second is the transactions table. 

- Accounts Table : This table is the main data table for the project. It consists of all the cutomer records. The schema for this table is

```id        TEXT UNIQUE NOT NULL,
  balance   DECIMAL(18,2) NOT NULL DEFAULT 0,
  CONSTRAINT pk_accounts_id PRIMARY KEY (id)
```
  
  






# Diamonds

### Introduction

This is a small example Stencila document, stored as [Markdown in a Github repository](https://github.com/stencila/examples/diamonds), which illustrates:

- using multiple languages within a single document
- passing data between languages
- using an output to display a variable
- using a inputs to create an interactive document

### Data

We analysed the [diamonds data set](http://ggplot2.tidyverse.org/reference/diamonds.html) which contains the prices, carat, colour and other attributes of almost 54,000 diamonds. This data is also available in the Github repo as a [csv file](https://github.com/stencila/examples/blob/master/diamonds/data.csv). A random sample of [1000]{name=sample_size type=range min=100 max=10000 step=100} diamonds was taken from the data (using Python).

```py
#! data = (sample_size)
return pandas.read_csv('data.csv').sample(sample_size)
```

### Methods

We calculated the number and mean price of diamonds in each color category: J (worst) to D (best) (using SQLite).

```sql
--! summary = (data)
SELECT color, count(*) diamonds, round(avg(price), 2) AS price FROM data GROUP BY color
```

We then used R to perform a generalised linear model of diamond price using carat and price as explanatory variables.

```r
#! pseudo_r2 = (data)
model <- glm(price~carat+color, data=data)
round(1-model$deviance/model$null.deviance,2)
```
