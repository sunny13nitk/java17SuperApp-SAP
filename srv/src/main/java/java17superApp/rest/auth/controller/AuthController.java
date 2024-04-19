package java17superApp.rest.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;

import java17superApp.rest.auth.CL_DestinationConstants;
import java17superApp.rest.auth.pojos.TY_BearerToken;
import java17superApp.rest.auth.pojos.TY_DesDetails_OAuth2CC;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/authorize")
public class AuthController
{
    @GetMapping("/")
    public ResponseEntity<TY_BearerToken> authorize()
    {
        final String destination = "REST_API_AUTH_CODE";
        final String destinationBearer = "REST_API_BEARER";

        ResponseEntity<TY_BearerToken> bt = null;
        TY_DesDetails_OAuth2CC desDetails = getBearerTokenCode(destination);
        if (desDetails != null)
        {
            log.info(desDetails.toString());
        }

        return bt;

    }

    private TY_DesDetails_OAuth2CC getBearerTokenCode(String destination)
    {
        TY_DesDetails_OAuth2CC desDetails = null;
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
                        desDetails = new TY_DesDetails_OAuth2CC();
                        if (dest.get(CL_DestinationConstants.CF_CLIENTID).isDefined())
                        {
                            desDetails.setClientId(dest.get(CL_DestinationConstants.CF_CLIENTID).get().toString());
                        }
                    }

                        break;

                    default:
                        break;
                    }

                }

            }
        }

        return desDetails;
    }

}
