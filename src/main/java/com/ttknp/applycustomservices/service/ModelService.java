package com.ttknp.applycustomservices.service;


import java.util.List;

public interface ModelService<T> {
    List<T> retrieveAllModels();
    <U> T retrieveModel(U key);
    Boolean createModel(T model);
    Boolean updateModel(T model);
    <U> Boolean deleteModel(U key);
}