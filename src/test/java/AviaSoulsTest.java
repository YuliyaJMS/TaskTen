import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AviaSoulsTest {

    @Test
    void testCompareTo() {
        Ticket t1 = new Ticket("A", "B", 5000, 600, 800);
        Ticket t2 = new Ticket("A", "B", 7000, 600, 800);
        assertTrue(t1.compareTo(t2) < 0);
        assertTrue(t2.compareTo(t1) > 0);
        assertEquals(0, t1.compareTo(new Ticket("A", "B", 5000, 600, 800)));
    }

    @Test
    void testSearchSortedByPrice() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 8000, 600, 800));
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700));
        manager.add(new Ticket("LED", "MOW", 6000, 550, 750));

        Ticket[] result = manager.search("LED", "MOW");
        assertEquals(5000, result[0].getPrice());
        assertEquals(6000, result[1].getPrice());
        assertEquals(8000, result[2].getPrice());
    }

    @Test
    void testTicketTimeComparator() {
        Ticket t1 = new Ticket("A", "B", 5000, 600, 800); // 200 мин
        Ticket t2 = new Ticket("A", "B", 5000, 600, 900); // 300 мин

        TicketTimeComparator comp = new TicketTimeComparator();
        assertTrue(comp.compare(t1, t2) < 0);
        assertTrue(comp.compare(t2, t1) > 0);
    }

    @Test
    void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 8000, 600, 900)); // 300 мин
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700)); // 200 мин
        manager.add(new Ticket("LED", "MOW", 6000, 550, 800)); // 250 мин

        Ticket[] result = manager.searchAndSortBy("LED", "MOW", new TicketTimeComparator());
        assertEquals(200, result[0].getTimeTo() - result[0].getTimeFrom());
        assertEquals(250, result[1].getTimeTo() - result[1].getTimeFrom());
        assertEquals(300, result[2].getTimeTo() - result[2].getTimeFrom());
    }
}