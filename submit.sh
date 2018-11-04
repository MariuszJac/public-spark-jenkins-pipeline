/home/mj/projects/sys/spark/bin/spark-submit \
     --class is.spark.examples.WordCount \
     --driver-memory 2g \
     --executor-memory 2g \
     --executor-cores 4 \
     --conf "spark.executor.extraJavaOptions=-Dlog4j.configuration=file:log4j-executor.properties" \
     --driver-java-options "-Djava.security.auth.login.config=config/kafka_client_jaas_consumer1.conf -Dlog4j.configuration=file:log4j-driver.properties -Ddm.logging.level=INFO" \
     --files log4j-executor.properties \
     --master local[4] \
     --conf spark.cassandra.connection.host=127.0.0.1 \
     --conf spark.cassandra.auth.username='cassandra' \
     --conf spark.cassandra.auth.password='cassandra' \
     --conf spark.kafka.brokerServer='104.199.19.201:9092' \
     --conf spark.kafka.consumer.groupId='spark-executor-consumer1-group' \
     --conf spark.kafka.topics='clickstream1' \
     target/SparkSample-1.0-jar-with-dependencies.jar
