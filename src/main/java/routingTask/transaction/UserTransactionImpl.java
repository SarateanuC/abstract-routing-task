package routingTask.transaction;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.transaction.*;

@Component
@RequiredArgsConstructor
public class UserTransactionImpl implements javax.transaction.UserTransaction {
    private final TransactionManager jtaTransactionManager;


    @Override
    public void begin() throws NotSupportedException, SystemException {
        jtaTransactionManager.getUserTransaction().begin();
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        jtaTransactionManager.getUserTransaction().commit();
    }

    @Override
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        jtaTransactionManager.getUserTransaction().rollback();
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        jtaTransactionManager.getUserTransaction().setRollbackOnly();
    }

    @Override
    public int getStatus() throws SystemException {
        return jtaTransactionManager.getUserTransaction().getStatus();
    }

    @Override
    public void setTransactionTimeout(int i) throws SystemException {
        jtaTransactionManager.getUserTransaction().setTransactionTimeout(i);
    }
}
