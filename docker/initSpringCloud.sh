#!/bin/bash
#create by yuxuewen
#email 8586826@qq.com

homePath=`echo $HOME`
currentProjectName=`pwd | awk 'BEGIN{FS="/"} {print $(NF-1)}'`

lastDocumentName=`pwd | awk 'BEGIN{FS="/"} {print $(NF)}'`
currentProjectSourcePath=`pwd | awk 'BEGIN{FS="/docker"} {print $1}'`
cat > ./docker-compose-spring-cloud.yml <<EOF
#create by yuxuewen
#email 8586826@qq.com
version: '3'
services:
    trade-base:
        image: maven:3-jdk-8
        container_name: trade-base
        hostname: trade-base
        network_mode: bridge
        ports:
          - "7100:7100"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-provider-trade-basic-data
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]
    product-combo:
        image: maven:3-jdk-8
        container_name: product-combo
        hostname: product-combo
        network_mode: bridge
        ports:
          - "7200:7200"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-provider-product-combo
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]
    consumer:
        image: maven:3-jdk-8
        container_name: consumer
        hostname: consumer
        network_mode: bridge
        ports:
          - "8080:8080"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-consumer
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]
    jwt:
        image: maven:3-jdk-8
        container_name: jwt
        hostname: jwt
        network_mode: bridge
        ports:
          - "8100:8100"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-security-jwt-auth-server
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]
    deal-huobi:
        image: maven:3-jdk-8
        container_name: deal-huobi
        hostname: deal-huobi
        network_mode: bridge
        ports:
          - "6101:6101"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-deal
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]
    deal-okex:
        image: maven:3-jdk-8
        container_name: deal-huobi
        hostname: deal-huobi
        network_mode: bridge
        ports:
          - "6102:6102"
        external_links:
          - mysql-dev:mysql-dev
          - redis-dev:redis-dev
          - nacos-dev:nacos-dev
        volumes:
          - $currentProjectSourcePath:$currentProjectSourcePath
          - $homePath/.m2:/root/.m2
        working_dir: $currentProjectSourcePath/bs-ccr-deal-okex
        command: ["/bin/sh", "-c", "mvn clean spring-boot:run"]

EOF
