package routing_task.contextHolder;

import org.springframework.stereotype.Component;

import routing_task.main.entity.NextDBConnection;

@Component
public class DataSourceContextHolder {
    private final ThreadLocal<NextDBConnection> threadLocal;

    public DataSourceContextHolder() {
        threadLocal = new ThreadLocal<>();
    }

    public void setBranchContext(NextDBConnection nextDbConnection) {
        threadLocal.set(nextDbConnection);
    }

    public NextDBConnection getBranchContext() {
        return threadLocal.get();
    }

    public void clearBranchContext() {
        threadLocal.remove();
    }
}
