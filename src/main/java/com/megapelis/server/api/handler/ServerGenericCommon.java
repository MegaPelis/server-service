package com.megapelis.server.api.handler;

import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerException;

/**
 * Clase {@link ServerGenericCommon}
 * @author yadir.garcia.
 */
public class ServerGenericCommon {

    public ServerGenericCommon(){
    }

    /**
     * Metodo que permite parsear el payload.
     * @param request
     * @param clazz
     * @return {@link T}
     * @param <T>
     * @throws ServerException
     */
    protected <T> T convertPayload(Request request, Class<T> clazz) throws ServerException{
        if(null == request || null == request.getData())
            throw new ServerException(ServerStatusEnum.ERROR_FORMAT_REQUEST);
        return ServerCommon.convertObjectToClass(request.getData(), clazz);
    }
}
