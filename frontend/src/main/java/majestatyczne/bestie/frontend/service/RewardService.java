package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Reward;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RewardService {

    public List<Reward> getRewards() {
        APIService service = getAPIService();
        try {
            return service.getRewards().execute().body();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void updateReward(Reward reward) {
        APIService service = getAPIService();
        try {
            service.updateReward(reward).execute();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
