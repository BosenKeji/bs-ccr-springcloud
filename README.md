# bs-ccr-springcloud


### 端口分配
* consumer  --- `80`
* bs-ccr-provider-user-front --- `7010`
* bs-ccr-provider-user-boss  --- `7020`
* bs-ccr-provider-coin  ---  `7030`
* bs-ccr-provider-tradeplatform  --- `7040`
* bs-ccr-provider-strategy  ---  `7050`
* bs-ccr-provider-product   ---  `7060`
* bs-ccr-provider-combo   ---   `7070`
* bs-ccr-provider-transaction  --- `7080`

-------
### Feign

> ##### coin
* Provider Controller
    * list
    * get
    * add
    * put
    * delete
* Feign Client
    * ~~getCoin~~ -> get
    * ~~listCoin~~  -> list
    * ~~addCoin~~  -> add 
    * ~~updateCoin~~  -> update
    * ~~deleteCoin~~  -> delete
* External Consumer
    * ~~getCoin~~ -> get
    * ~~listCoin~~  -> list
    * ~~addCoin~~  -> add 
    * ~~updateCoin~~  -> update
    * ~~deleteCoin~~  -> delete

-------

### Nacos
##### server
##### client

### Spring Security


### Ribbon




### Hystrix 


### Config
#####ACM


### Flyway

### Exception

### Validation


### Swagger
