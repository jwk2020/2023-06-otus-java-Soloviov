package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return clone(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return clone(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> clone(Map.Entry<Customer, String> e) {
        return e == null ? null : Map.entry(
                new Customer(e.getKey().getId(), e.getKey().getName(), e.getKey().getScores()), e.getValue());
    }
}
