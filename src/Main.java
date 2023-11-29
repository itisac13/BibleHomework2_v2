import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("All numbers are rounded\n\n");
        Sections sections = new Sections();
        BigDecimal initialPopulationPerSection = Constants.TOTAL_INITIAL_POPULATION.divide(BigDecimal.valueOf(30).add(BigDecimal.ONE), 30, RoundingMode.HALF_UP);

        // Create initial sections, at first we start with a population whose maximum age is 30
        for (int age = 0; age <= 30; age++) {
            sections.add(new Section(age, initialPopulationPerSection, BigDecimal.ZERO));
        }
        ArrayList<BigInteger> newDisciplesTracker = new ArrayList<>();
        // Add initial 13 disciples at age 30
        sections.get(30).addDisciples(Constants.INITIAL_DISCIPLES);

        double maxRatio = 0.0;
        int yearWithMaxRatio = 0;

        BigDecimal theologicalKnowledge = new BigDecimal("1");

        int years = 0;
        while (true) {
            years = years + 1;

            // Kill anyone who reaches a certain age, which in the assignment is 72
            sections.deleteSectionByAge(Constants.DEATH_AGE);

            // Increase age of everyone by 1
            for (Section section : sections) {
                section.age = section.age + 1;
            }

            // Create new babies
            BigDecimal couplesWhoCanReproduce = sections.get(30).population.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
            BigDecimal newBabiesPopulation = couplesWhoCanReproduce.multiply(Constants.BABIES_PER_COUPLE);
            Section babySection = new Section(0, newBabiesPopulation, BigDecimal.ZERO);
            sections.add(0, babySection);

            BigDecimal disciplePopulation = sections.getTotalDisciples();
            BigDecimal newDisciples = disciplePopulation.multiply(Constants.NEW_DISCIPLES_PER_DISCIPLES).divide(Constants.YEARS_TO_TRAIN, RoundingMode.HALF_UP);

            System.out.println("knowledge:" + theologicalKnowledge);
            System.out.println("before conversion:" + newDisciples);

            // More theological knowledge means more disciples can be trained
            newDisciples = newDisciples.multiply(theologicalKnowledge);
            System.out.println("after conversion:" + newDisciples);

            BigDecimal totalPopulation = sections.getTotalPopulation();

            // count all sections that are 18 and older
            int eligibleSectionsCount = 0;
            for (Section section : sections) {
                if (section.age >= Constants.MIN_AGE_FOR_DISCIPLE_TRAINING) {
                    eligibleSectionsCount++;
                }
            }



            BigDecimal newDisciplesPerSection = newDisciples.divide(BigDecimal.valueOf(eligibleSectionsCount), 30, RoundingMode.HALF_UP);
            // System.out.println("Disciples per section:" + newDisciplesPerSection);

            // Add new disciples to eligible sections starting from age 18
            BigDecimal excess = BigDecimal.ZERO; // some age sections are full of disciples, so any extra is carried over to the next section
            for (Section section : sections) {
                if (section.age >= Constants.MIN_AGE_FOR_DISCIPLE_TRAINING) {
                    excess = section.addDisciples(newDisciplesPerSection.add(excess));
                }
            }


            double ratio = sections.getTotalDisciples().divide(sections.getTotalPopulation(), 4, RoundingMode.HALF_UP).doubleValue();
            if (ratio > maxRatio)
            {
                maxRatio = ratio;
                yearWithMaxRatio = years;
            }
            if (years % 5 == 0)
            {
                System.out.println("\nSTATUS AT YEAR " + years);
                System.out.println("Population: " + sections.getTotalPopulation().setScale(0, RoundingMode.HALF_UP));
                System.out.println("New disciples this year: " + newDisciples.subtract(excess).setScale(0, RoundingMode.HALF_UP));
                System.out.println("Total disciples this year:  " + sections.getTotalDisciples().setScale(0, RoundingMode.HALF_UP));
                System.out.println("Percentage of disciples: " + ratio*100 + "%");
                System.out.println("Excess (i.e., those who died): " + excess.setScale(0, RoundingMode.HALF_UP));
                System.out.println("Theological knowledge: " + theologicalKnowledge);
                System.out.println("______________________________________________________");
            }

            newDisciplesTracker.add(newDisciples.toBigInteger());

            theologicalKnowledge = theologicalKnowledge.add(Constants.yearlyTheologicalKnowledgeIncrease);


            if (ratio > 0.99 || years == Constants.MAX_YEARS) {
                break;
            }
        }
        System.out.println("\n\nDisciple percentage peaked " + yearWithMaxRatio + " years after the 13 initial disciples began training");
        System.out.println("The percentage of disciples for that year was " + maxRatio*100 + "%");

        System.out.println(newDisciplesTracker);
    }
}