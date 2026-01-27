package com.williamsilva.algashop.billingscheduler.infrastructure;

import com.williamsilva.algashop.billingscheduler.application.CancelExpiredInvoicesApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class CancelExpiredInvoicesApplicationServiceJDBCImpl implements CancelExpiredInvoicesApplicationService {

    private static final Logger log = LoggerFactory.getLogger(CancelExpiredInvoicesApplicationServiceJDBCImpl.class);

    private static final Duration EXPIRED_SINCE = Duration.ofDays(1);
    private static final String UNPAID_STATUS = "UNPAID";
    private static final String CANCEL_STATUS = "CANCELED";
    private static final String CANCEL_REASON = "Invoice expired";

    private static final String SELECT_EXPIRED_INVOICES_SQL = String.format("""
                select id
                from invoice i
                where i.expires_at <= now() - interval '%d days'
                  and i.status = ?
            """, EXPIRED_SINCE.toDays());

    private static final String UPDATE_INVOICE_STATUS_SQL = """
                update invoice set status = ?, canceled_at = now(), cancel_reason = ?
                where id = ?
            """;

    private final JdbcOperations jdbcOperations;

    public CancelExpiredInvoicesApplicationServiceJDBCImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void cancelExpiredInvoices() {
        List<UUID> invoiceIds = fetchExpiredInvoices();
        log.info("Task - Total invoices fetched: {}", invoiceIds.size());
        int totalCanceledInvoices = cancelInvoices(invoiceIds);
        log.info("Task - Total invoices canceled: {}", totalCanceledInvoices);
    }

    private List<UUID> fetchExpiredInvoices() {
        PreparedStatementSetter preparedStatementSetter = ps -> {
            ps.setString(1, UNPAID_STATUS);
        };
        RowMapper<UUID> mapper = (resultSet, rowNum) -> resultSet.getObject("id", UUID.class);
        return jdbcOperations.query(SELECT_EXPIRED_INVOICES_SQL, preparedStatementSetter, mapper);
    }

    private int cancelInvoices(List<UUID> invoiceIds) {
        int updatedInvoices = 0;
        for (UUID invoiceId : invoiceIds) {
            try {
                jdbcOperations.update(UPDATE_INVOICE_STATUS_SQL, CANCEL_STATUS, CANCEL_REASON, invoiceId);
                updatedInvoices++;
                log.info("Task - Invoice canceled ID {}", invoiceId);
            } catch (DataAccessException e) {
                log.error("Task - Failed to cancel invoice with ID {}", invoiceId, e);
            }
        }
        return updatedInvoices;
    }
}
