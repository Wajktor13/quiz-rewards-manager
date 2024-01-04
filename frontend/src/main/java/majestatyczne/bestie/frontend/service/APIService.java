package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Quiz;
import majestatyczne.bestie.frontend.model.Result;
import majestatyczne.bestie.frontend.model.Reward;
import majestatyczne.bestie.frontend.model.RewardCategory;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface APIService {

    @GET("quizzes")
    Call<List<Quiz>> getQuizzes();

    @DELETE("quizzes/{quizId}")
    Call<Void> deleteQuizById(@Path("quizId") int quizId);

    @GET("results")
    Call<List<Result>> getResults(@Query("quizId") int quizId);

    @PUT("results")
    Call<Void> updateResult(@Body Result result);

    @GET("reward-categories")
    Call<List<RewardCategory>> getRewardCategories();

    @PUT("reward-categories")
    Call<Void> updateRewardCategory(@Body RewardCategory rewardCategory);

    @POST("reward-categories")
    Call<Void> addRewardCategory(@Body RewardCategory rewardCategory);

    @DELETE("reward-categories/{rewardCategoryId}")
    Call<Void> deleteRewardCategoryById(@Path("rewardCategoryId") int rewardCategoryId);

    @GET("rewards")
    Call<List<Reward>> getRewards();

    @PUT("rewards")
    Call<Void> updateReward(@Body Reward reward);

    @POST("rewards")
    Call<Void> addReward(@Body Reward reward);

    @DELETE("rewards/{rewardId}")
    Call<Void> deleteRewardById(@Path("rewardId") int rewardId);
}
