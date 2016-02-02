package com.normanhoeller.twitterydoo.dagger;


import com.normanhoeller.twitterydoo.MainActivity;
import com.normanhoeller.twitterydoo.WorkerFragment;

/**
 * Created by norman on 31/08/15.
 */
public interface ApplicationComponent {
    void inject(WorkerFragment fragment);
    void inject(MainActivity activity);
}
