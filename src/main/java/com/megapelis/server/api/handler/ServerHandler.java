package com.megapelis.server.api.handler;

import com.google.gson.JsonObject;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerException;

public abstract class ServerHandler extends ServerGenericCommon  {
    /**
     * Metodo que permite ejecutar la logica del handler.
     * @param request
     * @return {@link Response}
     */
    public Response execute(Request request) {
        Response response;
        try {
            Object object = validatePayload(request);

            response = ServerCommon.buildResponse(request, ServerStatusEnum.SUCCESS);
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
    public abstract Object validatePayload(Request request) throws ServerException;

}
