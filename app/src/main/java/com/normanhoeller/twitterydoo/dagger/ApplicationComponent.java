package com.normanhoeller.twitterydoo.dagger;


import com.normanhoeller.twitterydoo.MainActivity;
import com.normanhoeller.twitterydoo.WorkerFragment;

/**
 * Created by norman on 02/02/16.
 */
public interface ApplicationComponent {
    void inject(WorkerFragment fragment);

}
