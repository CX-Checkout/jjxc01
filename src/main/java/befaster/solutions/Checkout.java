package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checkout {
	
	private final static Map<String, PriceCalculator> prices = new HashMap<String, PriceCalculator>() {{
		put("A", howMany -> {
			int total = (howMany / 5) * 200;
			howMany %= 5;
			total += (howMany/ 3) * 130 + (howMany % 3) * 50;
			return total;
		}); 
		put("B", howMany -> (howMany / 2) * 45 + (howMany % 2) * 30);
		put("C", howMany -> howMany * 20);
		put("D", howMany -> howMany * 15);
		put("E", howMany -> howMany * 40);
	}};
	
	
		
	public static int checkout(String skus) {
		if(Objects.isNull(skus))
			return -1;
		if(skus.isEmpty())
			return 0;
		final Map<String, Long> groupedOrder = groupOrder(skus);
		applyFreeItems(groupedOrder);
		try
		{
			return calculatePrice(groupedOrder);
		}
		catch(RuntimeException e)
		{
			return -1;
		}
	}



	private static int calculatePrice(final Map<String, Long> groupedOrder) {
		return groupedOrder
				.entrySet().stream()
				.mapToInt(entry -> prices.getOrDefault(entry.getKey(), c -> {throw new RuntimeException();}).calculatePrice(entry.getValue().intValue()))
				.sum();
	}



	private static void applyFreeItems(final Map<String, Long> groupedOrder) {
		if(groupedOrder.containsKey("B"))
		{
			final long es = groupedOrder.getOrDefault("E", 0L) / 2;
			groupedOrder.computeIfPresent("B", (k,v) -> Math.max(0, v - es));
		}
	}



	private static Map<String, Long> groupOrder(String skus) {
		return Stream.of(skus.split(""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}


	
}