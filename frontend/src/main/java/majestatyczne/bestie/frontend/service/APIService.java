package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Quiz;
import majestatyczne.bestie.frontend.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface APIService {

    @GET("quiz/all")
    Call<List<Quiz>> getQuizzes();
    @GET("results/by_quiz_id/{quizId}")
    Call<List<Result>> getResults(@Path("quizId") int quizId);
}
