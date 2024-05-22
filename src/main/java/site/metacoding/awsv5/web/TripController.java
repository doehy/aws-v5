package site.metacoding.awsv5.web;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.awsv5.service.TripService;
import site.metacoding.awsv5.web.dto.PlanDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TripController {

    private final TripService tripService;

//    @PostMapping("/plan")
//    public String makePlan(@RequestBody PlanDto planDto) throws JSONException, IOException {
//        String makedPlan = tripService.makePlan(planDto);
//        return makedPlan;
//        return "1";
//    }

    @PostMapping("/plan")
    public Map<String, String> makePlan(@RequestBody PlanDto planDto) throws JSONException, IOException {
        String makedPlan = tripService.makePlan(planDto);
        Map<String, String> response = new HashMap<>();
        response.put("example", makedPlan); // `example` 필드에 "1"을 반환
        return response;
    }
}
