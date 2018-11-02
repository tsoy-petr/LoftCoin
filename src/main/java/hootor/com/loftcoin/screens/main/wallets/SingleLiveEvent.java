package hootor.com.loftcoin.screens.main.wallets;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveEvent";


    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    public void setValue(T value) {
        mPending.set(true);
        super.setValue(value);
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
            return;
        }

        super.observe(owner, t -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });


    }

    @MainThread
    public void call() {
        setValue(null);
    }

}
