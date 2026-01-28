package com.williamsilva.algashop.billingscheduler.infrastructure;

import com.williamsilva.algashop.billingscheduler.application.CancelExpiredInvoicesApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CancelExpiredInvoicesRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CancelExpiredInvoicesRunner.class);

    private final CancelExpiredInvoicesApplicationService applicationService;

    public CancelExpiredInvoicesRunner(CancelExpiredInvoicesApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Task started - Canceling expired invoices.");
        applicationService.cancelExpiredInvoices();
        log.info("Task ended - Expired invoices.");
    }
}
