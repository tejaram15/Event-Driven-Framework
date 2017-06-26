//package kafka_producer;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.*;

/**
 *
 * @author raghu
 */
public class Kafka_producer {
    public static void main(String[] args) throws Exception{
    Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shutting down ...");
                    //some cleaning up code...

                } catch (InterruptedException e) {
                }
            }
        });

        String CreateDataString;
        CreateDataString = "mutation { \n\tset { \n\t\t_:iphone <name> \"iphone\" .\n";

        final LinkedBlockingQueue<Status> queue = new LinkedBlockingQueue<> (1000);
      String consumerKey = "6mPWT9hJotfqQNtUAyrAHXfra";
      String consumerSecret = "LWdCYKK0sCB1fa05T3nHoi9L6lr5DN380DaWffvOnZxfcQgQ7b";
      String accessToken = "344709461-r7RB2ZhSc5wz0cdXxg2I2xP5V3ltmlxyY3B84qsp";
      String accessTokenSecret = "oXzSUHNmkGO9daqqWu1UG3lmQ8HV0mtVRRsO9oEs2Saze";
      String topicName = "TwitterData";

      
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setDebugEnabled(true)
         .setOAuthConsumerKey(consumerKey)
         .setOAuthConsumerSecret(consumerSecret)
         .setOAuthAccessToken(accessToken)
         .setOAuthAccessTokenSecret(accessTokenSecret);
      
      TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

      StatusListener listener;
        listener = new StatusListener() {
            
            @Override
            public void onStatus(Status status) {
                boolean offer = queue.offer(status);
                // System.out.println("@" + status.getUser().getScreenName()
                //+ " - " + status.getText());
                // System.out.println("@" + status.getUser().getScreen-Name());
                /*for(URLEntity urle : status.getURLEntities()) {
                System.out.println(urle.getDisplayURL());
                }*/
                /*for(HashtagEntity hashtage : status.getHashtagEntities()) {
                System.out.println(hashtage.getText());
                }*/
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onException(Exception excptn) {
                excptn.printStackTrace(); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        twitterStream.addListener(listener);
        FilterQuery query = new FilterQuery().track("iphone");
        twitterStream.filter(query);

        Thread.sleep(5000);   
        //Add Kafka producer config settings
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);

        props.put("key.serializer", 
           "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", 
           "org.apache.kafka.common.serialization.StringSerializer");   
        
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            int i = 0;
            int j = 0;
            
            while(i < 50) {
                Status ret = queue.poll();
                
                if (ret == null) {
                    Thread.sleep(100);
                    i++;
                }else {
                    for(HashtagEntity hashtage : ret.getHashtagEntities()) {
                        System.out.println("Hashtag: " + hashtage.getText());
                        producer.send(new ProducerRecord<>(
                                topicName, Integer.toString(j++), hashtage.getText()));
                        CreateDataString += "\n\t\t_:";
                        CreateDataString += hashtage.getText();
                        CreateDataString += " <name> \"";
                        CreateDataString += hashtage.getText();
                        CreateDataString += "\" .";                        

                        CreateDataString += "\n\t\t_:";
                        CreateDataString += "iphone";
                        CreateDataString += " <hash> _:";
                        CreateDataString += hashtage.getText();
                        CreateDataString += " .\n";                        

                    }
                }
            } }
      CreateDataString += "\n\t}\n}";
      System.out.printf(CreateDataString);
      Thread.sleep(5000);
      twitterStream.shutdown();        
    }
    
}
