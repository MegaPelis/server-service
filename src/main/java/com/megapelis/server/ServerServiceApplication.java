package com.megapelis.server;


import com.megapelis.server.api.factory.ServerFactory;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;

/**
 * Clase {@link ServerServiceApplication}
 * @author yadir.garcia.
 */
public class ServerServiceApplication {

    /**
     * Metodo que permite realizar el llamado de los servicios.
     * @param request
     * @return {@link Response}
     */

    public Response handler(Request request){
        return ServerFactory.handler(request);
    }


}
