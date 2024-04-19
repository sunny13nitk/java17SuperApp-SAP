package java17superApp.rest.auth.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TY_DesDetails_OAuth2CC
{
    private String clientId;
    private String clientSecret;
    private String url;
    private String tokenUrl;
    private String code;

}
