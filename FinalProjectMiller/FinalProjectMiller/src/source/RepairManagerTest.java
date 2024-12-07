package source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepairManagerTest {

    private RepairManager manager;
    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        manager = new RepairManager();
        car1 = new Car("Alice");
        car2 = new Car("Bob");
        manager.addCar(car1);
        manager.addCar(car2);
    }

    @Test
    void testAddCar() {
        assertEquals(2, manager.getCarsInQueue().size());
        assertEquals(car1, manager.getCarsInQueue().get(0));
    }

    @Test
    void testAddTaskToCar() {
        RepairTask task = new RepairTask("Oil Change", 1);
        manager.addTaskToCar(car1, task);
        assertEquals(1, car1.getRepairTasks().size());
        assertEquals(task, car1.getRepairTasks().get(0));
    }

    @Test
    void testGetCarById() {
        Car retrievedCar = manager.getCarById(car1.getCarId());
        assertNotNull(retrievedCar);
        assertEquals("Alice", retrievedCar.getOwnerName());
    }

    @Test
    void testReplaceCarsInQueue() {
        LinkedList<Car> sortedCars = new LinkedList<>();
        sortedCars.add(car2);
        sortedCars.add(car1);

        manager.replaceCarsInQueue(sortedCars);

        List<Car> cars = manager.getCarsInQueue();
        assertEquals(car2, cars.get(0));
        assertEquals(car1, cars.get(1));
    }
}
