package java17superApp.rest.auth.pojos;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TY_BearerToken
{
    private String destinationName;
    private String acessToken;
    private String refreshToken;
    private String scopes;
    private Timestamp requestedAt;
    private long expiresIn;

}
