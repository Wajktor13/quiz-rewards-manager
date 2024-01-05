package majestatyczne.bestie.frontend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import majestatyczne.bestie.frontend.model.RewardStrategy;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Optional;

public class RewardStrategyService {
    public Optional<RewardStrategy> getRewardStrategyByQuizId(int quizId) {
        APIService service = getAPIService();
        try {
            return Optional.ofNullable(service.getRewardStrategyByQuizId(quizId).execute().body());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public int addRewardStrategy(RewardStrategy rewardStrategy) {
        APIService service = getAPIService();
        try {
            var response = service.addRewardStrategy(rewardStrategy).execute();
            return response.code();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }

    public int updateRewardStrategy(RewardStrategy rewardStrategy) {
        APIService service = getAPIService();
        try {
            var response = service.updateRewardStrategy(rewardStrategy).execute();
            return response.code();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }

    private APIService getAPIService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIService.class);
    }
}
