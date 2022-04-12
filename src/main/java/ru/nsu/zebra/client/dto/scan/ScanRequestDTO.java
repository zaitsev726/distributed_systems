package ru.nsu.zebra.client.dto.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.nsu.zebra.client.dto.QueryType;

import java.util.ArrayList;
import java.util.List;

public record ScanRequestDTO(
        QueryType type,
        @JsonProperty(required = true)
        String scanClause,
        Integer number,
        Integer position) {

    public List<NameValuePair> convertToParamList() {
        var result = new ArrayList<NameValuePair>();
        if (type != null) {
            result.add(new BasicNameValuePair("type", type.toString()));
        }
        result.add(new BasicNameValuePair("scanClause", scanClause));

        if (number != null) {
            result.add(new BasicNameValuePair("number", String.valueOf(number)));
        }

        if (position != null) {
            result.add(new BasicNameValuePair("position", String.valueOf(position)));
        }
        return result;
    }
}
