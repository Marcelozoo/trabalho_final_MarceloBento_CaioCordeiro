package dao;

import com.ufes.logadapter.models.Log;

import java.util.List;

public interface LogDAO {

    void inserirLog(Log log);
    List<Log> listarLogs();
}
