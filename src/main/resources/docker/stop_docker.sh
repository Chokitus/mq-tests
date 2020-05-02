case $1 in
    rmq)
        folder=rabbitmq ;;
    kfk)
        folder=kafka    ;;
    *)
        echo "Invalid option!"
        echo "Valid options: rmq, kfk"
        return          ;;
esac

docker-compose.exe -f $folder/docker-compose.yaml down