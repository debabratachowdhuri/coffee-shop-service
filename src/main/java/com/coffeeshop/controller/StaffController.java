package com.coffeeshop.controller;

import com.coffeeshop.constant.*;
import com.coffeeshop.domain.Response;
import com.coffeeshop.domain.user.User;
import com.coffeeshop.domain.user.staff.Staff;
import com.coffeeshop.domain.user.staff.StaffAssembler;
import com.coffeeshop.domain.user.staff.StaffRegisterRequestDTO;
import com.coffeeshop.domain.user.staff.StaffRegisterRequestValidator;
import com.coffeeshop.exception.StaffNotFoundException;
import com.coffeeshop.service.StaffService;
import com.coffeeshop.service.common.MessageService;
import com.coffeeshop.util.CoffeeShopUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Route.STAFF)
@Api(tags="Staff", description = "Endpoint for staff")
public class StaffController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    StaffRegisterRequestValidator staffRegisterRequestValidator;

    @Autowired
    StaffService staffService;

    @RequestMapping(value = Route.STAFF_REGISTER,
            headers = APIVersion.V1,
            method = RequestMethod.POST)

    @ApiOperation(
            value = "Register Staff API",
            notes = "Returns new Staff registered data",
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity registerStaff(@RequestBody StaffRegisterRequestDTO staffRegisterRequestDTO, BindingResult errors)throws NoSuchMethodException,MethodArgumentNotValidException {

        Response response = new Response(messageService);

        staffRegisterRequestValidator.validate(staffRegisterRequestDTO,errors);
        if(errors.hasErrors()){
            LOGGER.debug("Errors in registration form {}",staffRegisterRequestDTO);
            throw new MethodArgumentNotValidException(
                    new MethodParameter(
                            this.getClass()
                                    .getDeclaredMethod(
                                            "registerStaff",
                    StaffRegisterRequestDTO.class,
                                            BindingResult.class),
                            0),errors);
        }

        Staff staff = staffService.register(staffRegisterRequestDTO);
        if (staff != null){
            response
                    .setStatus(com.coffeeshop.constant.ResponseStatus.SUCCESS)
                    .setMessage("staff.register.success")
                    .setData(StaffAssembler.fromStaff(staff));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response
                    .setStatus(com.coffeeshop.constant.ResponseStatus.FAIL)
                    .setMessage("staff.register.fail");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = Route.STAFF_ME,
            headers = APIVersion.V1,
            method = RequestMethod.GET)

    @ApiOperation(
            value = "Staff LOGGED IN DETAILS",
            notes = "Returns authenticated Staff registered data",
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server Error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity me() throws StaffNotFoundException {

        Response response = new Response(messageService);

        User user = CoffeeShopUtils.getLoggedInUser().getUser();
        Staff staff = staffService.getByID(user.getId());
        if (staff != null){
            response
                    .setStatus(com.coffeeshop.constant.ResponseStatus.SUCCESS)
                    .setMessage("staff.me.success")
                    .setData(StaffAssembler.fromStaff(staff));
        }else{
            throw new StaffNotFoundException("staff.not.found");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
