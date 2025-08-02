package com.vianavitor.enterprisechat.dto;

import java.util.List;

// store the id of the sharable entity and the id of the destination groups
public record ShareableDTO(
        Long id,
        List<Long> destinations
) {
}
