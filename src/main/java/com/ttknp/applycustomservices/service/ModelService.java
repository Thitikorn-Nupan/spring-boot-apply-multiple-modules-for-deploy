package com.ttknp.applycustomservices.service;


import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlOrderByHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlWhereHelper;

import java.util.List;

public interface ModelService<T> {
    List<T> retrieveAllModels();
    List<T> retrieveOrderByAllModels(SqlOrderByHelper<T> sqlOrderByHelper);
    List<T> retrieveWhereAndOrderByAllModels(SqlWhereHelper<T> sqlWhereHelper, SqlOrderByHelper<T> sqlOrderByHelper,T model);
    List<T> retrieveWhereAndOrderByAllModels(SqlWhereHelper<T> sqlWhereHelper, SqlOrderByHelper<T> sqlOrderByHelper,String alias,T model);
    List<T> retrieveOrderByAllModelsAndReplaceAssignValues(SqlOrderByHelper<T> sqlOrderByHelper);
    <U> T retrieveModel(U key);
    Boolean createModel(T model);
    Boolean updateModel(T model);
    <U> Boolean deleteModel(U key);
    // ** For test read sql as statement then query
    void loadSqlStatementByAbsPath();
    void loadSqlStatementByRootPath();
    void loadSqlStatementAndBindParamsByAbsPath();
    void loadSqlStatementAndBindParamsByRootPath();
    List<T> loadSqlStatementAndRetrieveAllModels();
    <U> List<T> loadSqlStatementAndRetrieveModel(U key);
    List<T> loadSqlStatementAndRetrieveModel(Float price1,Float price2,String brand);
}