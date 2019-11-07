#!/bin/bash
#create by yuxuewen
#email 8586826@qq.com

currentPath=`pwd`
currentProjectName=`pwd | awk 'BEGIN{FS="/"} {print $(NF-1)}'`
currentProjectSourcePath=`pwd | awk 'BEGIN{FS="/docker"} {print $1}'`

mysqlAndRedisFileName=docker-compose-mysql-redis.yml
mysqlDevName=mysql-dev
redisDevName=redis-dev

nacosFileName=docker-compose-nacos.yml
nacosDevName=nacos-dev

springCloudFileName=docker-compose-spring-cloud.yml

#checkSuccess(){
#    while true; do
#       tailLog=`docker-compose -f $currentProjectDockerPath/docker-compose.yml logs --tail=1 $1`
#       isSuccessToConnect=`echo $tailLog | grep "ready to handle connections"  | wc -l`
#       echo $tailLog
#       if [[ isSuccessToConnect -eq 1 ]]; then
#           break;
#       fi
#       sleep 2
#   done
#}

initConfigFile(){
echo "initConfigFile"


    source ./initMysqlAndRedis.sh $mysqlAndRedisFileName $mysqlDevName $redisDevName
    source ./initNacos.sh $nacosFileName $nacosDevName
    source ./initSpringCloud.sh


    # check mysql and redis
    mysqlAndRedisContainerNum=`docker-compose -f ./$mysqlAndRedisFileName ps |
        awk -v mysql=$mysqlDevName -v redis=$redisDevName 'BEGIN{FS=" "} {
        if ($1 ==mysql || $1 ==redis){
            print $1
        }
    }' | wc -l`

    if [[ mysqlAndRedisContainerNum -eq 0 ]]; then
        echo -e "\033[36m Running...... \033[0m"
        docker-compose -f ./$mysqlAndRedisFileName up -d
    else
        echo -e "\033[36m Mysql service and Redis service have started. \033[0m"
        echo -e "\033[36m Ignore...... \033[0m"
    fi

    #check nacos
    nacosContainerNum=`docker-compose -f ./$nacosFileName ps |
        awk -v nacos=$nacosDevName 'BEGIN{FS=" "} {
        if ($1 ==nacos){
            print $1
        }
    }' | wc -l`

    if [[ nacosContainerNum -eq 0 ]]; then
        echo -e "\033[36m Running...... \033[0m"
        docker-compose -f ./$nacosFileName up -d
    else
        echo -e "\033[36m Nacos service have started. \033[0m"
        echo -e "\033[36m Ignore...... \033[0m"
    fi


    echo -e "\033[36m Beginning <<mvn clean>> to $currentProjectSourcePath...... \033[0m"

    mvn -f $currentProjectSourcePath/pom.xml clean

    echo -e "\033[36m Beginning <<mvn install>> to $currentProjectSourcePath...... \033[0m"
    mvn -f $currentProjectSourcePath/pom.xml install

    echo -e "\033[36m Beginning <<spring cloud applications>> to $currentProjectSourcePath...... \033[0m"
    docker-compose -f ./$springCloudFileName up -d

}

addPort2NginxFile(){

    echo "createNginxFile";
}

addHost2NginxFile(){

    echo "createNginxFile";
}



start(){
    if [[ "$1" == "mysql" || "$1" == "redis" ]]; then
        docker-compose -f ./$mysqlAndRedisFileName up -d
    fi

    if [[ "$1" == "nacos" ]]; then
        docker-compose -f ./$nacosFileName up -d
    fi

    if [[ "$1" == "spring" ]]; then
        docker-compose -f ./$springCloudFileName up -d
#        docker-compose -f ./$springCloudFileName logs -f trade-base
    fi
}

stopAll(){
   docker-compose -f ./$mysqlAndRedisFileName down
   docker-compose -f ./$nacosFileName down
   docker-compose -f ./$springCloudFileName down
}

stop(){
    if [[ "$1" == "mysql" || "$1" == "redis" ]]; then
        docker-compose -f ./$mysqlAndRedisFileName down
    fi

    if [[ "$1" == "nacos" ]]; then
        docker-compose -f ./$nacosFileName down
    fi

    if [[ "$1" == "spring" ]]; then
        docker-compose -f ./$springCloudFileName down
    fi


}

exec(){
   docker exec -it $1 /bin/bash
}

logs(){
   docker-compose logs -f $1
}




# create by yuxuewen
# email 8586826@qq.com
# Identify parameters and options
while [ -n $1 ]
do
    case $1 in
        "init")
            initConfigFile ;;
        "start")
            start $2;;
        "stop")
            stop $2;;
        "stopAll")
            stopAll;;
        "exec")
            exec $2;;
        "logs")
            logs $2;;
        *) break ;;
    esac

    shift

done

while [ -n $1 ]
do
    case $1 in
        -port)
            addPort2NginxFile $2;;
        -host)
            addHost2NginxFile $2;;
        *) break ;;
    esac
    shift
done
