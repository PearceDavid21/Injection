package com.example.injection.Data;

import com.example.RandomUser;
import com.example.injection.network.NetManager;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Randomservice {

    private RandomAPI randomAPI;

    public Randomservice(){
        NetManager netManager= new NetManager();
        Retrofit retrofit=netManager.provideRetrofitClient(RandomAPI.BASE_URL);
        randomAPI=retrofit.create(RandomAPI.class);
    }

    public void getRandomUser(final RandomCallback callback){
        randomAPI.getRandomUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(RandomUser randomUser) {
                        callback.onRandomUser(randomUser);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    interface RandomCallback{
        void onRandomUser(RandomUser user);
        void onError(String error);
    }

}
