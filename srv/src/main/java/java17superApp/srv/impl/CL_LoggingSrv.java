package java17superApp.srv.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sap.cds.Result;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.request.UserInfo;

import cds.gen.db.esmlogs.Esmappmsglog;
import java17superApp.srv.intf.IF_LoggingSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CL_LoggingSrv implements IF_LoggingSrv
{

    private final PersistenceService ps;

    private final UserInfo userInfo;

    private final String msgLogsTablePath = "db.esmlogs.esmappmsglog"; // Table Path - HANA

    @Override
    public Result addLog()
    {
        Result response = null;

        Map<String, Object> logEntity = new HashMap<>();
        logEntity.put("ID", UUID.randomUUID()); // ID
        logEntity.put("username", userInfo.getName()); // User Name
        logEntity.put("timestamp", new Timestamp(System.currentTimeMillis())); // TimeStamp
        logEntity.put("status", "New"); // Status
        logEntity.put("msgtype", "Information"); // Message Type
        logEntity.put("objectid", UUID.randomUUID()); // Object ID
        logEntity.put("message", "New Message Logged in the DB"); // Message Text

        if (logEntity != null)
        {
            CqnInsert qLogInsert = Insert.into(this.msgLogsTablePath).entry(logEntity);
            if (qLogInsert != null)
            {
                log.info("LOG Insert Query Bound!");
                Result result = ps.run(qLogInsert);
                if (result.list().size() > 0)
                {
                    log.info("# Log Successfully Inserted - " + result.rowCount());
                    response = result;

                }
            }
        }

        return response;
    }

    @Override
    public List<Esmappmsglog> readLogs()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readLogs'");
    }

}
