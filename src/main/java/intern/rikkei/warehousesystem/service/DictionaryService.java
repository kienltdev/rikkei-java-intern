package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.response.DictionaryResponseDTO;

public interface DictionaryService {
    DictionaryResponseDTO getProductTypes();
    DictionaryResponseDTO getSupplierCodes();
    DictionaryResponseDTO getShippingMethods();
    DictionaryResponseDTO getInboundStatuses();
}
