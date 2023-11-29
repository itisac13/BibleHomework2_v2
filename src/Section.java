import java.math.BigDecimal;

public class Section {
    public int age;
    public BigDecimal population;
    public BigDecimal numDisciples;
    public BigDecimal nonbelievers;

    public Section(int age, BigDecimal population, BigDecimal numDisciples) {
        this.age = age;
        this.population = population;
        if (numDisciples.compareTo(population) > 0) {
            numDisciples = population;
        }
        this.numDisciples = numDisciples;
        this.nonbelievers = this.population.subtract(numDisciples);
    }

    public BigDecimal addDisciples(BigDecimal amount) {
        BigDecimal extra = BigDecimal.ZERO;
        if (numDisciples.add(amount).compareTo(population) > 0) {
            extra = numDisciples.add(amount).subtract(population);
            amount = BigDecimal.ZERO;

            // System.out.println("No new disciples were added to age section " + this.age + " there was an excess of " + extra);
        }
        numDisciples = numDisciples.add(amount);
        nonbelievers = population.subtract(numDisciples);

        return extra;
    }

    @Override
    public String toString() {
        return "Age group: " + age + ". Disciples: " + numDisciples + " Nonbelievers: " + nonbelievers;
    }
}