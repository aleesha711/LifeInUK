package modules;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.tecjaunt.www.lifeinuk.R;


/**
 * Created by Aleesha Kanwal on 12/10/2017.
 */


public class AnalyticsEventsSender extends Application{
    Tracker tracker;
    Context AppContext;
    GoogleAnalytics analytics;

    public AnalyticsEventsSender(Context context) {

        AppContext = context;
        analytics = GoogleAnalytics.getInstance(AppContext);
        tracker = analytics.newTracker(R.xml.global_tracker);
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
    }

    public void sendScreenName(String screenName) {
        if(screenName!=null && tracker!=null) {
            tracker.setScreenName(screenName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public void sendCrash(String errorReport) {

        /*if (BuildConfig.isProduction) {
            tracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(errorReport)
                    .setFatal(true)
                    .build());

            GoogleAnalytics.getInstance(AppContext).dispatchLocalHits();
        }*/
    }

    public void sendEvent(String category, String action, String label) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

}
