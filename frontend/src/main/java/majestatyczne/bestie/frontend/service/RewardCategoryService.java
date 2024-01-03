package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Reward;
import majestatyczne.bestie.frontend.model.RewardCategory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RewardCategoryService {

    public List<RewardCategory> getRewardCategories() {
        APIService service = getAPIService();
        try {
            return service.getRewardCategories().execute().body();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    private APIService getAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }
}
