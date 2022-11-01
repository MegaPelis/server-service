package com.megapelis.server.model.enums;

import com.megapelis.server.util.ServerException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Clase {@link ServerOperationEnum}
 *
 * @author yadir.garcia.
 */
@Getter
public enum ServerOperationEnum {

    CREATE("create"),
    FIND_ALL("findAll"),
    DELETE("delete"),
    UPDATE("update");

    private String name;

    ServerOperationEnum(String name) {
        this.name = name;
    }

    /**
     * Metodo que permite validar si existe esa operación
     * @param name
     * @return {@link ServerOperationEnum}
     * @throws ServerException
     */
    public static ServerOperationEnum isValid(String name) throws ServerException {
        return Arrays.stream(ServerOperationEnum.values())
                .filter(operationEnum -> operationEnum.getName().equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new ServerException(ServerStatusEnum.ERROR_SERVICE_OR_OPERATION));
    }

}
