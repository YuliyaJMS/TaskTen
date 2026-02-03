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
    void shouldReturnMultipleTicketsWhenSearchMatchesSeveral() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 8000, 600, 900));
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700));
        manager.add(new Ticket("LED", "MOW", 6000, 550, 800));
        manager.add(new Ticket("SPB", "KZN", 4000, 700, 900)); // другой маршрут — не попадёт

        Ticket[] expected = {
                new Ticket("LED", "MOW", 5000, 500, 700),
                new Ticket("LED", "MOW", 6000, 550, 800),
                new Ticket("LED", "MOW", 8000, 600, 900)
        };

        Ticket[] actual = manager.search("LED", "MOW");
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnSingleTicketWhenOnlyOneMatches() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("SPB", "KZN", 4000, 700, 900));
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700)); // единственный подходящий
        manager.add(new Ticket("LED", "SVX", 6000, 550, 800));

        Ticket[] expected = {
                new Ticket("LED", "MOW", 5000, 500, 700)
        };

        Ticket[] actual = manager.search("LED", "MOW");
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyArrayWhenNoTicketsMatch() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("SPB", "KZN", 4000, 700, 900));
        manager.add(new Ticket("LED", "SVX", 5000, 500, 700));
        manager.add(new Ticket("MOW", "LED", 6000, 550, 800));

        Ticket[] expected = {}; // пустой массив
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
        assertEquals(0, comp.compare(t1, new Ticket("A", "B", 7000, 600, 800))); // same duration
    }

    @Test
    void shouldSearchAndSortByTimeWhenMultipleTickets() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 8000, 600, 900)); // 300 мин
        manager.add(new Ticket("LED", "MOW", 5000, 500, 700)); // 200 мин
        manager.add(new Ticket("LED", "MOW", 6000, 550, 800)); // 250 мин

        Ticket[] expected = {
                new Ticket("LED", "MOW", 5000, 500, 700), // 200
                new Ticket("LED", "MOW", 6000, 550, 800), // 250
                new Ticket("LED", "MOW", 8000, 600, 900)  // 300
        };

        Ticket[] actual = manager.searchAndSortBy("LED", "MOW", new TicketTimeComparator());
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyArrayForSearchAndSortByWhenNoMatches() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("SPB", "KZN", 4000, 700, 900));

        Ticket[] expected = {};
        Ticket[] actual = manager.searchAndSortBy("LED", "MOW", new TicketTimeComparator());

        assertArrayEquals(expected, actual);
    }
}