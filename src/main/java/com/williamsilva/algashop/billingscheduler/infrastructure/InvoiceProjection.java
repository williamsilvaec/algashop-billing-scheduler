package com.williamsilva.algashop.billingscheduler.infrastructure;

import java.util.UUID;

public class InvoiceProjection {

    private UUID id;
    private String paymentGatewayCode;

    public InvoiceProjection() {
    }

    public InvoiceProjection(UUID id, String paymentGatewayCode) {
        this.id = id;
        this.paymentGatewayCode = paymentGatewayCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPaymentGatewayCode() {
        return paymentGatewayCode;
    }

    public void setPaymentGatewayCode(String paymentGatewayCode) {
        this.paymentGatewayCode = paymentGatewayCode;
    }
}
