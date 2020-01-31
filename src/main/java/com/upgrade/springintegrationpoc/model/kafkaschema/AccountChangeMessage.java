package com.upgrade.springintegrationpoc.model.kafkaschema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "accountId", "loanAccountNumber" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountChangeMessage implements SpectrumMessage {

    private Long accountId;

    private Long loanAccountNumber;

    private String firstName;

    private String lastName;

    private String address;
}
