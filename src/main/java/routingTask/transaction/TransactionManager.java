package routingTask.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;

@Component
public class TransactionManager extends JtaTransactionManager {
}
