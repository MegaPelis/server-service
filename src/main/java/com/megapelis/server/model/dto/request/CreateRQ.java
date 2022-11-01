package com.megapelis.server.model.dto.request;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.megapelis.server.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase {@link CreateRQ}
 *
 * @author yadir.garcia.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRQ implements Serializable {
    private String name;
    private String imageBase64;
}
