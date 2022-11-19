package com.megapelis.server.api.handler.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.megapelis.server.api.handler.ServerHandler;
import com.megapelis.server.model.dto.request.CreateServerRQ;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.entity.Server;
import com.megapelis.server.model.entity.Status;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerException;

/**
 * Clase {@link CreateServerHandler}
 *
 * @author yadir.garcia.
 */
public class CreateServerHandler extends ServerHandler implements RequestHandler<Request, Response> {

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
        CreateServerRQ createServerRQ = convertPayload(request, CreateServerRQ.class);
        if (!ServerCommon.isValidString(createServerRQ.getName(), createServerRQ.getImageBase64()))
            throw new ServerException(ServerStatusEnum.ERROR_FORMAT_REQUEST);
        return createServerRQ;
    }

    @Override
    public Response handleRequest(Request request, Context context) {
        db = AmazonDynamoDBClientBuilder.defaultClient();
        mapper = new DynamoDBMapper(db);
        Server server = ServerCommon.convertObjectToClass(request.getData(), Server.class);
        server.setStatus(Status.ACTIVE.toString());
        server.setCreatedDate(ServerCommon.getDateTime());
        server.setLastModifiedDate(null);
        mapper.save(server);
        return ServerCommon.buildResponse(request, ServerStatusEnum.SUCCESS);
    }
}
