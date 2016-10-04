package icell.hu.testdemo.ui.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import icell.hu.testdemo.R;

/**
 * Created by User on 2016. 10. 02..
 */

public class RoundedButton {


    @BindView(R.id.custom_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.custum_button)
    Button button;

    @BindView(R.id.custum_container)
    RelativeLayout container;

    Unbinder unbinder;

    RoundedButtomListener listener;
    Drawable backDrawable;
    View view;

    public RoundedButton(View view, RoundedButtomListener listener) {
        this.view = view;
        this.listener = listener;
        backDrawable = view.getResources().getDrawable(R.drawable.layout_bg);
        unbinder = ButterKnife.bind(this, view);
        container.setBackground(backDrawable);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE,
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void setText(String text) {
        button.setText(text);
    }


    public void onDestroy() {
        unbinder.unbind();
        if (container != null)
            container.setBackground(null);
        backDrawable = null;
    }

    /**
     * change {@link #progressBar} visibility to {@link View#VISIBLE}
     */
    public void startProcess() {
        if (isProcessRunning()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        backDrawable.setColorFilter(
                view.getResources().getColor(R.color.colorPrimary),
                PorterDuff.Mode.OVERLAY);
        container.invalidate();
        button.setTextColor(Color.WHITE);
    }

    /**
     * change {@link #progressBar} visibility to {@link View#INVISIBLE}
     */
    public void stopProcess() {
        if (!isProcessRunning()) {
            return;
        }
        progressBar.setVisibility(View.INVISIBLE);
        backDrawable.clearColorFilter();
        container.invalidate();
        button.setTextColor(view.getResources().getColor(R.color.colorPrimary));
    }

    public boolean isProcessRunning() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @OnClick(R.id.custum_container)
    void containerPushed() {
        listener.onButtonClicked();
    }

    @OnClick(R.id.custum_button)
    void buttonPushed() {
        listener.onButtonClicked();
    }

    public static interface RoundedButtomListener {
        void onButtonClicked();
    }

}
