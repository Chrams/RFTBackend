package com.rftdevgroup.transporthub.data.dto.transport;

import com.rftdevgroup.transporthub.data.dto.auction.BidDTO;
import com.rftdevgroup.transporthub.data.dto.user.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TransportDetailsDTO {
    private long id;
    private TransportOwnerDTO owner;
    private CargoDTO cargo;
    private AddressDTO placeOfLoad;
    private LocalDate timeOfLoad;
    private AddressDTO placeofUnload;
    private LocalDate timeOfUnload;
    private List<BidDTO> bids;
    private int startingPrice;

    public int getCurrentPrice() {
        return bids.size() > 0 ? bids.stream().mapToInt(b -> b.getAmount()).min().getAsInt() : startingPrice;
    }
}
