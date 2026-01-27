package com.williamsilva.algashop.billingscheduler.infrastructure;

import com.williamsilva.algashop.billingscheduler.application.CancelExpiredInvoicesApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CancelExpiredInvoicesScheduler {

    private static final Logger log = LoggerFactory.getLogger(CancelExpiredInvoicesScheduler.class);

    private final CancelExpiredInvoicesApplicationService applicationService;

    public CancelExpiredInvoicesScheduler(CancelExpiredInvoicesApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Scheduled(fixedRate = 5000) // a cada 5 segundos
    public void runTask() {
        log.info("Task started - Canceling expired invoices.");
        applicationService.cancelExpiredInvoices();
        log.info("Task ended - Expired invoices.");
    }
}
