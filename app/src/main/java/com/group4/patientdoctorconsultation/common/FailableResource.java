package com.group4.patientdoctorconsultation.common;

public class FailableResource<T> {

    private final T resource;
    private final Exception error;

    FailableResource(T resource) {
        this(resource, null);
    }

    FailableResource(Exception error) {
        this(null, error);
    }

    private FailableResource(T resource, Exception error) {
        this.resource = resource;
        this.error = error;
    }

    boolean isSuccessful(){
        return resource != null && error == null;
    }

    public T getResource(){
        if(error != null){
            throw new IllegalStateException("Resource include error, call isSuccessful()");
        }

        return resource;
    }

    Exception getError(){
        if(resource != null){
            throw new IllegalStateException("Resource include data, call isSuccessful()");
        }

        return error;
    }
}
