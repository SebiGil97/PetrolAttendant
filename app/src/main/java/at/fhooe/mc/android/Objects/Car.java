package at.fhooe.mc.android.Objects;



import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Car implements Serializable {
    String mCar;
    int mMileage;
    boolean delete;
    boolean readyDelete;


    public Car() {
    }

    public Car(String mCar, int mMileage) {
        this.mMileage=mMileage;
        this.mCar = mCar;
        delete=false;
        readyDelete=false;
    }


    public String getmCar() {
        return mCar;
    }

    public void setmCar(String mCar) {
        this.mCar = mCar;
    }


    public int getmMileage() {
        return mMileage;
    }

    public void setmMileage(int mMileage) {
        this.mMileage = mMileage;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isReadyDelete() {
        return readyDelete;
    }

    public void setReadyDelete(boolean readyDelete) {
        this.readyDelete = readyDelete;
    }
}
