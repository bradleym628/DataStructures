package source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("John Doe");
    }

    @Test
    void testCarCreation() {
        assertEquals("John Doe", car.getOwnerName());
        assertEquals(0, car.getRepairTasks().size());
    }

    @Test
    void testAddRepairTask() {
        RepairTask task = new RepairTask("Oil Change", 2);
        car.addRepairTask(task);
        LinkedList<RepairTask> tasks = car.getRepairTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.getFirst());
    }

    @Test
    void testRemoveRepairTask() {
        RepairTask task = new RepairTask("Oil Change", 2);
        car.addRepairTask(task);
        assertTrue(car.removeRepairTask(task));
        assertTrue(car.getRepairTasks().isEmpty());
    }

    @Test
    void testToString() {
        RepairTask task = new RepairTask("Brake Fix", 1);
        car.addRepairTask(task);
        String expected = "Car ID: " + car.getCarId() + ", Owner: John Doe, Tasks: 1";
        assertEquals(expected, car.toString());
    }
}