# serverApplication
Simple Dummy Server Application.


*********************ACCOUNT*********************

1) Create Account :

Http Method : POST

URL : http://localhost:8080/api/account/create

Body : 

{     "balance":100,
   "accountUsername":"a",
   "accountUserSurname":"a",
   "accountType":"EURO"
}


2) Get All Accounts :

Http Method : GET

URL : http://localhost:8080/api/account/accounts


3) Get Account By accountId :

Http Method : GET

URL : http://localhost:8080/api/account/{accountId}



4) Get Account Balance By accountId :

Http Method : GET

URL : http://localhost:8080/api/account/balance/{accountId}


*********************TRANSACTION*********************

1) Make Transfer Between Accounts :

Http Method : POST

URL : http://localhost:8080/api/transaction/doTransfer

Body : 

{     "senderId":21,
   "receiverId":23,
   "transactionAmount":50
}



2) Get Transaction By tansactionId :

Http Method : GET

URL : http://localhost:8080/api/transaction/{tansactionId}



3) Get All Transactions :

Http Method : GET

URL : http://localhost:8080/api/transaction/transactions


