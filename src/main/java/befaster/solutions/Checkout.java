package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checkout {
	
	private final static Map<String, PriceCalculator> prices = new HashMap<String, PriceCalculator>() {{
		put("A", howMany -> (howMany / 3) * 130 + (howMany % 3) * 50);
		put("B", howMany -> (howMany / 2) * 45 + (howMany % 2) * 30);
		put("C", howMany -> howMany * 20);
		put("D", howMany -> howMany * 15);
	}};
	
	
		
	public static int checkout(String skus) {
		if(Objects.isNull(skus))
			return -1;
		if(skus.isEmpty())
			return 0;
		try
		{
			return Stream.of(skus.split(""))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
					.entrySet().stream()
					.mapToInt(entry -> prices.getOrDefault(entry.getKey(), c -> {throw new RuntimeException();}).calculatePrice(entry.getValue().intValue()))
					.sum();
		}
		catch(RuntimeException e)
		{
			return -1;
		}
	}


	
}