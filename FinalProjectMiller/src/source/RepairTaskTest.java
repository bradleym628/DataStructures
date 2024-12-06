package source;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepairTaskTest {

    @Test
    void testCreateTask() {
        RepairTask task = new RepairTask("Oil Change", 1);
        assertEquals("Oil Change", task.getDescription());
        assertEquals(1, task.getPriority());
    }

    @Test
    void testSetPriorityValid() {
        RepairTask task = new RepairTask("Brake Check", 2);
        task.setPriority(3);
        assertEquals(3, task.getPriority());
    }

    @Test
    void testSetPriorityInvalid() {
        RepairTask task = new RepairTask("Brake Check", 2);
        assertThrows(IllegalArgumentException.class, () -> task.setPriority(5), 
            "Should throw an exception for invalid priority");
    }

    @Test
    void testCompareTo() {
        RepairTask highPriority = new RepairTask("High Priority Task", 1);
        RepairTask lowPriority = new RepairTask("Low Priority Task", 3);
        assertTrue(highPriority.compareTo(lowPriority) < 0, 
            "High-priority task should come before low-priority task");
    }

    @Test
    void testToString() {
        RepairTask task = new RepairTask("Engine Check", 1);
        String expected = "Task ID: 1, Description: Engine Check, Priority: 1";
        assertEquals(expected, task.toString());
    }
}
