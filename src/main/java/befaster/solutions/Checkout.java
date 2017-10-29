package befaster.solutions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
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
		put("F", howMany -> (howMany / 3) * 20 + (howMany % 3) * 10);	
		put("G", howMany -> howMany * 20);
		put("H", howMany -> {
			int total = (howMany / 10) * 80;
			howMany %= 10;
			total += (howMany/ 5) * 45 + (howMany % 5) * 10;
			return total;
		}); 
		put("I", howMany -> howMany * 35);
		put("J", howMany -> howMany * 60);
		put("K", howMany -> (howMany / 2) * 120 + (howMany % 2) * 70);
		put("L", howMany -> howMany * 90);
		put("M", howMany -> howMany * 15);
		put("N", howMany -> howMany * 40);
		put("O", howMany -> howMany * 10);
		put("P", howMany -> (howMany / 5) * 200 + (howMany % 5) * 50);
		put("Q", howMany -> (howMany / 3) * 80 + (howMany % 3) * 30);
		put("R", howMany -> howMany * 50);
		put("S", howMany -> howMany * 20);
		put("T", howMany -> howMany * 20);
		put("U", howMany -> (howMany / 4) * 120 + (howMany % 4) * 40);
		put("V", howMany -> {
			int total = (howMany / 3) * 130;
			howMany %= 3;
			total += (howMany/ 2) * 90 + (howMany % 2) * 50;
			return total;
		}); 
		put("W", howMany -> howMany * 20);
		put("X", howMany -> howMany * 17);
		put("Y", howMany -> howMany * 20);
		put("Z", howMany -> howMany * 21);
	}};
	
	
		
	public static int checkout(String skus) {
		if(Objects.isNull(skus))
			return -1;
		if(skus.isEmpty())
			return 0;
		final Map<String, Long> groupedOrder = groupOrder(skus);
		applyFreeItems(groupedOrder, "E", "B", 2);
		applyFreeItems(groupedOrder, "R", "Q", 3);
		applyFreeItems(groupedOrder, "N", "M", 3);
		anyOfFor(groupedOrder, 3, 45, "S", "T", "X", "Y", "Z");
		try
		{
			return calculatePrice(groupedOrder);
		}
		catch(RuntimeException e)
		{
			return -1;
		}
	}



	private static void anyOfFor(Map<String, Long> groupedOrder, int groupSize, int groupPrice, String... skus) {
		final List<String> orderedSkus = Arrays.asList(skus);
		orderedSkus.sort((sku1, sku2) -> prices.get(sku2).calculatePrice(1) - prices.get(sku1).calculatePrice(1));
		final long itemInGroup = orderedSkus.stream()
			.mapToLong(sku -> groupedOrder.getOrDefault(sku, 0L))
			.sum();
		final String key = String.join(",", skus);
		prices.put(key, c -> c * groupPrice);
		groupedOrder.put(key, itemInGroup / groupSize);
		
		AtomicLong runningTotal = new AtomicLong((itemInGroup / groupSize) * groupSize);
		for(String sku : orderedSkus)
		{
			groupedOrder.computeIfPresent(sku, (k, v) -> {
				if(runningTotal.get() <= 0)
					return v;
				if(runningTotal.get() >= v)
				{
					runningTotal.accumulateAndGet(v, (long left, long right) ->	 left - right);
					return 0L;
				}
				final Long l = v - runningTotal.get();
				runningTotal.set(0);
				return l;
			});

		}
	}



	private static int calculatePrice(final Map<String, Long> groupedOrder) {
		return groupedOrder
				.entrySet().stream()
				.mapToInt(entry -> prices.getOrDefault(entry.getKey(), c -> {throw new RuntimeException();}).calculatePrice(entry.getValue().intValue()))
				.sum();
	}



	private static void applyFreeItems(final Map<String, Long> groupedOrder, String k1, String k2, int howMany) {
		if(groupedOrder.containsKey(k1))
		{
			final long freeItemsCount = groupedOrder.getOrDefault(k1, 0L) / howMany;
			groupedOrder.computeIfPresent(k2, (k,v) -> Math.max(0, v - freeItemsCount));
		}
	}



	private static Map<String, Long> groupOrder(String skus) {
		return Stream.of(skus.split(""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}


	
}