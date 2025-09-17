package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;

public interface InboundService {
    InboundResponse createInbound(InboundRequest request);
}
