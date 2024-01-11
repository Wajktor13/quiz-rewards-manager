package majestatyczne.bestie.frontend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import majestatyczne.bestie.frontend.model.Result;
import majestatyczne.bestie.frontend.model.Reward;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuizResultsService {
    public List<Result> getResults(int quizId) {
        APIService service = getAPIService();
        try {
            return service.getResults(quizId).execute().body();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public int updateResult(Result result) {
        APIService service = getAPIService();
        try {
            return service.updateResult(result).execute().code();
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
