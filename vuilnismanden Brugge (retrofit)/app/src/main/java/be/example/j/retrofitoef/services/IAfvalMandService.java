package be.example.j.retrofitoef.services;

import java.util.List;

import be.example.j.retrofitoef.model.AfvalMand;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IAfvalMandService {

    @GET("/afvalmandenjson")
    Call<List<AfvalMand>> getAllAfvalmandenjson();
}
