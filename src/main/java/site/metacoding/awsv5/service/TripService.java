package site.metacoding.awsv5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.metacoding.awsv5.web.dto.PlanDto;

import com.deepl.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

@RequiredArgsConstructor
@Service
public class TripService {

    private static final String GOOGLE_API_KEY = "AIzaSyD6ViW6gPSQhClKclXm9L19pYK7tupWo8E";
    // 장소를 찾는 메서드
    public List<Place> findPlace(String place, List<String> tagList) throws JSONException {
        // 태그를 쿼리로 변환
        String query = String.join(", ", tagList) + " tourist attraction in " + place;
        System.out.println(query);
        String translatedQuery = null;
        try {
            // 쿼리를 영어로 번역
            translatedQuery = translateText(query);
        } catch (com.deepl.api.DeepLException e) {
            e.printStackTrace();
            // 번역 API 예외 처리 로직
            return new ArrayList<>(); // 번역 실패 시 빈 리스트 반환
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 스레드 인터럽트 예외 처리 로직
            Thread.currentThread().interrupt(); // 인터럽트 상태를 다시 설정
            return new ArrayList<>(); // 인터럽트 발생 시 빈 리스트 반환
        }
        System.out.println(translatedQuery);

        JSONObject data = new JSONObject();
        data.put("textQuery", translatedQuery);
        data.put("languageCode", "ko");
        // HTTP 요청 수행
        String response = null;
        try {
            response = performHttpPostRequest("https://places.googleapis.com/v1/places:searchText", data.toString(), "AIzaSyD6ViW6gPSQhClKclXm9L19pYK7tupWo8E");
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        // 결과 파싱
        JSONObject jsonResponse;
        JSONArray placesArray;
        try {
            jsonResponse = new JSONObject(response);
            // 여기서는 첫 번째 키에 대한 값을 JSONArray로 추출
            String firstKey;
            Iterator<String> keys = jsonResponse.keys();
            if (keys.hasNext()) {
                firstKey = keys.next();
            } else {
                firstKey = null;
            }
            placesArray = jsonResponse.getJSONArray(firstKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < placesArray.length(); i++) {
            JSONObject placeObj = placesArray.getJSONObject(i);
            Place p = new Place();
            p.setId(placeObj.getString("id"));
            p.setRating(placeObj.getDouble("rating"));

            JSONObject locObj = placeObj.getJSONObject("location");
            locate loc = new locate();
            loc.setLatitude(locObj.getDouble("latitude"));
            loc.setLongitude(locObj.getDouble("longitude"));

            JSONObject nameObj = placeObj.getJSONObject("displayName");
            name n = new name();
            n.setText(nameObj.getString("text"));
            n.setLanguageCode(nameObj.getString("languageCode"));

            p.setLocation(loc);
            p.setDisplayName(n);
            places.add(p);
        }

        places.sort((p1, p2) -> {
            return Double.compare(p2.getRating(), p1.getRating()); // 내림차순 정렬
        });

        return places;
    }

    // 텍스트를 번역하는 메서드
    private String translateText(String text) throws com.deepl.api.DeepLException, InterruptedException {
        String authKey = "e0e9745a-f486-47fc-b5d7-3d9280d628ba:fx";
        Translator translator = new Translator(authKey);
        TextResult result = null;
        try {
            result = translator.translateText(text, null, "en-US");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        }
        return result != null ? result.getText() : "";
    }

    private String performHttpPostRequest(String urlString, String jsonData, String apiKey) throws IOException, MalformedURLException, ProtocolException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-Goog-Api-Key", apiKey);
        connection.setRequestProperty("X-Goog-FieldMask", "places.id,places.rating,places.location,places.displayName");
        connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    public static class name {
        String text = "";
        String languageCode = "";

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public JSONObject toJson() throws JSONException {
            JSONObject nameJson = new JSONObject();
            nameJson.put("text", text);
            nameJson.put("languageCode", languageCode);
            return nameJson;
        }
    }
    public static class  locate {
        Double latitude;
        Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String toString() {
            return "locate{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
        public JSONObject toJson() throws JSONException {
            JSONObject locJson = new JSONObject();
            locJson.put("latitude", latitude);
            locJson.put("longitude", longitude);
            return locJson;
        }
    }
    // Place 클래스 (필요에 따라 필드를 추가)
    public static class Place {
        // 필드, 생성자, 게터와 세터를 정의
        String id = "";
        locate location;
        Double rating;
        name displayName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public locate getLocation() {
            return location;
        }

        public void setLocation(locate location) {
            this.location = location;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public name getDisplayName() {
            return displayName;
        }

        public void setDisplayName(name displayName) {
            this.displayName = displayName;
        }

        public String toString() {
            return "Place{" +
                    "id='" + id + '\'' +
                    ", location=" + location.toString() +
                    ", rating=" + rating +
                    ", displayName=" + displayName +
                    '}';
        }

        public JSONObject toJson() throws JSONException {
            JSONObject placeJson = new JSONObject();
            placeJson.put("id", this.id);
            placeJson.put("location", this.location.toJson());  // locate가 toJson 메소드를 가지고 있다고 가정
            placeJson.put("rating", this.rating);
            placeJson.put("displayName", this.displayName.toJson());  // name이 toJson 메소드를 가지고 있다고 가정
            return placeJson;
        }
    }


    public static int getSpendTime(String origin, String destination, String mode) throws IOException {
        String urlStr = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + origin
                + "&destination=" + destination
                + "&mode=" + mode
                + "&departure_time=now"
                + "&language=ko"
                + "&key=" + GOOGLE_API_KEY;

        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 응답 읽기
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // JSON 파싱
            JSONObject responseJson = new JSONObject(response.toString());
            if (responseJson.getJSONArray("routes").length() > 0) {
                return responseJson.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs")
                        .getJSONObject(0)
                        .getJSONObject("duration")
                        .getInt("value");
            } else {
                return 99999; // 기본값 반환
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 99999; // 예외 처리 시 기본값 반환
        }
    }

    public int[][] distanceMatrix(int day, List<Place> sortedPlaces) {
        int size = day * 3;
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 999999999;
                    continue;
                }
                String ori = sortedPlaces.get(i).getLocation().getLatitude() + "," + sortedPlaces.get(i).getLocation().getLongitude();
                String dst = sortedPlaces.get(j).getLocation().getLatitude() + "," + sortedPlaces.get(j).getLocation().getLongitude();
                String mode = "transit";
                try {
                    long temp = getSpendTime(ori, dst, mode);
                    matrix[i][j] = (int) temp;
                    matrix[j][i] = (int) temp;
                } catch (Exception e) {
                    matrix[i][j] = 999999999;
                    matrix[j][i] = 999999999;
                }
            }
        }
        return matrix;
    }

    public int[][] primAlgorithmFinal(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> edgeHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        edgeHeap.add(new int[]{0, 0, -1}); // (가중치, 현재 정점, 이전 정점)

        List<int[]> mstEdges = new ArrayList<>();

        while (mstEdges.size() < n - 1) {
            int[] edge = edgeHeap.poll();
            int weight = edge[0];
            int current = edge[1];
            int prev = edge[2];

            if (visited[current]) {
                continue;
            }

            visited[current] = true;
            if (prev != -1) {
                mstEdges.add(new int[]{prev, current, weight});
            }

            for (int nextVertex = 0; nextVertex < n; nextVertex++) {
                int edgeWeight = graph[current][nextVertex];
                if (!visited[nextVertex] && edgeWeight != 0) {
                    edgeHeap.add(new int[]{edgeWeight, nextVertex, current});
                }
            }
        }

        int[][] mstMatrix = new int[n][n];
        for (int[] e : mstEdges) {
            int i = e[0], j = e[1], k = e[2];
            mstMatrix[i][j] = k;
            mstMatrix[j][i] = k;
        }
        return mstMatrix;
    }

    public List<Integer> findRoute(int[][] mstMatrix) {
        List<Integer> route = new ArrayList<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);

        while (!queue.isEmpty()) {
            int node = queue.pop();
            route.add(node);

            for (int i = 0; i < mstMatrix[node].length; i++) {
                if (!route.contains(i) && mstMatrix[node][i] > 0) {
                    queue.add(i);
                }
            }
        }
        return route;
    }

    public JSONObject findRestaurant(Place place1, Place place2) throws IOException, JSONException {
        double lat, lon;
        if (place2 == null) {
            lat = place1.getLocation().getLatitude();
            lon = place1.getLocation().getLongitude();
        } else {
            lat = (place1.getLocation().getLatitude() + place2.getLocation().getLatitude()) / 2;
            lon = (place1.getLocation().getLongitude() + place2.getLocation().getLongitude()) / 2;
        }

        String urlStr = "https://places.googleapis.com/v1/places:searchNearby";
        JSONObject payload = new JSONObject();
        payload.put("includedTypes", new JSONArray(Arrays.asList("restaurant")));
        payload.put("maxResultCount", 10);
        JSONObject locationRestriction = new JSONObject();
        locationRestriction.put("circle", new JSONObject()
                .put("center", new JSONObject()
                        .put("latitude", lat)
                        .put("longitude", lon))
                .put("radius", 500.0));
        payload.put("locationRestriction", locationRestriction);
        payload.put("languageCode", "ko");

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Goog-Api-Key", GOOGLE_API_KEY);
        conn.setRequestProperty("X-Goog-FieldMask", "places.id,places.rating,places.location,places.displayName");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject responseJson = new JSONObject(response.toString());
            JSONArray places = responseJson.getJSONArray("places");
            // 최고 평점 레스토랑 찾기
            if (places.length() > 0) {
                return places.getJSONObject(0); // 최고 평점 레스토랑 반환
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String autoMode(String place, List<String> tagList, int day) throws IOException, JSONException {
        List<Place> places = findPlace(place, tagList);
        int[][] distanceMatrix = distanceMatrix(day, places);
        int[][] mstMatrix = primAlgorithmFinal(distanceMatrix);
        List<Integer> route = findRoute(mstMatrix);
        System.out.println(route.size());
        boolean err = false;
        int resultDist = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            if (distanceMatrix[route.get(i)][route.get(i + 1)] == 999999999) {
                err = true;
                break;
            }
            resultDist += distanceMatrix[route.get(i)][route.get(i + 1)];
        }

        if (!err) {
            System.out.println(resultDist);
        } else {
            System.out.println("Error occurred");
            return "";
        }

        List<JSONObject> orderedPlaces = new ArrayList<>();
        for (int i = 0; i < day * 3; i++) {
            int idx = i * 2;
            JSONObject jsonPlace = places.get(route.get(i)).toJson();  // 관광지를 JSON 객체로 추가

            if (idx % 3 == 0) { // 아침
                JSONObject rest = findRestaurant(places.get(route.get(i)), places.get(route.get(i)));
                orderedPlaces.add(rest);
                orderedPlaces.add(jsonPlace);
            } else if(idx % 3 == 2){ // 저녁
                JSONObject rest = findRestaurant(places.get(route.get(i)), places.get(route.get(i)));
                orderedPlaces.add(rest);
                orderedPlaces.add(jsonPlace);
            } else { // 점심
                JSONObject rest = findRestaurant(places.get(route.get(i - 1)), places.get(route.get(i)));
                orderedPlaces.add(jsonPlace);
                orderedPlaces.add(rest);
            }
        }

        JSONArray jsonPlaces = new JSONArray();
        for (JSONObject placeObj : orderedPlaces) {
            jsonPlaces.put(placeObj);
        }
        return jsonPlaces.toString(4);
    }

    public List<Place> manualMode(String place, List<String> tagList) throws JSONException {
        return findPlace(place, tagList);
    }

    public String makePlan(PlanDto planDto) throws JSONException, IOException {
        String result = autoMode(planDto.getPlace(), planDto.getTags(), planDto.getDay());
        return result;
    }
}
