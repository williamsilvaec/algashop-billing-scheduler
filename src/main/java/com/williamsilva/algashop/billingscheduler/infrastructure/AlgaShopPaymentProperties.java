package com.williamsilva.algashop.billingscheduler.infrastructure;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("algashop.integrations.payment")
@Validated
public class AlgaShopPaymentProperties {

    @NotNull
    @Valid
    private FastpayProperties fastpay;

    @Validated
    public static class FastpayProperties {

        @NotBlank
        private String hostname;

        @NotBlank
        private String privateToken;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getPrivateToken() {
            return privateToken;
        }

        public void setPrivateToken(String privateToken) {
            this.privateToken = privateToken;
        }
    }

    public FastpayProperties getFastpay() {
        return fastpay;
    }

    public void setFastpay(FastpayProperties fastpay) {
        this.fastpay = fastpay;
    }
}
