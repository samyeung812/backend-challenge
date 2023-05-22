# Synpulse8 backend hiring challenge  

## Requirements

* MySQL
* Kafka
* Zookeeper
* Java 17

## API

### Endpoints

| Endpoint                                      | Method | Description                                 | 
|:----------------------------------------------|:-------|:--------------------------------------------| 
| [`/auth/register`](#register)                 | POST   | Register new user                           |
| [`/auth/authenticate`](#authenticate)         | POST   | Authenticate user and retrieve jwt          |
| [`/transactions/publish`](#post-transaction)  | POST   | Publish a transaction                       |
| [`/transactions/get`](#get-transactions)      | GET    | Fetch transactions for specific month       |

___

#### Register
```http
POST /auth/register
```

##### Request body

`(Content-Type: application/json)`

| Parameter       | Type     | Example      |
|:----------------|:---------|:-------------|
| `username`      | `String` | "user"       |
| `password`      | `String` | "p@ssw0rd"   |


##### Response

| Code | Body                                                              |
|:-----|:------------------------------------------------------------------|
| 200  | ```{"userID": String, "username": String, "password": String} ``` |
| 400  | `Bad Request`                                                     |

___

#### Authenticate

```http
POST /auth/authenticate
```

##### Request body

`(Content-Type: application/json)`

| Parameter    | Type     | Example            |
|:-------------|:---------|:-------------------|
| `username`   | `String` | "user"             |
| `password`   | `String` | "p@ssw0rd"         |



##### Response

| Code  | Body                       |
|:------|:---------------------------|
| 200   | ```{"token": String} ```   |
| 403   | `Forbidden`                |

___

#### POST Transaction
```http
POST /transactions/publish
```

##### Headers

| Parameter         | Type     | Example            |
|:------------------|:---------|:-------------------|
| `Authorization`   | `String` | "Bearer <jwt>"     |

##### Request body

`(Content-Type: application/json)`

| Parameter    | Type                 | Example                     |
|:-------------|:---------------------|:----------------------------|
| `currency`   | `String`             | "USD 100"                   |
| `accountIBAN`| `String`             | "CH93-0000-0000-0000-0000-0"|
| `date`       | `String`             | "23-05-2023"                |
| `description`| `String`             | "Transaction description"   |

___

#### GET Transactions
```http
GET /transactions/get?date=05-2023&currency=USD
```

##### Headers

| Parameter          | Type     | Example            |
|:-------------------|:---------|:-------------------|
| `Authorization`    | `String` | "Bearer <jwt>"     |

##### Query parameters

| Parameter  | Type     | Example   |
|:-----------|:---------|:----------|
| `date`     | `String` | "05-2023" | 
| `currency` | `String` | "USD"     | 
