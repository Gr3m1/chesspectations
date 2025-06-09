package mate.check.chesspectations.controller;

import lombok.RequiredArgsConstructor;
import mate.check.chesspectations.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authHeader) throws Exception {

        boolean validated = authService.validate(authHeader);

        if (validated) {
            return new ResponseEntity<>(validated, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
}
