import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomHashMapTest {
    @Test
    public void testBasicOperations() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        System.out.println("Начальное состояние: " + map);
        map.put("first", 10);
        map.put("second", 20);
        map.put("third", 30);
        System.out.println("после добавления: " + map);

        map.put("second", 50);
        System.out.println("после добавления существующего ключа: " + map);

        map.remove("first");
        map.remove("third");
        System.out.println("Удаление элементов под ключами: first, third " + map);
        map.remove("second");

    }

    @Test
    public void testResize(){
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        for(int i = 0;i < 100;i++){
            map.put("k" + i, i);
        }
        System.out.println(map);
    }
}