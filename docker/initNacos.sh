#!/bin/bash
#create by yuxuewen
#email 8586826@qq.com


cat > ./$1 <<EOF
#create by yuxuewen
#email 8586826@qq.com
version: '3'
services:
    $2:
      image: nacos/nacos-server:latest
      container_name: $2
      hostname: $2
      network_mode: bridge
      environment:
        - PREFER_HOST_MODE=hostname
        - MODE=standalone
      volumes:
        - ./standalone-logs/:/home/nacos/logs
        - ./init.d/custom.properties:/home/nacos/init.d/custom.properties
      ports:
        - "8848:8848"
    prometheus:
      container_name: prometheus
      image: prom/prometheus:latest
      network_mode: bridge
      volumes:
        - ./prometheus/prometheus-standalone.yaml:/etc/prometheus/prometheus.yml
      ports:
        - "9090:9090"
      depends_on:
        - $2
      restart: on-failure
    grafana:
      container_name: grafana
      image: grafana/grafana:latest
      network_mode: bridge
      ports:
        - 3000:3000
      restart: on-failure

EOF
