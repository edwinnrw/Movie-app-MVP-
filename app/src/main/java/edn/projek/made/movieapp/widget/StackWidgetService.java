package edn.projek.made.movieapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by dicoding on 1/9/2017.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteView(this.getApplicationContext(), intent);
    }
}