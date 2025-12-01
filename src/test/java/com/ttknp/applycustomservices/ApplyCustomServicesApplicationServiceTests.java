package com.ttknp.applycustomservices;


import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.helpers.JdbcTableHelper;
import com.ttknp.jdbccustomservice.jdbc.helpers.ReadSQLStatementHelper;
import com.ttknp.jdbccustomservice.jdbc.select.JdbcSelectHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlOrderByHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlWhereHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.entity.RequestOrderBy;
import com.ttknp.jdbccustomservice.jdbc.update.JdbcInsertUpdateDeleteHelper;
import com.ttknp.jdbccustomservice.jdbc.utility.JdbcReadSQLFileHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

/**
 * @SpringBootTest for testing on service layer
 */
// @SpringBootTest // fix error create bean
@ExtendWith(MockitoExtension.class)
class ApplyCustomServicesApplicationServiceTests {


    @InjectMocks
    private JdbcSelectHelper<Gadget> jdbcSelectHelper;
    @InjectMocks
    private JdbcInsertUpdateDeleteHelper<Gadget> jdbcInsertUpdateDeleteHelper;
    @InjectMocks
    private JdbcReadSQLFileHelper jdbcReadSQLFileHelper;
    @InjectMocks
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private JdbcTableHelper jdbcTableHelper;
    @InjectMocks
    private ReadSQLStatementHelper readSQLFileAsStatement;
    @Mock
    private ModelService<Gadget> modelService;


    @Test
    public void testRetrieveAllModels() {
        // Note this not normal concept but it works with multiple module
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveAllModels()).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgets = modelService.retrieveAllModels();
        Assertions.assertEquals(3, gadgets.size());
        Mockito.verify(modelService, Mockito.times(1)).retrieveAllModels();
    }


    @Test
    public void testRetrieveOrderByAllModels() {
        // Note this not normal concept but it works with multiple module
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveOrderByAllModels(sqlOrderByHelper)).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgets = modelService.retrieveOrderByAllModels(sqlOrderByHelper);
        Assertions.assertEquals(3, gadgets.size());
        Mockito.verify(modelService, Mockito.times(1)).retrieveOrderByAllModels(sqlOrderByHelper);
    }

    @Test
    public void testRetrieveWhereAndOrderByAllModels() {
        // Note this not normal concept but it works with multiple module
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(100,List.of(new RequestOrderBy.OrderBy("gid","desc")),getGadgets().get(0));
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, gadget)).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgets = modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, gadget);
        Assertions.assertEquals(3, gadgets.size());
        Mockito.verify(modelService, Mockito.times(1)).retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, gadget);
    }

    @Test
    public void testRetrieveWhereAndOrderByAllModelsCustomAlias() {
        // Note this not normal concept but it works with multiple module
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(100,List.of(new RequestOrderBy.OrderBy("gid","desc")),getGadgets().get(0));
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper,null, gadget)).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgets = modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper,null, gadget);
        Assertions.assertEquals(3, gadgets.size());
        Mockito.verify(modelService, Mockito.times(1)).retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper,null, gadget);
    }

    @Test
    public void testRetrieveOrderByAllModelsAndReplaceAssignValues() {
        // Note this not normal concept but it works with multiple module
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveOrderByAllModelsAndReplaceAssignValues(sqlOrderByHelper)).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgets = modelService.retrieveOrderByAllModelsAndReplaceAssignValues(sqlOrderByHelper);
        Assertions.assertEquals(3, gadgets.size());
        Mockito.verify(modelService, Mockito.times(1)).retrieveOrderByAllModelsAndReplaceAssignValues(sqlOrderByHelper);
    }

    @Test
    public void testRetrieveModel() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        String id = gadget.getGid();
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.retrieveModel(id)).thenReturn(gadget);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        Gadget gadgetRes = modelService.retrieveModel(id);
        Assertions.assertEquals(gadgetRes.getModel(), gadget.getModel());
        Mockito.verify(modelService, Mockito.times(1)).retrieveModel(id);
    }

    @Test
    public void testCreateModel() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        gadget.setGid("G004");
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.createModel(gadget)).thenReturn(true);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        Boolean gadgetRes = modelService.createModel(gadget);
        Assertions.assertEquals(true, gadgetRes);
        Mockito.verify(modelService, Mockito.times(1)).createModel(gadget);
    }

    @Test
    public void testUpdateModel() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        gadget.setModel("Update");
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.updateModel(gadget)).thenReturn(true);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        Boolean gadgetRes = modelService.updateModel(gadget);
        Assertions.assertEquals(true, gadgetRes);
        Mockito.verify(modelService, Mockito.times(1)).updateModel(gadget);
    }

    @Test
    public void testDeleteModel() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.deleteModel(gadget)).thenReturn(true);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        Boolean gadgetRes = modelService.deleteModel(gadget);
        Assertions.assertEquals(true, gadgetRes);
        Mockito.verify(modelService, Mockito.times(1)).deleteModel(gadget);
    }

    @Test
    public void testLoadSqlStatementAndRetrieveAllModels() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.loadSqlStatementAndRetrieveAllModels()).thenReturn(gadgets);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgetsRes = modelService.loadSqlStatementAndRetrieveAllModels();
        Assertions.assertEquals(3, gadgetsRes.size());
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementAndRetrieveAllModels();
    }

    @Test
    public void testLoadSqlStatementAndRetrieveModel() {
        // Note this not normal concept but it works with multiple module
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        String id = gadget.getGid();
        List<Gadget> gadgetsNew = List.of(gadget);

        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.loadSqlStatementAndRetrieveModel(id)).thenReturn(gadgetsNew);
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgetsRes = modelService.loadSqlStatementAndRetrieveModel(id);
        Assertions.assertEquals(1, gadgetsRes.size());
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementAndRetrieveModel(id);
    }

    @Test
    public void testLoadSqlStatementAndRetrieveModels() {
        // Note this not normal concept but it works with multiple module
        Float price1 = 100.0f;
        Float price2 = 2000.0f;
        String brand = "A";
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.when(modelService.loadSqlStatementAndRetrieveModel(price1,price2,brand)).thenReturn(getGadgets());
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        List<Gadget> gadgetsRes = modelService.loadSqlStatementAndRetrieveModel(price1,price2,brand);
        Assertions.assertEquals(3, gadgetsRes.size());
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementAndRetrieveModel(price1,price2,brand);
    }

    @Test
    public void testLoadSqlAsVoidMethods() {
        // Note this not normal concept but it works with multiple module
        /*
        So, we have to tell Mockito to return something when userRepository.findAll() is called.
        We do this with the static when method.
        */
        Mockito.doNothing().when(modelService).loadSqlStatementByAbsPath();
        Mockito.doNothing().when(modelService).loadSqlStatementByRootPath();
        Mockito.doNothing().when(modelService).loadSqlStatementAndBindParamsByAbsPath();
        Mockito.doNothing().when(modelService).loadSqlStatementAndBindParamsByRootPath();
        /*
        Now users will call employeeLayerService.reads() it means. employeeRepository.findAll() method was called
        */
        modelService.loadSqlStatementByAbsPath();
        modelService.loadSqlStatementByRootPath();
        modelService.loadSqlStatementAndBindParamsByAbsPath();
        modelService.loadSqlStatementAndBindParamsByRootPath();

        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementByAbsPath();
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementByRootPath();
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementAndBindParamsByAbsPath();
        Mockito.verify(modelService, Mockito.times(1)).loadSqlStatementAndBindParamsByRootPath();
    }




    private List<Gadget> getGadgets() {
        return List.of(
                new Gadget("G001", "A", "A", 750.50, 10000L),
                new Gadget("G002", "A", "A", 750.50, 10000L),
                new Gadget("G003", "A", "A", 750.50, 10000L)
        );
    }

}
