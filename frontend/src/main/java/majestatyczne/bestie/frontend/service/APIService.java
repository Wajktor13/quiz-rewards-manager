package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Quiz;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface APIService {

    @GET("quiz/all")
    Call<List<Quiz>> getQuizzes();
}
