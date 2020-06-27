case $1 in
    amq)
        folder=activemq ;;
    pul)
        folder=pulsar   ;;
    rkt)
        folder=rocketmq ;;
    rmq)
        folder=rabbitmq ;;
    kfk)
        folder=kafka    ;;
    *)
        echo "Invalid option!"
        echo "Valid options: rmq, kfk, rkt, pul, amq"
        return          ;;
esac

docker-compose.exe -f $folder/docker-compose.yaml up -d