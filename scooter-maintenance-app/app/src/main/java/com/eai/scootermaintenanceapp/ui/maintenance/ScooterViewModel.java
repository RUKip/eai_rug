package com.eai.scootermaintenanceapp.ui.maintenance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eai.scootermaintenanceapp.data.model.Scooter;

import java.util.ArrayList;
import java.util.List;

public class ScooterViewModel extends ViewModel {

    private final MutableLiveData<List<Scooter>> mScooterList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Scooter> mSelectedScooter = new MutableLiveData<>();

    public LiveData<List<Scooter>> getScooterList() {
        return mScooterList;
    }

    public void addScooter(Scooter scooter) {
        List<Scooter> scooterList = mScooterList.getValue();

        if (scooterList == null) {
            scooterList = new ArrayList<>();
        }

        // Set selected scooter to first scooter in list
        if (scooterList.isEmpty()) {
            mSelectedScooter.postValue(scooter);
        }

        scooterList.add(scooter);
        mScooterList.postValue(scooterList);
    }

    public void selectScooter(Scooter scooter) {
        mSelectedScooter.setValue(scooter);
    }

    public LiveData<Scooter> getSelectedScooter() {
        return mSelectedScooter;
    }

    public void removeScooter(Scooter scooter) {
        // TODO: add messaging logic

        List<Scooter> oldList = mScooterList.getValue();

        if (oldList == null) {
            return;
        }

        if (oldList.isEmpty()) {
            mSelectedScooter.setValue(null);
            return;
        }

        oldList.remove(scooter);
        mSelectedScooter.setValue(oldList.get(0));
    }
}
