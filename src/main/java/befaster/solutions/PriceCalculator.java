package befaster.solutions;

@FunctionalInterface
public interface PriceCalculator {
	int calculatePrice(int howMany);
}