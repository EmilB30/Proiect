package repository.myfridge.Listeners;

import java.util.Random;

import repository.myfridge.Models.RandomResponseApi;

public interface RandomResponseListener {
     void didFetch(RandomResponseApi respinse, String message);

    void didError(String message);
}
