# bankmoneytransfer
Simple money transfer application for revolut


*********************ACCOUNT*********************

Create Account :

Http Method : POST

URL : http://localhost:8080/api/account/create

Body : 

{     "balance":100,
   "accountUsername":"a",
   "accountUserSurname":"a",
   "accountType":"EURO"
}



Get All Accounts :

Http Method : GET

URL : http://localhost:8080/api/account/accounts



Get Account By accountId :

Http Method : GET

URL : http://localhost:8080/api/account/{accountId}



Get Account Balance By accountId :

Http Method : GET

URL : http://localhost:8080/api/account/balance/{accountId}


*********************TRANSACTION*********************

Make Transfer Between Accounts :

Http Method : POST

URL : http://localhost:8080/api/transaction/doTransfer

Body : 

{     "senderId":21,
   "receiverId":23,
   "transactionAmount":50
}



Get Transaction By tansactionId :

Http Method : GET

URL : http://localhost:8080/api/transaction/{tansactionId}



Get All Transactions :

Http Method : GET

URL : http://localhost:8080/api/transaction/transactions


