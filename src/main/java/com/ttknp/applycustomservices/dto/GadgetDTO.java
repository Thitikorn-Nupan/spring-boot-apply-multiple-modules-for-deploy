package com.ttknp.applycustomservices.dto;

import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.select.JdbcSelectHelper;
import com.ttknp.jdbccustomservice.jdbc.update.JdbcInsertUpdateDeleteHelper;
import com.ttknp.webcustomservice.exception.ContentNotAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class GadgetDTO implements ModelService<Gadget> {

    private final JdbcSelectHelper<Gadget> jdbcSelectHelper;
    private final JdbcInsertUpdateDeleteHelper<Gadget> jdbcInsertUpdateDeleteHelper;

    @Autowired
    public GadgetDTO(JdbcSelectHelper<Gadget> jdbcSelectHelper, JdbcInsertUpdateDeleteHelper<Gadget> jdbcInsertUpdateDeleteHelper) {
        this.jdbcSelectHelper = jdbcSelectHelper;
        this.jdbcInsertUpdateDeleteHelper = jdbcInsertUpdateDeleteHelper;
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
    public <U> Gadget retrieveModel(U key) {
        try {
            return jdbcSelectHelper.selectOne(Gadget.class,"gid",key);
        } catch (EmptyResultDataAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public Boolean createModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.insertOne(Gadget.class,model) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public Boolean updateModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.updateOne(Gadget.class,"gid",model) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }

    @Override
    public <U> Boolean deleteModel(U key) {
        try {
            return jdbcInsertUpdateDeleteHelper.deleteOne(Gadget.class,"gid",key) > 0;
        } catch (IllegalAccessException e) {
            throw new ContentNotAllowed(e);
        }
    }
}
