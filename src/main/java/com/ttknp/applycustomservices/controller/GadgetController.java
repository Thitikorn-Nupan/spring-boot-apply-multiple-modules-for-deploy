package com.ttknp.applycustomservices.controller;

import com.ttknp.applycustomservices.entity.Gadget;
import com.ttknp.applycustomservices.service.ModelService;
import com.ttknp.responsecustomservice.constant.CommonStatus;
import com.ttknp.responsecustomservice.entity.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value = "/api/gadget")
public class GadgetController {

    private final ModelService<Gadget> modelService;

    @Autowired
    public GadgetController(ModelService<Gadget> modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/selectAll")
    public ResponseEntity<ResponseObject<List<Gadget>>> retrieveAllModels() {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String)  CommonStatus.OK[1])
                        .data(modelService.retrieveAllModels())
                        .build()
                );
    }

    @GetMapping(value = "/selectOne/{gid}")
    public ResponseEntity<ResponseObject<Gadget>> retrieveModel(@PathVariable String gid) {
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String)  CommonStatus.OK[1])
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
                        .info((String)  CommonStatus.CREATE[1])
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
                        .info((String)  CommonStatus.ACCEPTED[1])
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
                        .info((String)  CommonStatus.ACCEPTED[1])
                        .data(modelService.deleteModel(gid))
                        .build()
                );
    }

}
