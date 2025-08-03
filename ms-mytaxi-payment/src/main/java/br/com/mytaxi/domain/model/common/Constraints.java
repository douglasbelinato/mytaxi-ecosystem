package br.com.mytaxi.domain.model.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
@Getter
@ToString
public final class Constraints {

    private final String fieldName;
    private final Map<String, String> value;

    private Constraints(String fieldName, Map<String, String> value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public static ConstraintsBuilder builder() {
        return new ConstraintsBuilder();
    }

    public static class ConstraintsBuilder {

        private String fieldName = "undefined";
        private final Map<String, String> value = new LinkedHashMap<>();

        public ConstraintsBuilder fieldName(String fieldName) {
            if (StringUtils.isNotBlank(fieldName)) {
                this.fieldName = fieldName;
            } else {
                this.fieldName = "undefined";
            }
            return this;
        }

        public ConstraintsBuilder add(final String fieldName, final String value) {
            if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(value)) {
                this.value.put(fieldName, value);
            }
            return this;
        }

        public ConstraintsBuilder addFromCandidates(final List<? extends Candidate<?>> candidateList) {
            if (CollectionUtils.isNotEmpty(candidateList)) {
                var map = new LinkedHashMap<String, String>();
                for (var candidate : candidateList) {
                    for (var entry : candidate.getConstraints().getValue().entrySet()) {
                        map.put(fieldName + "." + entry.getKey(), entry.getValue());
                    }
                }
                value.putAll(map);
            }
            return this;
        }

        public Constraints build() {
            return new Constraints(fieldName, value);
        }
    }

    public boolean doesNotExist() {
        return !exist();
    }

    public boolean exist() {
        return !value.isEmpty();
    }

    public String getAllInline() {
        var result = new StringBuilder();
        for (var entry : value.entrySet()) {
            result.append(entry.getKey()).append("=>").append(String.join("; ", entry.getValue())).append(";");
        }
        return result.toString();
    }

    public List<String> listAll() {
        var result = new ArrayList<String>();
        for (var entry : value.entrySet()) {
            result.add(entry.getKey() + "=>" + entry.getValue());
        }
        return result;
    }

}
