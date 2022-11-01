package com.megapelis.server.api.factory;

import com.megapelis.server.api.handler.ServerHandler;
import com.megapelis.server.api.handler.impl.CreateServerHandler;
import com.megapelis.server.api.handler.impl.DeleteServerHandler;
import com.megapelis.server.api.handler.impl.FindAllServerHandler;
import com.megapelis.server.api.handler.impl.UpdateServerHandler;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.enums.ServerOperationEnum;
import com.megapelis.server.model.enums.ServerStatusEnum;
import com.megapelis.server.util.ServerCommon;
import com.megapelis.server.util.ServerException;

/**
 * Clase {@link ServerFactory}
 *
 * @author yadir.garcia.
 */
public class ServerFactory {
    private ServerFactory() {
    }
    /**
     * Fabrica que permite ejecutar mediante la operacion.
     * @param request
     * @return {@link Response}
     */

    public static Response handler(Request request) {
        ServerCommon.output(request);
        Response response = null;
        ServerHandler handler = null;
        try {
            ServerCommon.isValidRequest(request);
            switch (ServerOperationEnum.isValid(request.getOperation())){
                case CREATE:
                    handler = new CreateServerHandler();
                    break;
                case FIND_ALL:
                    handler = new FindAllServerHandler();
                    break;
                case DELETE:
                    handler = new DeleteServerHandler();
                    break;
                case UPDATE:
                    handler = new UpdateServerHandler();
                    break;
                default:
                    response =  ServerCommon.buildResponse(request, ServerStatusEnum.ERROR);
                    break;
            }
        } catch (ServerException e) {
            response =  ServerCommon.buildResponse(request, e.getStatus());
        }
        if(null == response && null != handler)
            response = handler.execute(request);
        ServerCommon.output(response);
        return response;
    }


}
