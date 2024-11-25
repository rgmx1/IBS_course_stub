package rgmx1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/compass")
public class CompassController {
    private final Map<String, Range> directions = new HashMap<>();

    @PostMapping("/configure")
    public ResponseEntity<String> configureDirections(@RequestBody Map<String, String> directionRanges) {
        directions.clear();

        for (Map.Entry<String, String> entry : directionRanges.entrySet()) {
            String[] range = entry.getValue().split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            directions.put(entry.getKey(), new Range(start, end));
        }

        return ResponseEntity.ok("Directions configured successfully");
    }

    @PostMapping("/degree")
    public ResponseEntity<Map<String, String>> getDirection(@RequestBody Map<String, Integer> request) {
        Integer degree = request.get("Degree");

        if (degree == null || degree < 0 || degree > 359) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid degree"));
        }

        String direction = findDirection(degree);
        Map<String, String> response = new HashMap<>();
        response.put("Side", direction);

        return ResponseEntity.ok(response);
    }

    private String findDirection(int degree) {
        for (Map.Entry<String, Range> entry : directions.entrySet()) {
            Range range = entry.getValue();
            if (isInRange(degree, range)) {
                return entry.getKey();
            }
        }
        return "Unknown";
    }

    private boolean isInRange(int degree, Range range) {
        if (range.start <= range.end) {
            return degree >= range.start && degree <= range.end;
        } else {
            // Для случаев когда диапазон пересекает 0 (например, 316-45 для севера)
            return degree >= range.start || degree <= range.end;
        }
    }
}

