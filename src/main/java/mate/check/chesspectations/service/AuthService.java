package mate.check.chesspectations.service;

import javax.security.auth.login.LoginException;

public interface AuthService {

    boolean validate(String authHeader)  throws LoginException;
}
