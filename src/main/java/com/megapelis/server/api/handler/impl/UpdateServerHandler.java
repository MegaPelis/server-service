package com.megapelis.server.api.handler.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.megapelis.server.api.handler.ServerHandler;
import com.megapelis.server.model.dto.request.CreateServerRQ;
import com.megapelis.server.model.dto.request.UpdateServerRQ;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.entity.Server;
import com.megapelis.server.model.entity.Status;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerException;

public class UpdateServerHandler extends ServerHandler  implements RequestHandler<Request, Response> {

    private AmazonDynamoDB db;
    private DynamoDBMapper mapper;

    @Override
    public Response execute(Request request) {
        Response response;
        try {
            Object object = validatePayload(request);
            response = handleRequest(request, null);
        } catch (ServerException exception) {
            response = ServerCommon.buildResponse(request, exception.getStatus());
        }
        return response;
    }
    @Override
    public Object validatePayload(Request request) throws ServerException {
        UpdateServerRQ updateServerRQ = convertPayload(request, UpdateServerRQ.class);
        if (!ServerCommon.isValidString(updateServerRQ.getId(),updateServerRQ.getName(), updateServerRQ.getImageBase64()))
            throw new ServerException(ServerStatusEnum.ERROR_FORMAT_REQUEST);
        return updateServerRQ;
    }

    @Override
    public Response handleRequest(Request request, Context context) {
        db = AmazonDynamoDBClientBuilder.defaultClient();
        mapper = new DynamoDBMapper(db);
        Server server = ServerCommon.convertObjectToClass(request.getData(), Server.class);
        System.out.println("Server to update: " + server.getId());
        Server serverDB = mapper.load(Server.class, server.getId());
        serverDB.setName(server.getName());
        serverDB.setImageBase64(server.getImageBase64());
        serverDB.setLastModifiedDate(ServerCommon.getDateTime());
        mapper.save(serverDB);
        return ServerCommon.buildResponse(request, ServerStatusEnum.SUCCESS,serverDB);
    }
}
