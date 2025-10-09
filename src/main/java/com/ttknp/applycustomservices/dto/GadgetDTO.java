package com.ttknp.applycustomservices.dto;

import com.ttknp.applycustomservices.ApplyCustomServicesApplication;
import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.select.JdbcSelectHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlOrderByHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlWhereHelper;
import com.ttknp.jdbccustomservice.jdbc.update.JdbcInsertUpdateDeleteHelper;
import com.ttknp.jdbccustomservice.jdbc.utility.JdbcReadSQLFileHelper;
import com.ttknp.webcustomservice.exception.ContentNotAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class GadgetDTO implements ModelService<Gadget> {

    public static String SQL_SCRIPT_DIR_ON_ROOT = "/sql/";
    public static String SQL_SCRIPT_DIR_ON_ABS = "B:/practice-java-one-jetbrains/spring-boot-skills/lab_core_40/apply-custom-services/src/main/resources/sql/";

    private final JdbcSelectHelper<Gadget> jdbcSelectHelper;
    private final JdbcInsertUpdateDeleteHelper<Gadget> jdbcInsertUpdateDeleteHelper;

    // **
    private final JdbcReadSQLFileHelper jdbcReadSQLFileHelper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GadgetDTO(JdbcSelectHelper<Gadget> jdbcSelectHelper, JdbcInsertUpdateDeleteHelper<Gadget> jdbcInsertUpdateDeleteHelper, JdbcReadSQLFileHelper jdbcReadSQLFileHelper, JdbcTemplate jdbcTemplate) {
        this.jdbcSelectHelper = jdbcSelectHelper;
        this.jdbcInsertUpdateDeleteHelper = jdbcInsertUpdateDeleteHelper;
        // **
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcReadSQLFileHelper = new JdbcReadSQLFileHelper(this.jdbcTemplate);
    }

    @Override
    public List<Gadget> retrieveAllModels() {
        try {
            return jdbcSelectHelper.selectAll(Gadget.class);
        } catch (Exception e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public List<Gadget> retrieveOrderByAllModels(SqlOrderByHelper<Gadget> sqlOrderByHelper) {
        return jdbcSelectHelper.selectAll(Gadget.class, sqlOrderByHelper);
    }

    @Override
    public List<Gadget> retrieveWhereAndOrderByAllModels(SqlWhereHelper<Gadget> sqlWhereHelper, SqlOrderByHelper<Gadget> sqlOrderByHelper, Gadget model) {
        return jdbcSelectHelper.selectAll(Gadget.class, sqlOrderByHelper, sqlWhereHelper, model);
    }

    @Override
    public List<Gadget> retrieveWhereAndOrderByAllModels(SqlWhereHelper<Gadget> sqlWhereHelper, SqlOrderByHelper<Gadget> sqlOrderByHelper, String alias, Gadget model) {
        return jdbcSelectHelper.selectAll(Gadget.class, sqlOrderByHelper, sqlWhereHelper, alias, model);
    }

    @Override
    public List<Gadget> retrieveOrderByAllModelsAndReplaceAssignValues(SqlOrderByHelper<Gadget> sqlOrderByHelper) {
        StringBuilder stringBuilderSql = jdbcSelectHelper.getStatement("select_star_gadget.sql");
        return jdbcSelectHelper.selectAll(Gadget.class, stringBuilderSql, sqlOrderByHelper);
    }

    @Override
    public <U> Gadget retrieveModel(U key) {
        try {
            return jdbcSelectHelper.selectOne(Gadget.class, "gid", key);
        } catch (EmptyResultDataAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public Boolean createModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.insertOne(Gadget.class, model) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public Boolean updateModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.updateOne(Gadget.class, "gid", model) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public <U> Boolean deleteModel(U key) {
        try {
            return jdbcInsertUpdateDeleteHelper.deleteOne(Gadget.class, "gid", key) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public void loadSqlStatementByAbsPath() {
        this.jdbcReadSQLFileHelper.setSqlScriptDir(SQL_SCRIPT_DIR_ON_ABS);
        this.jdbcReadSQLFileHelper.loadScriptAbsPath("truncate_gadget_bak.sql");
    }

    @Override
    public void loadSqlStatementByRootPath() {
        // loadScriptRootPath start searching on resource dir ex, resource/sql/truncate_gadget_bak
        this.jdbcReadSQLFileHelper.loadScriptRootPath(SQL_SCRIPT_DIR_ON_ROOT+"truncate_gadget_bak.sql");
    }

    @Override
    public void loadSqlStatementAndBindParamsByAbsPath() {
        HashMap<String,String> params = new HashMap<>();
        params.put("[AMOUNT]","1000");
        params.put("{GID}","'G001'");
        this.jdbcReadSQLFileHelper.setSqlScriptDir(SQL_SCRIPT_DIR_ON_ABS);
        try {
            this.jdbcReadSQLFileHelper.loadScriptAbsPath("insert_select_gadget_bak.sql",params);
        } catch (Exception e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public void loadSqlStatementAndBindParamsByRootPath() {
        HashMap<String,String> params = new HashMap<>();
        params.put("[AMOUNT]","null");
        params.put("{GID}","'G001'");
        try {
            // you can call insert_select_gadget_bak.sql or <dir name>/<sql name>.sql it will look to the resources folder of ApplyCustomServicesApplication.class
            this.jdbcReadSQLFileHelper.loadScriptRootPath("sql/insert_select_gadget_bak.sql", ApplyCustomServicesApplication.class,params);
        } catch (Exception e) {
            throw new ContentNotAllowed(e);
        }
    }
}
