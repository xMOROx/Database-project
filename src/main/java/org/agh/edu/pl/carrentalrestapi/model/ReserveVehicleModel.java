package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.agh.edu.pl.carrentalrestapi.validation.FutureDate;
import org.agh.edu.pl.carrentalrestapi.validation.PastOrPresentDate;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "ReserveVehicle")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ReserveVehicleModel {
    @NotNull(message = "User ID is required")
    private Long userID;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleID;

    @NotNull(message = "Location ID is required")
    private Long locationID;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PastOrPresentDate(message = "Receipt date must be in the past or present")
    private String receiptDate;

    @NotBlank(message = "Return date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureDate(message = "Return date must be in the future")
    private String returnDate;

    @NotNull(message = "Total cost is required")
    @Positive(message = "Total cost must be positive")
    private BigDecimal totalCost;
}
