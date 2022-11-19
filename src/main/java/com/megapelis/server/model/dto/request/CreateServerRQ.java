package com.megapelis.server.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase {@link CreateServerRQ}
 *
 * @author yadir.garcia.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateServerRQ implements Serializable {
    private String name;
    private String imageBase64;
}
