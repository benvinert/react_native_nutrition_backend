package com.example.FitnessApp.Service;
import com.example.FitnessApp.Enums.ValuesEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.FitnessApp.Enums.ValuesEnum.*;

@Service
public class RequestsService {
    final static String URL_TO_GET_FOOD = "https://www.goleango.com/calculators/nutrition_calculator/utils/get_hebrew_nutritional_value_for_food_id_json.php";


    public Map<ValuesEnum,Float> getFoodById(String foodId) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("food_id", foodId)
                .build();

        Request request = new Request.Builder()
                .url(URL_TO_GET_FOOD)
                .post(formBody)
                .build();
        try{
            //0 => calories[0]
            //1 => Protein[1]
            //3 => fat[2]
            //8 => Carbs[3]
            //223 => collesterol[4]
            //291 => fiber[5]
            Response response = client.newCall(request).execute();
            String responseAsJsonString = response.body().string();
            JsonNode jsonNode = stringToJSONObject(responseAsJsonString);

            Map<ValuesEnum,Float> valuesOfFood = new HashMap<ValuesEnum,Float>();
            valuesOfFood = fillMapWithValues(valuesOfFood,jsonNode);
            return valuesOfFood;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode stringToJSONObject(String jsonString) throws Exception {
        ObjectMapper jacksonObjMapper = new ObjectMapper();
        return jacksonObjMapper.readTree(jsonString);
    }

    public Map<ValuesEnum,Float> fillMapWithValues(Map<ValuesEnum,Float> valuesOfFood , JsonNode jsonNode){
        valuesOfFood.put(CALORIES, Float.parseFloat(jsonNode.get(CALORIES.ordinal()).get("value").asText()));
        valuesOfFood.put(PROTEIN, Float.parseFloat(jsonNode.get(PROTEIN.ordinal()).get("value").asText()));
        valuesOfFood.put(FAT, Float.parseFloat(jsonNode.get(FAT.ordinal()).get("value").asText()));
        valuesOfFood.put(CARBS, Float.parseFloat(jsonNode.get(CARBS.ordinal()).get("value").asText()));
        valuesOfFood.put(COLLESTEROL, Float.parseFloat(jsonNode.get(COLLESTEROL.ordinal()).get("value").asText()));
        valuesOfFood.put(FIBER, Float.parseFloat(jsonNode.get(FIBER.ordinal()).get("value").asText()));
        return valuesOfFood;
    }
}
