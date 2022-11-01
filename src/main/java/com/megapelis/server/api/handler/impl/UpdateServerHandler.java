package com.megapelis.server.api.handler.impl;

import com.megapelis.server.api.handler.ServerHandler;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.util.ServerException;

public class UpdateServerHandler extends ServerHandler {
    @Override
    public Object validatePayload(Request request) throws ServerException {
        return null;
    }
}
