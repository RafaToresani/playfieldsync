package com.playfieldsync.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends RuntimeException{
    private String resourceName;
    private String resourceValue;
    private String message;

    public ResourceAlreadyExistException(String resourceName, String resourceValue){
        super("El " + resourceName + " '" + resourceValue + "'"+ " ya está en uso.");
        this.resourceName=resourceName;
        this.resourceValue=resourceValue;
    }

    public ResourceAlreadyExistException(String message){
        super(message);
        this.message = message;
    }
}
