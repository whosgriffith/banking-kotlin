# Banking Transactions DEMO
A RESTful API example for a simple transactions processing application.

## REQUIREMENTS

### PostgreSQL

This application uses PostgreSQL as its database. Ensure you have PostgreSQL installed and running on your system. You can download and install PostgreSQL from the [official website](https://www.postgresql.org/download/).

### Environment Variables

The application requires certain environment variables to be set for the database configuration. These variables are used to configure the connection to the PostgreSQL database. Ensure you have the following environment variables set:

- `DB_URL`: The URL of your PostgreSQL database.
- `PG_USER`: The username for your PostgreSQL database.
- `PG_PASSWORD`: The password for your PostgreSQL database.

The DB configuration in `application.properties` looks like this:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${PG_USER}
spring.datasource.password=${PG_PASSWORD}
```



> **_NOTE:_** Some configurations in `applications.properties` are for DEBUGGING and must be changed before running the application in a production environment.


## API

#### /api/account
* `GET` : Get a test accout

#### /api/accounts
* `GET` : Get a list of all the accounts
* `POST` : Create a new account

#### /api/transaction
* `POST` : Send a transaction to be processed


## Tests

The tests for `calclularImpuestos()` can be run from the file `TransactionTest.kt` in `/test/kotlin/com.banking.transactions`
