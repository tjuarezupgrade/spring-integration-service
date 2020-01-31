package com.upgrade.springintegrationpoc.model.spectrumdeffinition;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SpectrumBaseMessage {

    private Long id;

    private String messageType;

    private String company;

    private String opsArea;

    private BigDecimal amount;

    private LocalDate date;
}
