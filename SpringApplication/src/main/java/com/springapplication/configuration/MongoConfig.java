package com.springapplication.configuration;


import com.adapters.outbound.EnvNames;
import com.adapters.outbound.persistence.consumption.ConsumptionDocument;
import com.adapters.outbound.persistence.measurement.MeasurementDocument;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

  private final String mongoConnUri;
  private final String dbName;
  private final CodecRegistry codecRegistry;


  public MongoConfig(
      @Value("${" + EnvNames.MONGO_CONNECTION_URI + "}") String mongoConnUri,
      @Value("${" + EnvNames.MONGO_DB_NAME + "}") String dbName
  ) {
    this.mongoConnUri = mongoConnUri;
    this.dbName = dbName;
    this.codecRegistry = CodecRegistries
        .fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(
                PojoCodecProvider.builder()
                    .register(ClassModel.builder(ConsumptionDocument.class).build())
                    .register(ClassModel.builder(MeasurementDocument.class).build())
                    .build())
        );
  }

  @Bean
  public MongoClient mongoClient() {
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(mongoConnUri))
        .codecRegistry(codecRegistry)
        .build();
    return MongoClients.create(settings);
  }

  @Bean
  public MongoDatabase mongoDatabase(MongoClient client) {
    return client.getDatabase(dbName);
  }
}
