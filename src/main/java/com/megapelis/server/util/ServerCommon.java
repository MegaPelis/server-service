package com.megapelis.server.util;

import com.google.gson.Gson;
import com.megapelis.server.model.dto.request.generic.Request;
import com.megapelis.server.model.dto.response.Response;
import com.megapelis.server.model.dto.response.ResponseStatus;
import com.megapelis.server.model.entity.Server;
import com.megapelis.server.model.enums.ServerStatusEnum;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ServerCommon {

    /**
     * Metodo que permite validar una cadena no este vacia.
     *
     * @param value
     * @return {@link boolean}
     */
    public static boolean isValidString(String value) {
        return null != value && !value.trim().isEmpty();
    }

    /**
     * Metodo que permite validar varias cadenas.
     *
     * @param values
     * @return {@link boolean}
     */
    public static boolean isValidString(String... values) {
        for (String value : values) {
            if (!isValidString(value)) {
                return ServerConstant.BOOLEAN_FALSE;
            }
        }
        return ServerConstant.BOOLEAN_TRUE;
    }

    /**
     * Metodo que permite registrar la salida para cloud watch.
     *
     * @param object
     */
    public static void output(Object object) {
        if (object instanceof Response || object instanceof Request || object instanceof Server)
            System.out.println(getStringJSON(object));
        else
            System.out.println(object);
    }

    /**
     * Metodo que permite obtener el objeto en cadena.
     *
     * @param object
     * @return {@link  String}
     */
    public static String getStringJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Metodo que valida la petición.
     *
     * @param request
     * @throws ServerException
     */
    public static void isValidRequest(Request request) throws ServerException {
        boolean isError = ServerConstant.BOOLEAN_FALSE;
        if (null == request || !isValidString(request.getTraceId(), request.getDateTime(), request.getService(), request.getOperation()))
            isError = ServerConstant.BOOLEAN_TRUE;
        if (!request.getService().equalsIgnoreCase(ServerConstant.STRING_SERVICE_NAME))
            isError = ServerConstant.BOOLEAN_TRUE;
        if (request.getService().equalsIgnoreCase(request.getOperation()))
            isError = ServerConstant.BOOLEAN_TRUE;
        if (isError)
            throw new ServerException(ServerStatusEnum.ERROR_FORMAT_REQUEST);
    }

    /**
     * Metodo que permite construir la respuesta del servicio.
     *
     * @param request
     * @return {@link Response}
     */
    public static Response buildResponse(Request request) {
        return buildResponse(request, null, null, null, null);
    }

    /**
     * Metodo que permite construir la respuesta del servicio.
     *
     * @param request
     * @param statusEnum
     * @return {@link Response}
     */
    public static Response buildResponse(Request request, ServerStatusEnum statusEnum) {
        return buildResponse(request, statusEnum, null);
    }

    /**
     * Metodo que permite construir la respuesta del servicio.
     *
     * @param request
     * @param statusEnum
     * @param data
     * @return {@link Response}
     */
    public static Response buildResponse(Request request, ServerStatusEnum statusEnum, Object data) {
        return buildResponse(request, statusEnum.getCode(), statusEnum.getMessageBackend(), statusEnum.getMessageFrontend(), data);
    }

    /**
     * Metodo que permite construir la respuesta del servicio.
     *
     * @param request
     * @param code
     * @param messageBackend
     * @param messageFrontend
     * @param data
     * @return {@link Response}
     */
    private static Response buildResponse(Request request, String code, String messageBackend, String messageFrontend, Object data) {
        if (null == request)
            return null;
        if (!isValidString(code, messageBackend, messageFrontend)) {
            code = ServerStatusEnum.ERROR.getCode();
            messageBackend = ServerStatusEnum.ERROR.getMessageBackend();
            messageFrontend = ServerStatusEnum.ERROR.getMessageFrontend();
        }
        if (null != data) {
            Gson gson = new Gson();
            data = gson.toJson(data);
        }
        String dateTime = ServerCommon.getDateTime();
        return Response
                .builder()
                .traceId(request.getTraceId())
                .dateTime(dateTime)
                .service(request.getService())
                .operation(request.getOperation())
                .status(new ResponseStatus(code, messageBackend, messageFrontend))
                .data(data)
                .build();
    }

    /**
     * Metodo que permite obtener la fecha y hora actual.
     *
     * @return {@link String}
     */
    public static String getDateTime() {
        return ZonedDateTime.now(ZoneId.of(ServerConstant.STRING_DATE_ZONE))
                .format(DateTimeFormatter.ofPattern(ServerConstant.STRING_DATE_TIME_FORMAT));
    }

    public static <T> T convertObjectToClass(Object object, Class<T> clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return gson.fromJson(json, clazz);
    }
}
