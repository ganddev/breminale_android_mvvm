package de.ahlfeld.breminale.app.viewmodel;


import android.support.annotation.NonNull;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private Navigation listener;


    public MainViewModel(@NonNull Navigation listener) {
        this.listener = listener;
    }


    @Override
    public void destroy() {
        listener = null;
    }


    public void showFavorits() {
        if(listener != null) {
            listener.showFavorits();
        }
    }

    public void showProgram() {
        if(listener != null) {
            listener.showProgram();
        }
    }

    public void showMap() {
        if(listener != null) {
            listener.showMap();
        }
    }

    public void showBrefunk() {
        if(listener != null) {
            listener.showBrefunk();
        }
    }

    public void showMore(){
        if(listener != null) {
            listener.showMore();
        }
    }

    public interface Navigation {
        void showProgram();
        void showMap();
        void showFavorits();
        void showBrefunk();
        void showMore();
    }
}
