package fr.test;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class ConfigParamsTest {
	private static final long serialVersionUID = 1L;
	ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String, String>();
	Properties properties = new Properties();

	public void populate() {
		map.put("Network_Params_host", "localhost");
		map.put("Network_Params_port", "1234");
		map.put("Network_Params_socket_timeout", "1000");
		map.put("JDBC_URL", "jdbc:derby:phonebook");
		map.put("JDBC_poolsize", "10");
		map.put("Thread_Params_poolsize", "10");
		properties.put("Network_Params_host", "localhost");
		properties.put("Network_Params_port", "1234");
		properties.put("Network_Params_socket_timeout", "1000");
		properties.put("JDBC_URL", "jdbc:derby:phonebook");
		properties.put("JDBC_poolsize", "10");
		properties.put("Thread_Params_poolsize", "10");
	}

	// return a map starting with param type
	Map<String, String> lookupProperties(String paramType) {
		Map<String, String> result = new HashMap<String, String>();
		String key = null;
		for (Object obj : properties.keySet()) {
			key = (String) obj;
			if (key.startsWith(paramType)) {
				result.put(key, properties.getProperty(key));
			}
		}
		return result;
	}

	// return a map starting with param type
	Map<String, String> lookupMap(String paramType) {
		return (map.subMap(paramType,
				new String("" + (char) (paramType.charAt(0) + 1))));
	}

	public static void main(String[] args) {
		ConfigParamsTest test = new ConfigParamsTest();
		test.populate();
		System.out
				.println("Shows lookups in ConcurrentSkipListMap is faster than HashTable");
		long start, stop, elapsedTime1, elapsedTime2;
		start = System.nanoTime();
		test.lookupMap("Network_Params");
		stop = System.nanoTime();
		elapsedTime1 = stop - start;
		System.out.println("execution time in nanos->" + elapsedTime1);
		start = System.nanoTime();
		test.lookupProperties("Network_Params");
		stop = System.nanoTime();
		elapsedTime2 = stop - start;
		System.out.println("execution time in nanos->" + elapsedTime2);
		System.out.println("ConcurrentSkipListMap is " + (double) elapsedTime2
				/ (double) elapsedTime1 + " times faster than HashTable");
	}
}