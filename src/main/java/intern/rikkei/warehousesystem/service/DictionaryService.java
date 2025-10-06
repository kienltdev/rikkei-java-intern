package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.common.DictionaryResponseDTO;

public interface DictionaryService {
    DictionaryResponseDTO getProductTypes();
    DictionaryResponseDTO getSupplierCodes();
    DictionaryResponseDTO getShippingMethods();
    DictionaryResponseDTO getInboundStatuses();
}
