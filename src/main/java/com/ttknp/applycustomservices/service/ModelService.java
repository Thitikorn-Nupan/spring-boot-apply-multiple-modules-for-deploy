package com.ttknp.applycustomservices.service;


import com.ttknp.jdbccustomservice.jdbc.sql_order_by.SqlOrderByHelper;

import java.util.List;

public interface ModelService<T> {
    List<T> retrieveAllModels();
    List<T> retrieveOrderByAllModels(SqlOrderByHelper<T> sqlOrderByHelper);
    List<T> retrieveOrderByAllModelsAndReplaceAssignValues(SqlOrderByHelper<T> sqlOrderByHelper);
    <U> T retrieveModel(U key);
    Boolean createModel(T model);
    Boolean updateModel(T model);
    <U> Boolean deleteModel(U key);
}