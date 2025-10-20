package ru.arkhipov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.*;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final List<ModifyRequestService> modifyRequestServices;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        List<ModifyRequestService> modifyRequestService){
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestServices = modifyRequestService;
    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult){
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemName(request.getSystemName())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        logResponse(response);

        try{
            validationService.isValid(bindingResult, request);
        } catch (ValidationFailedException | UnsupportedCodeException e){
            logError(e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            logResponse(response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logError(e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            logResponse(response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        for(ModifyRequestService modifyRequestService : modifyRequestServices){
            modifyRequestService.modify(request);
        }

        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }

    private static void logError(Exception e) {
        log.error("Error: {}", e.getMessage());
    }

    private static void logResponse(Response response) {
        log.info("response {}", response);
    }
}
