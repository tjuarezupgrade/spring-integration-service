package com.upgrade.springintegrationpoc.model.kafkaschema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "accountId", "loanAccountNumber", "amount", "date" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonetaryTransactionMessage implements SpectrumMessage {

    private Long accountId;

    private Long loanAccountNumber;

    private BigDecimal amount;

    private LocalDate date;
}
