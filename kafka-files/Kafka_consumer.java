//package kafka_consumer;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class Kafka_consumer {    
    public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shutting down ...");
                } catch (InterruptedException e) {
                }
            }
        });
      String CreateDataString;
      CreateDataString = "mutation { \n\tset { \n\t\t_:iphone <name> \"iphone\" .\n";
      //These are the properties of the consumer
      Properties props = new Properties();
      props.put("bootstrap.servers", "localhost:9092");
      props.put("enable.auto.commit", "true");
      props.put("auto.commit.interval.ms", "1000");
      props.put("session.timeout.ms", "30000");
      props.put("key.deserializer",          
         "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", 
         "org.apache.kafka.common.serialization.StringDeserializer");

      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
      
      consumer.subscribe(Arrays.asList("TwitterData"));
      System.out.println("Subscribed to topic: TwitterData");
      int i = 0;
      List<ConsumerRecords<String, String>> buffer = new ArrayList<>();
      while (i++ <= 100) {
         ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                CreateDataString += "\n\t\t_:";
                CreateDataString += record.value();
                CreateDataString += " <name> \"";
                CreateDataString += record.value();
                CreateDataString += "\" .";                        

                CreateDataString += "\n\t\t_:iphone <hash> _:";
                CreateDataString += record.value();
                CreateDataString += " .\n";                        
            }
      }
      System.out.printf(CreateDataString);
    }    
}
