package com.ttknp.applycustomservices.dto;

import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.select.JdbcSelectHelper;
import com.ttknp.jdbccustomservice.jdbc.update.JdbcInsertUpdateDeleteHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcSelectHelper.selectAll(Gadget.class);
    }

    @Override
    public <U> Gadget retrieveModel(U key) {
        return jdbcSelectHelper.selectOne(Gadget.class,"gid",key);
    }

    @Override
    public Boolean createModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.insertOne(Gadget.class,model) > 0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean updateModel(Gadget model) {
        try {
            return jdbcInsertUpdateDeleteHelper.updateOne(Gadget.class,"gid",model) > 0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Boolean deleteModel(U key) {
        try {
            return jdbcInsertUpdateDeleteHelper.deleteOne(Gadget.class,"gid",key) > 0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
