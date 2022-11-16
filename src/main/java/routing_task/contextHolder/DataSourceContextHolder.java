package routing_task.contextHolder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import routing_task.context.DataSourceEnum;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(value = SCOPE_SINGLETON)
public class DataSourceContextHolder {
    private static ThreadLocal<DataSourceEnum> threadLocal;

    public DataSourceContextHolder() {
        threadLocal = new ThreadLocal<>();
    }

    public void setBranchContext(DataSourceEnum dataSourceEnum) {
        threadLocal.set(dataSourceEnum);
    }

    public DataSourceEnum getBranchContext() {
        return threadLocal.get();
    }

    public static void clearBranchContext() {
        threadLocal.remove();
    }
}
