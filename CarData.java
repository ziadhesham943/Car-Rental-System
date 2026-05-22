package oop.project;

import java.util.ArrayList;
import java.util.List;

public final class CarData {

    public static final List<Car> CARS = new ArrayList<>();

    static {
        CARS.add(new Car("Toyota Corolla", 2022, "White", 200, 3));
        CARS.add(new Car("Hyundai Elantra", 2021, "Silver", 350, 2));
        CARS.add(new Car("BMW 320i", 2020, "Black", 500, 1));
        CARS.add(new Car("Kia Rio", 2019, "Red", 150, 4));
        CARS.add(new Car("Nissan Sunny", 2021, "Gray", 300, 2));
    }

    private CarData() {
    }
}
