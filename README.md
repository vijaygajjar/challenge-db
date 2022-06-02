# challenge-db

1. Need to add transcaiton in transction repository so user can access transaction history
2. Need to support differnt transfer ways IMPS/RTGS
3. Need to add support to amount transfer to different bank
4. Need to implment securty to check if the user requesting transfer has access & aurthorize to do so
5. Implmented pasimistic lock need to find another way if possible to implment con-curreny 
6. Proper error handing like custom exception of negative amout transfer, sufficient balance to intitate transfer, per day limit of transfer for the fromaccount holder
