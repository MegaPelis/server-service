package com.megapelis.server.mapper;

import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.entity.Server;

import java.util.List;

public interface ServerMapper {
    Response toDto(Server server);
    List<Response> toDtoList(List<Server> serverList);
}
