package br.com.mytaxi.infrastructure.mapper.json;

import br.com.mytaxi.application.mapper.json.JsonMapper;
import br.com.mytaxi.domain.exception.SystemException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class JsonMapperImpl implements JsonMapper {

    private final ObjectMapper objectMapper;

    @Override
    public String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new SystemException("error.parsing.event.paload.to.json", e);
        }
    }

}
