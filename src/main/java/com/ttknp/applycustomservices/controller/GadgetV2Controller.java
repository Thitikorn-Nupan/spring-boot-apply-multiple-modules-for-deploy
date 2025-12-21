package com.ttknp.applycustomservices.controller;

import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlOrderByHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.SqlWhereHelper;
import com.ttknp.jdbccustomservice.jdbc.sql_order_by_and_where.entity.RequestOrderBy;
import com.ttknp.responsecustomservice.constant.CommonStatus;
import com.ttknp.responsecustomservice.entity.ResponseObject;
import com.ttknp.webcustomservice.annotation.CommonRestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// No security
// Note, configOrigins you can test on postman on header
@CommonRestAPI(configPath = "/api.v2/gadget", configOrigins = "http://localhost:4200")
public class GadgetV2Controller {

    private static final Logger log = LoggerFactory.getLogger(GadgetV2Controller.class);
    private final ModelService<Gadget> modelService;

    @Autowired
    public GadgetV2Controller(ModelService<Gadget> modelService) {
        this.modelService = modelService;
    }


    @GetMapping(value = "/loadStatementAndSelectAll")
    private ResponseEntity<ResponseObject<List<Gadget>>> loadStatementAndSelectAll() {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.loadSqlStatementAndRetrieveAllModels())
                        .build()
                );
    }

    @GetMapping(value = "/loadSqlStatementAndRetrieveModel")
    private ResponseEntity<ResponseObject<List<Gadget>>> loadSqlStatementAndRetrieveModel(@RequestParam String gid) {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.loadSqlStatementAndRetrieveModel(gid))
                        .build()
                );
    }

    @GetMapping(value = "/loadSqlStatementAndRetrieveModels")
    private ResponseEntity<ResponseObject<List<Gadget>>> loadSqlStatementAndRetrieveModel(@RequestParam Float price1,@RequestParam Float price2,@RequestParam String brand) {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.loadSqlStatementAndRetrieveModel(price1,price2,brand))
                        .build()
                );
    }


    @GetMapping(value = "/selectAll")
    private ResponseEntity<ResponseObject<List<Gadget>>> retrieveAllModels() {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveAllModels())
                        .build()
                );
    }

    @GetMapping(value = "/selectAllWhereAndOrderByCustomAlias")
    private ResponseEntity<ResponseObject<List<Gadget>>> getAllWhereAndOrderByCustomAlias(@RequestBody(required = false) RequestOrderBy<Gadget> requestOrderBy) {
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        if (requestOrderBy != null) {
            if (gadget != null) {
                sqlWhereHelper = ((stringBuilder, alias, model) -> {
                    String whereAsString = requestOrderBy.getWhereModelIsPkSubclass(alias); // Case class have primary key on subclass
                    stringBuilder.append(whereAsString);
                });
            }

            if (requestOrderBy.getOrderBy() != null && !requestOrderBy.getOrderBy().isEmpty()) {
                sqlOrderByHelper = ((stringBuilder, alias, model) -> {
                    String orderByAsString = requestOrderBy.getOrderBy(alias, requestOrderBy.getOrderBy());
                    stringBuilder.append(orderByAsString);
                });
            }

        }
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, null, gadget))
                        .build()
                );
    }

    @GetMapping(value = "/selectAllWhereAndOrderBy")
    private ResponseEntity<ResponseObject<List<Gadget>>> getAllWhereAndOrderBy(@RequestBody(required = false) RequestOrderBy<Gadget> requestOrderBy) {
        SqlWhereHelper<Gadget> sqlWhereHelper = null;
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        Gadget gadget = requestOrderBy.getWhereModel() != null ? requestOrderBy.getWhereModel() : null;
        if (requestOrderBy != null) {
            if (gadget != null) {
                sqlWhereHelper = ((stringBuilder, alias, model) -> {
                    String whereAsString = requestOrderBy.getWhereModelIsPkSubclass(alias); // Case class have primary key on subclass
                    stringBuilder.append(whereAsString);
                });
            }

            if (requestOrderBy.getOrderBy() != null && !requestOrderBy.getOrderBy().isEmpty()) {
                sqlOrderByHelper = ((stringBuilder, alias, model) -> {
                    String orderByAsString = requestOrderBy.getOrderBy(alias, requestOrderBy.getOrderBy());
                    stringBuilder.append(orderByAsString);
                });
            }

        }
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveWhereAndOrderByAllModels(sqlWhereHelper, sqlOrderByHelper, gadget))
                        .build()
                );
    }

    @GetMapping(value = "/selectAllOrderBy")
    private ResponseEntity<ResponseObject<List<Gadget>>> getAllCustomersOrderBy(@RequestBody(required = false) RequestOrderBy<Gadget> requestOrderBy) {
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        if (requestOrderBy != null) {
            log.debug("orderBy (list) = {}", requestOrderBy.getOrderBy()); // [OrderBy{column='price', direction='asc'}, OrderBy{column='brand', direction='desc'}, OrderBy{column='gid', direction='asc'}]
            if (!requestOrderBy.getOrderBy().isEmpty()) {
                log.debug("orderBy & requestOrderBy are not null");
                //  Rough (adj. คร่าวๆ) process ex,
                //  Before implement method void work query look like (talk about stringBuilder)
                //  1. select * from gadget
                //  If have a call SqlOrderByHelper.appendOrderBy(...)
                //  2. select * from gadget as alias order by
                sqlOrderByHelper = ((stringBuilder, alias, model) -> {
                    /*
                      This is lambda expression for implement SqlOrderByHelper interface, Note! sqlOrderByHelper = ((...)) -> {...}); means implement interface  appendOrderBy(...) method
                      Again ! Not work yet at the first time If you see log it switched 2 classes as JdbcSelectHelper & CustomerController
                    */
                    // 3. orderByAsString will store value as alias.price,alias.model,...
                    String orderByAsString = requestOrderBy.getOrderBy(alias, requestOrderBy.getOrderBy());
                    stringBuilder.append(orderByAsString);
                    // 4. select * from gadget as alias order by  alias.price asc, alias.brand desc, alias.gid asc limit 20
                });
            } else { // Optional else
                log.debug("orderBy & requestOrderBy are null");
            }
        } else {  // On postman set body to none for no request body
            log.debug("requestOrderBy is null");
        }
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveOrderByAllModels(sqlOrderByHelper))
                        .build()
                );
    }

    @GetMapping(value = "/selectAllOrderByAndReplaceAssignValues")
    private ResponseEntity<ResponseObject<List<Gadget>>> getAllCustomersOrderByAndReplaceAssignValues(@RequestBody(required = false) RequestOrderBy requestOrderBy) {
        SqlOrderByHelper<Gadget> sqlOrderByHelper = null;
        if (requestOrderBy != null) {
            if (!requestOrderBy.getOrderBy().isEmpty()) {
                sqlOrderByHelper = ((stringBuilder, alias, model) -> {
                    String orderByAsString = requestOrderBy.getOrderBy(alias, requestOrderBy.getOrderBy());
                    stringBuilder.append(orderByAsString);
                });
            }
        }
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveOrderByAllModelsAndReplaceAssignValues(sqlOrderByHelper))
                        .build()
                );
    }


    @GetMapping(value = "/selectOne/{gid}")
    public ResponseEntity<ResponseObject<Gadget>> retrieveModel(@PathVariable String gid) {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(modelService.retrieveModel(gid))
                        .build()
                );
    }

    @PostMapping(value = "/insertOne")
    public ResponseEntity<ResponseObject<Boolean>> createModel(@RequestBody Gadget gadget) {
        return ResponseEntity
                .status((Short) CommonStatus.CREATE[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.CREATE[0])
                        .info((String) CommonStatus.CREATE[1])
                        .data(modelService.createModel(gadget))
                        .build()
                );
    }

    @PutMapping(value = "/updateOne")
    public ResponseEntity<ResponseObject<Boolean>> updateModel(@RequestBody Gadget gadget) {
        return ResponseEntity
                .status((Short) CommonStatus.ACCEPTED[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.ACCEPTED[0])
                        .info((String) CommonStatus.ACCEPTED[1])
                        .data(modelService.updateModel(gadget))
                        .build()
                );
    }

    @DeleteMapping(value = "/deleteOne/{gid}")
    public ResponseEntity<ResponseObject<Boolean>> deleteModel(@PathVariable String gid) {
        return ResponseEntity
                .status((Short) CommonStatus.ACCEPTED[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.ACCEPTED[0])
                        .info((String) CommonStatus.ACCEPTED[1])
                        .data(modelService.deleteModel(gid))
                        .build()
                );
    }


    @GetMapping(value = "/readStatement/{con}")
    public ResponseEntity<ResponseObject<Boolean>> readStatement(@PathVariable String con) {
        switch (con) {
            case "truncateTableByAbsPath":
                modelService.loadSqlStatementByAbsPath();
                break;
            case "truncateTableByRootPath":
                modelService.loadSqlStatementByRootPath();
                break;
            case "insertSelectWherePkByAbsPath":
                modelService.loadSqlStatementAndBindParamsByAbsPath();
                break;
            case"insertSelectWherePkByRootPath":
                modelService.loadSqlStatementAndBindParamsByRootPath();
                break;
            default:
                break;
        }
        return ResponseEntity
                .status((Short) CommonStatus.ACCEPTED[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.ACCEPTED[0])
                        .info((String) CommonStatus.ACCEPTED[1])
                        .data(true)
                        .build()
                );
    }

}
