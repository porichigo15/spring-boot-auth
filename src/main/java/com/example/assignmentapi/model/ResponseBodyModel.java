package com.example.assignmentapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBodyModel<T> {
    private T objectValue;
    private String message;
}
