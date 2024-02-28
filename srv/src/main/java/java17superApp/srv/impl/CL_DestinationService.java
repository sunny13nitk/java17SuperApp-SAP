package java17superApp.srv.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.SessionScope;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;

import java17superApp.model.pojos.TY_DestinationProps;
import java17superApp.srv.intf.IF_DestinationService;
import lombok.extern.slf4j.Slf4j;

@Service
@SessionScope
@Slf4j
public class CL_DestinationService implements IF_DestinationService
{
    private TY_DestinationProps desProps;

    private static final String prop_URL = "URL";
    private static final String prop_Token = "authTokens";
    private static final String cons_value = ", value=";
    private static final String cons_bracketClose = "\\)";

    @Override
    public TY_DestinationProps getDestinationDetails4User(String DestinationName) throws Exception
    {

        if (StringUtils.hasText(DestinationName))
        {
            log.info("Scanning for Destination : " + DestinationName);
            if (desProps == null)
            {
                log.info("Destination Not bound. Invoking Destination Service..");
                getDestinationDetails(DestinationName);
            }
        }

        return desProps;
    }

    private void getDestinationDetails(String destinationName) throws Exception
    {
        try
        {

            log.info("Scanning for Destination : " + destinationName);
            Destination dest = DestinationAccessor.getDestination(destinationName);
            if (dest != null)
            {

                log.info("Destination Bound via Destination Accessor.");

                desProps = new TY_DestinationProps();

                for (String prop : dest.getPropertyNames())
                {

                    if (prop.equals(prop_URL))
                    {
                        desProps.setBaseUrl(dest.get(prop).get().toString());
                    }

                    if (prop.equals(prop_Token))
                    {
                        desProps.setAuthToken(parseToken(dest.get(prop).get().toString()));
                    }

                }

            }
        }
        catch (DestinationAccessException e)
        {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            log.error("Error Accessing Destination : " + e.getLocalizedMessage());
            log.error(stackTrace);
            throw new Exception("Not able to connect to the Destination : " + e.getLocalizedMessage());

        }
    }

    private String parseToken(String authToken)
    {
        String token = null;

        if (StringUtils.hasText(authToken))
        {
            String[] tokens = authToken.split(cons_value);
            if (tokens.length > 0)
            {
                String tokenval = tokens[tokens.length - 1];
                if (StringUtils.hasText(tokenval))
                {
                    String[] tokenAuth = tokenval.split(cons_bracketClose);
                    if (tokenAuth.length > 0)
                    {
                        token = tokenAuth[0];
                    }
                }
            }
        }

        return token;
    }

}
