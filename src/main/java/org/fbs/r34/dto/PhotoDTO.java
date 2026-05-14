package org.fbs.r34.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoDTO {

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("file_url")
    private String fileUrl;

    private String id;

}
