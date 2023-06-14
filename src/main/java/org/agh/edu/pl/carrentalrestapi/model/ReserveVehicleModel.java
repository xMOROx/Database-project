package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "ReserveVehicle")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ReserveVehicleModel {
    @NotNull
    private Long userID;
    @NotNull
    private Long vehicleID;
    @NotNull
    private Long locationID;
    private String receiptDate;
    @NotNull
    private String returnDate;
    @NotNull
    private BigDecimal totalCost;
}
