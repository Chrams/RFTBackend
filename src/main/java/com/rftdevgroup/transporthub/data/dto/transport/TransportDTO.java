package com.rftdevgroup.transporthub.data.dto.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportDTO {
    private String userName;
    private String placeOfLoad;
    private String placeOfUnload;
}
