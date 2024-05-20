package site.metacoding.awsv5.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class PlanDto {

    @NotBlank
    private List<String> tags;
    @NotBlank
    private int day;
    @NotBlank
    private String place;
}
