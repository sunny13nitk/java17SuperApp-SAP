package java17superApp.srv.intf;

import java17superApp.model.pojos.TY_DestinationProps;

public interface IF_DestinationService
{
    public TY_DestinationProps getDestinationDetails4User(String DestinationName) throws Exception;
}
