package at.fhooe.mc.android.Objects;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Refuel implements Serializable {
    protected float mPrice;
    protected float mLiter;
    protected Date mDate;
    protected int mMileage;
    boolean delete;
    boolean readyDelete;


    public Refuel(float mPrice, float mLiter, Date mDate, int mMileage) {
        this.mPrice = mPrice;
        this.mLiter = mLiter;
        this.mDate = mDate;
        this.mMileage = mMileage;
        delete=false;
        readyDelete=false;
    }

    public Refuel() {
    }

    public float getmPrice() {
        return mPrice;
    }

    public void setmPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public float getmLiter() {
        return mLiter;
    }

    public void setmLiter(float mLiter) {
        this.mLiter = mLiter;
    }


    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
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
