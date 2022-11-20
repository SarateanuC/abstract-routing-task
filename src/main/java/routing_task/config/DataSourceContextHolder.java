package routing_task.config;

import org.springframework.stereotype.Component;
import routing_task.config.enums.DataSourceEnum;

@Component
public class DataSourceContextHolder {
    private final ThreadLocal<DataSourceEnum> threadLocal;

    public DataSourceContextHolder() {
        threadLocal = new ThreadLocal<>();
    }

    public void setBranchContext(DataSourceEnum dataSourceEnum) {
        threadLocal.set(dataSourceEnum);
    }

    public DataSourceEnum getBranchContext() {
        return threadLocal.get();
    }

    public void clearBranchContext() {
        threadLocal.remove();
    }
}
