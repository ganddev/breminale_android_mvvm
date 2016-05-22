package de.ahlfeld.breminale.viewmodel;


import de.ahlfeld.breminale.view.MainActivity;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final MainActivity view;

    public MainViewModel(MainActivity view) {
        this.view = view;
    }


    @Override
    public void destroy() {

    }


    public void showFavorits() {
        this.view.showFavorits();
    }

    public void showProgram() {
        this.view.showProgam();
    }

    public void showMap() {
        this.view.showMap();
    }
}
