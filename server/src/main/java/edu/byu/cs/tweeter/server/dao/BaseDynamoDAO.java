package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class BaseDynamoDAO {

    private DynamoDB dynamoDB;
    private Table table;
    private String tableName;

    public BaseDynamoDAO(String tableName) {
        this.tableName = tableName;
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        this.table = dynamoDB.getTable(tableName);
    }

    public Table getTable() {
        return this.table;
    }

    public DynamoDB getDatabase() {
        return this.dynamoDB;
    }
}
