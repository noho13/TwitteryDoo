package com.normanhoeller.twitterydoo;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.normanhoeller.twitterydoo.api.TwitterService;
import com.normanhoeller.twitterydoo.dagger.ApplicationComponent;
import com.normanhoeller.twitterydoo.ui.PictureFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by norman on 02/02/16.
 */
@RunWith(AndroidJUnit4.class)
public class TestPictureActivity {

    @Rule
    public ActivityTestRule<PictureActivity> activityRule = new ActivityTestRule<>(
            PictureActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method
    @Inject
    TwitterService twitterService;
    private PictureFragment fragment;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TwitteryDooApplication app
                = (TwitteryDooApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestPictureActivity_TestComponent.builder().mockApplicationModule(new MockApplicationModule()).build();
        app.setComponent(component);
        component.inject(this);

        Intent launchIntent = new Intent();
        launchIntent.putExtra(PictureActivity.SEARCH_QUERY, "bikes");
        activityRule.launchActivity(launchIntent);
        PictureActivity activity = activityRule.getActivity();
        fragment = (PictureFragment) activity.getSupportFragmentManager().findFragmentById(android.R.id.content);
    }

    @Test
    public void fragmentNotNull() {
        assertNotNull(twitterService);
        assertNotNull(fragment);
    }

    @Test
    public void itemCount() {
        RecyclerView recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.rv_item_grid);
        assertNotNull(recyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        int expectedItems = 1;
        assertEquals(expectedItems, itemCount);
    }

    @Test
    public void modelDataCorrect() {
        RecyclerView recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.rv_item_grid);
        assertNotNull(recyclerView);
        View child = recyclerView.getLayoutManager().getChildAt(0);
        TextView textView = (TextView) child.findViewById(R.id.tv_line1);
        String expected = "This is a fake tweet";
        String result = textView.getText().toString();
        assertEquals(expected, result);
    }

    @Component(modules = MockApplicationModule.class)
    @Singleton
    public interface TestComponent extends ApplicationComponent {
        void inject(TestPictureActivity testPictureActivity);
    }

}
