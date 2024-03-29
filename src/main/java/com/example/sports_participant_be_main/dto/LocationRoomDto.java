package com.example.sports_participant_be_main.dto;

import com.example.sports_participant_be_main.models.LocationRoom;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LocationRoomDto {

    private UUID id;

    @NotNull
    private String name;

    private String description;

    private Integer roomNumber;

    private Integer capacity;

    @NotNull
    private UUID locationId;

    private LocationRoom.Status status;

    public LocationRoom ofEntity(){
        return LocationRoom.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .roomNumber(this.roomNumber)
                .capacity(capacity)
                .status(this.status)
                .build()
                ;
    }
}
