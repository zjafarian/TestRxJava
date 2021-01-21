package com.example.testrxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class Disposable extends AppCompatActivity {
    /**
     * Basic Observable, Observer, Subscriber example
     * Observable emits list of animal names
     * You can see Disposable introduced in this example
     */
    private static final String TAG = Disposable.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposable);

        // observable
        Observable<String> animalsObservable = getAnimalsObservable();

        // observer
        Observer<String> animalsObserver = getAnimalsObserver();

        // observer subscribing to observable
        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(animalsObserver);
    }

    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {


            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = (Disposable) d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Eagle", "Bee", "Lion", "Dog", "Wolf");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //disposable.dispose;
    }
}