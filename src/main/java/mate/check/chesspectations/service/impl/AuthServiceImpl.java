package mate.check.chesspectations.service.impl;

import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.service.AuthService;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    public static String ADMIN = "YWRtaW46YWRtaW4=";

    // TODO: do actual security implementation like LDAP or AD, this is horrible
    @Override
    public boolean validate(String authHeader) throws LoginException {
        try {
            if(null != authHeader && authHeader.startsWith("Basic ")){
                String base64Creds = authHeader.substring("Basic ".length());

                return base64Creds.equals(ADMIN);
            }
        } catch (Exception e) {
            throw new LoginException();
        }
        return false;
    }
}
