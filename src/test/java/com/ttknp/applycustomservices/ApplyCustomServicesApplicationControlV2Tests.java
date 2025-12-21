package com.ttknp.applycustomservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttknp.applycustomservices.controller.GadgetV2Controller;
import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlOrderByHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlWhereHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.entity.RequestOrderBy;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 Note If the bean you're trying to inject from a JAR file is not an MVC-specific component (e.g., it's a service or a utility class),
 @WebMvcTest will not include it in the application context created for the test.
 But you can still inject all the beans on jar file
 You have to comment @ComponentScan(basePackages = {"com.ttknp"}) on your main class for testing mode
*/
// *** JUnit5 test cases for CRUD REST APIs. use the @WebMvcTest annotation to load only UserController class. (can multiple rest controller)
@WebMvcTest(GadgetV2Controller.class)
class ApplyCustomServicesApplicationControlV2Tests {
    private static final Logger log = LoggerFactory.getLogger(ApplyCustomServicesApplicationControlV2Tests.class);
    // *** using MockMvc class to make REST API calls.
    @Autowired
    private MockMvc mockMvc;
    // *** using @MockitoBean annotation to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.
    @MockBean
    private ModelService<Gadget> modelService;


    @Test
    public void testSelectAll() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.retrieveAllModels()).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/selectAll"); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void testSelectAllWhereAndOrderByCustomAlias() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        // RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(10,List.of(new RequestOrderBy.OrderBy("gid","asc")),getGadgets().get(0));
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(10,List.of(new RequestOrderBy.OrderBy("gid","asc")),null);
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(requestOrderBy);
        log.debug("requestBody: {}", requestBody); // {"length":10,"orderBy":[{"column":"gid","direction":"asc"}],"whereModel":{"gid":"G001","model":"A","brand":"A","price":750.5,"amount":10000}}
        given(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, null, gadget)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/selectAllWhereAndOrderByCustomAlias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void testSelectAllWhereAndOrderBy() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(100,List.of(new RequestOrderBy.OrderBy("gid","desc")),getGadgets().get(0));
        // RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(10,List.of(new RequestOrderBy.OrderBy("gid","asc")),null);
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(requestOrderBy);
        log.debug("requestBody: {}", requestBody);
        given(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, gadget)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/selectAllWhereAndOrderBy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void testSelectAllOrderBy() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        // RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(100,List.of(new RequestOrderBy.OrderBy("gid","desc")),getGadgets().get(0));
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(10,List.of(new RequestOrderBy.OrderBy("gid","asc"),new RequestOrderBy.OrderBy("model","asc")),null);
        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(requestOrderBy);
        given(modelService.retrieveOrderByAllModels( sqlOrderByHelper)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/selectAllOrderBy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }


    @Test
    public void testSelectAllOrderByAndReplaceAssignValues() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        RequestOrderBy<Gadget> requestOrderBy = new RequestOrderBy<>(10,List.of(new RequestOrderBy.OrderBy("gid","asc"),new RequestOrderBy.OrderBy("model","asc")),null);
        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(requestOrderBy);
        given(modelService.retrieveOrderByAllModelsAndReplaceAssignValues( sqlOrderByHelper)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/selectAllOrderByAndReplaceAssignValues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void testLoadStatementAndSelectAll() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.loadSqlStatementAndRetrieveAllModels()).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/loadStatementAndSelectAll"); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void testLoadSqlStatementAndRetrieveModel() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        String gid = "G002";
        given(modelService.loadSqlStatementAndRetrieveModel(gid)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/loadSqlStatementAndRetrieveModel").param("gid","G002"); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }


    @Test
    public void testLoadSqlStatementAndRetrieveModels() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        Float price1 = 100.0f;
        Float price2 = 2000.0f;
        String brand = "A";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("brand", brand);
        params.add("price1", price1.toString());
        params.add("price2", price2.toString());
        given(modelService.loadSqlStatementAndRetrieveModel(price1,price2,brand)).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/loadSqlStatementAndRetrieveModels")
                .params(params);// Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }


    @Test
    public void testSelectOne() throws Exception {
        List<Gadget> gadgets = getGadgets();
        Gadget gadget = gadgets.get(0);
        String id = gadget.getGid();
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.retrieveModel(id)).willReturn(gadget);
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api.v2/gadget/selectOne/{gid}", id);
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isOk());
    }


    @Test
    public void testInsertOne() throws Exception {
        Gadget gadget = getGadgets().get(0);
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.createModel(gadget)).willReturn(true);

        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(gadget);

        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api.v2/gadget/insertOne")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isCreated());
    }


    @Test
    public void testUpdateOne() throws Exception {
        Gadget gadget = getGadgets().get(0);
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.updateModel(gadget)).willReturn(true);

        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(gadget);

        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api.v2/gadget/updateOne")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody); // Note i have to cut /api because this prefix work after module running
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isAccepted());
    }

    @Test
    public void testDeleteOne() throws Exception {
        String gid = getGadgets().get(0).getGid();
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.deleteModel(gid)).willReturn(true);
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api.v2/gadget/deleteOne/{gid}", gid);
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isAccepted());
    }

    @Test
    public void testReadStatement() throws Exception {
        String con = "truncateTableByAbsPath";
        String con1 = "truncateTableByRootPath";
        String con2 = "insertSelectWherePkByAbsPath";
        String con3 = "insertSelectWherePkByRootPath";
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        willDoNothing().given(modelService).loadSqlStatementByAbsPath();
        willDoNothing().given(modelService).loadSqlStatementByRootPath();
        willDoNothing().given(modelService).loadSqlStatementAndBindParamsByAbsPath();
        willDoNothing().given(modelService).loadSqlStatementAndBindParamsByRootPath();
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api.v2/gadget/readStatement/{con}", con);
        RequestBuilder request1 = MockMvcRequestBuilders.get("/api.v2/gadget/readStatement/{con}", con1);
        RequestBuilder request2 = MockMvcRequestBuilders.get("/api.v2/gadget/readStatement/{con}", con2);
        RequestBuilder request3 = MockMvcRequestBuilders.get("/api.v2/gadget/readStatement/{con}", con3);
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ResultActions response1 = mockMvc.perform(request1);
        ResultActions response2 = mockMvc.perform(request2);
        ResultActions response3 = mockMvc.perform(request3);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isAccepted());
        response1.andExpect(status().isAccepted());
        response2.andExpect(status().isAccepted());
        response3.andExpect(status().isAccepted());
    }

    private List<Gadget> getGadgets() {
        return List.of(
                new Gadget("G001","A","A",750.50,10000L),
                new Gadget("G002","A","A",750.50,10000L),
                new Gadget("G003","A","A",750.50,10000L)
        );
    }

}
