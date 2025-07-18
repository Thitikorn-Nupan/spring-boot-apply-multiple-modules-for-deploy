package com.ttknp.applycustomservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttknp.applycustomservices.controller.GadgetController;
import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 Note If the bean you're trying to inject from a JAR file is not an MVC-specific component (e.g., it's a service or a utility class),
 @WebMvcTest will not include it in the application context created for the test.
 But you can still inject all the beans on jar file
 You have to comment @ComponentScan(basePackages = {"com.ttknp"}) on your main class for testing mode
*/
// *** JUnit5 test cases for CRUD REST APIs. use the @WebMvcTest annotation to load only UserController class. (can multiple rest controller)
@WebMvcTest(GadgetController.class)
class ApplyCustomServicesApplicationTests {
    // *** using MockMvc class to make REST API calls.
    @Autowired
    private MockMvc mockMvc;
    // *** using @MockitoBean annotation to add mock objects to the Spring application context.
    // *** The mock will replace any existing bean of the same type in the application context.
    @MockBean
    private ModelService<Gadget> modelService;

    @Test
    public void testSelectAll() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        // given - precondition or setup
        given(modelService.retrieveAllModels()).willReturn(getGadgets());
        /// ** call the provider **
        // when -  action or the behaviour(n.พฤติกรรม) that we are going test
        RequestBuilder request = MockMvcRequestBuilders.get("/api/gadget/selectAll"); // Note i have to cut /api because this prefix work after module running
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
                .get("/api/gadget/selectOne/{gid}", id);
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
                .post("/api/gadget/insertOne")
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
                .put("/api/gadget/updateOne")
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
                .delete("/api/gadget/deleteOne/{gid}", gid);
        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);
        ///  ** result follow your api response
        // then - verify the output
        response.andExpect(status().isAccepted());
    }

    private List<Gadget> getGadgets() {
        return List.of(
                new Gadget("G001","A","A",750.50,10000L),
                new Gadget("G002","A","A",750.50,10000L),
                new Gadget("G003","A","A",750.50,10000L)
        );
    }

}
