package com.megapelis.server.mapper.impl;

import com.megapelis.server.mapper.ServerMapper;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.entity.Server;
import com.megapelis.server.util.ServerCommon;

import java.util.ArrayList;
import java.util.List;

public class ServerMapperImpl implements ServerMapper {

    @Override
    public Response toDto(Server server) {
        if (server == null)
            return null;
        Response response = new Response();
        response.setData(ServerCommon.convertObjectToClass(server, Server.class));
        return response;
    }

    @Override
    public List<Response> toDtoList(List<Server> serverList) {
        List<Response> responses = new ArrayList<>();
        for (Server server : serverList)
            responses.add(toDto(server));
        return responses;
    }
}
