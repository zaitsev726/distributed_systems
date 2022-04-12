package ru.nsu.zebra.client.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.nsu.zebra.client.dto.QueryType;

import java.util.ArrayList;
import java.util.List;

public record SearchRequestDTO(
        QueryType type,
        @JsonProperty(required = true)
        String query,
        Integer startRecord,
        Integer maximumRecords,
        String recordSchema,
        String sortKeys) {

    public List<NameValuePair> convertToParamList() {
        var result = new ArrayList<NameValuePair>();
        if (type != null) {
            result.add(new BasicNameValuePair("type", type.toString()));
        }
        result.add(new BasicNameValuePair("query", query));

        if (startRecord != null) {
            result.add(new BasicNameValuePair("startRecord", String.valueOf(startRecord)));
        }

        if (maximumRecords != null) {
            result.add(new BasicNameValuePair("maximumRecords", String.valueOf(maximumRecords)));
        }

        if (recordSchema != null) {
            result.add(new BasicNameValuePair("recordSchema", recordSchema));
        }

        if (sortKeys != null) {
            result.add(new BasicNameValuePair("startRecord", sortKeys));
        }

        return result;
    }
}
