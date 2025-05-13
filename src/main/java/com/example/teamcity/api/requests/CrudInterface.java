package com.example.teamcity.api.requests;

public interface CrudInterface {
    public Object create(Object obj);

    public Object get(String id);

<<<<<<< HEAD
    Object update(Object obj);
=======
    public Object update(String id, Object obj);
>>>>>>> hometask-4

    public Object delete(String id);
}
