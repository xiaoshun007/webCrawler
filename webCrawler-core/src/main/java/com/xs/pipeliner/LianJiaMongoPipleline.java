package com.xs.pipeliner;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xs.configure.MongoDBConfiguration;
import com.xs.data.Mongo;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 链家数据持久化到mongodb
 */
public class LianJiaMongoPipleline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(ResultItems resultItems, Task task) {
        String result = JSON.toJSONString(resultItems.getAll());

        MongoDBConfiguration mongoDBConfiguration = new MongoDBConfiguration();
        Mongo mongo = mongoDBConfiguration.getMogo();
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( mongo.getServerAddress() , mongo.getPort() );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(mongo.getDbName());
            MongoCollection<Document> collection = mongoDatabase.getCollection(mongo.getCollectionName());
            Document document = Document.parse(result);
            collection.insertOne(document);
        }catch(Exception e){
            logger.error( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public static void main( String args[] ){

    }
}
