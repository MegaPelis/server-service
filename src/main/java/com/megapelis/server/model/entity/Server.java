package com.megapelis.server.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.Date;

/**
 * @author yadir.garcia.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@DynamoDBTable(tableName = "server")
public class Server {

    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String imageBase64;

    @DynamoDBAttribute
    private String createdDate;

    @DynamoDBAttribute
    private String lastModifiedDate;

    @DynamoDBAttribute
    private Status status;

    @DynamoDBHashKey(attributeName = "Id")
    @DynamoDBAutoGeneratedKey
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}