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
        manager.add(new Ticket("LED", "MOW", 8000, 600, 900)); // 300 мин
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700)); // 200 мин
        manager.add(new Ticket("LED", "MOW", 6000, 550, 800)); // 250 мин

        Ticket[] expected = {
                new Ticket("LED", "MOW", 5000, 500, 700),
                new Ticket("LED", "MOW", 6000, 550, 800),
                new Ticket("LED", "MOW", 8000, 600, 900)
        };

        Ticket[] actual = manager.search("LED", "MOW");
        assertArrayEquals(expected, actual);
    }

    @Test
    void testTicketTimeComparator() {
        Ticket t1 = new Ticket("A", "B", 5000, 600, 800); // 200 мин
        Ticket t2 = new Ticket("A", "B", 5000, 600, 900); // 300 мин

        TicketTimeComparator comp = new TicketTimeComparator();
        assertTrue(comp.compare(t1, t2) < 0);
        assertTrue(comp.compare(t2, t1) > 0);
        assertEquals(0, comp.compare(t1, new Ticket("A", "B", 6000, 600, 800))); // same duration
    }

    @Test
    void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 8000, 600, 900)); // 300 мин
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700)); // 200 мин
        manager.add(new Ticket("LED", "MOW", 6000, 550, 800)); // 250 мин

        Ticket[] expected = {
                new Ticket("LED", "MOW", 5000, 500, 700), // 200 мин
                new Ticket("LED", "MOW", 6000, 550, 800), // 250 мин
                new Ticket("LED", "MOW", 8000, 600, 900)  // 300 мин
        };

        Ticket[] actual = manager.searchAndSortBy("LED", "MOW", new TicketTimeComparator());
        assertArrayEquals(expected, actual);
    }
}