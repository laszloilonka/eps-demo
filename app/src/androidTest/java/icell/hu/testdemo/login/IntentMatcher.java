package icell.hu.testdemo.login;

import android.content.Intent;

import org.mockito.ArgumentMatcher;

/**
 * Created by ilaszlo on 15/09/16.
 */

public class IntentMatcher extends ArgumentMatcher<Intent> {

    private Intent intent;

    public IntentMatcher(Intent intent) {
        this.intent = intent;
    }

    @Override
    public boolean matches(Object argument) {
        Intent arg = (Intent) argument;
        boolean areIntentsEquals = arg.filterEquals(intent);
        areIntentsEquals = areIntentsEquals && arg.getFlags() == intent.getFlags();
        return areIntentsEquals;
    }
}
