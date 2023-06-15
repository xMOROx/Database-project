package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "VehiclesAdd")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Vehicles")
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class VehicleAddModel extends RepresentationModel<VehicleAddModel> {

    @NotBlank(message = "Registration is required")
    @Size(min = 1, max = 30, message = "Registration must be between 1 and 30 characters long")
    @Pattern(regexp = "^[A-Z][A-Z0-9]+$", message = "Registration must contain only uppercase letters and digits. For example: 'KR1234'")
    private String registration;

    @NotBlank(message = "Brand is required")
    @Size(min = 1, max = 255, message = "Brand must be between 1 and 255 characters long")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 255, message = "Model must be between 1 and 255 characters long")
    private String model;

    @NotNull(message = "Daily fee is required")
    @Positive(message = "Daily fee must be positive")
    private BigDecimal dailyFee;

    @NotBlank(message = "Body type is required")
    @Size(min = 1, max = 100, message = "Body type must be between 1 and 100 characters long")
    private String bodyType;

    @Min(value = 1900, message = "Production year must be greater than 1900")
    private Integer productionYear;

    @NotBlank(message = "Fuel type is required")
    @Size(min = 1, max = 30, message = "Fuel type must be between 1 and 30 characters long")
    private String fuelType;

    @Positive(message = "Power must be greater than 0")
    private Integer power;

    @NotBlank(message = "Gearbox is required")
    @Size(min = 1, max = 50, message = "Gearbox must be between 1 and 50 characters long")
    private String gearbox;

    @NotNull(message = "Front wheel drive is required")
    private Boolean frontWheelDrive;

    @Min(value = 3, message = "Doors number must be between 1 and 5")
    @Max(value = 5, message = "Doors number must be between 1 and 5")
    private Integer doorsNumber;

    @Min(value = 2, message = "Seats number must be between 2 and 9")
    @Max(value = 9, message = "Seats number must be between 2 and 9")
    private Integer seatsNumber;

    @NotBlank(message = "Color is required")
    @Size(min = 1, max = 50, message = "Color must be between 1 and 50 characters long")
    private String color;

    @NotNull(message = "Metalic is required")
    private Boolean metalic;

    @NotBlank(message = "Description is required")
    private String description;

    private String photoURL;

    @NotNull(message = "Vehicle status is required")
    private Boolean bestOffer;

    @NotNull(message = "Vehicle status id is required")
    private Long vehicleStatusId;

    @NotNull(message = "Vehicle location id is required")
    private Long vehicleLocationId;


}
