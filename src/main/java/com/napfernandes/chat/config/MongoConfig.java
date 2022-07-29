package com.napfernandes.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {
    private final MongoMappingContext mongoMappingContext;
    private final MongoDatabaseFactory mongoDatabaseFactory;

    public MongoConfig(MongoMappingContext mongoMappingContext, MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoMappingContext = mongoMappingContext;
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);

        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }
}
