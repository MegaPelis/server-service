package com.megapelis.server.util;

import com.megapelis.server.model.enums.ServerStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase {@link ServerException}
 * @author yadir.garcia.
 */
@Getter
@Setter
public class ServerException extends Exception {
    private ServerStatusEnum status;

    public ServerException(ServerStatusEnum status) {
        super(status.getMessageBackend());
        this.status = status;
    }
}

