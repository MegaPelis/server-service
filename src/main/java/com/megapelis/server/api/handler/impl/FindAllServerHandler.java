package com.megapelis.server.api.handler.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.megapelis.server.api.handler.ServerHandler;
import com.megapelis.server.model.dto.request.CreateServerRQ;
import com.megapelis.server.model.dto.request.FindAllServerRQ;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.entity.Server;
import com.megapelis.server.model.entity.Status;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerConstant;
import com.megapelis.server.util.ServerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase {@link FindAllServerHandler}
 *
 * @author yadir.garcia.
 */

public class FindAllServerHandler extends ServerHandler implements RequestHandler<Request, Response> {

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

    /**
     * Metodo que permite validar el payload.
     *
     * @param request
     * @return {@link Object}
     * @throws ServerException
     */
    @Override
    public Object validatePayload(Request request) throws ServerException {
        FindAllServerRQ findAllServerRQ = convertPayload(request, FindAllServerRQ.class);
        if(ServerConstant.INTEGER_ONE > findAllServerRQ.getPage())
            findAllServerRQ.setPage(ServerConstant.INTEGER_ONE);
        return findAllServerRQ;
    }

    @Override
    public Response handleRequest(Request request, Context context) {
        db = AmazonDynamoDBClientBuilder.defaultClient();
        mapper = new DynamoDBMapper(db);
        List<Server> servers = mapper.scan(Server.class, new DynamoDBScanExpression());
        return ServerCommon.buildResponse(request, ServerStatusEnum.SUCCESS,servers);
    }
}
