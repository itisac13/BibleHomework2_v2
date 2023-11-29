import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Sections extends ArrayList<Section> {

    public Section getSectionByAge(int targetAge) {
        for (Section section : this) {
            if (section.age == targetAge) {
                return section;
            }
        }
        throw new IllegalArgumentException("No such age exists");
    }

    public void deleteSectionByAge(int targetAge) {
        Iterator<Section> iterator = this.iterator();
        while (iterator.hasNext()) {
            Section section = iterator.next();
            if (section.age == targetAge) {
                iterator.remove();
            }
        }
    }

    public BigDecimal getTotalPopulation() {
        BigDecimal count = BigDecimal.ZERO;
        for (Section section : this) {
            count = count.add(section.population);
        }
        return count;
    }

    public BigDecimal getTotalDisciples() {
        BigDecimal count = BigDecimal.ZERO;
        for (Section section : this) {
            count = count.add(section.numDisciples);
        }
        return count;
    }

    public BigDecimal getTotalNonbelievers() {
        BigDecimal count = BigDecimal.ZERO;
        for (Section section : this) {
            count = count.add(section.nonbelievers);
        }
        return count;
    }
}
