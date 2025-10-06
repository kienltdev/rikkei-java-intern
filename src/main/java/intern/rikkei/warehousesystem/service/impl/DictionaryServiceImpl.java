package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.common.DictionaryItemDTO;
import intern.rikkei.warehousesystem.dto.common.DictionaryResponseDTO;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.ShippingMethod;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final MessageSource messageSource;

    @Override
    public DictionaryResponseDTO getProductTypes() {
        List<DictionaryItemDTO> items = getDictionaryFromEnum(ProductType.class, ProductType::getCode);
        return new DictionaryResponseDTO(items);
    }

    @Override
    public DictionaryResponseDTO getSupplierCodes() {
        List<DictionaryItemDTO> items = getDictionaryFromEnum(SupplierCd.class, SupplierCd::getCode);
        return new DictionaryResponseDTO(items);
    }

    @Override
    public DictionaryResponseDTO getShippingMethods() {
        List<DictionaryItemDTO> items = getDictionaryFromEnum(ShippingMethod.class, ShippingMethod::getCode);
        return new DictionaryResponseDTO(items);
    }

    @Override
    public DictionaryResponseDTO getInboundStatuses() {
        List<DictionaryItemDTO> items = getDictionaryFromEnum(InboundStatus.class, InboundStatus::getCode);
        return new DictionaryResponseDTO(items);
    }


    private <E extends Enum<E>> List<DictionaryItemDTO> getDictionaryFromEnum(
            Class<E> enumClass,
            Function<E, Object> codeExtractor) {

        return Arrays.stream(enumClass.getEnumConstants())
                .map(enumConstant -> {

                    String messageKey = "enum." + enumClass.getSimpleName() + "." + enumConstant.name();

                    String name = messageSource.getMessage(messageKey, null, enumConstant.name(), LocaleContextHolder.getLocale());

                    Object code = codeExtractor.apply(enumConstant);

                    return new DictionaryItemDTO(code, name);
                })
                .collect(Collectors.toList());
    }
}
