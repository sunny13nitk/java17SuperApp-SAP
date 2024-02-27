package java17superApp.srv.intf;

import java.util.List;

import com.sap.cds.Result;

import cds.gen.db.esmlogs.Esmappmsglog;

public interface IF_LoggingSrv
{
    public Result addLog();

    public List<Esmappmsglog> readLogs();

}
