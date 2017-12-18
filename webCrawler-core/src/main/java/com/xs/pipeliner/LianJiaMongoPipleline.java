package com.xs.pipeliner;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

        MongoDBConfiguration mongoDBConfiguration = new MongoDBConfiguration();
        Mongo mongo = mongoDBConfiguration.getMogo();
        MongoClient mongoClient = null;
        try{
            // 连接到 mongodb 服务
            mongoClient = new MongoClient( mongo.getServerAddress() , mongo.getPort() );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(mongo.getDbName());
            MongoCollection<Document> collection = mongoDatabase.getCollection(mongo.getCollectionName());

            saveOrUpdate(resultItems, collection);
        } catch(Exception e) {
            logger.error( e.getClass().getName() + ": " + e.getMessage() );
        } finally {
            // 关闭mongodb防止异常
            if (null != mongoClient) {
                mongoClient.close();
            }
        }
    }

    private boolean isUrlExists(ResultItems resultItems, MongoCollection<Document> collection) {
        String curUrl = resultItems.get("url");

        Document searchQuery = new Document();
        searchQuery.put("url", curUrl);

        FindIterable<Document> findIterable = collection.find(searchQuery);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()) {
            logger.info(curUrl + "exists");
            return true;
        }
        return false;
    }

    private void saveOrUpdate(ResultItems resultItems, MongoCollection<Document> collection) {
        String result = JSON.toJSONString(resultItems.getAll());

        // 记录存在则更新
        if (isUrlExists(resultItems, collection)) {
            Document newDocument = new Document();
            newDocument.put("$set", Document.parse(result));

            Document queryDoc = new Document();
            queryDoc.put("url", resultItems.get("url"));

            collection.updateOne(queryDoc, newDocument);
        } else {
            Document document = Document.parse(result);
            collection.insertOne(document);
        }
    }

    public static void main( String args[] ){

    }
}
