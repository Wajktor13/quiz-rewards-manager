package majestatyczne.bestie.frontend.service;

import majestatyczne.bestie.frontend.model.Quiz;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuizService {

    public Optional<Quiz> getQuizById(int quizId) {
        APIService service = getAPIService();
        try {
            return Optional.ofNullable(service.getQuizById(quizId).execute().body());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public List<Quiz> getQuizzes() {
        APIService service = getAPIService();
        try {
            return service.getQuizzes().execute().body();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void deleteQuizById(int quizId) {
        APIService service = getAPIService();
        try {
            service.deleteQuizById(quizId).execute();
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
