package com.example.sports_participant_be_main.utils.exceptions;

import com.example.sports_participant_be_main.utils.ResponseMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
@Slf4j
public class GymBrandHasAlreadyExistsException extends RuntimeException {
    private final String message = ResponseMessages.GymBrand.GYM_BRAND_EXISTS.message;

    public GymBrandHasAlreadyExistsException() {
        super(ResponseMessages.GymBrand.GYM_BRAND_EXISTS.message);
        log.error(message, this);
    }
}
