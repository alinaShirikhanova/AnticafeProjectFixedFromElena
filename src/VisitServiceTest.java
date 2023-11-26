import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
class VisitServiceTest {

@BeforeEach
public void setUp(){
    VisitService.createVisit(new Client(), 1);
    VisitService.createVisit(new Client(), 2);
    VisitService.createVisit(new Client(), 3);
    VisitService.createVisit(new Client(), 4);
    VisitService.createVisit(new Client(), 5);
}
    @org.junit.jupiter.api.Test
    void createVisitPositive() {
        assertEquals(1, VisitService.getVisits().size());
        assertEquals(1, VisitService.getVisits().get(0).getTable().getId());
    }
    @org.junit.jupiter.api.Test
    void createVisitNegative() {
        assertThrows(RuntimeException.class, () -> VisitService.createVisit(new Client(), 1));

    }

    @org.junit.jupiter.api.Test
    void finishVisitPositive() {
        VisitService.finishVisit(1);
        assertEquals(true,VisitService.getVisits().get(0).isFinished());
        assertEquals(false,VisitService.getVisits().get(1).isFinished());
    }

    @org.junit.jupiter.api.Test
    void finishVisitNegative() {

    assertThrows(RuntimeException.class, () -> VisitService.finishVisit(7));
    }

    @org.junit.jupiter.api.Test
    void getFreeTables() {

        assertEquals(5, VisitService.getFreeTables().size());
        Set<Table> tables = new HashSet<>();
        tables.add(TableService.tables.get(6));
        tables.add(TableService.tables.get(7));
        tables.add(TableService.tables.get(8));
        tables.add(TableService.tables.get(9));
        tables.add(TableService.tables.get(10));
        assertEquals(5, VisitService.getFreeTables().size());
        assertEquals(tables, new HashSet<>(VisitService.getFreeTables()));
    }

    @org.junit.jupiter.api.Test
    void getReservedTables() {
        assertEquals(5, VisitService.getFreeTables().size());
        Set<Table> tables = new HashSet<>();
        tables.add(TableService.tables.get(1));
        tables.add(TableService.tables.get(2));
        tables.add(TableService.tables.get(3));
        tables.add(TableService.tables.get(4));
        tables.add(TableService.tables.get(5));
        assertEquals(5, VisitService.getFreeTables().size());
        assertEquals(tables, new HashSet<>(VisitService.getReservedTables()));
    }

    @org.junit.jupiter.api.Test
    void getFinishedVisits() {
    VisitService.finishVisit(1);
    VisitService.finishVisit(2);
    assertEquals(1, VisitService.getFinishedVisits().get(0).getTable().getId());
    assertEquals(2, VisitService.getFinishedVisits().get(1).getTable().getId());
    assertEquals(2, VisitService.getFinishedVisits().size());
    }

    @org.junit.jupiter.api.Test
    void getCurrentDuration() throws InterruptedException {
    VisitService.getCurrentDuration(5);
    TimeUnit.MINUTES.sleep(1);
    assertEquals(1, VisitService.getCurrentDuration(5));
    }

    @org.junit.jupiter.api.Test
    void getTotalCurrentDuration() {
    }

    @org.junit.jupiter.api.Test
    void getCurrentCost() {
    }

    @org.junit.jupiter.api.Test
    void getTotalCurrentCost() {
    }

    @org.junit.jupiter.api.Test
    void getTotalCostOfAllTime() {
    }

    @org.junit.jupiter.api.Test
    void getTheMostPopularTable() {
    }

    @org.junit.jupiter.api.Test
    void getAverageDurationOfAllTables() {
    }

    @org.junit.jupiter.api.Test
    void getTheMostEarnedTable() {
    }
}