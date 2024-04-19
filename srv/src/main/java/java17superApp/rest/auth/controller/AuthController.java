package java17superApp.rest.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;

import java17superApp.rest.auth.CL_DestinationConstants;
import java17superApp.rest.auth.pojos.TY_BearerToken;
import java17superApp.rest.auth.pojos.TY_DesDetails_OAuth2CC;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthController
{
    @GetMapping("/authorize")
    public ResponseEntity<TY_BearerToken> authorize(String destination)
    {

        ResponseEntity<TY_BearerToken> bt = null;
        if (StringUtils.hasText(destination))
        {
            Destination dest = DestinationAccessor.getDestination(destination);
            if (dest != null)
            {
                for (String prop : dest.getPropertyNames())
                {
                    log.info(prop);
                }
                
                if (dest.get(CL_DestinationConstants.CF_AUTHENTICATION).isDefined())
                {
                    switch (dest.get(CL_DestinationConstants.CF_AUTHENTICATION).get().toString())
                    {
                    case CL_DestinationConstants.CF_OAUTH2_CLIENT_CREDENTIALS_TYPE:
                    {
                        TY_DesDetails_OAuth2CC dD = new TY_DesDetails_OAuth2CC();
                        if (dest.get(CL_DestinationConstants.CF_CLIENTID).isDefined())
                        {
                            dD.setClientId(dest.get(CL_DestinationConstants.CF_CLIENTID).get().toString());
                        }
                    }

                        break;

                    default:
                        break;
                    }

                }

            }
        }

        return bt;

    }
}
